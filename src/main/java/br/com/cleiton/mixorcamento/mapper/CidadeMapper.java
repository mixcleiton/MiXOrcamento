package br.com.cleiton.mixorcamento.mapper;

import br.com.cleiton.mixorcamento.dto.CidadeDTO;
import br.com.cleiton.mixorcamento.modelo.Cidade;

public class CidadeMapper extends AbstractBaseMapper<Cidade, CidadeDTO> {

    private static CidadeMapper INSTANCIA;

    private CidadeMapper(){}
    
    public static CidadeMapper getInstancia() {

        if (INSTANCIA == null) {
            INSTANCIA = new CidadeMapper();
        }

        return INSTANCIA;
    }

    @Override
    public CidadeDTO toDTO(Cidade modelo) {
        return CidadeDTO.builder()
                .nome(modelo.getNome())
                .id(modelo.getId().toString())
                .build();
    }

    @Override
    public Cidade toModelo(CidadeDTO dto) {
        return Cidade.builder()
                .nome(dto.getNome())
                .id(this.criarObjectId(dto.getId()))
                .build();
    }
}
