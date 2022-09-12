package br.com.cleiton.mixorcamento.mapper;

import br.com.cleiton.mixorcamento.dto.VinculoDTO;
import br.com.cleiton.mixorcamento.modelo.Vinculo;

public class VinculoMapper extends  AbstractBaseMapper <Vinculo, VinculoDTO>{

    private static VinculoMapper instancia;

    private VinculoMapper() {}

    public static VinculoMapper getInstancia() {

        if (instancia == null) {
            instancia = new VinculoMapper();
        }

        return instancia;
    }

    @Override
    public VinculoDTO toDTO(Vinculo modelo) {
        return VinculoDTO.builder()
                .empresa(EmpresaMapper.getInstancia().toDTO(modelo.getEmpresa()))
                .item(ItemMapper.getInstancia().toDTO(modelo.getItem()))
                .cidade(CidadeMapper.getInstancia().toDTO(modelo.getCidade()))
                .valor(modelo.getValor())
                .id(modelo.getId().toString())
                .nome(modelo.getNome())
                .build();
    }

    @Override
    public Vinculo toModelo(VinculoDTO dto) {
        return Vinculo.builder()
                .empresa(EmpresaMapper.getInstancia().toModelo(dto.getEmpresa()))
                .item(ItemMapper.getInstancia().toModelo(dto.getItem()))
                .cidade(CidadeMapper.getInstancia().toModelo(dto.getCidade()))
                .valor(dto.getValor())
                .id(this.criarObjectId(dto.getId()))
                .nome(dto.getNome())
                .build();
    }
}
