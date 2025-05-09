package vn.graybee.models.users;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private final UserPrincipalDto user;

    public UserPrincipal(UserPrincipalDto user) {
        this.user = user;
    }

    public UserPrincipalDto getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (user.getRoleName() != null && !user.getRoleName().isEmpty()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRoleName().toUpperCase()));
        }

        if (user.getPermissions() != null && !user.getPermissions().isEmpty()) {
            user.getPermissions().forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.toUpperCase())));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isUserActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isUserActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isUserActive();
    }

    @Override
    public boolean isEnabled() {
        return user.isUserActive();
    }

}
