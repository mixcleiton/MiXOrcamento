package br.com.cleiton.mixorcamento.exception;

import lombok.Getter;

@Getter
public class CampoObrigatorioException extends Exception {

    private final String nomeCampo;

    public CampoObrigatorioException(String nomeCampo, String mensagem) {
        super(mensagem);
        this.nomeCampo = nomeCampo;
    }

}
