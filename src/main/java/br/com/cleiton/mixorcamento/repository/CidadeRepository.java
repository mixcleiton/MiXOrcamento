package br.com.cleiton.mixorcamento.repository;

import br.com.cleiton.mixorcamento.modelo.Cidade;

public class CidadeRepository extends MongoRepository<Cidade> {

    private static CidadeRepository INSTANCIA;

    private CidadeRepository() {}

    public static CidadeRepository getInstancia() {

        if (INSTANCIA == null) {
            INSTANCIA = new CidadeRepository();
        }

        return INSTANCIA;
    }
}
