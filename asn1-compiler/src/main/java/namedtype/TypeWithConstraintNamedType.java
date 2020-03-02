package namedtype;

import descriptor.AsnFieldDescriptor;
import generated.asn1.antlr.ASN1Parser;
import jinja.JavaClass;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1OctetString;
import types.ConstrainedTypes;
import types.NamedTypes;

import java.util.Optional;

public class TypeWithConstraintNamedType
        extends NamedTypeVisitor {

    @Override
    public AsnFieldDescriptor doVisit(ASN1Parser.NamedTypeContext it) {
        return new TypeWithConstraintNamedType().visit(it);
    }

    @Override
    public boolean accepts(ASN1Parser.NamedTypeContext namedType) {
        return Optional.ofNullable(namedType)
                       .map(NamedTypes::constrainedType)
                       .map(ConstrainedTypes::typeWithConstraint)
                       .isPresent();
    }

    @Override
    public AsnFieldDescriptor visitNamedType(ASN1Parser.NamedTypeContext namedType) {
        var asnFieldDescriptor = super.visitNamedType(namedType);

        var typeWithConstraintContext = namedType.type().constrainedType().typeWithConstraint();
        var type = typeWithConstraintContext.type().getText();
        var identifier = JavaClass.identifier(type);

        asnFieldDescriptor.setSchemaType(true);
        asnFieldDescriptor.setAsnType(identifier);
        asnFieldDescriptor.setJavaType(identifier);

        return asnFieldDescriptor;
    }

}
