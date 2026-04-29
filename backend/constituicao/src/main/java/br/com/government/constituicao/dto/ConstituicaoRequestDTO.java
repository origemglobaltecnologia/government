package br.com.government.constituicao.dto;

public record ConstituicaoRequestDTO(
    String identificador,
    String titulo,
    String ementa
) {}
