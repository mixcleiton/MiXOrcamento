package br.com.cleiton.mixorcamento.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TabelaVinculoDTO {

    private String id;
    private String nome;
    private String empresa;
    private String cidade;
    private String item;
    private String valor;

}
