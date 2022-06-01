package br.com.cleiton.mixorcamento.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public abstract class BaseDTO {

    private String id;

}
