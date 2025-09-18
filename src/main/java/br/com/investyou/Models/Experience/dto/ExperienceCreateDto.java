package br.com.investyou.Models.Experience.dto;

public record ExperienceCreateDto(Long userId, String period, String reaction, Double value, Double revenue) {}