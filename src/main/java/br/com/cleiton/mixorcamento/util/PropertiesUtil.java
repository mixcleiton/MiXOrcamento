package br.com.cleiton.mixorcamento.util;

import br.com.cleiton.mixorcamento.enums.PropertiesEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    private PropertiesUtil() {}

    private static final Logger logger = LogManager.getLogger(PropertiesUtil.class);
    protected static final Properties prop = new Properties();
    public static final String MENSAGEM_ERRO = "Erro ao carregar as propriedades";

    public static void carregarApplicationProperties () {

        try (InputStream input = MongoUtil.class.getClassLoader().getResourceAsStream("application.properties")) {

            if (input == null) {
                logger.error(MENSAGEM_ERRO);
                return;
            }

            prop.load(input);

        } catch (IOException e) {
            logger.error(MENSAGEM_ERRO, e);
        }

    }

    public static String getValor(PropertiesEnum propertiesEnum) {
        return prop.getProperty(propertiesEnum.getPropertie());
    }

}
