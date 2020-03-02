package jinja;

import com.hubspot.jinjava.Jinjava;
import descriptor.AsnEnumDescriptor;
import org.codehaus.groovy.runtime.IOGroovyMethods;

import java.io.IOException;
import java.util.Map;

public class JavaEnum {

    public static String render(AsnEnumDescriptor descriptor) throws IOException {
        var stream = JavaSequence.class.getResourceAsStream("/jinja/" + JavaEnum.class.getSimpleName() + ".j2");
        var templateString = IOGroovyMethods.getText(stream);

        var jinjava = new Jinjava();
        var globalContext = jinjava.getGlobalContext();
        globalContext.registerFunction(JavaConstant.identifier());
        globalContext.registerFunction(JavaClass.identifier());

        var bindings = Map.of("jenum", descriptor);
        return jinjava.render(templateString, bindings);
    }

}
