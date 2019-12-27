package com.exercise.security.dto;

import com.exercise.security.entity.Role;
import lombok.*;

import javax.validation.constraints.Size;
import java.util.List;

import static com.exercise.security.ErrorMessageUsersConstant.PASSWORD_LENGTH;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserRequestDto {

    private String username;

    @Size(min = 8, message = PASSWORD_LENGTH)
    private String password;

    private List<Role> authorities;
}
