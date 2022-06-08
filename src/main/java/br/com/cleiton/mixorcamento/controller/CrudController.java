package br.com.cleiton.mixorcamento.controller;

import br.com.cleiton.mixorcamento.dto.BaseDTO;
import br.com.cleiton.mixorcamento.exception.ListaCampoObrigatorioException;
import br.com.cleiton.mixorcamento.service.BaseService;
import br.com.cleiton.mixorcamento.util.MensagemUtil;
import dev.morphia.query.experimental.filters.Filter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import java.util.List;

public abstract class CrudController<T, D extends BaseDTO> extends BaseController {

    private static final Logger logger = LogManager.getLogger(CrudController.class);
    public static final int MAX_LENGTH_10 = 10;
    public static final int MAX_LENGTH_100 = 100;
    public static final int MAX_LENGTH_255 = 255;
    public static final int SIZE_PARA_INICIAR_BUSCA = 3;

    @FXML
    protected TableView<D> tabela;

    protected BaseService<T, D> service;

    protected CrudController(BaseService<T, D> service) {
        this.service = service;
    }

    public void carregarDados() {
        List<D> itens = this.service.findAll();
        this.carregarTabela(itens);
    }

    public void carregarTabela(List<D> itens) {
        this.tabela.setItems(FXCollections.observableList(itens));
    }

    protected void carregarEventoTabela() {
        this.tabela.setRowFactory( tv -> {
            TableRow<D> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    carregarEditar(row.getItem());
                }
            });

            return row;
        });
    }

    public abstract void carregarCamposNoEditar(D item);
    public abstract String carregarMensagemEditar(D item);

    public void carregarEditar(D item) {
        String mensagem = this.carregarMensagemEditar(item);
        if (Boolean.TRUE.equals(MensagemUtil.mostrarMensagemPergunta(mensagem))) {
            this.carregarCamposNoEditar(item);
        }
    }

    @Override
    protected void editar() {
        this.modoEditar = Boolean.TRUE;
        this.bloquearEditar(Boolean.TRUE);
        this.bloquearCampos(Boolean.FALSE);
    }

    public abstract D carregarDTO();
    public abstract String getMensagemSucessoSalvar();
    public abstract String getMensagemSucessoApagar();
    public abstract String getMensagemPerguntaApagar();

    @Override
    protected void salvar() {
        try {
            this.service.salvar(this.carregarDTO(), modoEditar);
            this.limpar();
            this.carregarDados();
            this.modoEditar = Boolean.FALSE;
            MensagemUtil.mostrarMensagemSucesso(this.getMensagemSucessoSalvar());
        } catch (ListaCampoObrigatorioException e) {
            MensagemUtil.mostrarMensagemCamposObrigatorios(e);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    protected  void apagar() {
        if (MensagemUtil.mostrarMensagemPergunta(this.getMensagemPerguntaApagar())) {
            this.service.apagar(this.carregarDTO());
            this.limpar();
            this.carregarDados();
            this.modoEditar = Boolean.FALSE;
            MensagemUtil.mostrarMensagemSucesso(this.getMensagemSucessoApagar());
        }
    }

    protected void realizarBuscaComFiltro(String field, String filtro) {
        if (filtro.length() >= SIZE_PARA_INICIAR_BUSCA) {

            Filter filtroLikePorNome =
                    this.carregarFiltroString(field, filtro);

            this.carregarTabela(this.service.buscarFiltrado(filtroLikePorNome));
        } else if (filtro.isEmpty()) {
            this.carregarTabela(this.service.findAll());
        }
    }

    protected String getIdValidado(String texto) {
        String idValidado = texto;

        if (Strings.isEmpty(texto)) {
            idValidado = null;
        }

        return idValidado;
    }
}
