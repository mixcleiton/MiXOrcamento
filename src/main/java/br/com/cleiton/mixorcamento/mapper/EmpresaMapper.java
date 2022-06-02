package br.com.cleiton.mixorcamento.mapper;

import br.com.cleiton.mixorcamento.dto.EmpresaDTO;
import br.com.cleiton.mixorcamento.modelo.Empresa;

public class EmpresaMapper extends AbstractBaseMapper<Empresa, EmpresaDTO> {

    private static EmpresaMapper instancia;

    private EmpresaMapper() {}

    public static EmpresaMapper getInstancia() {

        if (instancia == null) {
            instancia = new EmpresaMapper();
        }

        return instancia;
    }

    @Override
    public Empresa toModelo (EmpresaDTO dto) {
        return Empresa.builder()
                .bairro(dto.getBairro())
                .cnpj(dto.getCnpj())
                .cidade(dto.getCidade())
                .endereco(dto.getEndereco())
                .nome(dto.getNome())
                .id(this.criarObjectId(dto.getId())).build();
    }

    @Override
    public EmpresaDTO toDTO (Empresa modelo) {
        return EmpresaDTO.builder()
                .bairro(modelo.getBairro())
                .cnpj(modelo.getCnpj())
                .cidade(modelo.getCidade())
                .id(modelo.getId().toString())
                .endereco(modelo.getEndereco())
                .nome(modelo.getNome())
                .build();
    }

}
