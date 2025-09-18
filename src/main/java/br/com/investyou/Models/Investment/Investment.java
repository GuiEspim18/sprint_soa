package br.com.investyou.Models.Investment;

import br.com.investyou.Models.InvestmentAttribute.InvestmentAttribute;
import br.com.investyou.Models.Portfolio.Portfolio;
import jakarta.persistence.*;

import lombok.*;

import java.util.List;

@Entity
@Table(name = "investments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Investment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String name;
    private String code;
    @Column(length = 2000)
    private String description;
    private String status;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @OneToMany(mappedBy = "investment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvestmentAttribute> attributes;

}
