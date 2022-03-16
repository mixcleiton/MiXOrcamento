package br.com.cleiton.mixorcamento.controller;

import br.com.cleiton.mixorcamento.MiXOrcamentoApplication;
import br.com.cleiton.mixorcamento.exception.ListaCampoObrigatorioException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class BaseController {

    public static final String TITULO_CAMPOS_OBRIGATORIOS = "Campos Obrigatórios";
    public static final String ERRO_NO_CAMPOS = "Erro no campos";
    protected Boolean edicao = Boolean.FALSE;

    @FXML
    protected Button salvar;

    @FXML
    protected Button atualizar;

    @FXML
    protected Button apagar;

    @FXML
    protected Button limpar;

    @FXML
    protected Button fechar;

    @FXML
    public void initialize() {
        this.inicializar();
        this.carregarBotaoSalvar();
        this.carregarBotaoFechar();
    }

    public abstract void inicializar();

    protected void bloquearEditar() {
        this.atualizar.setDisable(!this.edicao);
        this.apagar.setDisable(!this.edicao);
    }

    protected void habilitarEditar() {
        this.atualizar.setDisable(this.edicao);
        this.apagar.setDisable(this.edicao);
    }

    protected abstract void salvar();

    protected void mostrarMensagemCamposObrigatorios(ListaCampoObrigatorioException campos) {
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

    private void fecharAction(Stage window) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MiXOrcamentoApplication.class.getResource("home-view.fxml"));
        Scene scene =  new Scene(fxmlLoader.load(), window.getWidth(), window.getHeight());
        window.setResizable(Boolean.FALSE);
        window.setScene(scene);
        window.show();
        HomeController controller = fxmlLoader.getController();
        controller.expandirMenuBar();
    }

    private void carregarBotaoFechar() {
        this.fechar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    fecharAction((Stage) fechar.getScene().getWindow());
                } catch (IOException e) {

                }
            }
        });
    }

    private void carregarBotaoSalvar() {
        this.salvar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                salvar();
            }
        });
    }
}
