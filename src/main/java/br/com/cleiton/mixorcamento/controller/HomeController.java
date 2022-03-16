package br.com.cleiton.mixorcamento.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeController {

    private CidadeController cidadeController;
    private EmpresaController informacaoEmpresaController;

    @FXML
    MenuBar menuBar;

    @FXML
    MenuItem menuCidade;

    @FXML
    MenuItem menuEmpresa;

    public HomeController() {}

    @FXML
    public void initialize() {
        this.cidadeController = new CidadeController();
        this.informacaoEmpresaController = new EmpresaController();

        this.carregarTelaCadastroCidade();
        this.carregarTelaCadastroEmpresa();
    }

    public void expandirMenuBar() {
        menuBar.prefWidthProperty().bind(menuBar.getScene().getWindow().widthProperty());
    }

    public void carregarTelaCadastroCidade() {
        this.carregarAction(menuCidade, cidadeController);
    }

    public void carregarTelaCadastroEmpresa() {
        this.carregarAction(menuEmpresa, informacaoEmpresaController);
    }

    private void carregarAction(MenuItem menu, IBaseController controller) {
        menu.setOnAction(getEventHandler(controller));
    }

    private EventHandler<ActionEvent> getEventHandler(IBaseController controller) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) menuBar.getScene().getWindow();
                try {
                    controller.start(stage);
                } catch (Exception e) {

                }
            }
        };
    }
}