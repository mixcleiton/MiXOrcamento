package br.com.cleiton.mixorcamento.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class EmpresaDTO extends BaseDTO {

    private String nome;
    private String cnpj;
    private String cidade;
    private String bairro;
    private String endereco;

}
