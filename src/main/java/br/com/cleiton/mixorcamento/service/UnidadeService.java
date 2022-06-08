package br.com.cleiton.mixorcamento.service;

import br.com.cleiton.mixorcamento.dto.UnidadeDTO;
import br.com.cleiton.mixorcamento.exception.CampoObrigatorioException;
import br.com.cleiton.mixorcamento.exception.ListaCampoObrigatorioException;
import br.com.cleiton.mixorcamento.mapper.UnidadeMapper;
import br.com.cleiton.mixorcamento.modelo.Unidade;
import br.com.cleiton.mixorcamento.repository.UnidadeRepository;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;

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
        List<CampoObrigatorioException> camposObrigatorio = new ArrayList<>();

        if (Strings.isEmpty(dto.getNome())) {
            camposObrigatorio.add(new CampoObrigatorioException("nome", "Campo Nome é obrigatório"));
        }

        if (Strings.isEmpty(dto.getCodigo())) {
            camposObrigatorio.add(new CampoObrigatorioException("codigo", "Campo Código é obrigatório"));
        }

        this.validarListaCamposObrigatorios(camposObrigatorio);
    }
}
