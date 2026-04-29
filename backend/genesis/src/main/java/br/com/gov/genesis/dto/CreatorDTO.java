package br.com.gov.genesis.dto;

import lombok.Data;
import java.util.UUID;
import java.util.List;

@Data
public class CreatorDTO {
    private String nome;
    private List<UUID> conexoes;
}
