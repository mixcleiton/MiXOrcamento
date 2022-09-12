package br.com.cleiton.mixorcamento.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
public class VinculoDTO extends BaseDTO {

    private String nome;
    private EmpresaDTO empresa;
    private CidadeDTO cidade;
    private ItemDTO item;
    private BigDecimal valor;

}
