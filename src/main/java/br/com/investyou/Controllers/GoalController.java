package br.com.investyou.Controllers;

import br.com.investyou.Models.Experience.Experience;
import br.com.investyou.Models.Experience.ExperienceRepository;
import br.com.investyou.Models.Goal.Goal;
import br.com.investyou.Models.Goal.GoalRepository;
import br.com.investyou.Models.Goal.dto.GoalCreateDto;
import br.com.investyou.Models.Goal.dto.GoalListDto;
import br.com.investyou.Models.Goal.dto.GoalUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @PostMapping
    public ResponseEntity<GoalListDto> create(@RequestBody GoalCreateDto dto) {
        Experience experience = experienceRepository.findById(dto.experienceId())
                .orElseThrow(() -> new RuntimeException("Experience not found"));

        Goal goal = new Goal();
        goal.setExperience(experience);
        goal.setGoal(dto.goal());

        Goal saved = goalRepository.save(goal);

        GoalListDto response = new GoalListDto(
                saved.getId(),
                saved.getExperience().getId(),
                saved.getGoal()
        );

        return ResponseEntity.created(URI.create("/api/goals/" + saved.getId())).body(response);
    }

    @GetMapping
    public List<GoalListDto> findAll() {
        return goalRepository.findAll().stream()
                .map(g -> new GoalListDto(
                        g.getId(),
                        g.getExperience().getId(),
                        g.getGoal()
                ))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalListDto> findById(@PathVariable Long id) {
        return goalRepository.findById(id)
                .map(g -> new GoalListDto(
                        g.getId(),
                        g.getExperience().getId(),
                        g.getGoal()
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoalListDto> update(@PathVariable Long id, @RequestBody GoalUpdateDto dto) {
        return goalRepository.findById(id)
                .map(g -> {
                    g.setGoal(dto.goal());
                    Goal updated = goalRepository.save(g);
                    return ResponseEntity.ok(new GoalListDto(
                            updated.getId(),
                            updated.getExperience().getId(),
                            updated.getGoal()
                    ));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return goalRepository.findById(id)
                .map(g -> {
                    goalRepository.delete(g);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
