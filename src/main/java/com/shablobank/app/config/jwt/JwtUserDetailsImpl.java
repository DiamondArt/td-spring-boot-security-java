package com.shablobank.app.config.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shablobank.app.models.Role;
import com.shablobank.app.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


public class JwtUserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String email;
    @JsonIgnore
    private String password;
    private Role role = new Role();
    private HashMap device = new HashMap<>();

    public JwtUserDetailsImpl(Long id, String email, String password, Role role, HashMap device) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.device = device;
    }

    public static JwtUserDetailsImpl build(User user, HttpServletRequest request) {

        HashMap device = new HashMap<>();
        device.put("userAgent", request.getHeader("user-agent"));
        device.put("remoteAddress", request.getRemoteAddr());
        return new JwtUserDetailsImpl(user.getId(), user.getEmail(), user.getPassword(), user.getRole(), device);
    }

    public Claims getClaims() {
        Claims claims = new DefaultClaims();
        claims.setId(UUID.randomUUID().toString());
        claims.setIssuer("SHABLOBANK");
        claims.put("email", this.getUsername());
        claims.put("role", this.getAuthorities());
        claims.put("device", this.getDevice());
        return claims;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(this.getRole().getAuthority()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || !(getClass() == o.getClass()))
            return false;
        JwtUserDetailsImpl user = (JwtUserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public HashMap getDevice() {
        return device;
    }

    public void setDevice(HashMap device) {
        this.device = device;
    }
}








