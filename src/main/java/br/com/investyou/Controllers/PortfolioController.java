package br.com.investyou.Controllers;

import br.com.investyou.Models.Portfolio.Portfolio;
import br.com.investyou.Models.Portfolio.PortfolioRepository;
import br.com.investyou.Models.Portfolio.dto.PortfolioCreateDto;
import br.com.investyou.Models.Portfolio.dto.PortfolioListDto;
import br.com.investyou.Models.User.User;
import br.com.investyou.Models.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<PortfolioListDto> create(@RequestBody PortfolioCreateDto dto) {
        Optional<User> uOpt = userRepository.findById(dto.userId());
        if (uOpt.isEmpty()) return ResponseEntity.badRequest().build();
        // ensure user doesn't already have a portfolio (optional)
        Portfolio p = new Portfolio();
        p.setUser(uOpt.get());
        portfolioRepository.save(p);
        PortfolioListDto out = new PortfolioListDto(p.getId(), p.getUser().getId());
        return ResponseEntity.created(URI.create("/api/portfolios/" + p.getId())).body(out);
    }

    @GetMapping("{id}")
    public ResponseEntity<PortfolioListDto> get(@PathVariable Long id) {
        return portfolioRepository.findById(id)
                .map(p -> ResponseEntity.ok(new PortfolioListDto(p.getId(), p.getUser() != null ? p.getUser().getId() : null)))
                .orElse(ResponseEntity.notFound().build());
    }
}
