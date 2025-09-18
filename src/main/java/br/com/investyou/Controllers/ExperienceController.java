package br.com.investyou.Controllers;

import br.com.investyou.Models.Experience.Experience;
import br.com.investyou.Models.Experience.ExperienceRepository;
import br.com.investyou.Models.Experience.dto.ExperienceCreateDto;
import br.com.investyou.Models.Experience.dto.ExperienceListDto;
import br.com.investyou.Models.Experience.dto.ExperienceUpdateDto;
import br.com.investyou.Models.User.User;
import br.com.investyou.Models.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/experiences")
public class ExperienceController {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private UserRepository userRepository;

    public ExperienceController(ExperienceRepository experienceRepository, UserRepository userRepository) {
        this.experienceRepository = experienceRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<ExperienceListDto> create(@RequestBody ExperienceCreateDto dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Experience experience = new Experience();
        experience.setUser(user);
        experience.setPeriod(dto.period());
        experience.setReaction(dto.reaction());
        experience.setValue(dto.value());
        experience.setRevenue(dto.revenue());

        Experience saved = experienceRepository.save(experience);

        ExperienceListDto response = new ExperienceListDto(
                saved.getId(),
                saved.getUser().getId(),
                saved.getPeriod(),
                saved.getReaction(),
                saved.getValue(),
                saved.getRevenue()
        );

        return ResponseEntity.created(URI.create("/api/experiences/" + saved.getId())).body(response);
    }

    @GetMapping
    public List<ExperienceListDto> findAll() {
        return experienceRepository.findAll().stream()
                .map(exp -> new ExperienceListDto(
                        exp.getId(),
                        exp.getUser().getId(),
                        exp.getPeriod(),
                        exp.getReaction(),
                        exp.getValue(),
                        exp.getRevenue()
                ))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExperienceListDto> findById(@PathVariable Long id) {
        return experienceRepository.findById(id)
                .map(exp -> new ExperienceListDto(
                        exp.getId(),
                        exp.getUser().getId(),
                        exp.getPeriod(),
                        exp.getReaction(),
                        exp.getValue(),
                        exp.getRevenue()
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExperienceListDto> update(@PathVariable Long id, @RequestBody ExperienceUpdateDto dto) {
        return experienceRepository.findById(id)
                .map(exp -> {
                    exp.setPeriod(dto.period());
                    exp.setReaction(dto.reaction());
                    exp.setValue(dto.value());
                    exp.setRevenue(dto.revenue());

                    Experience updated = experienceRepository.save(exp);

                    return ResponseEntity.ok(new ExperienceListDto(
                            updated.getId(),
                            updated.getUser().getId(),
                            updated.getPeriod(),
                            updated.getReaction(),
                            updated.getValue(),
                            updated.getRevenue()
                    ));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return experienceRepository.findById(id)
                .map(exp -> {
                    experienceRepository.delete(exp);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}