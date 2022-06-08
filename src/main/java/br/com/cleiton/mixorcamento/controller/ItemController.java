package br.com.cleiton.mixorcamento.controller;

import br.com.cleiton.mixorcamento.MiXOrcamentoApplication;
import br.com.cleiton.mixorcamento.dto.ItemDTO;
import br.com.cleiton.mixorcamento.dto.UnidadeDTO;
import br.com.cleiton.mixorcamento.modelo.Item;
import br.com.cleiton.mixorcamento.modelo.metamodelo.EmpresaEnum;
import br.com.cleiton.mixorcamento.service.ItemService;
import br.com.cleiton.mixorcamento.service.UnidadeService;
import br.com.cleiton.mixorcamento.util.MaskUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class ItemController extends CrudController<Item, ItemDTO>
        implements IBaseController {

    private static final Logger LOGGER = LogManager.getLogger(ItemController.class);
    public static final String MENSAGEM_SUCESSO_ITEM = "Dados do Item salvo com sucesso";
    public static final String MENSAGEM_SUCESSO_APAGAR_ITEM = "Dados do Item apagados com sucesso";
    public static final String MENSAGEM_PERGUNTA_APAGAR = "Realmente gostaria apagar os dados do item?";

    @FXML
    private Label id;

    @FXML
    private TextField nome;

    @FXML
    private ComboBox<UnidadeDTO> unidade;

    @FXML
    private TextField valorPadrao;

    @FXML
    private CheckBox naoVaria;

    @FXML
    private TextArea descricao;

    @FXML
    private TextField filtro;

    @FXML
    private TableColumn<TableView<UnidadeDTO>, String> idColumn;

    @FXML
    private TableColumn<TableView<UnidadeDTO>, String> itemColumn;

    @FXML
    private TableColumn<TableView<UnidadeDTO>, String> valorPadraoColumn;

    public ItemController() {
        super(ItemService.getInstancia());
    }

    @Override
    public void start(Stage window) throws IOException {
        Parent root = FXMLLoader.load(MiXOrcamentoApplication.class.getResource("item-view.fxml"));
        Scene scene =  new Scene(root, window.getWidth(), window.getHeight());
        window.setResizable(Boolean.FALSE);
        window.setScene(scene);
        window.show();
    }

    public void inicializar() {
        LOGGER.debug("Inicializando ItemController");
        this.carregarLimiteCampos();
        this.bloquearEditar(Boolean.TRUE);
        this.inicializarColunas();
        this.carregarDados();
        this.carregarEventoTabela();
        this.buscarFiltrado();
        this.carregarComboUnidade();
    }

    private void inicializarColunas() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        valorPadraoColumn.setCellValueFactory(new PropertyValueFactory<>("valorPadrao"));
    }

    @Override
    public String carregarMensagemEditar(ItemDTO item) {
        return
                "Deseja realmente editar o Registro do Item: ".concat(item.getId());
    }

    @Override
    public void carregarCamposNoEditar(ItemDTO dto) {
        this.id.setText(dto.getId());
        this.descricao.setText(dto.getDescricao());
        this.valorPadrao.setText(dto.getValorPadrao().toString());
        this.nome.setText(dto.getNome());
        this.unidade.getSelectionModel().select(dto.getUnidade());
        this.naoVaria.setSelected(dto.getNaoVaria());
        this.bloquearCampos(Boolean.TRUE);
        this.bloquearEditar(Boolean.FALSE);
    }

    private void carregarLimiteCampos() {
        MaskUtil.addTextLimiter(nome, MAX_LENGTH_100);
        MaskUtil.addTextLimiter(descricao, MAX_LENGTH_255);
        MaskUtil.addTextLimiter(valorPadrao, MAX_LENGTH_10);
        MaskUtil.mascaraNumero(valorPadrao);
    }

    @Override
    public String getMensagemSucessoSalvar() {
        return MENSAGEM_SUCESSO_ITEM;
    }

    @Override
    public String getMensagemSucessoApagar() {
        return MENSAGEM_SUCESSO_APAGAR_ITEM;
    }

    @Override
    public String getMensagemPerguntaApagar() {
        return MENSAGEM_PERGUNTA_APAGAR;
    }

    @Override
    public void limpar() {
        this.id.setText("");
        this.descricao.setText("");
        this.valorPadrao.setText("");
        this.nome.setText("");
        this.bloquearCampos(Boolean.FALSE);
        this.bloquearEditar(Boolean.TRUE);
        this.modoEditar = Boolean.FALSE;
    }

    @Override
    protected void bloquearCampos(Boolean bloquear) {
        this.descricao.setDisable(bloquear);
        this.valorPadrao.setDisable(bloquear);
        this.unidade.setDisable(bloquear);
        this.naoVaria.setDisable(bloquear);
        this.nome.setDisable(bloquear);
    }


    @Override
    public ItemDTO carregarDTO() {
        return ItemDTO.builder()
                .nome(nome.getText())
                .id(this.getIdValidado(id.getText()))
                .descricao(this.descricao.getText())
                .valorPadrao(new BigDecimal(this.valorPadrao.getText()))
                .naoVaria(this.naoVaria.selectedProperty().getValue())
                .unidade(this.unidade.getValue())
                .build();
    }

    public void buscarFiltrado() {
        this.filtro.setOnKeyReleased(
                e -> realizarBuscaComFiltro(EmpresaEnum.nome.name(), this.filtro.getText())
        );
    }

    private void carregarComboUnidade() {

        List<UnidadeDTO> unidades = UnidadeService.getInstancia().findAll();

        this.unidade.getItems().clear();
        this.unidade.getItems().addAll(unidades);
        this.unidade.getSelectionModel().selectFirst();
        this.unidade.setCellFactory(param -> getListCell());
        this.unidade.setButtonCell(getListCell());
    }

    @NotNull
    private ListCell<UnidadeDTO> getListCell() {
        return new ListCell<>() {
            @Override
            protected void updateItem(UnidadeDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getNome().concat(" - ").concat(item.getCodigo()));
                } else {
                    setText(null);
                }
            }
        };
    }

}