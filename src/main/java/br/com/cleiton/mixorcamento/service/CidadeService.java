package br.com.cleiton.mixorcamento.service;

import br.com.cleiton.mixorcamento.dto.CidadeDTO;
import br.com.cleiton.mixorcamento.exception.CampoObrigatorioException;
import br.com.cleiton.mixorcamento.exception.ListaCampoObrigatorioException;
import br.com.cleiton.mixorcamento.mapper.CidadeMapper;
import br.com.cleiton.mixorcamento.modelo.Cidade;
import br.com.cleiton.mixorcamento.repository.CidadeRepository;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;

public class CidadeService extends  BaseService<Cidade, CidadeDTO>{

    private static CidadeService instancia;

    private CidadeService() {
        super(CidadeRepository.getInstancia(),
                CidadeMapper.getInstancia());
    }

    public static CidadeService getInstancia() {
        if (instancia == null) {
            instancia = new CidadeService();
        }

        return instancia;
    }

    @Override
    public void validarDTO(CidadeDTO dto) throws ListaCampoObrigatorioException {
        List<CampoObrigatorioException> camposObrigatorio = new ArrayList<>();

        if (Strings.isEmpty(dto.getNome())) {
            camposObrigatorio.add(new CampoObrigatorioException("nome", "Campo nome é obrigatório"));
        }

        this.validarListaCamposObrigatorios(camposObrigatorio);
    }
}
