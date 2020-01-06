package cn.junengxiong.bean;

import java.util.Set;

public class User {
    
    private String username;
    
    private String password;
    
    private Set<String> role;
    
    private Set<String> permission;
    
    private String phone;
    
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

    public Set<String> getPermission() {
        return permission;
    }

    public void setPermission(Set<String> permission) {
        this.permission = permission;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User [username=" + username + ", password=" + password + ", role=" + role + ", permission=" + permission
                + ", phone=" + phone + ", email=" + email + ", getUsername()=" + getUsername() + ", getPassword()="
                + getPassword() + ", getRole()=" + getRole() + ", getPermission()=" + getPermission() + ", getPhone()="
                + getPhone() + ", getEmail()=" + getEmail() + ", getClass()=" + getClass() + ", hashCode()="
                + hashCode() + ", toString()=" + super.toString() + "]";
    }

}
