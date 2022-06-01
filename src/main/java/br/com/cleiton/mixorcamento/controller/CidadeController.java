package br.com.cleiton.mixorcamento.controller;

import br.com.cleiton.mixorcamento.MiXOrcamentoApplication;
import br.com.cleiton.mixorcamento.dto.CidadeDTO;
import br.com.cleiton.mixorcamento.service.BaseService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CidadeController extends CrudController<CidadeDTO>
        implements IBaseController {


    public CidadeController(BaseService service) {
        super(service);
    }

    @Override
    public void start(Stage window) throws IOException {
        Parent root = FXMLLoader.load(MiXOrcamentoApplication.class.getResource("cidade-view.fxml"));
        Scene scene =  new Scene(root, window.getWidth(), window.getHeight());
        window.setResizable(Boolean.FALSE);
        window.setScene(scene);
        window.show();
    }


    @Override
    public void inicializar() {

    }

    @Override
    protected void limpar() {

    }

    @Override
    protected void bloquearCampos(Boolean bloquear) {

    }

    @Override
    public void carregarCamposNoEditar(CidadeDTO item) {

    }

    @Override
    public String carregarMensagemEditar(CidadeDTO item) {
        return null;
    }

    @Override
    public CidadeDTO carregarDTO() {
        return null;
    }

    @Override
    public String getMensagemSucessoSalvar() {
        return null;
    }

    @Override
    public String getMensagemSucessoApagar() {
        return null;
    }

    @Override
    public String getMensagemPerguntaApagar() {
        return null;
    }
}
