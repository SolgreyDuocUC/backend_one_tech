package backend_one_tech.dto.user.userDTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDTO {

    private String currentPassword;

    private String newPassword;

    private String confirmNewPassword;
}
