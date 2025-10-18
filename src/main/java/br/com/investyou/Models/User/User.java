package br.com.investyou.Models.User;


import br.com.investyou.Models.Experience.Experience;
import br.com.investyou.Models.Portfolio.Portfolio;
import jakarta.persistence.*;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    // One user -> many experiences
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experiences;

    // One user -> one portfolio
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Portfolio portfolio;

    // 🔐 --- Métodos do UserDetails ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Se ainda não tiver roles, retorne uma role padrão:
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        // O campo usado para autenticação — geralmente o e-mail
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // pode ajustar depois se quiser bloquear contas antigas
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // idem para bloqueios
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // true = senha nunca expira
    }

    @Override
    public boolean isEnabled() {
        return true; // false se quiser desativar o usuário
    }
}