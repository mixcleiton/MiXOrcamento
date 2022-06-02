package br.com.cleiton.mixorcamento.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Getter
@Setter
public class HomeController {

    private static final Logger logger = LogManager.getLogger(HomeController.class);
    private CidadeController cidadeController;
    private EmpresaController informacaoEmpresaController;
    private UnidadeController unidadeController;

    @FXML
    MenuBar menuBar;

    @FXML
    MenuItem menuCidade;

    @FXML
    MenuItem menuEmpresa;

    @FXML
    MenuItem menuUnidade;

    @FXML
    public void initialize() {
        this.cidadeController = new CidadeController();
        this.informacaoEmpresaController = new EmpresaController();
        this.unidadeController = new UnidadeController();

        this.carregarTelaCidade();
        this.carregarTelaEmpresa();
        this.carregarTelaUnidade();
    }

    public void expandirMenuBar() {
        menuBar.prefWidthProperty().bind(menuBar.getScene().getWindow().widthProperty());
    }

    public void carregarTelaCidade() {
        this.carregarAction(menuCidade, cidadeController);
    }

    public void carregarTelaEmpresa() {
        this.carregarAction(menuEmpresa, informacaoEmpresaController);
    }

    public void carregarTelaUnidade() {
        this.carregarAction(menuUnidade, unidadeController);
    }

    private void carregarAction(MenuItem menu, IBaseController controller) {
        menu.setOnAction(getEventHandler(controller));
    }

    private EventHandler<ActionEvent> getEventHandler(IBaseController controller) {
        return e -> {
            Stage stage = (Stage) menuBar.getScene().getWindow();
            try {
                controller.start(stage);
            } catch (Exception exception) {
                logger.error("erro ao carregar controller", exception);
            }
        };
    }
}