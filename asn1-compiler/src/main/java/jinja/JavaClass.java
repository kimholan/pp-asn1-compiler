package jinja;

import com.hubspot.jinjava.lib.fn.ELFunctionDefinition;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JavaClass {

    private static final Map<String, List> CLASS_INTERFACES = newClassInterfaces();

    public static ELFunctionDefinition identifier() {
        return new ELFunctionDefinition("jclass", "identifier", JavaClass.class, "identifier", String.class);
    }

    public static ELFunctionDefinition interfaces() {
        return new ELFunctionDefinition("jclass", "interfaces", JavaClass.class, "interfaces", String.class);
    }

    public static List interfaces(String jclassIdentifier) {
        return Optional.ofNullable(jclassIdentifier)
                       .filter(StringUtils::isNotBlank)
                       .map(CLASS_INTERFACES::get)
                       .orElseGet(Collections::emptyList);
    }

    public static String identifier(String object) {
        return Optional.ofNullable(object)
                       .map(it -> it.replaceAll("[^a-zA-Z0-9]", ""))
                       .map(StringUtils::capitalize)
                       .orElse(null);
    }

    private static Map<String, List> newClassInterfaces() {
        var interfaces = System.getProperty("asn1.compiler.class.interfaces", "");
        var file = new File(interfaces);
        Map<String, List> returnValue = null;

        try {
            System.err.println("Loading interfaces mapping:" + file.getCanonicalPath());
        } catch (IOException exception) {
            System.err.println("Failed to load interfaces mapping:" + interfaces);
        }

        if (file.exists()) {
            try {
                returnValue = new Yaml().load(new FileReader(file));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return Optional.ofNullable(returnValue)
                       .orElseGet(Collections::emptyMap);
    }

}
