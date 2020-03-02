package types;

import generated.asn1.antlr.ASN1Parser;
import generated.asn1.antlr.ASN1Parser.ConstrainedTypeContext;
import generated.asn1.antlr.ASN1Parser.ObjectIdentifierTypeContext;
import generated.asn1.antlr.ASN1Parser.TypeWithConstraintContext;
import org.antlr.v4.runtime.RuleContext;

import java.util.Optional;

public interface ConstrainedTypes {

    static ObjectIdentifierTypeContext objectIdentifier(ConstrainedTypeContext constrainedType) {
        return Optional.ofNullable(constrainedType)
                       .map(ConstrainedTypeContext::builtinType)
                       .map(ASN1Parser.BuiltinTypeContext::objectIdentifierType)
                       .orElse(null);
    }

    static String constraintSpecText(ConstrainedTypeContext constrainedType) {
        return Optional.ofNullable(constrainedType)
                       .map(ConstrainedTypeContext::constraint)
                       .map(ASN1Parser.ConstraintContext::constraintSpec)
                       .map(RuleContext::getText)
                       .orElse(null);
    }

    static TypeWithConstraintContext typeWithConstraint(ConstrainedTypeContext constrainedTypeContext) {
        return Optional.ofNullable(constrainedTypeContext)
                       .map(ConstrainedTypeContext::typeWithConstraint)
                       .orElse(null);
    }

    static boolean isSetOfConstrainedType(ConstrainedTypeContext constrainedTypeContext) {
        return Optional.ofNullable(constrainedTypeContext)
                       .map(ConstrainedTypes::setOfTypeWithConstraint)
                       .isPresent();
    }



    static TypeWithConstraintContext setOfTypeWithConstraint(ConstrainedTypeContext constrainedTypeContext) {
        return Optional.ofNullable(constrainedTypeContext)
                       .map(ConstrainedTypeContext::typeWithConstraint)
                       .filter(ConstrainedTypes::isSetOf)
                       .orElse(null);
    }


    static TypeWithConstraintContext sequenceOfTypeWithConstraint(ConstrainedTypeContext constrainedTypeContext) {
        return Optional.ofNullable(constrainedTypeContext)
                       .map(ConstrainedTypeContext::typeWithConstraint)
                       .filter(ConstrainedTypes::isSequenceOf)
                       .orElse(null);
    }

    private static boolean isSetOf(TypeWithConstraintContext typeWithConstraintContext) {
        return Optional.ofNullable(typeWithConstraintContext)
                       .map(TypeWithConstraintContext::SET_WORD)
                       .map(it -> typeWithConstraintContext)
                       .map(TypeWithConstraintContext::OF_WORD)
                       .isPresent()

                ;
    }

    private static boolean isSequenceOf(TypeWithConstraintContext typeWithConstraintContext) {
        return Optional.ofNullable(typeWithConstraintContext)
                       .map(TypeWithConstraintContext::SEQUENCE_WORD)
                       .map(it -> typeWithConstraintContext)
                       .map(TypeWithConstraintContext::OF_WORD)
                       .isPresent()

                ;
    }

}
