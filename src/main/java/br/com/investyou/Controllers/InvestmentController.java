package br.com.investyou.Controllers;

import br.com.investyou.Models.Investment.Investment;
import br.com.investyou.Models.Investment.InvestmentRepository;
import br.com.investyou.Models.Investment.dto.InvestmentCreateDto;
import br.com.investyou.Models.Investment.dto.InvestmentListDto;
import br.com.investyou.Models.Investment.dto.InvestmentUpdateDto;
import br.com.investyou.Models.InvestmentAttribute.InvestmentAttribute;
import br.com.investyou.Models.Portfolio.Portfolio;
import br.com.investyou.Models.Portfolio.PortfolioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/investments")
public class InvestmentController {
    private final InvestmentRepository investmentRepository;
    private final PortfolioRepository portfolioRepository;

    public InvestmentController(InvestmentRepository investmentRepository, PortfolioRepository portfolioRepository) {
        this.investmentRepository = investmentRepository;
        this.portfolioRepository = portfolioRepository;
    }

    @PostMapping
    public ResponseEntity<InvestmentListDto> create(@RequestBody InvestmentCreateDto dto) {
        var pfOpt = portfolioRepository.findById(dto.portfolioId());
        if (pfOpt.isEmpty()) return ResponseEntity.badRequest().build();

        Portfolio p = pfOpt.get();
        Investment inv = new Investment();
        inv.setPortfolio(p);
        inv.setType(dto.type());
        inv.setName(dto.name());
        inv.setCode(dto.code());
        inv.setDescription(dto.description());
        inv.setStatus(dto.status());

        if (dto.attributes() != null) {
            var attrs = dto.attributes().stream().map(a -> {
                InvestmentAttribute ia = new InvestmentAttribute();
                ia.setType(a.type());
                ia.setValue(a.value());
                ia.setInvestment(inv);
                return ia;
            }).collect(Collectors.toList());
            inv.setAttributes(attrs);
        }

        investmentRepository.save(inv);
        return ResponseEntity.created(URI.create("/api/investments/" + inv.getId()))
                .body(new InvestmentListDto(inv.getId(), p.getId(), inv.getType(), inv.getName(), inv.getCode(), inv.getDescription(), inv.getStatus()));
    }

    @GetMapping
    public List<InvestmentListDto> list() {
        return investmentRepository.findAll().stream()
                .map(i -> new InvestmentListDto(i.getId(), i.getPortfolio() != null ? i.getPortfolio().getId() : null, i.getType(), i.getName(), i.getCode(), i.getDescription(), i.getStatus()))
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<InvestmentListDto> get(@PathVariable Long id) {
        return investmentRepository.findById(id)
                .map(i -> ResponseEntity.ok(new InvestmentListDto(i.getId(), i.getPortfolio()!=null?i.getPortfolio().getId():null, i.getType(), i.getName(), i.getCode(), i.getDescription(), i.getStatus())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<InvestmentListDto> update(@PathVariable Long id, @RequestBody InvestmentUpdateDto dto) {
        return investmentRepository.findById(id).map(i -> {
            i.setType(dto.type());
            i.setName(dto.name());
            i.setCode(dto.code());
            i.setDescription(dto.description());
            i.setStatus(dto.status());
            investmentRepository.save(i);
            return ResponseEntity.ok(new InvestmentListDto(i.getId(), i.getPortfolio()!=null?i.getPortfolio().getId():null, i.getType(), i.getName(), i.getCode(), i.getDescription(), i.getStatus()));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!investmentRepository.existsById(id)) return ResponseEntity.notFound().build();
        investmentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
