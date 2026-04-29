package br.com.government.projetodelei.dto;
import java.util.UUID;

public record ProjetoLeiResponseDTO(
    UUID uuid,
    String identificador,
    String titulo,
    String ementa,
    String responsavel,
    String voto
) {}
