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
public class ResetPasswordRequest {

    @NotEmpty
    private String login;

    @NotEmpty
    private Integer otp;

    @NotEmpty
    private String password;
}
