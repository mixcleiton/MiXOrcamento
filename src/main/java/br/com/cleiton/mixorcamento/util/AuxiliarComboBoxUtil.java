package br.com.cleiton.mixorcamento.util;

import br.com.cleiton.mixorcamento.dto.CidadeDTO;
import br.com.cleiton.mixorcamento.dto.EmpresaDTO;
import br.com.cleiton.mixorcamento.dto.ItemDTO;
import br.com.cleiton.mixorcamento.dto.UnidadeDTO;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AuxiliarComboBoxUtil {

    private static AuxiliarComboBoxUtil instance;


    private AuxiliarComboBoxUtil() {
    }

    public static AuxiliarComboBoxUtil getInstance() {
        if (instance == null) {
            instance = new AuxiliarComboBoxUtil();
        }

        return instance;
    }

    @NotNull
    public ListCell<CidadeDTO> getCidadesListCell() {
        return new ListCell<>() {
            @Override
            protected void updateItem(CidadeDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getNome());
                } else {
                    setText(null);
                }
            }
        };
    }

    @NotNull
    public ListCell<UnidadeDTO> getUnidadesListCell() {
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

    @NotNull
    public ListCell<EmpresaDTO> getEmpresaListCell() {
        return new ListCell<>() {
            @Override
            protected void updateItem(EmpresaDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getNome());
                } else {
                    setText(null);
                }
            }
        };
    }

    @NotNull
    public ListCell<ItemDTO> getItemListCell() {
        return new ListCell<>() {
            @Override
            protected void updateItem(ItemDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getNome());
                } else {
                    setText(null);
                }
            }
        };
    }

    public void carregarComboComUnidade(List<UnidadeDTO> itens, ComboBox<UnidadeDTO> comboBox) {
        comboBox.getItems().clear();
        comboBox.getItems().addAll(itens);
        comboBox.getSelectionModel().selectFirst();
        comboBox.setCellFactory(param -> AuxiliarComboBoxUtil.getInstance().getUnidadesListCell());
        comboBox.setButtonCell(AuxiliarComboBoxUtil.getInstance().getUnidadesListCell());
    }

    public void carregarComboComEmpresa(List<EmpresaDTO> itens, ComboBox<EmpresaDTO> comboBox) {
        comboBox.getItems().clear();
        comboBox.getItems().add(EmpresaDTO.builder().nome("Selecione um Item").build());
        comboBox.getItems().addAll(itens);
        comboBox.getSelectionModel().selectFirst();
        comboBox.setCellFactory(param -> AuxiliarComboBoxUtil.getInstance().getEmpresaListCell());
        comboBox.setButtonCell(AuxiliarComboBoxUtil.getInstance().getEmpresaListCell());
    }

    public void carregarComboComCidade(List<CidadeDTO> itens, ComboBox<CidadeDTO> comboBox) {
        comboBox.getItems().clear();
        comboBox.getItems().add(CidadeDTO.builder().nome("Selecione um Item").build());
        comboBox.getItems().addAll(itens);
        comboBox.getSelectionModel().selectFirst();
        comboBox.setCellFactory(param -> AuxiliarComboBoxUtil.getInstance().getCidadesListCell());
        comboBox.setButtonCell(AuxiliarComboBoxUtil.getInstance().getCidadesListCell());
    }

    public void carregarComboComItem(List<ItemDTO> itens, ComboBox<ItemDTO> comboBox) {
        comboBox.getItems().clear();
        comboBox.getItems().add(ItemDTO.builder().nome("Selecione um Item").build());
        comboBox.getItems().addAll(itens);
        comboBox.getSelectionModel().selectFirst();
        comboBox.setCellFactory(param -> AuxiliarComboBoxUtil.getInstance().getItemListCell());
        comboBox.setButtonCell(AuxiliarComboBoxUtil.getInstance().getItemListCell());
    }

}
