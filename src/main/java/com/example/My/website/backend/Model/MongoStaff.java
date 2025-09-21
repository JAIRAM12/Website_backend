package com.example.My.website.backend.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Document(collection = "Staff database")
@Getter
@Setter
public class MongoStaff implements UserDetails {

    @Id
    private String id;
    private String name;
    private String department;
    private String email;
    private String phone;
    private List<String> education;
    private String staffId;
    private Binary image;
    @JsonIgnore
    private String password;
    private List<String> skills;
    private String address;
    private String position;
    private String role;

    // UserDetails interface implementation
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_STAFF"));
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password; // Use the 'pass' field as password
    }

    @Override
    public String getUsername() {
        return staffId; // Use staffId as the username for authentication
    }

    // The following methods can return default values since you might not need these features
    @Override
    public boolean isAccountNonExpired() {
        return true; // Account never expires
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Account is never locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Credentials never expire
    }

    @Override
    public boolean isEnabled() {
        return true; // Account is always enabled
    }
}