package br.com.cleiton.mixorcamento.repository;

import br.com.cleiton.mixorcamento.modelo.Vinculo;

public class VinculoRepository extends MongoRepository<Vinculo> {

    private static VinculoRepository instancia;

    private VinculoRepository() {}

    public static VinculoRepository getInstancia() {

        if (instancia == null) {
            instancia = new VinculoRepository();
        }

        return instancia;
    }

}
