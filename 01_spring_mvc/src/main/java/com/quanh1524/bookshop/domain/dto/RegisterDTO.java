package com.quanh1524.bookshop.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class RegisterDTO {
    @NotEmpty(message = "Họ và tên không được để trống")
    private String fullName;

    @NotEmpty(message = "Email không hợp lệ")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotEmpty(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu có ít nhất 6 ký tự")
    private String password;

    @NotEmpty(message = "Vui lòng xác nhận lại mật khẩu")
    private String confirmPassword;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isPasswordMatching() {
        return this.password != null && this.password.equals(this.confirmPassword);
    }
}
