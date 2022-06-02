package br.com.cleiton.mixorcamento.controller;

import br.com.cleiton.mixorcamento.MiXOrcamentoApplication;
import br.com.cleiton.mixorcamento.dto.CidadeDTO;
import br.com.cleiton.mixorcamento.modelo.Cidade;
import br.com.cleiton.mixorcamento.modelo.metamodelo.CidadeEnum;
import br.com.cleiton.mixorcamento.service.CidadeService;
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

public class CidadeController extends CrudController<Cidade, CidadeDTO>
        implements IBaseController {

    private static final Logger LOGGER = LogManager.getLogger(CidadeController.class);
    public static final String MENSAGEM_SUCESSO_CIDADE = "Dados da Cidade salvo com sucesso";
    public static final String MENSAGEM_SUCESSO_APAGAR_CIDADE = "Dados da Cidade apagados com sucesso";
    public static final String MENSAGEM_PERGUNTA_APAGAR = "Realmente gostaria de apagar os dados da cidade?";

    @FXML
    private Label id;

    @FXML
    private TextField nome;

    @FXML
    private TableColumn<TableView<CidadeDTO>, String> idColumn;

    @FXML
    private TableColumn<TableView<CidadeDTO>, String> cidadeColumn;

    @FXML
    private TextField filtro;

    public CidadeController() {
        super(CidadeService.getInstancia());
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
        LOGGER.info("inicializando o CidadeController");
        this.carregarLimiteCampos();
        this.bloquearEditar(Boolean.TRUE);
        this.inicializarColunas();
        this.carregarDados();
        this.carregarEventoTabela();
        this.buscarFiltrado();
    }

    private void carregarLimiteCampos() {
        MaskUtil.addTextLimiter(nome, MAX_LENGTH_100);
    }

    private void inicializarColunas() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        cidadeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
    }

    public void buscarFiltrado() {
        this.filtro.setOnKeyReleased(
                e -> realizarBuscaComFiltro(CidadeEnum.nome.name(), this.filtro.getText())
        );
    }

    @Override
    protected void limpar() {
        this.id.setText("");
        this.nome.setText("");
        this.bloquearCampos(Boolean.FALSE);
        this.bloquearEditar(Boolean.TRUE);
        this.modoEditar = Boolean.FALSE;
    }

    @Override
    protected void bloquearCampos(Boolean bloquear) {
        this.nome.setDisable(bloquear);
    }

    @Override
    public void carregarCamposNoEditar(CidadeDTO item) {
        this.id.setText(item.getId());
        this.nome.setText(item.getNome());
        this.bloquearCampos(Boolean.TRUE);
        this.bloquearEditar(Boolean.FALSE);
    }

    @Override
    public String carregarMensagemEditar(CidadeDTO item) {
        return
                "Deseja realmente editar o Registro da Cidade: ".concat(item.getId());
    }

    @Override
    public CidadeDTO carregarDTO() {
        return CidadeDTO.builder()
                .nome(this.nome.getText())
                .id(this.getIdValidado(id.getText()))
                .build();
    }

    @Override
    public String getMensagemSucessoSalvar() {
        return MENSAGEM_SUCESSO_CIDADE;
    }

    @Override
    public String getMensagemSucessoApagar() {
        return MENSAGEM_SUCESSO_APAGAR_CIDADE;
    }

    @Override
    public String getMensagemPerguntaApagar() {
        return MENSAGEM_PERGUNTA_APAGAR;
    }
}
