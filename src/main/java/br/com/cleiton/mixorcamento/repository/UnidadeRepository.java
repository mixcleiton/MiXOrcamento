package br.com.cleiton.mixorcamento.repository;

import br.com.cleiton.mixorcamento.modelo.Unidade;

public class UnidadeRepository extends MongoRepository<Unidade> {

    private static UnidadeRepository instancia;

    private UnidadeRepository() {}

    public static UnidadeRepository getInstancia() {

        if (instancia == null) {
            instancia = new UnidadeRepository();
        }

        return instancia;
    }

}
