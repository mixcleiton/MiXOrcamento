package br.com.cleiton.mixorcamento.repository;

import br.com.cleiton.mixorcamento.modelo.Empresa;

public class EmpresaRepository extends MongoRepository<Empresa> {

    private static EmpresaRepository instancia;

    private EmpresaRepository() {}

    public static EmpresaRepository getInstancia() {

        if (instancia == null) {
            instancia = new EmpresaRepository();
        }

        return instancia;
    }

}
