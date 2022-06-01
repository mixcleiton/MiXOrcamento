package br.com.cleiton.mixorcamento.enums;

import lombok.Getter;

@Getter
public enum PropertiesEnum {

    MONGO_URL("mongo.url"),
    MONGO_DATABASE("mongo.database"),
    MONGO_PACKAGES_MODELOS("mongo.packages.modelos");

    private String propertie;

    PropertiesEnum (String propertie) {
        this.propertie = propertie;
    }
}
