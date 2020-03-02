package namedtype;

import descriptor.AsnFieldDescriptor;
import generated.asn1.antlr.ASN1Parser.NamedTypeContext;
import org.bouncycastle.asn1.ASN1Integer;
import types.NamedTypes;

import java.math.BigInteger;
import java.util.Optional;

public class IntegerNamedTypeVisitor
        extends NamedTypeVisitor {

    @Override
    public AsnFieldDescriptor doVisit(NamedTypeContext it) {
        return new IntegerNamedTypeVisitor().visit(it);
    }

    @Override
    public boolean accepts(NamedTypeContext namedType) {
        return Optional.ofNullable(namedType)
                       .map(NamedTypes::integerType)
                       .isPresent();
    }

    @Override
    public AsnFieldDescriptor visitNamedType(NamedTypeContext namedType) {
        var asnFieldDescriptor = super.visitNamedType(namedType);

        asnFieldDescriptor.setAsnType(ASN1Integer.class.getSimpleName());
        asnFieldDescriptor.setJavaType(BigInteger.class.getSimpleName());

        return asnFieldDescriptor;
    }

}
