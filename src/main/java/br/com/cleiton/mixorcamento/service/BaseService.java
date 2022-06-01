package br.com.cleiton.mixorcamento.service;

import br.com.cleiton.mixorcamento.dto.BaseDTO;
import br.com.cleiton.mixorcamento.exception.ListaCampoObrigatorioException;
import br.com.cleiton.mixorcamento.mapper.BaseMapper;
import br.com.cleiton.mixorcamento.repository.MongoRepository;
import dev.morphia.query.FindOptions;
import dev.morphia.query.experimental.filters.Filter;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseService<T,
        R extends MongoRepository,
        M extends BaseMapper,
        D extends BaseDTO> {

    protected BaseMapper mapper;
    protected MongoRepository repository;

    public BaseService(BaseMapper mapper, MongoRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public List<D> findAll() {
        return
                (List<D>) repository.findAll()
                        .stream()
                        .map(entidade -> mapper.toDTO(entidade))
                        .collect(Collectors.toList());
    }

    public abstract void validarDTO(D dto) throws ListaCampoObrigatorioException;

    public void salvar(D dto, Boolean editar) throws ListaCampoObrigatorioException {
        this.validarDTO(dto);

        if (editar != null && editar) {
            this.repository.atualizar(this.mapper.toModelo(dto));
        } else {
            this.repository.inserir(this.mapper.toModelo(dto));
        }
    }

    public void apagar(D dto) {
        this.repository.deletar(this.mapper.toModelo(dto));
    }

    public List<D> buscarFiltrado(FindOptions findOptions, Filter filtros) {
        return (List<D>) this.repository.buscarFiltrado(findOptions, filtros)
                .stream()
                .map(dto -> this.mapper.toDTO(dto))
                .collect(Collectors.toList());
    }

    public List<D> buscarFiltrado(Filter filtros) {
        return (List<D>) this.repository.buscarFiltrado(new FindOptions(), filtros)
                .stream()
                .map(dto -> this.mapper.toDTO(dto))
                .collect(Collectors.toList());
    }

}
