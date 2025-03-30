package org.example.mobile.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SignInRequest {
    @NotEmpty(message = "Email/SĐT không được để trống")
    private String login;
    @NotEmpty(message = "Mật khẩu không được để trống")
    private String password;
}
