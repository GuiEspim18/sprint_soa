package br.com.investyou.Infra.Security;

import br.com.investyou.Infra.Security.dto.TokenDataDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.subject}")
    private String subject;

    public String generate(TokenDataDTO data) {
        try {
            return JWT.create()
                    .withIssuer(subject)
                    .withSubject(data.email())
                    .withClaim("id", data.id())
                    .withClaim("name", data.name())
                    .withClaim("admin", data.admin())
                    .withExpiresAt(expiration())
                    .sign(getAlgorithm());
        } catch (JWTCreationException e) {
            throw new RuntimeException(e);
        }
    }

    public String getSubject(String jwt) {
        try {
            return JWT.require(getAlgorithm())
                    .withIssuer(subject)
                    .build()
                    .verify(jwt)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException(e);
        }
    }

    private Algorithm getAlgorithm() {
        return  Algorithm.HMAC256(secret);
    }

    private Instant expiration() {
        return LocalDateTime.now().plusDays(30).toInstant(ZoneOffset.of("-03:00"));
    }

    public DecodedJWT decode(String token) {
        return JWT.decode(token.replace("Bearer ", ""));
    }

}
