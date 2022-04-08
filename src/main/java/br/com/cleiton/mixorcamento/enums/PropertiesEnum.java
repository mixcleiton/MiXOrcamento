package br.com.cleiton.mixorcamento.enums;

import lombok.Getter;

@Getter
public enum PropertiesEnum {

    MONGO_URL("mongo.url"),
    MONGO_DATABASE("mongo.database");

    private String propertie;

    PropertiesEnum (String propertie) {
        this.propertie = propertie;
    }
}
