package br.com.cleiton.mixorcamento.repository;

import br.com.cleiton.mixorcamento.modelo.Cidade;

public class CidadeRepository extends MongoRepository<Cidade> {

    private static CidadeRepository instancia;

    private CidadeRepository() {}

    public static CidadeRepository getInstancia() {

        if (instancia == null) {
            instancia = new CidadeRepository();
        }

        return instancia;
    }
}
