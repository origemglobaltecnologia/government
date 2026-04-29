package br.com.government.constituicao.dto;

import lombok.Data;
import java.util.List;

@Data
public class ConstituicaoDTO {
    private String identificador;
    private String titulo;
    private String tipoNorma;
    private String ementa;
    private String textoOriginal;
    private String justificativa;
    private List<String> palavrasChave;
    private String tituloConstituicao;
    private String capitulo;
    private String secao;
    private String artigo;
    private String inciso;
    private String paragrafo;
    private String statusTramitacao;
    private Boolean geraDespesa;
    private Double estimativaCusto;
    private String origemServico;
}
