package br.com.cleiton.mixorcamento.util;

import dev.morphia.query.experimental.filters.Filter;
import dev.morphia.query.experimental.filters.Filters;

import java.util.regex.Pattern;

public class RegexUtil {

    private static RegexUtil instancia;

    private RegexUtil() {}

    public static RegexUtil getInstancia() {
        if (instancia == null) {
            instancia = new RegexUtil();
        }

        return instancia;
    }

    public Filter getFiltroLike(String field, String valor) {
        Pattern regex = Pattern.compile("^.*".concat(valor));
        return Filters.regex(field).pattern(regex).caseInsensitive();
    }

}
