package br.com.cleiton.mixorcamento.util;

import dev.morphia.query.experimental.filters.Filter;
import dev.morphia.query.experimental.filters.Filters;

import java.util.regex.Pattern;

public class RegexUtil {

    private static RegexUtil INSTANCIA;

    private RegexUtil() {}

    public static RegexUtil getInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new RegexUtil();
        }

        return INSTANCIA;
    }

    public Filter getFiltroLike(String field, String valor) {
        Pattern regex = Pattern.compile("^.*".concat(valor));
        return Filters.regex(field).pattern(regex).caseInsensitive();
    }

}
