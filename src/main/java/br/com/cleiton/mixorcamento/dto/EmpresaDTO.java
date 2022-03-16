package br.com.cleiton.mixorcamento.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmpresaDTO extends BaseDTO {

    private String id;
    private String nome;
    private String CNPJ;
    private String cidade;
    private String bairro;
    private String endereco;

}
