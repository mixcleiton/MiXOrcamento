package br.com.cleiton.mixorcamento.modelo;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Field;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Index;
import dev.morphia.annotations.IndexOptions;
import dev.morphia.annotations.Indexes;
import dev.morphia.utils.IndexType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(value = "item")
@Indexes(@Index(options = @IndexOptions(name = "nome"), fields = @Field(value = ("nome"), type = IndexType.TEXT)))
public class Item {

    @Id
    private ObjectId id;
    private String nome;
    private String descricao;
    private Unidade unidade;
    private BigDecimal valorPadrao;
    private Boolean naoVaria;

}