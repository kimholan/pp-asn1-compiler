package namedtype;

import descriptor.AsnFieldDescriptor;
import generated.asn1.antlr.ASN1BaseVisitor;
import generated.asn1.antlr.ASN1Parser.NamedTypeContext;
import org.bouncycastle.asn1.ASN1Primitive;

public class NamedTypeVisitor
        extends ASN1BaseVisitor<AsnFieldDescriptor> {

    public boolean accepts(NamedTypeContext namedTypeContext) {
        return true;
    }

    public AsnFieldDescriptor doVisit(NamedTypeContext namedTypeContext) {
        return visitNamedType(namedTypeContext);
    }

    @Override
    public AsnFieldDescriptor visitNamedType(NamedTypeContext namedTypeContext) {
        var fieldDescriptor = new AsnFieldDescriptor();

        // Field name
        var name = namedTypeContext.identifier().getText();
        fieldDescriptor.setIdentifier(name);
        fieldDescriptor.setAsnType(ASN1Primitive.class.getSimpleName());
        fieldDescriptor.setJavaType(Object.class.getSimpleName());

        return fieldDescriptor;
    }

}
