package br.com.cleiton.mixorcamento.modelo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

@SuperBuilder
@Getter
@Setter
public abstract class BaseModelo {

    private ObjectId id;

}
