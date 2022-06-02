package br.com.cleiton.mixorcamento.mapper;

import br.com.cleiton.mixorcamento.dto.UnidadeDTO;
import br.com.cleiton.mixorcamento.modelo.Unidade;

public class UnidadeMapper extends  AbstractBaseMapper <Unidade, UnidadeDTO> {

    private static UnidadeMapper instancia;

    private UnidadeMapper() {}

    public static UnidadeMapper getInstancia() {

        if (instancia == null) {
            instancia = new UnidadeMapper();
        }

        return instancia;
    }

    @Override
    public UnidadeDTO toDTO(Unidade modelo) {
        return UnidadeDTO.builder()
                .nome(modelo.getNome())
                .codigo(modelo.getCodigo())
                .id(modelo.getId().toString())
                .build();
    }

    @Override
    public Unidade toModelo(UnidadeDTO dto) {
        return Unidade.builder()
                .codigo(dto.getCodigo())
                .nome(dto.getNome())
                .id(this.criarObjectId(dto.getId()))
                .build();
    }
}
