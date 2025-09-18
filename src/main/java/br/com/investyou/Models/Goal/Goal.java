package br.com.investyou.Models.Goal;


import br.com.investyou.Models.Experience.Experience;
import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "goals")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Goal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String goal;

    @ManyToOne
    @JoinColumn(name = "experience_id")
    private Experience experience;

}
