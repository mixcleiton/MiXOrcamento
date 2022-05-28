package br.com.cleiton.mixorcamento.util;

import br.com.cleiton.mixorcamento.enums.PropertiesEnum;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MongoUtil {
    private static final Logger logger = LogManager.getLogger(MongoUtil.class);

    private MongoUtil() {}
    private static Datastore datastore;

    public static MongoClient carregarConexao() {
        logger.debug("Carregando conexão do mongo");

        return
                MongoClients.create(PropertiesUtil.getValor(PropertiesEnum.MONGO_URL));
    }

    public static Datastore criarMorphiaDataStore() {

        return Morphia.createDatastore(carregarConexao(), PropertiesUtil.getValor(PropertiesEnum.MONGO_DATABASE));
    }

    public static Datastore criarConexao() {

        if (datastore == null) {
            datastore = criarMorphiaDataStore();
            datastore.getMapper()
                    .mapPackage(PropertiesUtil.getValor(PropertiesEnum.MONGO_PACKAGES_MODELOS));
            datastore.ensureIndexes();
        }

        return datastore;
    }

}
