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

    private MongoRepository mongoRepository;
    private BaseMapper baseMapper;

    public BaseService(MongoRepository repository, BaseMapper mapper) {
        this.mongoRepository = repository;
        this.baseMapper = mapper;
    }

    public List<D> findAll() {
        return
                (List<D>) this.mongoRepository.findAll()
                        .stream()
                        .map(entidade -> this.baseMapper.toDTO(entidade))
                        .collect(Collectors.toList());
    }

    public abstract void validarDTO(D dto) throws ListaCampoObrigatorioException;

    public void salvar(D dto, Boolean editar) throws ListaCampoObrigatorioException {
        this.validarDTO(dto);

        if (editar != null && editar) {
            this.mongoRepository.atualizar(this.baseMapper.toModelo(dto));
        } else {
            this.mongoRepository.inserir(this.baseMapper.toModelo(dto));
        }
    }

    public void apagar(D dto) {
        this.mongoRepository.deletar(this.baseMapper.toModelo(dto));
    }

    public List<D> buscarFiltrado(FindOptions findOptions, Filter filtros) {
        return (List<D>) this.mongoRepository.buscarFiltrado(findOptions, filtros)
                .stream()
                .map(dto -> this.baseMapper.toDTO(dto))
                .collect(Collectors.toList());
    }

    public List<D> buscarFiltrado(Filter filtros) {
        return (List<D>) this.mongoRepository.buscarFiltrado(new FindOptions(), filtros)
                .stream()
                .map(dto -> this.baseMapper.toDTO(dto))
                .collect(Collectors.toList());
    }

}
