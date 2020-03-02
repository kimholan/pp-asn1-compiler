package types;

import generated.asn1.antlr.ASN1Parser.BuiltinValueContext;
import generated.asn1.antlr.ASN1Parser.ObjectIdentifierValueContext;

import java.util.Optional;

public interface BuiltinValues {

    static ObjectIdentifierValueContext objectIdentifierValue(BuiltinValueContext builtinValueContext) {
        return Optional.ofNullable(builtinValueContext)
                       .map(BuiltinValueContext::objectIdentifierValue)
                       .orElse(null);
    }

}
