package br.com.cleiton.mixorcamento.service;

import br.com.cleiton.mixorcamento.dto.UnidadeDTO;
import br.com.cleiton.mixorcamento.exception.ListaCampoObrigatorioException;
import br.com.cleiton.mixorcamento.mapper.UnidadeMapper;
import br.com.cleiton.mixorcamento.modelo.Unidade;
import br.com.cleiton.mixorcamento.repository.UnidadeRepository;

public class UnidadeService extends BaseService<Unidade, UnidadeDTO> {

    private static UnidadeService instancia;

    private UnidadeService() {
        super(UnidadeRepository.getInstancia(),
                UnidadeMapper.getInstancia());
    }

    public static UnidadeService getInstancia() {
        if (instancia == null) {
            instancia = new UnidadeService();
        }

        return instancia;
    }

    @Override
    public void validarDTO(UnidadeDTO dto) throws ListaCampoObrigatorioException {

    }
}
