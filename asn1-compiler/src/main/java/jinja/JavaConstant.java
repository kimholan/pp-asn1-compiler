package jinja;

import com.hubspot.jinjava.lib.fn.ELFunctionDefinition;

public class JavaConstant {

    public static ELFunctionDefinition identifier() {
        return new ELFunctionDefinition("jconstant", "identifier", JavaConstant.class, "identifier", String.class);
    }

    public static String identifier(String object) {
        return String.valueOf(object).toUpperCase()
                     .replaceAll("[^a-zA-Z0-9]", "_")
                     .replaceAll("__*", "_");
    }

}
