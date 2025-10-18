package br.com.investyou.Controllers;


import br.com.investyou.Models.User.User;
import br.com.investyou.Models.User.UserRepository;
import br.com.investyou.Models.User.dto.UserCreateDto;
import br.com.investyou.Models.User.dto.UserListDto;
import br.com.investyou.Models.User.dto.UserUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<UserListDto> list() {
        return userRepository.findAll()
                .stream()
                .map(u -> new UserListDto(u.getId(), u.getName(), u.getEmail()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<UserListDto> create(@RequestBody UserCreateDto dto) {
        User u = new User();
        u.setName(dto.name());
        u.setEmail(dto.email());
        u.setPassword(passwordEncoder.encode(dto.password()));
        userRepository.save(u);
        UserListDto out = new UserListDto(u.getId(), u.getName(), u.getEmail());
        return ResponseEntity.created(URI.create("/api/users/" + u.getId())).body(out);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserListDto> get(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(u -> ResponseEntity.ok(new UserListDto(u.getId(), u.getName(), u.getEmail())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<UserListDto> update(@PathVariable Long id, @RequestBody UserUpdateDto dto) {
        return userRepository.findById(id).map(u -> {
            u.setName(dto.name());
            u.setEmail(dto.email());
            userRepository.save(u);
            return ResponseEntity.ok(new UserListDto(u.getId(), u.getName(), u.getEmail()));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!userRepository.existsById(id)) return ResponseEntity.notFound().build();
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
