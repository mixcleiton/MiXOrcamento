package br.com.cleiton.mixorcamento.controller;

import br.com.cleiton.mixorcamento.dto.BaseDTO;
import br.com.cleiton.mixorcamento.exception.ListaCampoObrigatorioException;
import br.com.cleiton.mixorcamento.service.BaseService;
import br.com.cleiton.mixorcamento.util.MensagemUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public abstract class CrudController<T extends BaseDTO> extends BaseController {

    private static final Logger logger = LogManager.getLogger(CrudController.class);

    @FXML
    protected TableView<T> tabela;

    protected BaseService service;

    public CrudController(BaseService service) {
        this.service = service;
    }

    public void carregarDados() {
        List<T> itens = this.service.findAll();
        this.carregarTabela(itens);
    }

    public void carregarTabela(List<T> itens) {
        this.tabela.setItems(FXCollections.observableList(itens));
    }

    protected void carregarEventoTabela() {
        this.tabela.setRowFactory( tv -> {
            TableRow<T> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    carregarEditar(row.getItem());
                }
            });

            return row;
        });
    }

    public abstract void carregarCamposNoEditar(T item);
    public abstract String carregarMensagemEditar(T item);

    public void carregarEditar(T item) {
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

    public abstract T carregarDTO();
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
}
