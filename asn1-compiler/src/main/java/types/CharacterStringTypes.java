package types;

import generated.asn1.antlr.ASN1Parser.CharacterStringTypeContext;
import generated.asn1.antlr.ASN1Parser.RestrictedCharacterStringTypeContext;

import java.util.Optional;

public interface CharacterStringTypes {

    static RestrictedCharacterStringTypeContext utf8StringType(CharacterStringTypeContext context) {
        return Optional.ofNullable(context)
                       .map(CharacterStringTypeContext::restrictedCharacterStringType)
                       .filter(CharacterStringTypes::isUTF8String)
                       .orElse(null);
    }

    static RestrictedCharacterStringTypeContext ia5String(CharacterStringTypeContext context) {
        return Optional.ofNullable(context)
                       .map(CharacterStringTypeContext::restrictedCharacterStringType)
                       .filter(CharacterStringTypes::isIA5String)
                       .orElse(null);
    }

    private static boolean isIA5String(RestrictedCharacterStringTypeContext context) {
        return Optional.ofNullable(context)
                       .map(RestrictedCharacterStringTypeContext::IA5String_WORD)
                       .isPresent();
    }

    private static boolean isUTF8String(RestrictedCharacterStringTypeContext context) {
        return Optional.ofNullable(context)
                       .map(RestrictedCharacterStringTypeContext::UTF8String_WORD)
                       .isPresent();
    }

}
