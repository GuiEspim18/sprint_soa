package br.com.investyou.Models.Investment.dto;

public record InvestmentListDto(Long id, Long portfolioId, String type, String name, String code, String description, String status) {}