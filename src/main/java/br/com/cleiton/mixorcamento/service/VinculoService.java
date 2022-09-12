package br.com.cleiton.mixorcamento.service;

import br.com.cleiton.mixorcamento.dto.VinculoDTO;
import br.com.cleiton.mixorcamento.exception.CampoObrigatorioException;
import br.com.cleiton.mixorcamento.exception.ListaCampoObrigatorioException;
import br.com.cleiton.mixorcamento.mapper.VinculoMapper;
import br.com.cleiton.mixorcamento.modelo.Vinculo;
import br.com.cleiton.mixorcamento.repository.VinculoRepository;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;

public class VinculoService extends BaseService<Vinculo, VinculoDTO>{

    private static VinculoService instancia;

    private VinculoService() {
        super(VinculoRepository.getInstancia(),
                VinculoMapper.getInstancia());
    }

    public static VinculoService getInstancia() {
        if (instancia == null) {
            instancia = new VinculoService();
        }

        return instancia;
    }

    @Override
    public void validarDTO(VinculoDTO dto) throws ListaCampoObrigatorioException {

        List<CampoObrigatorioException> camposObrigatorio = new ArrayList<>();

        if ((dto.getEmpresa() == null || Strings.isEmpty(dto.getEmpresa().getId()))
            && (dto.getCidade() == null || Strings.isEmpty(dto.getCidade().getId()))) {
            camposObrigatorio.add(new CampoObrigatorioException("empresa", "Preencher campo Empresa ou Cidade"));
        }

        if (dto.getItem() == null || Strings.isEmpty(dto.getItem().getId())) {
            camposObrigatorio.add(new CampoObrigatorioException("item", "Campo Item é obrigatório"));
        }

        this.validarListaCamposObrigatorios(camposObrigatorio);

    }
}
