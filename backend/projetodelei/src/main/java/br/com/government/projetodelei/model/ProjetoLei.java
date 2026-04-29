package br.com.government.projetodelei.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("projetodelei")
public class ProjetoLei implements Serializable {
    @Id
    private UUID uuid;
    private String identificador;
    private String titulo;
    private String ementa;
    private String responsavel;
    private String voto;
}
