package br.com.cleiton.mixorcamento.service;

import br.com.cleiton.mixorcamento.dto.CidadeDTO;
import br.com.cleiton.mixorcamento.exception.ListaCampoObrigatorioException;
import br.com.cleiton.mixorcamento.mapper.CidadeMapper;
import br.com.cleiton.mixorcamento.modelo.Cidade;
import br.com.cleiton.mixorcamento.repository.CidadeRepository;

public class CidadeService extends  BaseService<Cidade,
        CidadeRepository,
        CidadeMapper,
        CidadeDTO>{

    public CidadeService() {
        super(CidadeRepository.getInstancia(),
                CidadeMapper.getInstancia());
    }

    @Override
    public void validarDTO(CidadeDTO dto) throws ListaCampoObrigatorioException {

    }
}
