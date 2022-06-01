package br.com.cleiton.mixorcamento.repository;

import br.com.cleiton.mixorcamento.util.MongoUtil;
import dev.morphia.query.FindOptions;
import dev.morphia.query.experimental.filters.Filter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

public abstract class MongoRepository<T> {
    private static final Logger logger = LogManager.getLogger(MongoRepository.class);

    private Class<T> genericType;

    protected MongoRepository() {
        this.genericType = ((Class) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public void inserir(T document) {
        logger.info("Chamando o método inserir, document: {}", document);
        MongoUtil.getInstancia().criarConexao().insert(document);
    }

    public void atualizar(T documento) {
        MongoUtil.getInstancia().criarConexao().merge(documento);
    }

    private Document getId(String id) {
        return new Document().append("_id", new ObjectId(id));
    }

    public List<T> findAll() {
        return MongoUtil.getInstancia()
                .criarConexao()
                .find(genericType)
                .stream()
                .collect(Collectors.toList());
    }

    public void deletar(T document) {
        MongoUtil.getInstancia().criarConexao().delete(document);
    }

    public List<T> buscarFiltrado(FindOptions findOptions, Filter filtros) {
        return MongoUtil.getInstancia().criarConexao()
                .find(genericType)
                .filter(filtros)
                .iterator(findOptions)
                .toList();
    }
}
