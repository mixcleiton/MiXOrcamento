package br.com.cleiton.mixorcamento.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class ListaCampoObrigatorioException extends Exception {

    private final List<CampoObrigatorioException> camposObrigatorios;

    public ListaCampoObrigatorioException(List<CampoObrigatorioException> camposObrigatorios) {
       this.camposObrigatorios = camposObrigatorios;
    }

}
