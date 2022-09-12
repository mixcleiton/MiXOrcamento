package br.com.cleiton.mixorcamento.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class UnidadeDTO extends BaseDTO {

    private String nome;
    private String codigo;

}
