package br.com.cleiton.mixorcamento.mapper;

import br.com.cleiton.mixorcamento.dto.BaseDTO;
import org.apache.logging.log4j.util.Strings;
import org.bson.types.ObjectId;

public abstract class AbstractBaseMapper<T, D extends BaseDTO> implements  BaseMapper<T, D> {

    protected ObjectId criarObjectId(String id) {

        ObjectId novoId = new ObjectId();

        if (!Strings.isEmpty(id)) {
            novoId = new ObjectId(id);
        }

        return novoId;
    }

}
