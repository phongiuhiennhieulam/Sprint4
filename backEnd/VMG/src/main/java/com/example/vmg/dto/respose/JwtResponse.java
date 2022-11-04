package com.example.vmg.dto.respose;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String userName;
    private Collection<? extends GrantedAuthority> roles;

    public JwtResponse(String token, Long id, String userName, String email, Collection<? extends GrantedAuthority> roles) {
        this.token = token;
        this.id = id;
        this.userName = userName;
        this.roles = roles;
    }

    public JwtResponse(String token, String userName, Collection<? extends GrantedAuthority> authorities) {
    this.token = token;
    this.userName = userName;
    this.roles = authorities;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Collection<? extends GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(Collection<? extends GrantedAuthority> roles) {
        this.roles = roles;
    }
}
