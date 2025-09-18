package br.com.investyou.Models.Portfolio;


import br.com.investyou.Models.Investment.Investment;
import br.com.investyou.Models.User.User;
import jakarta.persistence.*;

import lombok.*;

import java.util.List;

@Entity
@Table(name = "portfolios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Portfolio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Investment> investments;

}
