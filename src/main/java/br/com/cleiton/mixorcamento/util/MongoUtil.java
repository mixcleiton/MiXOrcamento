package br.com.cleiton.mixorcamento.util;

import br.com.cleiton.mixorcamento.enums.PropertiesEnum;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

public class MongoUtil {
    private static final Logger logger = LogManager.getLogger(MongoUtil.class);
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;

    private MongoUtil() {}

    public static MongoClient carregarConexao() {
        logger.debug("Carregando conexão");

        return
                MongoClients.create(PropertiesUtil.prop.getProperty(PropertiesEnum.MONGO_URL.getPropertie()));
    }

    public static MongoDatabase carregarDatabase(String database) {
        validarConexao();
        return mongoClient.getDatabase(database);
    }

    public static MongoCollection<Document> carregarCollection(String collection) {
        validarDatabase();

        return mongoDatabase.getCollection(collection);
    }

    private static void validarDatabase() {
        String database = PropertiesUtil.prop.getProperty(PropertiesEnum.MONGO_DATABASE.getPropertie());
        if (mongoDatabase == null) {
            mongoDatabase = carregarDatabase(database);
        }
    }

    private static void validarConexao() {
        if (mongoClient == null) {
            mongoClient = carregarConexao();
        }
    }


}
