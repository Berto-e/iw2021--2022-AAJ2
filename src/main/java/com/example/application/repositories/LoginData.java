package com.example.application.repositories;

import com.example.application.classes.Persona;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.GrantedAuthority;
import java.util.Collection;

public class LoginData implements UserDetails {
    private Persona persona;

    public LoginData(Persona persona){
        this.persona = persona;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return persona.getPassword();
    }

    @Override
    public String getUsername() {
        return persona.getusername();
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
        return persona.isEnabled();
    }


    public int role() {
        return persona.getClase();
    }
}
