package br.com.cleiton.mixorcamento.repository;

import br.com.cleiton.mixorcamento.modelo.Empresa;

public class EmpresaRepository extends MongoRepository<Empresa> {

    private static EmpresaRepository INSTANCIA;

    private EmpresaRepository() {}

    public static EmpresaRepository getInstancia() {

        if (INSTANCIA == null) {
            INSTANCIA = new EmpresaRepository();
        }

        return INSTANCIA;
    }

}
