package br.com.cleiton.mixorcamento.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmpresaDTO implements BaseDTO {

    private String id;
    private String nome;
    private String cnpj;
    private String cidade;
    private String bairro;
    private String endereco;

}
