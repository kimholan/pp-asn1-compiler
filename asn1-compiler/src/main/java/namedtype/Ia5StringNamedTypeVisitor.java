package namedtype;

import descriptor.AsnFieldDescriptor;
import generated.asn1.antlr.ASN1Parser.NamedTypeContext;
import org.bouncycastle.asn1.DERIA5String;
import types.NamedTypes;

import java.util.Optional;

public class Ia5StringNamedTypeVisitor
        extends NamedTypeVisitor {

    @Override
    public AsnFieldDescriptor doVisit(NamedTypeContext it) {
        return new Ia5StringNamedTypeVisitor().visit(it);
    }

    @Override
    public boolean accepts(NamedTypeContext namedType) {
        return Optional.ofNullable(namedType)
                       .map(NamedTypes::ia5String)
                       .isPresent();
    }

    @Override
    public AsnFieldDescriptor visitNamedType(NamedTypeContext namedType) {
        var asnFieldDescriptor = super.visitNamedType(namedType);

        asnFieldDescriptor.setAsnType(DERIA5String.class.getSimpleName());
        asnFieldDescriptor.setJavaType(String.class.getSimpleName());

        return asnFieldDescriptor;
    }

}
