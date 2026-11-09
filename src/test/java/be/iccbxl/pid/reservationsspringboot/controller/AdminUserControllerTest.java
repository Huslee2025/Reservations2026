package be.iccbxl.pid.reservationsspringboot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    assertEquals(
      UserRole.MEMBER,
      userRepository.findByIdAndDeletedAtIsNull(3L).getRole()
    );

    //Simuler la modification du rôle
    mockMvc.perform(
      MockMvcRequestBuilders.post("/admin/users/3/edit-role")
      .param("role","PRODUCER")
      .with(SecurityMockMvcRequestPostProcessors.csrf())
    )
      .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
      .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/users"));

    //Vérifier que le rôle a changé
    assertEquals(
      UserRole.PRODUCER,
      userRepository.findByIdAndDeletedAtIsNull(3L).getRole()
    );

  }

}
