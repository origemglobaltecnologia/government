package br.com.government.requisicao.dto;

import java.util.UUID;

public record RequisicaoResponseDTO(
    UUID uuid,
    String motivo,
    String descricao,
    String responsavel
) {}
