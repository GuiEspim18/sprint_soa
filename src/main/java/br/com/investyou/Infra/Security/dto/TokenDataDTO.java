package br.com.investyou.Infra.Security.dto;

public record TokenDataDTO(
        Long id,
        String email,
        String name,
        boolean admin
) {
}
