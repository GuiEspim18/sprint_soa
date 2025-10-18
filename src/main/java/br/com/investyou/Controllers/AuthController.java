package br.com.investyou.Controllers;

import br.com.investyou.Infra.Security.TokenService;
import br.com.investyou.Infra.Security.dto.TokenDataDTO;
import br.com.investyou.Models.Auth.dto.LoginDto;
import br.com.investyou.Models.Auth.dto.LoginResponseDto;
import br.com.investyou.Models.User.User;
import br.com.investyou.Models.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager manager;

    @PutMapping
    public ResponseEntity<?> login(@RequestBody LoginDto login) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login.email(), login.password());
        System.out.println(token);
        Authentication authentication = manager.authenticate(token);
        String tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());
        User user = userRepository.findByEmail(login.email());
        String message = "Olá, " + user.getName() + " !";
        return ResponseEntity.ok(new LoginResponseDto(message, tokenJWT));
    }


}
