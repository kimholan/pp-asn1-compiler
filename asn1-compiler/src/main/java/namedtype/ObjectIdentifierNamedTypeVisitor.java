package namedtype;

import descriptor.AsnFieldDescriptor;
import generated.asn1.antlr.ASN1Parser.NamedTypeContext;
import jinja.JavaConstant;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import types.ConstrainedTypes;
import types.NamedTypes;

import java.util.Optional;

public class ObjectIdentifierNamedTypeVisitor
        extends NamedTypeVisitor {

    @Override
    public AsnFieldDescriptor doVisit(NamedTypeContext it) {
        return new ObjectIdentifierNamedTypeVisitor().visit(it);
    }

    @Override
    public boolean accepts(NamedTypeContext namedType) {
        return Optional.ofNullable(namedType)
                       .map(NamedTypes::constrainedType)
                       .map(ConstrainedTypes::objectIdentifier)
                       .isPresent();
    }

    @Override
    public AsnFieldDescriptor visitNamedType(NamedTypeContext namedType) {
        var asnFieldDescriptor = super.visitNamedType(namedType);

        var constrainedType = NamedTypes.constrainedType(namedType);
        var objectIdentifierSpec = ConstrainedTypes.constraintSpecText(constrainedType);
        var identifier = JavaConstant.identifier(objectIdentifierSpec);

        asnFieldDescriptor.setSchemaType(true);
        asnFieldDescriptor.setAsnType(ASN1ObjectIdentifier.class.getSimpleName());
        asnFieldDescriptor.setJavaType("ObjectIdentifiers");
        asnFieldDescriptor.setValue(identifier);

        return asnFieldDescriptor;
    }

}
