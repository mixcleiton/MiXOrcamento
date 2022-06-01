package br.com.cleiton.mixorcamento.mapper;

import br.com.cleiton.mixorcamento.dto.BaseDTO;

public interface BaseMapper<T, D extends BaseDTO> {

    D toDTO (T modelo);

    T toModelo (D dto);

}
