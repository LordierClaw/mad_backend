package org.example.mobile.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
public class SignUpRequest {
    @NotEmpty(message = "Tên không được để trống")
    @Size(max = 250, message = "Tên người dùng phải có độ dài nhỏ hơn 250 kí tự")
    private String firstName;

    @NotEmpty(message = "Họ và tên đệm không được để trống")
    @Size(max = 250, message = "Họ và tên đệm phải có độ dài nhỏ hơn 250 kí tự")
    private String lastName;

    @NotEmpty(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotEmpty(message = "Số điện thoại không được để trống")
    @Size(max = 15, message = "Số điện thoại phải có độ dài nhỏ hơn 15 số")
    @Digits(message = "Số điện thoại không hợp lệ", integer = 15, fraction = 0)
    private String phoneNumber;

    @NotEmpty(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "Mật khẩu phải có độ dài ít nhất 8 kí tự")
    private String password;
}
