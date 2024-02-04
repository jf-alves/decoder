package io.github.jfalves.authuser.enums;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.jfalves.authuser.validation.UsernameConstraint;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    public interface UserView {
        interface RegistrationPost {
        }
        interface UserPut {
        }
        interface PasswordPut {
        }
        interface ImagePut {
        }
    }

    private UUID userId;

    @JsonView({UserView.RegistrationPost.class})
    @NotBlank(groups = UserView.RegistrationPost.class)
    @UsernameConstraint(groups = {UserView.RegistrationPost.class})
    @Size(min = 4, max = 50, groups = UserView.RegistrationPost.class)
    private String username;

    @JsonView({UserView.RegistrationPost.class})
    @Email(groups = {UserView.RegistrationPost.class})

    @NotBlank(groups = UserView.RegistrationPost.class)
    private String email;

    @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
    @NotBlank(groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
    @Size(min = 6, max = 20, groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
    private String password;

    @JsonView({UserView.PasswordPut.class})
    @NotBlank(groups = {UserView.PasswordPut.class})
    @Size(min = 6, max = 20, groups = {UserView.PasswordPut.class})
    private String oldPassword;

    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String fullName;

    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String phoneNumber;

    @Size(min = 11)
    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String cpf;

    @NotBlank
    @JsonView({UserView.ImagePut.class})
    private String imageUrl;
}
