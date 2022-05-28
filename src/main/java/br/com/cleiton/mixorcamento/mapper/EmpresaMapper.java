package br.com.cleiton.mixorcamento.mapper;

import br.com.cleiton.mixorcamento.dto.EmpresaDTO;
import br.com.cleiton.mixorcamento.modelo.Empresa;
import org.apache.logging.log4j.util.Strings;
import org.bson.types.ObjectId;

public class EmpresaMapper implements BaseMapper<Empresa, EmpresaDTO> {

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

    private ObjectId criarObjectId(String id) {

        ObjectId novoId = new ObjectId();

        if (!Strings.isEmpty(id)) {
            novoId = new ObjectId(id);
        }

        return novoId;
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
