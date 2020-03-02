package types;

import generated.asn1.antlr.ASN1Parser.BuiltinTypeContext;
import generated.asn1.antlr.ASN1Parser.ConstrainedTypeContext;
import generated.asn1.antlr.ASN1Parser.OctetStringTypeContext;
import generated.asn1.antlr.ASN1Parser.SequenceOfTypeContext;
import generated.asn1.antlr.ASN1Parser.TypeAssignmentContext;
import generated.asn1.antlr.ASN1Parser.TypeContext;

import java.util.Optional;

public interface TypeAssignments {

    static ConstrainedTypeContext constrainedType(TypeAssignmentContext typeAssignmentContext) {
        return Optional.ofNullable(typeAssignmentContext)
                       .map(TypeAssignmentContext::type)
                       .map(TypeContext::constrainedType)
                       .orElse(null);
    }

    static BuiltinTypeContext builtinType(TypeAssignmentContext typeAssignmentContext) {
        return Optional.ofNullable(typeAssignmentContext)
                       .map(TypeAssignmentContext::type)
                       .map(TypeContext::builtinType)
                       .orElse(null);
    }

    static OctetStringTypeContext octetString(TypeAssignmentContext typeAssignmentContext) {
        return Optional.ofNullable(typeAssignmentContext)
                       .map(TypeAssignmentContext::type)
                       .map(TypeContext::builtinType)
                       .map(BuiltinTypeContext::octetStringType)
                       .orElse(null);
    }

    static ConstrainedTypeContext setOfConstrainedType(TypeAssignmentContext typeAssignmentContext) {
        return Optional.ofNullable(typeAssignmentContext)
                       .map(TypeAssignments::constrainedType)
                       .filter(ConstrainedTypes::isSetOfConstrainedType)
                       .orElse(null);
    }

    static SequenceOfTypeContext sequenceOfBuiltinType(TypeAssignmentContext typeAssignmentContext) {
        return Optional.ofNullable(typeAssignmentContext)
                       .map(TypeAssignments::builtinType)
                       .map(BuiltinTypeContext::sequenceOfType)
                       .orElse(null);
    }

}
