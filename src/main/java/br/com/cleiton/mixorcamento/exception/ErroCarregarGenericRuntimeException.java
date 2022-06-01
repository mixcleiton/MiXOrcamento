package br.com.cleiton.mixorcamento.exception;

public class ErroCarregarGenericRuntimeException extends  RuntimeException {

    public ErroCarregarGenericRuntimeException(Throwable erro) {
        super(erro);
    }

}
