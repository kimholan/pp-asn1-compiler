package namedtype;

import descriptor.AsnFieldDescriptor;
import generated.asn1.antlr.ASN1Parser;
import org.bouncycastle.asn1.DERIA5String;
import types.NamedTypes;
import types.TaggedTypes;

import java.util.Optional;

public class Ia5StringTaggedNamedTypeVisitor
        extends NamedTypeVisitor {

    @Override
    public AsnFieldDescriptor doVisit(ASN1Parser.NamedTypeContext it) {
        return new Ia5StringTaggedNamedTypeVisitor().visit(it);
    }

    @Override
    public boolean accepts(ASN1Parser.NamedTypeContext namedType) {
        return Optional.ofNullable(namedType)
                       .map(NamedTypes::taggedType)
                       .map(TaggedTypes::ia5String)
                       .isPresent();
    }

    @Override
    public AsnFieldDescriptor visitNamedType(ASN1Parser.NamedTypeContext namedType) {
        var asnFieldDescriptor = super.visitNamedType(namedType);

        var taggedType = NamedTypes.taggedType(namedType);
        var tagClassNumberText = taggedType.tag().classNumber().getText();
        var tagClassNumber = Integer.valueOf(tagClassNumberText);
        var optional = Optional.of(namedType)
                               .map(it -> it.parent)
                               .filter(ASN1Parser.ComponentTypeContext.class::isInstance)
                               .map(ASN1Parser.ComponentTypeContext.class::cast)
                               .map(ASN1Parser.ComponentTypeContext::OPTIONAL_WORD)
                               .isPresent();

        asnFieldDescriptor.setOptional(optional);
        asnFieldDescriptor.setAsnType(DERIA5String.class.getSimpleName());
        asnFieldDescriptor.setJavaType(String.class.getSimpleName());
        asnFieldDescriptor.setTagClassNumber(tagClassNumber);

        return asnFieldDescriptor;
    }

}
