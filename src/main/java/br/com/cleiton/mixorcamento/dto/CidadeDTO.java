package br.com.cleiton.mixorcamento.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class CidadeDTO extends BaseDTO {

    private String nome;

}
