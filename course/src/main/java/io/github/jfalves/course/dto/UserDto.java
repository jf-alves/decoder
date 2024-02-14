package io.github.jfalves.course.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.jfalves.course.enums.UserStatus;
import io.github.jfalves.course.enums.UserType;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private UUID userId;
    private String username;
    private String email;
    private String fullName;
    private UserStatus userStatus;
    private UserType userType;
    private String phoneNumber;
    private String cpf;
    private String imageUrl;
}
