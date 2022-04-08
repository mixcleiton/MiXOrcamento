package br.com.cleiton.mixorcamento.modelo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

@SuperBuilder
@Data
public abstract class BaseModelo {

    @SerializedName("_id")
    private ObjectId id;

}
