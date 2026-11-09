package be.iccbxl.pid.reservationsspringboot.dto;

import be.iccbxl.pid.reservationsspringboot.model.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminUserRoleDto{

  @NotNull(message="Veuillez sélectionner un rôle")
  private UserRole role;
}
