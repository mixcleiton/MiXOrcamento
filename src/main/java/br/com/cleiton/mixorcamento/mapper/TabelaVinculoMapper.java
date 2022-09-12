package br.com.cleiton.mixorcamento.mapper;

import br.com.cleiton.mixorcamento.dto.CidadeDTO;
import br.com.cleiton.mixorcamento.dto.EmpresaDTO;
import br.com.cleiton.mixorcamento.dto.ItemDTO;
import br.com.cleiton.mixorcamento.dto.TabelaVinculoDTO;
import br.com.cleiton.mixorcamento.dto.VinculoDTO;
import org.apache.logging.log4j.util.Strings;

import java.math.BigDecimal;

public class TabelaVinculoMapper {

    private static TabelaVinculoMapper instancia;

    private TabelaVinculoMapper() {}

    public static TabelaVinculoMapper getInstancia() {

        if (instancia == null) {
            instancia = new TabelaVinculoMapper();
        }

        return instancia;
    }

    public TabelaVinculoDTO toDTO(VinculoDTO modelo) {
        return TabelaVinculoDTO.builder()
                .id(modelo.getId())
                .cidade(this.getNomeCidade(modelo.getCidade()))
                .empresa(this.getNomeEmpresa(modelo.getEmpresa()))
                .item(this.getNomeItem(modelo.getItem()))
                .nome(modelo.getNome())
                .valor(this.getValor(modelo.getValor()))
                .build();
    }

    private String getValor(BigDecimal valor) {
        String retorno = "";

        if (valor != null) {
            retorno = valor.toString();
        }

        return retorno;
    }

    private String getNomeCidade(CidadeDTO cidade) {
        String nome = "";

        if (cidade != null && Strings.isNotEmpty(cidade.getNome())) {
            nome = cidade.getNome();
        }

        return nome;
    }

    private String getNomeEmpresa(EmpresaDTO empresa) {
        String nome = "";

        if (empresa != null && Strings.isNotEmpty(empresa.getNome())) {
            nome = empresa.getNome();
        }

        return nome;
    }

    private String getNomeItem(ItemDTO item) {
        String nome = "";

        if (item != null && Strings.isNotEmpty(item.getNome())) {
            nome = item.getNome();
        }

        return nome;
    }
}
