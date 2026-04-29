package br.com.government.requisicao.dto;

public record RequisicaoRequestDTO(
    String motivo,
    String descricao,
    String responsavel
) {}
