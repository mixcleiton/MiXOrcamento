package br.com.cleiton.mixorcamento.controller;

import br.com.cleiton.mixorcamento.MiXOrcamentoApplication;
import br.com.cleiton.mixorcamento.dto.CidadeDTO;
import br.com.cleiton.mixorcamento.dto.EmpresaDTO;
import br.com.cleiton.mixorcamento.dto.ItemDTO;
import br.com.cleiton.mixorcamento.dto.TabelaVinculoDTO;
import br.com.cleiton.mixorcamento.dto.VinculoDTO;
import br.com.cleiton.mixorcamento.mapper.TabelaVinculoMapper;
import br.com.cleiton.mixorcamento.modelo.Vinculo;
import br.com.cleiton.mixorcamento.modelo.metamodelo.VinculoEnum;
import br.com.cleiton.mixorcamento.service.CidadeService;
import br.com.cleiton.mixorcamento.service.EmpresaService;
import br.com.cleiton.mixorcamento.service.ItemService;
import br.com.cleiton.mixorcamento.service.VinculoService;
import br.com.cleiton.mixorcamento.util.AuxiliarComboBoxUtil;
import br.com.cleiton.mixorcamento.util.MaskUtil;
import dev.morphia.query.experimental.filters.Filter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class VinculoController
        extends CrudController<Vinculo, VinculoDTO>
        implements IBaseController{

    private static final Logger LOGGER = LogManager.getLogger(VinculoController.class);
    public static final String MENSAGEM_SUCESSO_VINCULO = "Dados do Vinculo salvo com sucesso";
    public static final String MENSAGEM_SUCESSO_APAGAR_VINCULO = "Dados do Vinculo apagados com sucesso";
    public static final String MENSAGEM_PERGUNTA_APAGAR = "Realmente gostaria de apagar os dados do vinculo?";

    private final EmpresaService empresaService;
    private final CidadeService cidadeService;
    private final ItemService itemService;
    private final AuxiliarComboBoxUtil auxiliarComboBoxUtil;
    private final TabelaVinculoMapper tabelaVinculoMapper;

    @FXML
    protected TableView<TabelaVinculoDTO> tabela;

    @FXML
    private Label id;

    @FXML
    private TextField nome;

    @FXML
    private ComboBox<EmpresaDTO> empresa;

    @FXML
    private ComboBox<CidadeDTO> cidade;

    @FXML
    private ComboBox<ItemDTO> item;

    @FXML
    private TextField valor;

    @FXML
    private TextField filtro;

    @FXML
    private TableColumn<TableView<TabelaVinculoDTO>, String> idColumn;

    @FXML
    private TableColumn<TableView<TabelaVinculoDTO>, String> nomeColumn;

    @FXML
    private TableColumn<TableView<TabelaVinculoDTO>, String> empresaColumn;

    @FXML
    private TableColumn<TableView<TabelaVinculoDTO>, String> cidadeColumn;

    @FXML
    private TableColumn<TableView<TabelaVinculoDTO>, String> itemColumn;

    @FXML
    private TableColumn<TableView<TabelaVinculoDTO>, BigDecimal> valorColumn;

    public VinculoController() {
        super(VinculoService.getInstancia());
        this.empresaService = EmpresaService.getInstancia();
        this.cidadeService = CidadeService.getInstancia();
        this.itemService = ItemService.getInstancia();
        this.auxiliarComboBoxUtil = AuxiliarComboBoxUtil.getInstance();
        this.tabelaVinculoMapper = TabelaVinculoMapper.getInstancia();
    }

    @Override
    public void carregarDados() {
        List<TabelaVinculoDTO> itens = this.service.findAll()
                .stream()
                .map(vinculoDTO -> tabelaVinculoMapper.toDTO(vinculoDTO))
                .collect(Collectors.toList());
        this.carregarTabelaVinculo(itens);
    }

    @Override
    public void start(Stage window) throws IOException {
        Parent root = FXMLLoader.load(MiXOrcamentoApplication.class.getResource("vinculo-view.fxml"));
        Scene scene =  new Scene(root, window.getWidth(), window.getHeight());
        window.setResizable(Boolean.FALSE);
        window.setScene(scene);
        window.show();
    }

    @Override
    public void inicializar() {
        LOGGER.debug("Inicializando VinculoController");

        this.inicializarColunas();
        this.carregarLimiteCampos();
        this.bloquearEditar(Boolean.TRUE);
        this.carregarDados();
        this.carregarEventoTabela();
        this.buscarFiltrado();
        this.carregarComboEmpresa();
        this.carregarComboCidade();
        this.carregarComboItem();
    }

    private void carregarLimiteCampos() {
        MaskUtil.addTextLimiter(nome, MAX_LENGTH_100);
        MaskUtil.addTextLimiter(valor, MAX_LENGTH_10);
        MaskUtil.mascaraNumero(valor);
    }

    @Override
    protected void limpar() {
        this.id.setText("");
        this.cidade.getSelectionModel().selectFirst();
        this.empresa.getSelectionModel().selectFirst();
        this.item.getSelectionModel().selectFirst();
        this.valor.setText("");
        this.nome.setText("");
        this.bloquearCampos(Boolean.FALSE);
        this.bloquearEditar(Boolean.TRUE);
        this.modoEditar = Boolean.FALSE;
    }

    @Override
    protected void bloquearCampos(Boolean bloquear) {
        this.cidade.setDisable(bloquear);
        this.empresa.setDisable(bloquear);
        this.item.setDisable(bloquear);
        this.nome.setDisable(bloquear);
        this.valor.setDisable(bloquear);
    }

    @Override
    public void carregarCamposNoEditar(VinculoDTO dto) {
        this.id.setText(dto.getId());
        this.valor.setText(dto.getValor().toString());
        this.nome.setText(dto.getNome());
        this.cidade.getSelectionModel().select(dto.getCidade());
        this.empresa.getSelectionModel().select(dto.getEmpresa());
        this.item.getSelectionModel().select(dto.getItem());
        this.bloquearCampos(Boolean.TRUE);
        this.bloquearEditar(Boolean.FALSE);
    }

    @Override
    public String carregarMensagemEditar(VinculoDTO item) {
        return
                "Deseja realmente editar o Registro do Vinculo: ".concat(item.getId());
    }

    @Override
    public VinculoDTO carregarDTO() {
        return VinculoDTO.builder()
                .nome(nome.getText())
                .id(this.getIdValidado(id.getText()))
                .valor(this.getValor(this.valor.getText()))
                .cidade(this.validarValorComboCidade(this.cidade.getValue()))
                .empresa(this.validarValorComboEmpresa(this.empresa.getValue()))
                .item(this.validarValorComboItem(this.item.getValue()))
                .build();
    }

    private BigDecimal getValor(String valor) {
        BigDecimal valorConvertido = null;

        if (Strings.isNotEmpty(valor)) {
            valorConvertido = new BigDecimal(valor);
        }

        return valorConvertido;
    }

    private ItemDTO validarValorComboItem(ItemDTO item) {

        if (Strings.isEmpty(item.getId())) {
            item = ItemDTO.builder().build();
        }

        return item;
    }

    private EmpresaDTO validarValorComboEmpresa(EmpresaDTO empresa) {

        if (Strings.isEmpty(empresa.getId())) {
            empresa = EmpresaDTO.builder().build();
        }

        return empresa;
    }

    private CidadeDTO validarValorComboCidade(CidadeDTO cidade) {

        if (Strings.isEmpty(cidade.getId())) {
            cidade = CidadeDTO.builder().build();
        }

        return cidade;
    }

    @Override
    public String getMensagemSucessoSalvar() {
        return MENSAGEM_SUCESSO_VINCULO;
    }

    @Override
    public String getMensagemSucessoApagar() {
        return MENSAGEM_SUCESSO_APAGAR_VINCULO;
    }

    @Override
    public String getMensagemPerguntaApagar() {
        return MENSAGEM_PERGUNTA_APAGAR;
    }

    private void inicializarColunas() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        empresaColumn.setCellValueFactory(new PropertyValueFactory<>("empresa"));
        cidadeColumn.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
        valorColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));
    }

    public void buscarFiltrado() {
        this.filtro.setOnKeyReleased(
                e -> realizarBuscaComFiltro(VinculoEnum.nome.name(), this.filtro.getText())
        );
    }

    @Override
    protected void realizarBuscaComFiltro(String field, String filtro) {
        if (filtro.length() >= SIZE_PARA_INICIAR_BUSCA) {

            Filter filtroLikePorNome =
                    this.carregarFiltroString(field, filtro);

            this.carregarTabelaVinculo(this.service.buscarFiltrado(filtroLikePorNome).stream()
                    .map(vinculoDTO -> tabelaVinculoMapper.toDTO(vinculoDTO)).collect(Collectors.toList()));
        } else if (filtro.isEmpty()) {
            this.carregarTabelaVinculo(this.service.findAll().stream()
                            .map(vinculoDTO -> tabelaVinculoMapper.toDTO(vinculoDTO))
                    .collect(Collectors.toList()));
        }
    }

    public void carregarTabelaVinculo(List<TabelaVinculoDTO> itens) {
        this.tabela.setItems(FXCollections.observableList(itens));
    }

    private void carregarComboEmpresa() {
        List<EmpresaDTO> empresas = this.empresaService.findAll();
        this.auxiliarComboBoxUtil.carregarComboComEmpresa(empresas, this.empresa);
    }

    private void carregarComboCidade() {
        List<CidadeDTO> itens = this.cidadeService.findAll();
        this.auxiliarComboBoxUtil.carregarComboComCidade(itens, this.cidade);
    }

    private void carregarComboItem() {
        List<ItemDTO> itens = this.itemService.findAll();
        this.auxiliarComboBoxUtil.carregarComboComItem(itens, this.item);
    }
}
