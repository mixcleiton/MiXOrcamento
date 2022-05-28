package br.com.cleiton.mixorcamento.modelo;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Field;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Index;
import dev.morphia.annotations.IndexOptions;
import dev.morphia.annotations.Indexes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(value = "empresa")
@Indexes(@Index(options = @IndexOptions(name = "nome"), fields = @Field("nome")))
public class Empresa {

    @Id
    private ObjectId id;
    private String nome;
    private String cnpj;
    private String cidade;
    private String bairro;
    private String endereco;

}
