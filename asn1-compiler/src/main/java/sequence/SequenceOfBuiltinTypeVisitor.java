package sequence;

import descriptor.AsnFieldDescriptor;
import generated.asn1.antlr.ASN1BaseVisitor;
import generated.asn1.antlr.ASN1Parser.SequenceOfTypeContext;
import jinja.JavaClass;
import org.apache.commons.text.CaseUtils;

import java.util.List;

public class SequenceOfBuiltinTypeVisitor
        extends ASN1BaseVisitor<List<AsnFieldDescriptor>> {

    public static List<AsnFieldDescriptor> doVisit(SequenceOfTypeContext sequenceOfTypeContext) {
        return new SequenceOfBuiltinTypeVisitor().visitSequenceOfType(sequenceOfTypeContext);
    }

    @Override
    public List<AsnFieldDescriptor> visitSequenceOfType(SequenceOfTypeContext sequenceOfTypeContext) {
        var typereference = sequenceOfTypeContext.type().getText();
        var typeIdentifier = JavaClass.identifier(typereference);
        var identifier = CaseUtils.toCamelCase(typeIdentifier, false);

        var asnFieldDescriptor = new AsnFieldDescriptor();

        asnFieldDescriptor.setIdentifier(identifier);
        asnFieldDescriptor.setSchemaType(true);
        asnFieldDescriptor.setJavaType(typeIdentifier);
        asnFieldDescriptor.setAsnType(typeIdentifier);

        return List.of(asnFieldDescriptor);
    }

}
