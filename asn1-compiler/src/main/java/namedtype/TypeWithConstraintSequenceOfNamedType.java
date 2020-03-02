package namedtype;

import descriptor.AsnFieldDescriptor;
import jinja.JavaClass;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Sequence;
import types.ConstrainedTypes;
import types.NamedTypes;

import java.util.List;
import java.util.Optional;

import static generated.asn1.antlr.ASN1Parser.NamedTypeContext;

public class TypeWithConstraintSequenceOfNamedType
        extends NamedTypeVisitor {

    @Override
    public AsnFieldDescriptor doVisit(NamedTypeContext it) {
        return new TypeWithConstraintSequenceOfNamedType().visit(it);
    }

    @Override
    public boolean accepts(NamedTypeContext namedType) {
        return Optional.ofNullable(namedType)
                       .map(NamedTypes::constrainedType)
                       .map(ConstrainedTypes::typeWithConstraint)
                       .filter(it -> it.SEQUENCE_WORD() != null && it.OF_WORD() != null)
                       .isPresent();
    }

    @Override
    public AsnFieldDescriptor visitNamedType(NamedTypeContext namedType) {
        var asnFieldDescriptor = super.visitNamedType(namedType);

        var typeWithConstraintContext = namedType.type().constrainedType().typeWithConstraint();
        var type = typeWithConstraintContext.type().getText();
        var identifier = JavaClass.identifier(type);

        asnFieldDescriptor.setSchemaType(true);
        asnFieldDescriptor.setAsnType(ASN1Sequence.class.getSimpleName());
        asnFieldDescriptor.setJavaType(List.class.getSimpleName());
        asnFieldDescriptor.setJavaTypeParameter(identifier);

        return asnFieldDescriptor;
    }

}
