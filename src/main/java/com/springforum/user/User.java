package com.springforum.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springforum.generic.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity implements UserDetails {
    private static final long serialVersionUID = 8036152365529195346L;
    private static User empty = new User("", "", "", "", List.of(ROLE.NOTEXISTS));
    @NaturalId
    @Column(unique = true)
    @NotNull
    private String username;
    @JsonIgnore
    @NotNull
    private String password;
    @Column(unique = true)
    @JsonIgnore
    private String email;
    private String avatar;
    @NotNull
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<ROLE> role = new ArrayList<>();

    public static User empty() {
        return empty;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        String[] objects = role.stream().map(role -> "ROLE_" + role)
                .collect(Collectors.toList())
                .toArray(new String[0]);
        return
                AuthorityUtils.createAuthorityList(objects);
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isEmpty() {
        return this.equals(empty());
    }

    public enum ROLE {
        USER, BANNED, ADMIN, NOTEXISTS
    }
}
