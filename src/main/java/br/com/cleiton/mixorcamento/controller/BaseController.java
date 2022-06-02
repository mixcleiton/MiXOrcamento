package br.com.cleiton.mixorcamento.controller;

import br.com.cleiton.mixorcamento.MiXOrcamentoApplication;
import br.com.cleiton.mixorcamento.util.RegexUtil;
import dev.morphia.query.experimental.filters.Filter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public abstract class BaseController {

    private static final Logger logger = LogManager.getLogger(BaseController.class);
    protected Boolean modoEditar = Boolean.FALSE;

    @FXML
    protected Button btnSalvar;

    @FXML
    protected Button btnAtualizar;

    @FXML
    protected Button btnApagar;

    @FXML
    protected Button btnLimpar;

    @FXML
    protected Button btnFechar;

    @FXML
    public void initialize() {
        this.inicializar();
        this.carregarBotaoSalvar();
        this.carregarBotaoFechar();
        this.carregarBotaoLimpar();
        this.carregarBotaoEditar();
        this.carregarBotaoApagar();
    }

    public abstract void inicializar();

    protected void bloquearEditar(Boolean edicao) {
        this.btnAtualizar.setDisable(edicao);
        this.btnApagar.setDisable(edicao);
        this.btnSalvar.setDisable(!edicao);
    }

    protected abstract void salvar();
    protected abstract void editar();
    protected abstract void limpar();
    protected abstract void apagar();
    protected abstract void bloquearCampos(Boolean bloquear);

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
        this.btnFechar.setOnAction(event -> {
            try {
                fecharAction((Stage) btnFechar.getScene().getWindow());
            } catch (IOException e) {
                logger.error(e);
            }
        });
    }

    private void carregarBotaoEditar() {
        this.btnAtualizar.setOnAction(event -> editar());
    }

    private void carregarBotaoLimpar() {
        this.btnLimpar.setOnAction(event -> limpar());
    }

    private void carregarBotaoSalvar() {
        this.btnSalvar.setOnAction(event -> salvar());
    }

    private void carregarBotaoApagar() {
        this.btnApagar.setOnAction(event -> apagar());
    }

    @NotNull
    protected Filter carregarFiltroString(String field, String valor) {
        return RegexUtil.getInstancia()
                .getFiltroLike(
                        field,
                        valor
                );
    }
}
