package com.tecsup.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class SignupRequest {

    @NotBlank(message = "El username es obligatorio")
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank(message = "El email es obligatorio")
    @Size(max = 50)
    @Email(message = "Debe ser un email válido")
    private String email;

    @NotBlank(message = "El password es obligatorio")
    @Size(min = 6, max = 40)
    private String password;

    private Set<String> roles;

    public SignupRequest() {}

    public SignupRequest(String username, String email, String password, Set<String> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
