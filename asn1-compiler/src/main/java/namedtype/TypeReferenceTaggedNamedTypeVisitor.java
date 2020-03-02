package namedtype;

import descriptor.AsnFieldDescriptor;
import generated.asn1.antlr.ASN1Parser;
import generated.asn1.antlr.ASN1Parser.NamedTypeContext;
import jinja.JavaClass;
import org.antlr.v4.runtime.ParserRuleContext;
import types.NamedTypes;
import types.TaggedTypes;

import java.util.Optional;

public class TypeReferenceTaggedNamedTypeVisitor
        extends NamedTypeVisitor {

    @Override
    public AsnFieldDescriptor doVisit(NamedTypeContext it) {
        return new TypeReferenceTaggedNamedTypeVisitor().visit(it);
    }

    @Override
    public boolean accepts(NamedTypeContext namedType) {
        return Optional.ofNullable(namedType)
                       .map(NamedTypes::taggedType)
                       .map(TaggedTypes::typeReference)
                       .isPresent();
    }

    @Override
    public AsnFieldDescriptor visitNamedType(NamedTypeContext namedType) {
        var asnFieldDescriptor = super.visitNamedType(namedType);
        var taggedType = NamedTypes.taggedType(namedType);
        var typereference = TaggedTypes.typeReference(taggedType);
        var type = typereference.getText();
        var identifier = JavaClass.identifier(type);
        var tagClassNumberText = taggedType.tag().classNumber().getText();
        var tagClassNumber = Integer.valueOf(tagClassNumberText);

        var optional = Optional.of(namedType)
                               .map(ParserRuleContext::getParent)
                               .filter(ASN1Parser.ComponentTypeContext.class::isInstance)
                               .map(ASN1Parser.ComponentTypeContext.class::cast)
                               .map(ASN1Parser.ComponentTypeContext::OPTIONAL_WORD)
                               .isPresent();
        
        asnFieldDescriptor.setOptional(optional);
        asnFieldDescriptor.setSchemaType(true);
        asnFieldDescriptor.setAsnType(identifier);
        asnFieldDescriptor.setJavaType(identifier);
        asnFieldDescriptor.setTagClassNumber(tagClassNumber);

        return asnFieldDescriptor;
    }

}
