package br.com.cleiton.mixorcamento.service;

import br.com.cleiton.mixorcamento.dto.EmpresaDTO;
import br.com.cleiton.mixorcamento.exception.CampoObrigatorioException;
import br.com.cleiton.mixorcamento.exception.ListaCampoObrigatorioException;
import br.com.cleiton.mixorcamento.mapper.EmpresaMapper;
import br.com.cleiton.mixorcamento.modelo.Empresa;
import br.com.cleiton.mixorcamento.repository.EmpresaRepository;

import java.util.ArrayList;
import java.util.List;

public class EmpresaService extends BaseService<Empresa,
        EmpresaRepository,
        EmpresaMapper,
        EmpresaDTO> {

    public EmpresaService() {
        super(EmpresaRepository.getInstancia(),
                EmpresaMapper.getInstancia());
    }

    @Override
    public void validarDTO (EmpresaDTO dto) throws ListaCampoObrigatorioException {

        List<CampoObrigatorioException> camposObrigatorio = new ArrayList<>();

        if (dto.getNome() == null || dto.getNome().isEmpty()) {
            camposObrigatorio.add(new CampoObrigatorioException("nome", "Campo nome é obrigatório"));
        }

        if (dto.getCnpj() == null || dto.getCnpj().isEmpty()) {
            camposObrigatorio.add(new CampoObrigatorioException("cnpj", "Campo cnpj é obrigatório"));
        }

        if (dto.getCidade() == null || dto.getCidade().isEmpty()) {
            camposObrigatorio.add(new CampoObrigatorioException("cidade", "Campo cidade é obrigatório"));
        }

        if (dto.getBairro() == null || dto.getBairro().isEmpty()) {
            camposObrigatorio.add(new CampoObrigatorioException("bairro", "Campo bairro é obrigatório"));
        }

        if (dto.getEndereco() == null || dto.getEndereco().isEmpty()) {
            camposObrigatorio.add(new CampoObrigatorioException("endereco", "Campo endereço é obrigatório"));
        }

        if (!camposObrigatorio.isEmpty()) {
            throw new ListaCampoObrigatorioException(camposObrigatorio);
        }
    }

}
