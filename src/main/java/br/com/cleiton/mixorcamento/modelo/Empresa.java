package br.com.cleiton.mixorcamento.modelo;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Empresa extends BaseModelo {

    private String nome;
    private String cnpj;
    private String cidade;
    private String bairro;
    private String endereco;
}
