package types;

import descriptor.AsnFieldDescriptor;
import generated.asn1.antlr.ASN1Parser.BuiltinTypeContext;
import generated.asn1.antlr.ASN1Parser.ConstrainedTypeContext;
import generated.asn1.antlr.ASN1Parser.IntegerTypeContext;
import generated.asn1.antlr.ASN1Parser.NamedTypeContext;
import generated.asn1.antlr.ASN1Parser.OctetStringTypeContext;
import generated.asn1.antlr.ASN1Parser.ReferencedTypeContext;
import generated.asn1.antlr.ASN1Parser.RestrictedCharacterStringTypeContext;
import generated.asn1.antlr.ASN1Parser.TaggedTypeContext;
import generated.asn1.antlr.ASN1Parser.TypeContext;
import namedtype.Ia5StringNamedTypeVisitor;
import namedtype.Ia5StringTaggedNamedTypeVisitor;
import namedtype.IntegerNamedTypeVisitor;
import namedtype.NamedTypeVisitor;
import namedtype.ObjectIdentifierNamedTypeVisitor;
import namedtype.OctetStringNamedTypeVisitor;
import namedtype.SequenceTypeNamedType;
import namedtype.TypeReferenceNamedType;
import namedtype.TypeReferenceTaggedNamedTypeVisitor;
import namedtype.TypeWithConstraintNamedType;
import namedtype.TypeWithConstraintSequenceOfNamedType;
import namedtype.Utf8StringNamedTypeVisitor;

import java.util.Optional;
import java.util.stream.Stream;

public interface NamedTypes {

    static AsnFieldDescriptor visit(NamedTypeContext namedTypeContext) {
        return Stream.of(
                new ObjectIdentifierNamedTypeVisitor(),
                new OctetStringNamedTypeVisitor(),
                new Ia5StringNamedTypeVisitor(),
                new IntegerNamedTypeVisitor(),
                new TypeReferenceNamedType(),
                new SequenceTypeNamedType(),
                new TypeReferenceTaggedNamedTypeVisitor(),
                new Ia5StringTaggedNamedTypeVisitor(),
                new Utf8StringNamedTypeVisitor(),
                new TypeWithConstraintSequenceOfNamedType(),
                new TypeWithConstraintNamedType(),
                new NamedTypeVisitor()
        ).filter(it -> it.accepts(namedTypeContext))
                     .findFirst()
                     .map(it -> it.doVisit(namedTypeContext))
                     .orElse(null);
    }

    static IntegerTypeContext integerType(NamedTypeContext namedTypeContext) {
        return Optional.ofNullable(namedTypeContext)
                       .map(NamedTypes::builtinType)
                       .map(BuiltinTypes::integerType)
                       .orElse(null);
    }

    static OctetStringTypeContext octetStringType(NamedTypeContext namedTypeContext) {
        return Optional.ofNullable(namedTypeContext)
                       .map(NamedTypes::builtinType)
                       .map(BuiltinTypes::octetStringType)
                       .orElse(null);
    }

    static RestrictedCharacterStringTypeContext ia5String(NamedTypeContext namedTypeContext) {
        return Optional.ofNullable(namedTypeContext)
                       .map(NamedTypes::builtinType)
                       .map(BuiltinTypes::ia5StringType)
                       .orElse(null);
    }

    static RestrictedCharacterStringTypeContext utf8String(NamedTypeContext namedTypeContext) {
        return Optional.ofNullable(namedTypeContext)
                       .map(NamedTypes::builtinType)
                       .map(BuiltinTypes::utf8StringType)
                       .orElse(null);
    }

    static BuiltinTypeContext builtinType(NamedTypeContext namedTypeContext) {
        return Optional.ofNullable(namedTypeContext)
                       .map(NamedTypeContext::type)
                       .map(TypeContext::builtinType)
                       .orElse(null);
    }

    static ConstrainedTypeContext constrainedType(NamedTypeContext namedTypeContext) {
        return Optional.ofNullable(namedTypeContext)
                       .map(NamedTypeContext::type)
                       .map(TypeContext::constrainedType)
                       .orElse(null);
    }

    static ReferencedTypeContext referencedType(NamedTypeContext namedTypeContext) {
        return Optional.ofNullable(namedTypeContext)
                       .map(NamedTypeContext::type)
                       .map(TypeContext::referencedType)
                       .orElse(null);
    }

    static TaggedTypeContext taggedType(NamedTypeContext namedTypeContext) {
        return Optional.ofNullable(namedTypeContext)
                       .map(NamedTypeContext::type)
                       .map(TypeContext::builtinType)
                       .map(BuiltinTypeContext::taggedType)
                       .orElse(null);
    }

}

