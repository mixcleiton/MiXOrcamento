package br.com.cleiton.mixorcamento.controller;

import br.com.cleiton.mixorcamento.MiXOrcamentoApplication;
import br.com.cleiton.mixorcamento.dto.EmpresaDTO;
import br.com.cleiton.mixorcamento.modelo.Empresa;
import br.com.cleiton.mixorcamento.modelo.metamodelo.EmpresaEnum;
import br.com.cleiton.mixorcamento.service.EmpresaService;
import br.com.cleiton.mixorcamento.util.MaskUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class EmpresaController extends CrudController<Empresa, EmpresaDTO>
        implements IBaseController {

    private static final Logger LOGGER = LogManager.getLogger(EmpresaController.class);
    public static final int CNPJ_LENGTH = 18;
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
        super(EmpresaService.getInstancia());
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
        LOGGER.debug("Inicializando EmpresaController");
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

    public void buscarFiltrado() {
        this.filtro.setOnKeyReleased(
                e -> realizarBuscaComFiltro(EmpresaEnum.nome.name(), this.filtro.getText())
        );
    }

}