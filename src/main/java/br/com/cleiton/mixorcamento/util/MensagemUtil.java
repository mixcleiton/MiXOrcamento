package br.com.cleiton.mixorcamento.util;

import br.com.cleiton.mixorcamento.exception.ListaCampoObrigatorioException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class MensagemUtil {

    public static final String TITULO_CAMPOS_OBRIGATORIOS = "Campos Obrigatórios";
    public static final String SUCESSO = "Sucesso";
    public static final String ERRO_NO_CAMPOS = "Erro no campos";

    private MensagemUtil() {}

    public static void mostrarMensagemCamposObrigatorios(ListaCampoObrigatorioException campos) {
        StringBuilder mensagem = new StringBuilder();

        campos.getCamposObrigatorios().forEach(campo -> {
            mensagem.append(campo.getMessage());
            mensagem.append("\n");
        });

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(TITULO_CAMPOS_OBRIGATORIOS);
        alert.setHeaderText(ERRO_NO_CAMPOS);
        alert.setContentText(mensagem.toString());
        alert.showAndWait();
    }

    public static void mostrarMensagemSucesso(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(SUCESSO);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static boolean mostrarMensagemPergunta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(SUCESSO);
        alert.setContentText(mensagem);
        Optional<ButtonType> result = alert.showAndWait();

        return !result.isPresent() || result.get() == ButtonType.OK;
    }

}
