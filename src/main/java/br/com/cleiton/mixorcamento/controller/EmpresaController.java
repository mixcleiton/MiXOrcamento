package br.com.cleiton.mixorcamento.controller;

import br.com.cleiton.mixorcamento.MiXOrcamentoApplication;
import br.com.cleiton.mixorcamento.dto.EmpresaDTO;
import br.com.cleiton.mixorcamento.service.EmpresaService;
import br.com.cleiton.mixorcamento.util.MaskUtil;
import dev.morphia.query.FindOptions;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import java.io.IOException;

public class EmpresaController extends CrudController<EmpresaDTO>
        implements IBaseController {

    private static final Logger logger = LogManager.getLogger(EmpresaController.class);
    public static final int CNPJ_LENGTH = 18;
    public static final int MAX_LENGTH_100 = 100;
    public static final int MAX_LENGTH_255 = 255;
    public static final String MENSAGEM_SUCESSO_EMPRESA = "Dados da Empresa salvo com sucesso";
    public static final String MENSAGEM_SUCESSO_APAGAR_EMPRESA = "Dados da Empresa apagados com sucesso";
    public static final String MENSAGEM_PERGUNTA_APAGAR = "Realmente gostaria de apagar os dados da empresa?";

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
    private TextField filtro;

    @FXML
    private TextArea endereco;

    @FXML
    private TableColumn<TableView<EmpresaDTO>, String> idColumn;

    @FXML
    private TableColumn<TableView<EmpresaDTO>, String> empresaColumn;

    public EmpresaController() {
        super(new EmpresaService());
    }

    @Override
    public void start(Stage window) throws IOException {
        Parent root = FXMLLoader.load(MiXOrcamentoApplication.class.getResource("empresa-view.fxml"));
        Scene scene =  new Scene(root, window.getWidth(), window.getHeight());
        window.setResizable(Boolean.FALSE);
        window.setScene(scene);
        window.show();
    }

    public void inicializar() {
        this.logger.debug("Inicializando EmpresaController");
        this.carregarCampoCNPJ();
        this.carregarLimiteCampos();
        this.bloquearEditar(Boolean.TRUE);
        this.inicializarColunas();
        this.carregarDados();
        this.carregarEventoTabela();
        this.buscarFiltrado();
    }

    private void inicializarColunas() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        empresaColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
    }

    @Override
    public String carregarMensagemEditar(EmpresaDTO item) {
        return
                "Deseja realmente editar o Registro Empresa: ".concat(item.getId());
    }

    @Override
    public void carregarCamposNoEditar(EmpresaDTO empresa) {
        this.id.setText(empresa.getId());
        this.bairro.setText(empresa.getBairro());
        this.cidade.setText(empresa.getCidade());
        this.cnpj.setText(empresa.getCnpj());
        this.nome.setText(empresa.getNome());
        this.endereco.setText(empresa.getEndereco());
        this.bloquearCampos(Boolean.TRUE);
        this.bloquearEditar(Boolean.FALSE);
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
    public String getMensagemSucessoSalvar() {
        return MENSAGEM_SUCESSO_EMPRESA;
    }

    @Override
    public String getMensagemSucessoApagar() {
        return MENSAGEM_SUCESSO_APAGAR_EMPRESA;
    }

    @Override
    public String getMensagemPerguntaApagar() {
        return MENSAGEM_PERGUNTA_APAGAR;
    }

    @Override
    public void limpar() {
        this.id.setText("");
        this.bairro.setText("");
        this.cidade.setText("");
        this.cnpj.setText("");
        this.nome.setText("");
        this.endereco.setText("");
        this.bloquearCampos(Boolean.FALSE);
        this.bloquearEditar(Boolean.TRUE);
        this.modoEditar = Boolean.FALSE;
    }

    @Override
    protected void bloquearCampos(Boolean bloquear) {
        this.bairro.setDisable(bloquear);
        this.cidade.setDisable(bloquear);
        this.cnpj.setDisable(bloquear);
        this.nome.setDisable(bloquear);
        this.endereco.setDisable(bloquear);
    }


    @Override
    public EmpresaDTO carregarDTO() {
        return EmpresaDTO.builder()
                .bairro(bairro.getText())
                .cidade(cidade.getText())
                .cnpj(cnpj.getText())
                .nome(nome.getText())
                .id(this.getIdValidado(id.getText()))
                .endereco(endereco.getText())
                .build();
    }

    private String getIdValidado(String texto) {
        String idValidado = texto;

        if (Strings.isEmpty(texto)) {
            idValidado = null;
        }

        return idValidado;
    }

    public void buscarFiltrado() {
        this.filtro.setOnKeyReleased(e -> {
            if (this.filtro.getText().length() >= 3) {
                logger.info("realizando filtro de busca");
                FindOptions options = new FindOptions()
                        .projection()
                        .include("nome")
                        .hint("/".concat(this.filtro.getText()).concat("/"));
                this.carregarTabela(this.service.buscarFiltrado(options));
            }
        });
    }

}