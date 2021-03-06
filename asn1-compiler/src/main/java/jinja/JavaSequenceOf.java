package jinja;

import com.hubspot.jinjava.Jinjava;
import descriptor.AsnClassDescriptor;
import org.codehaus.groovy.runtime.IOGroovyMethods;

import java.io.IOException;
import java.util.Map;

public class JavaSequenceOf {

    public static String render(AsnClassDescriptor descriptor) throws IOException {
        var stream = JavaSequenceOf.class.getResourceAsStream("/jinja/" + JavaSequenceOf.class.getSimpleName() + ".j2");
        var templateString = IOGroovyMethods.getText(stream);

        var jinjava = new Jinjava();
        var globalContext = jinjava.getGlobalContext();
        globalContext.registerFunction(JavaConstant.identifier());
        globalContext.registerFunction(JavaClass.identifier());
        globalContext.registerFunction(JavaClass.interfaces());

        var bindings = Map.of("jclass", descriptor);
        return jinjava.render(templateString, bindings);
    }

}
