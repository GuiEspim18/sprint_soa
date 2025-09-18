package br.com.investyou.Models.Experience;

import br.com.investyou.Models.Goal.Goal;
import br.com.investyou.Models.User.User;
import jakarta.persistence.*;

import lombok.*;

import java.util.List;

@Entity
@Table(name = "experiences")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Experience {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // owner user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String period;
    private String reaction;
    private Double value;
    private Double revenue;

    // goals linked to this experience
    @OneToMany(mappedBy = "experience", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Goal> goals;
}
