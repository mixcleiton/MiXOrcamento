package br.com.cleiton.mixorcamento.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
public class ItemDTO extends BaseDTO {

    private String nome;
    private String descricao;
    private UnidadeDTO unidade;
    private BigDecimal valorPadrao;
    private Boolean naoVaria;

}