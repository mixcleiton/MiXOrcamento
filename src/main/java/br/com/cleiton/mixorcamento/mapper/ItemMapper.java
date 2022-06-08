package br.com.cleiton.mixorcamento.mapper;

import br.com.cleiton.mixorcamento.dto.ItemDTO;
import br.com.cleiton.mixorcamento.modelo.Item;

public class ItemMapper extends AbstractBaseMapper<Item, ItemDTO> {

    private static ItemMapper instancia;

    private ItemMapper() {}

    public static ItemMapper getInstancia() {

        if (instancia == null) {
            instancia = new ItemMapper();
        }

        return instancia;
    }

    @Override
    public ItemDTO toDTO(Item modelo) {
        return ItemDTO.builder()
                .nome(modelo.getNome())
                .naoVaria(modelo.getNaoVaria())
                .descricao(modelo.getDescricao())
                .id(modelo.getId().toString())
                .unidade(UnidadeMapper.getInstancia().toDTO(modelo.getUnidade()))
                .valorPadrao(modelo.getValorPadrao())
                .build();
    }

    @Override
    public Item toModelo(ItemDTO dto) {
        return Item.builder()
                .descricao(dto.getDescricao())
                .valorPadrao(dto.getValorPadrao())
                .nome(dto.getNome())
                .id(this.criarObjectId(dto.getId()))
                .naoVaria(dto.getNaoVaria())
                .unidade(UnidadeMapper.getInstancia().toModelo(dto.getUnidade()))
                .build();
    }
}
