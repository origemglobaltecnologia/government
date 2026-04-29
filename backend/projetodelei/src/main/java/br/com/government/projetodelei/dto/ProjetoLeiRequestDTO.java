package br.com.government.projetodelei.dto;

public record ProjetoLeiRequestDTO(
    String identificador,
    String titulo,
    String ementa,
    String responsavel,
    String voto
) {}
