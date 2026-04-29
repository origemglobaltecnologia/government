package br.com.government.cidadao.dto;

import java.util.UUID;

public record CidadaoResponseDTO(
    UUID uuid, 
    String nome, 
    String cpf
) {}
