package br.com.government.constituicao.dto;

import java.util.UUID;

public record ConstituicaoResponseDTO(
    UUID uuid,
    String identificador,
    String titulo,
    String ementa
) {}
