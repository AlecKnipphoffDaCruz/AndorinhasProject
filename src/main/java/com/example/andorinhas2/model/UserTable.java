package com.example.andorinhas2.model;
import com.example.andorinhas2.dto.UserDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTable implements UserDetails{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long usuarioId;
    @Column (unique = true)
    private String nome;
    @Column(unique = true)
    private String email;
    private String senha;
    @Column(name = "user_img", columnDefinition = "TEXT")
    private String userImg;

    @Enumerated(EnumType.STRING)
    private Erole role;

    public UserTable(@Valid UserDto userDto) {
        this.usuarioId = userDto.id();
        this.email = userDto.email();
        this.nome = userDto.nome();
        this.role = userDto.role();
        this.senha = userDto.senha();
        this.userImg = userDto.userImg();
    }
    @Column(name = "data_admissao")
    private LocalDateTime dataAdmissao;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
