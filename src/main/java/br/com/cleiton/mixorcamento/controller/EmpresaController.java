package br.com.cleiton.mixorcamento.controller;

import br.com.cleiton.mixorcamento.MiXOrcamentoApplication;
import br.com.cleiton.mixorcamento.dto.EmpresaDTO;
import br.com.cleiton.mixorcamento.exception.ListaCampoObrigatorioException;
import br.com.cleiton.mixorcamento.service.EmpresaService;
import br.com.cleiton.mixorcamento.util.MaskUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class EmpresaController extends BaseController implements IBaseController {

    public static final int CNPJ_LENGTH = 18;
    public static final int MAX_LENGTH_100 = 100;
    public static final int MAX_LENGTH_255 = 255;

    @FXML
    private Label id;

    @FXML
    private TextField nome;

    @FXML
    private TextField cnpj;

    @FXML
    private TextField cidade;

    @FXML
    private TextField bairro;

    @FXML
    private TextArea endereco;

    private EmpresaService service = new EmpresaService();

    @Override
    public void start(Stage window) throws IOException {
        Parent root = FXMLLoader.load(MiXOrcamentoApplication.class.getResource("empresa-view.fxml"));
        Scene scene =  new Scene(root, window.getWidth(), window.getHeight());
        window.setResizable(Boolean.FALSE);
        window.setScene(scene);
        window.show();
    }

    public void inicializar() {
        this.carregarCampoCNPJ();
        this.carregarLimiteCampos();
        this.bloquearEditar();
    }

    private void carregarCampoCNPJ() {
        MaskUtil.mascaraCNPJ(cnpj);
        MaskUtil.addTextLimiter(cnpj, CNPJ_LENGTH);
    }

    private void carregarLimiteCampos() {
        MaskUtil.addTextLimiter(nome, MAX_LENGTH_100);
        MaskUtil.addTextLimiter(cidade, MAX_LENGTH_100);
        MaskUtil.addTextLimiter(bairro, MAX_LENGTH_100);
        MaskUtil.addTextLimiter(endereco, MAX_LENGTH_255);
    }

    @Override
    protected void salvar() {
        try {
            this.service.salvar(this.carregarDTO());
        } catch (ListaCampoObrigatorioException e) {
            this.mostrarMensagemCamposObrigatorios(e);
        }
    }

    private EmpresaDTO carregarDTO() {
        return EmpresaDTO.builder()
                .bairro(bairro.getText())
                .cidade(cidade.getText())
                .CNPJ(cnpj.getText())
                .nome(nome.getText())
                .endereco(endereco.getText())
                .build();
    }

}