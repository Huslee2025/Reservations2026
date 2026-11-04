package be.iccbxl.pid.reservationsspringboot.validation;

import be.iccbxl.pid.reservationsspringboot.dto.UserProfileDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ProfilePasswordMatchesValidator implements ConstraintValidator<PasswordMatches,UserProfileDto>{


  @Override
  public boolean isValid(UserProfileDto dto,ConstraintValidatorContext context){
    if(dto == null){
      return true;
    }

    String pwd = dto.getPassword();
    String confirm = dto.getConfirmPassword();

    //Aucun changement de mot de passe 
    if((pwd == null || pwd.isBlank()) && (confirm == null || confirm.isBlank())){

      return true;
    }

    //Sinon les mots de passe doivent correspondre
    return pwd != null && pwd.equals(confirm);
  }

}


