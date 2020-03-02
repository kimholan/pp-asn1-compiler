package set;

import descriptor.AsnFieldDescriptor;
import generated.asn1.antlr.ASN1BaseVisitor;
import generated.asn1.antlr.ASN1Parser.ConstrainedTypeContext;
import jinja.JavaClass;
import org.apache.commons.text.CaseUtils;

import java.util.List;

public class SetOfConstrainedTypeVisitor
        extends ASN1BaseVisitor<List<AsnFieldDescriptor>> {

    public static List<AsnFieldDescriptor> doVisit(ConstrainedTypeContext constrainedTypeContext) {
        return new SetOfConstrainedTypeVisitor().visitConstrainedType(constrainedTypeContext);
    }

    @Override
    public List<AsnFieldDescriptor> visitConstrainedType(ConstrainedTypeContext constrainedTypeContext) {
        var type = constrainedTypeContext.typeWithConstraint().type();
        var typereference = type.referencedType().definedType().typereference().getText();
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
