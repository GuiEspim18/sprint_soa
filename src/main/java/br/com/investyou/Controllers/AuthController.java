package br.com.investyou.Controllers;

import br.com.investyou.Infra.Security.TokenService;
import br.com.investyou.Infra.Security.dto.TokenDataDTO;
import br.com.investyou.Models.Auth.dto.LoginDto;
import br.com.investyou.Models.User.User;
import br.com.investyou.Models.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PutMapping
    public ResponseEntity<?> login(@RequestBody LoginDto login) {
        User user = userRepository.findByEmail(login.email()).orElse(null);
        if (user != null) {
            if (user.comparePassword(login.password())) {
                TokenDataDTO tokenData = new TokenDataDTO(user.getId(), user.getEmail(), user.getName(), true);
                String token = tokenService.generate(tokenData);
                String message = "Olá " + user.getName() + "!";
                return ResponseEntity.ok(message);
            }
        }
        return ResponseEntity.status(404).body("Usuário não existe");
    }


}
