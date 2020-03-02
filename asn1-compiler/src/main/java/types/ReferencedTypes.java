package types;

import generated.asn1.antlr.ASN1Parser.DefinedTypeContext;
import generated.asn1.antlr.ASN1Parser.ReferencedTypeContext;
import generated.asn1.antlr.ASN1Parser.TypereferenceContext;

import java.util.Optional;

public interface ReferencedTypes {

    static TypereferenceContext typeReference(ReferencedTypeContext referencedTypeContext) {
        return Optional.ofNullable(referencedTypeContext)
                       .map(ReferencedTypeContext::definedType)
                       .map(DefinedTypeContext::typereference)
                       .orElse(null);
    }

}
