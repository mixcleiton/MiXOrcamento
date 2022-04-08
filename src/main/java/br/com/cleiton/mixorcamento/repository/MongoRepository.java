package br.com.cleiton.mixorcamento.repository;

import br.com.cleiton.mixorcamento.modelo.BaseModelo;
import br.com.cleiton.mixorcamento.util.MongoUtil;
import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class MongoRepository<T> {
    private static final Logger logger = LogManager.getLogger(MongoRepository.class);
    private Class<T> genericType;

    protected MongoRepository() {
        this.genericType = ((Class) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public void inserir(T document) {
        logger.info("Chamando o método inserir, document: {}", document);
        Document documento = Document.parse(new Gson().toJson(document));
        MongoUtil.carregarCollection(genericType.getSimpleName().toLowerCase())
                .insertOne(documento);
    }

    public void atualizar(T document) {
        Document documento = Document.parse(new Gson().toJson(document));
        MongoUtil.carregarCollection(genericType.getSimpleName().toLowerCase())
                .updateOne( this.getId(document), documento);
    }

    private Document getId(T document) {
        return new Document().append("_id", new ObjectId(String.valueOf(((BaseModelo) document).getId())));
    }

    public List<T> findAll() {
        FindIterable<Document> itens = MongoUtil.carregarCollection(genericType.getSimpleName().toLowerCase()).find();
        List<T> dados = new ArrayList<>();

        itens.forEach((Consumer)  item ->
            dados.add(
                    new Gson().fromJson(
                            ((Document) item).toJson(), genericType))
        );

        return dados;
    }

    public void deletar(T document) {
        Document documento = Document.parse(new Gson().toJson(document));
        MongoUtil.carregarCollection(genericType.getSimpleName().toLowerCase())
                .deleteOne(documento);
    }
}
