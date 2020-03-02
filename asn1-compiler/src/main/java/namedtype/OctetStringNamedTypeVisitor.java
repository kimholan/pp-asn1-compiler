package namedtype;

import descriptor.AsnFieldDescriptor;
import generated.asn1.antlr.ASN1Parser.NamedTypeContext;
import org.bouncycastle.asn1.DEROctetString;
import types.NamedTypes;

import java.util.Optional;

public class OctetStringNamedTypeVisitor
        extends NamedTypeVisitor {

    @Override
    public AsnFieldDescriptor doVisit(NamedTypeContext it) {
        return new OctetStringNamedTypeVisitor().visit(it);
    }

    @Override
    public boolean accepts(NamedTypeContext namedType) {
        return Optional.ofNullable(namedType)
                       .map(NamedTypes::octetStringType)
                       .isPresent();
    }

    @Override
    public AsnFieldDescriptor visitNamedType(NamedTypeContext namedType) {
        var asnFieldDescriptor = super.visitNamedType(namedType);

        asnFieldDescriptor.setAsnType(DEROctetString.class.getSimpleName());
        asnFieldDescriptor.setJavaType("byte[]");

        return asnFieldDescriptor;
    }

}
