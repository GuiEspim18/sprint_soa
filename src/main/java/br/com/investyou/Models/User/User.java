package br.com.investyou.Models.User;


import br.com.investyou.Models.Experience.Experience;
import br.com.investyou.Models.Portfolio.Portfolio;
import jakarta.persistence.*;

import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(unique = true)
    private String email;
    private String password;

    // One user -> many experiences
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experiences;

    // One user -> one portfolio (assumption: each user has one portfolio)
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Portfolio portfolio;

}
