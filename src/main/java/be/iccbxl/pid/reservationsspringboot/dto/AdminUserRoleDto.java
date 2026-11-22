package be.iccbxl.pid.reservationsspringboot.dto;

import java.util.LinkedHashSet;
import java.util.Set;

import be.iccbxl.pid.reservationsspringboot.model.UserRole;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AdminUserRoleDto{

  @NotEmpty(message="Veuillez sélectionner au moins un rôle")
  private Set<UserRole> roles = new LinkedHashSet<>();
}
