package br.com.cleiton.mixorcamento.service;

import br.com.cleiton.mixorcamento.dto.ItemDTO;
import br.com.cleiton.mixorcamento.exception.CampoObrigatorioException;
import br.com.cleiton.mixorcamento.exception.ListaCampoObrigatorioException;
import br.com.cleiton.mixorcamento.mapper.ItemMapper;
import br.com.cleiton.mixorcamento.modelo.Item;
import br.com.cleiton.mixorcamento.repository.ItemRepository;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;

public class ItemService extends BaseService<Item, ItemDTO>{

    private static ItemService instancia;

    private ItemService() {
        super(ItemRepository.getInstancia(),
                ItemMapper.getInstancia());
    }

    public static ItemService getInstancia() {
        if (instancia == null) {
            instancia = new ItemService();
        }

        return instancia;
    }

    @Override
    public void validarDTO(ItemDTO dto) throws ListaCampoObrigatorioException {

        List<CampoObrigatorioException> camposObrigatorio = new ArrayList<>();

        if (Strings.isEmpty(dto.getNome())) {
            camposObrigatorio.add(new CampoObrigatorioException("nome", "Campo nome é obrigatório"));
        }

        if (dto.getUnidade() == null || Strings.isEmpty(dto.getUnidade().getId())) {
            camposObrigatorio.add(new CampoObrigatorioException("unidade", "Campo Unidade é obrigatório"));
        }

        if ((dto.getNaoVaria() == null || !dto.getNaoVaria()) && dto.getValorPadrao() == null) {
            camposObrigatorio.add(new CampoObrigatorioException("valor", "Campo Não Vária ou Valor Padrão, deve ser preenchido"));
        }

        if (Strings.isEmpty(dto.getDescricao())) {
            camposObrigatorio.add(new CampoObrigatorioException("descricao", "Campo Descrição é obrigatório"));
        }

        validarListaCamposObrigatorios(camposObrigatorio);

    }
}