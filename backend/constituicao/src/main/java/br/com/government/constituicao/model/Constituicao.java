package br.com.government.constituicao.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.io.Serializable;
import java.util.UUID;
import java.util.List;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("constituicao")
public class Constituicao implements Serializable {

    @Id
    private UUID uuid;

    // --- IDENTIFICAÇÃO FORMAL ---
    @Indexed
    private String identificador; 
    private String titulo;
    private String tipoNorma; 

    // --- CONTEÚDO TEXTUAL ---
    private String ementa;
    private String textoOriginal;
    private String justificativa;
    private List<String> palavrasChave;

    // --- ESTRUTURA HIERÁRQUICA ---
    private String tituloConstituicao; 
    private String capitulo;
    private String secao;
    private String artigo;
    private String inciso;
    private String paragrafo;

    // --- ANÁLISE JURÍDICA E STATUS ---
    private String statusTramitacao; 
    private Double riscoInconstitucionalidade;
    private String parecerIA;

    // --- IMPACTO FINANCEIRO ---
    private Boolean geraDespesa;
    private Double estimativaCusto;

    // --- METADADOS DE AUDITORIA ---
    private LocalDateTime dataCriacao;
    private String hashAssinatura;
    private String origemServico;
}
