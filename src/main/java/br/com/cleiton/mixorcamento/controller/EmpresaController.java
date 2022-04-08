package br.com.cleiton.mixorcamento.controller;

import br.com.cleiton.mixorcamento.MiXOrcamentoApplication;
import br.com.cleiton.mixorcamento.dto.EmpresaDTO;
import br.com.cleiton.mixorcamento.exception.ListaCampoObrigatorioException;
import br.com.cleiton.mixorcamento.service.EmpresaService;
import br.com.cleiton.mixorcamento.util.MaskUtil;
import br.com.cleiton.mixorcamento.util.MensagemUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class EmpresaController extends BaseController implements IBaseController {

    private static final Logger logger = LogManager.getLogger(EmpresaController.class);
    public static final int CNPJ_LENGTH = 18;
    public static final int MAX_LENGTH_100 = 100;
    public static final int MAX_LENGTH_255 = 255;
    public static final String MENSAGEM_SUCESSO_EMPRESA = "Dados da Empresa salvo com sucesso";

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
    private TableView<EmpresaDTO> tabela;

    @FXML
    private TableColumn<TableView<EmpresaDTO>, String> idColumn;

    @FXML
    private TableColumn<TableView<EmpresaDTO>, String> empresaColumn;

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
        this.bloquearEditar(Boolean.TRUE);
        this.inicializarColunas();
        this.carregarDados();
        this.carregarEventoTabela();
    }

    private void inicializarColunas() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        empresaColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
    }

    private void carregarDados() {
        List<EmpresaDTO> empresas = this.service.findAll();
        this.carregarTabela(empresas);
    }

    private void carregarTabela(List<EmpresaDTO> empresas) {
        this.tabela.setItems(FXCollections.observableList(empresas));
    }

    private void carregarEventoTabela() {
        this.tabela.setRowFactory( tv -> {
            TableRow<EmpresaDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    carregarEditar(row.getItem());
                }
            });

            return row;
        });
    }

    private void carregarEditar(EmpresaDTO empresa) {
        StringBuilder mensagem = new StringBuilder("Deseja realmente editar o Registro Empresa: ");
        mensagem.append(empresa.getNome());

        if (Boolean.TRUE.equals(MensagemUtil.mostrarMensagemPergunta(mensagem.toString()))) {
            this.carregarCamposNoEditar(empresa);
        }
    }

    private void carregarCamposNoEditar(EmpresaDTO empresa) {
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
    protected void editar() {
        this.modoEditar = Boolean.TRUE;
        this.bloquearEditar(Boolean.TRUE);
        this.bloquearCampos(Boolean.FALSE);
    }

    @Override
    protected void salvar() {
        try {
            this.service.salvar(this.carregarDTO(), modoEditar);
            this.limpar();
            this.carregarDados();
            this.modoEditar = Boolean.FALSE;
            MensagemUtil.mostrarMensagemSucesso(MENSAGEM_SUCESSO_EMPRESA);
        } catch (ListaCampoObrigatorioException e) {
            MensagemUtil.mostrarMensagemCamposObrigatorios(e);
        } catch (Exception e) {
            logger.error(e);
        }
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


    private EmpresaDTO carregarDTO() {
        EmpresaDTO.EmpresaDTOBuilder builder = EmpresaDTO.builder()
                .bairro(bairro.getText())
                .cidade(cidade.getText())
                .cnpj(cnpj.getText())
                .nome(nome.getText())
                .endereco(endereco.getText());

        if (!id.getText().isEmpty()) {
            builder = builder.id(id.getText());
        }

        return builder.build();
    }

}