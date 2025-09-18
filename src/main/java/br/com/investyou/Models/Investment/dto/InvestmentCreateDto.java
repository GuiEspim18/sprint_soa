package br.com.investyou.Models.Investment.dto;

import br.com.investyou.Models.InvestmentAttribute.dto.InvestmentAttributeCreateDto;

import java.util.List;

public record InvestmentCreateDto(Long portfolioId, String type, String name, String code, String description, String status, List<InvestmentAttributeCreateDto> attributes) {}
