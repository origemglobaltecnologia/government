package br.com.government.requisicao.model;

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
@RedisHash("requisicao")
public class Requisicao implements Serializable {
    @Id
    private UUID uuid;
    private String motivo;
    private String descricao;
    private String responsavel;
}
