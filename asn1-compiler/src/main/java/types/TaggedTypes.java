package types;

import generated.asn1.antlr.ASN1Parser.BuiltinTypeContext;
import generated.asn1.antlr.ASN1Parser.RestrictedCharacterStringTypeContext;

import java.util.Optional;

import static generated.asn1.antlr.ASN1Parser.TaggedTypeContext;
import static generated.asn1.antlr.ASN1Parser.TypeContext;
import static generated.asn1.antlr.ASN1Parser.TypereferenceContext;

public interface TaggedTypes {

    static TypereferenceContext typeReference(TaggedTypeContext taggedTypeContext) {
        return Optional.ofNullable(taggedTypeContext)
                       .map(TaggedTypeContext::type)
                       .map(TypeContext::referencedType)
                       .map(ReferencedTypes::typeReference)
                       .orElse(null);
    }

    static RestrictedCharacterStringTypeContext ia5String(TaggedTypeContext namedTypeContext) {
        return Optional.ofNullable(namedTypeContext)
                       .map(TaggedTypeContext::type)
                       .map(TypeContext::builtinType)
                       .map(BuiltinTypeContext::characterStringType)
                       .map(CharacterStringTypes::ia5String)
                       .orElse(null);
    }

}
