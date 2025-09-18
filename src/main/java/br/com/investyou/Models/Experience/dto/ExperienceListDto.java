package br.com.investyou.Models.Experience.dto;

public record ExperienceListDto(Long id, Long userId, String period, String reaction, Double value, Double revenue) {}
