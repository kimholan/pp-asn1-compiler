package types;

import generated.asn1.antlr.ASN1Parser.BuiltinTypeContext;
import generated.asn1.antlr.ASN1Parser.IntegerTypeContext;
import generated.asn1.antlr.ASN1Parser.ObjectIdentifierTypeContext;
import generated.asn1.antlr.ASN1Parser.OctetStringTypeContext;
import generated.asn1.antlr.ASN1Parser.RestrictedCharacterStringTypeContext;
import generated.asn1.antlr.ASN1Parser.SequenceOfTypeContext;
import generated.asn1.antlr.ASN1Parser.SequenceTypeContext;

import java.util.Optional;

public interface BuiltinTypes {

    static IntegerTypeContext integerType(BuiltinTypeContext builtinType) {
        return Optional.ofNullable(builtinType)
                       .map(BuiltinTypeContext::integerType)
                       .orElse(null);

    }

    static ObjectIdentifierTypeContext objectIdentifierType(BuiltinTypeContext builtinType) {
        return Optional.ofNullable(builtinType)
                       .map(BuiltinTypeContext::objectIdentifierType)
                       .orElse(null);
    }

    static RestrictedCharacterStringTypeContext ia5StringType(BuiltinTypeContext builtinType) {
        return Optional.ofNullable(builtinType)
                       .map(BuiltinTypeContext::characterStringType)
                       .map(CharacterStringTypes::ia5String)
                       .orElse(null);
    }

    static RestrictedCharacterStringTypeContext utf8StringType(BuiltinTypeContext builtinType) {
        return Optional.ofNullable(builtinType)
                       .map(BuiltinTypeContext::characterStringType)
                       .map(CharacterStringTypes::utf8StringType)
                       .orElse(null);
    }

    static OctetStringTypeContext octetStringType(BuiltinTypeContext builtinType) {
        return Optional.ofNullable(builtinType)
                       .map(BuiltinTypeContext::octetStringType)
                       .orElse(null);

    }

    static SequenceTypeContext sequenceType(BuiltinTypeContext builtinType) {
        return Optional.ofNullable(builtinType)
                       .map(BuiltinTypeContext::sequenceType)
                       .orElse(null);

    }

    static SequenceOfTypeContext sequenceOfBuiltinType(BuiltinTypeContext builtinType) {
        return Optional.ofNullable(builtinType)
                       .map(BuiltinTypeContext::sequenceOfType)
                       .orElse(null);
    }

}
