package namedtype;

import descriptor.AsnFieldDescriptor;
import generated.asn1.antlr.ASN1Parser.NamedTypeContext;
import org.bouncycastle.asn1.DERUTF8String;
import types.NamedTypes;

import java.util.Optional;

public class Utf8StringNamedTypeVisitor
        extends NamedTypeVisitor {

    @Override
    public AsnFieldDescriptor doVisit(NamedTypeContext it) {
        return new Utf8StringNamedTypeVisitor().visit(it);
    }

    @Override
    public boolean accepts(NamedTypeContext namedType) {
        return Optional.ofNullable(namedType)
                       .map(NamedTypes::utf8String)
                       .isPresent();
    }

    @Override
    public AsnFieldDescriptor visitNamedType(NamedTypeContext namedType) {
        var asnFieldDescriptor = super.visitNamedType(namedType);

        asnFieldDescriptor.setAsnType(DERUTF8String.class.getSimpleName());
        asnFieldDescriptor.setJavaType(String.class.getSimpleName());

        return asnFieldDescriptor;
    }

}
