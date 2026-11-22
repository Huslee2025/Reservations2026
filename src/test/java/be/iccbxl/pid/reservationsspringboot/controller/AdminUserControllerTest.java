package be.iccbxl.pid.reservationsspringboot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import be.iccbxl.pid.reservationsspringboot.model.User;
import be.iccbxl.pid.reservationsspringboot.model.UserRole;
import be.iccbxl.pid.reservationsspringboot.repository.UserRepository;
import jakarta.transaction.Transactional;


@SpringBootTest
@AutoConfigureMockMvc
class AdminUserControllerTest{

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @Test
  @WithMockUser(username = "anna",roles = "MEMBER")
  void memberCannotUpdateUserRole() throws Exception{

    mockMvc.perform(
    MockMvcRequestBuilders.post("/admin/users/3/edit-role")
      .param("role","ADMIN")
      .with(SecurityMockMvcRequestPostProcessors.csrf())
    )
      .andExpect(MockMvcResultMatchers.status().isForbidden());

  }

  @Test
  @WithMockUser(username = "bob",roles="ADMIN")
  void adminCannotUpdateUserRoleWithoutCsrf() throws Exception{

    mockMvc.perform(
     MockMvcRequestBuilders.post("/admin/users/3/edit-role")
      .param("role", "ADMIN")
    )
      .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  @WithMockUser(username = "anna",roles = "MEMBER")
  void memberCannotAccessUserList() throws Exception{

    mockMvc.perform(
      MockMvcRequestBuilders.get("/admin/users")
    )
      .andExpect(MockMvcResultMatchers.status().isForbidden());

  }

  @Test
  @WithAnonymousUser
  void anonymousCannotAccessUserList() throws Exception{

    mockMvc.perform(
      MockMvcRequestBuilders.get("/admin/users")
    )
      .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
      .andExpect(MockMvcResultMatchers.redirectedUrl("/login?loginRequired=true"));

  }


  @Test
  @WithMockUser(username = "bob",roles = "ADMIN")
  void adminCannotEditAnotherAdminRole() throws Exception{

    mockMvc.perform(
        MockMvcRequestBuilders.get("/admin/users/8/edit-role")
    )
      .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  @Transactional
  @WithMockUser(username = "bob", roles = "ADMIN")
  void adminCanUpdateMemberRole() throws Exception{

    //Vérifier le rôle initial
    assertTrue(
        userRepository.findByIdAndDeletedAtIsNull(3)
        .hasRole(UserRole.MEMBER)
    );
 
    //Simuler la modification du rôle
    mockMvc.perform(
      MockMvcRequestBuilders.post("/admin/users/3/edit-role")
      .param("roles","PRODUCER")
      .with(SecurityMockMvcRequestPostProcessors.csrf())
    )
      .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
      .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/users"));

    //Vérifier le nouveau rôle
    assertTrue(
      userRepository.findByIdAndDeletedAtIsNull(3)
       .hasRole(UserRole.PRODUCER)
    );

    //Véerifier que l'ancien rôle a été retiré
    assertFalse(
      userRepository.findByIdAndDeletedAtIsNull(3)
        .hasRole(UserRole.MEMBER)
    );

  }

  @Test
  @Transactional
  @WithMockUser(username = "bob", roles = "ADMIN")
  void adminCanAssignMultipleRoles() throws Exception{

    //Attribuer 2 rôles à l'utilisateur n°3
    mockMvc.perform(
      MockMvcRequestBuilders.post("/admin/users/3/edit-role")
        .param("roles", "MEMBER", "PRESS")
        .with(SecurityMockMvcRequestPostProcessors.csrf())
    )
      .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
      .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/users"));

    //Récupérer l'utilisateur après la modification
    User user = userRepository.findByIdAndDeletedAtIsNull(3);

    //Vérifier qu'il possède les deux rôles
    assertTrue(user.hasRole(UserRole.MEMBER));
    assertTrue(user.hasRole(UserRole.PRESS));

    //Vérifier qu'il possède exactement deux rôles
    assertEquals(2, user.getRoles().size());
  }

}
