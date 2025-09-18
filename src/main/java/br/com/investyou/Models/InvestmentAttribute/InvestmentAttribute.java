package br.com.investyou.Models.InvestmentAttribute;

import br.com.investyou.Models.Investment.Investment;
import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "investment_attributes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class InvestmentAttribute {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String value;

    @ManyToOne
    @JoinColumn(name = "investment_id")
    private Investment investment;

}
