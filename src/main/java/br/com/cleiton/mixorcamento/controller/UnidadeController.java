package br.com.cleiton.mixorcamento.controller;

import br.com.cleiton.mixorcamento.MiXOrcamentoApplication;
import br.com.cleiton.mixorcamento.dto.UnidadeDTO;
import br.com.cleiton.mixorcamento.modelo.Unidade;
import br.com.cleiton.mixorcamento.modelo.metamodelo.UnidadeEnum;
import br.com.cleiton.mixorcamento.service.UnidadeService;
import br.com.cleiton.mixorcamento.util.MaskUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class UnidadeController extends CrudController<Unidade, UnidadeDTO>
        implements IBaseController {

    private static final Logger LOGGER = LogManager.getLogger(UnidadeController.class);
    public static final String MENSAGEM_SUCESSO_UNIDADE = "Dados da Unidade salvo com sucesso";
    public static final String MENSAGEM_SUCESSO_APAGAR_UNIDADE = "Dados da Unidade apagados com sucesso";
    public static final String MENSAGEM_PERGUNTA_APAGAR = "Realmente gostaria de apagar os dados da unidade?";

    @FXML
    private Label id;

    @FXML
    private TextField nome;

    @FXML
    private TextField codigo;

    @FXML
    private TextField filtro;

    @FXML
    private TableColumn<TableView<UnidadeDTO>, String> idColumn;

    @FXML
    private TableColumn<TableView<UnidadeDTO>, String> unidadeColumn;

    @FXML
    private TableColumn<TableView<UnidadeDTO>, String> codigoColumn;

    public UnidadeController() {
        super(UnidadeService.getInstancia());
    }

    @Override
    public void start(Stage window) throws IOException {
        Parent root = FXMLLoader.load(MiXOrcamentoApplication.class.getResource("unidade-view.fxml"));
        Scene scene =  new Scene(root, window.getWidth(), window.getHeight());
        window.setResizable(Boolean.FALSE);
        window.setScene(scene);
        window.show();
    }

    public void inicializar() {
        LOGGER.debug("Inicializando UnidadeController");
        this.carregarLimiteCampos();
        this.bloquearEditar(Boolean.TRUE);
        this.inicializarColunas();
        this.carregarDados();
        this.carregarEventoTabela();
        this.buscarFiltrado();
    }

    private void inicializarColunas() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        unidadeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        codigoColumn.setCellValueFactory(new PropertyValueFactory<>("codigo"));
    }

    @Override
    public String carregarMensagemEditar(UnidadeDTO item) {
        return
                "Deseja realmente editar o Registro da Unidade: ".concat(item.getId());
    }

    @Override
    public void carregarCamposNoEditar(UnidadeDTO dto) {
        this.id.setText(dto.getId());
        this.codigo.setText(dto.getCodigo());
        this.nome.setText(dto.getNome());
        this.bloquearCampos(Boolean.TRUE);
        this.bloquearEditar(Boolean.FALSE);
    }

    private void carregarLimiteCampos() {
        MaskUtil.addTextLimiter(nome, MAX_LENGTH_100);
        MaskUtil.addTextLimiter(codigo, MAX_LENGTH_100);
    }

    @Override
    public String getMensagemSucessoSalvar() {
        return MENSAGEM_SUCESSO_UNIDADE;
    }

    @Override
    public String getMensagemSucessoApagar() {
        return MENSAGEM_SUCESSO_APAGAR_UNIDADE;
    }

    @Override
    public String getMensagemPerguntaApagar() {
        return MENSAGEM_PERGUNTA_APAGAR;
    }

    @Override
    public void limpar() {
        this.id.setText("");
        this.codigo.setText("");
        this.nome.setText("");
        this.bloquearCampos(Boolean.FALSE);
        this.bloquearEditar(Boolean.TRUE);
        this.modoEditar = Boolean.FALSE;
    }

    @Override
    protected void bloquearCampos(Boolean bloquear) {
        this.codigo.setDisable(bloquear);
        this.nome.setDisable(bloquear);
    }


    @Override
    public UnidadeDTO carregarDTO() {
        return UnidadeDTO.builder()
                .nome(nome.getText())
                .id(this.getIdValidado(id.getText()))
                .codigo(this.codigo.getText())
                .build();
    }

    public void buscarFiltrado() {
        this.filtro.setOnKeyReleased(
                e -> realizarBuscaComFiltro(UnidadeEnum.nome.name(), this.filtro.getText())
        );
    }

}