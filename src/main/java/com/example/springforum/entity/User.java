package com.example.springforum.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
public class User extends BaseEntity{
    @OneToMany(mappedBy = "author")
    public Set<Thread> threads = new HashSet<>();
    @OneToMany(mappedBy = "author")
    public Set<Comment> comments = new HashSet<>();

    public User( @NotBlank(message = "Username could not be blank") String username, @NotBlank(message = "Password must not be blank") @Size(min = 8, message = "Password must be longer than 8 characters") String password, @Email(message = "Email must be valid") String email,String role) {
        this.role = role;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Getter @Setter
    public String role;

    @Setter @Getter
    @NotBlank(message = "Username could not be blank")
    @Column(unique = true)
    public String username;
    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, message = "Password must be longer than 8 characters")
    @Setter @Getter
    public String password;

    @Email(message = "Email must be valid")
    @Column(unique = true)
    @Setter @Getter
    public String email;

    public User(@NotBlank(message = "Username could not be blank") String username, @NotBlank @Size(min = 8) String password, @Email String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User() {
    }
    public Collection<GrantedAuthority> getAuthorities() {
        //make everyone ROLE_USER
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        //anonymous inner type
        GrantedAuthority grantedAuthority = (GrantedAuthority) () -> "ROLE_" + role;
        grantedAuthorities.add(grantedAuthority);
        return grantedAuthorities;
    }

    @Override
    public String toString() {
        return "User{" +
                "threads=" + threads +
                ", comments=" + comments +
                ", role='" + role + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                '}';
    }
}
