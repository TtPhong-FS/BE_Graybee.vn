package vn.graybee.account.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.graybee.account.dto.response.AccountPrincipal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetail implements UserDetails {

    private final AccountPrincipal user;

    public UserDetail(AccountPrincipal user) {
        this.user = user;
    }

    public AccountPrincipal getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

        if (user.getPermissions() != null && !user.getPermissions().isEmpty()) {
            user.getPermissions().forEach(per -> authorities.add(new SimpleGrantedAuthority(per)));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUid();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isActive();
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }

}
