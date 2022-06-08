package br.com.cleiton.mixorcamento.repository;

import br.com.cleiton.mixorcamento.modelo.Item;

public class ItemRepository extends MongoRepository<Item>{
    private static ItemRepository instancia;

    private ItemRepository() {}

    public static ItemRepository getInstancia() {

        if (instancia == null) {
            instancia = new ItemRepository();
        }

        return instancia;
    }
}