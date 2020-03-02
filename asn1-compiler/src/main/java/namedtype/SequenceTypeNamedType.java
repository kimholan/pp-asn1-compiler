package namedtype;

import descriptor.AsnClassDescriptor;
import descriptor.AsnFieldDescriptor;
import generated.asn1.antlr.ASN1Parser.NamedTypeContext;
import generated.asn1.antlr.ASN1Parser.SequenceTypeContext;
import generated.asn1.antlr.ASN1Parser.TypeAssignmentContext;
import jinja.JavaClass;
import org.antlr.v4.runtime.ParserRuleContext;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequenceParser;
import sequence.SequenceTypeVisitor;
import types.BuiltinTypes;
import types.NamedTypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SequenceTypeNamedType
        extends NamedTypeVisitor {

    @Override
    public AsnFieldDescriptor doVisit(NamedTypeContext it) {
        return new SequenceTypeNamedType().visit(it);
    }

    @Override
    public boolean accepts(NamedTypeContext namedType) {

        return Optional.ofNullable(namedType)
                       .map(NamedTypes::builtinType)
                       .map(BuiltinTypes::sequenceType)
                       .isPresent();
    }

    @Override
    public AsnFieldDescriptor visitNamedType(NamedTypeContext namedType) {
        var asnFieldDescriptor = super.visitNamedType(namedType);

        var referencedType = namedType.type().builtinType();
        var sequenceType = referencedType.sequenceType();

        // Inline sequence declaration
        var classDescriptor = newClassDescriptor(sequenceType);
        var classIdentifier = JavaClass.identifier(classDescriptor.getIdentifier());

        asnFieldDescriptor.setAsnType(classIdentifier);
        asnFieldDescriptor.setJavaType(classIdentifier);
        asnFieldDescriptor.setClassDescriptor(classDescriptor);
        asnFieldDescriptor.setSchemaType(true);

        return asnFieldDescriptor;
    }

    private AsnClassDescriptor newClassDescriptor(SequenceTypeContext sequenceType) {
        var asnClassDescriptor = new AsnClassDescriptor();

        var asnFieldDescriptors = SequenceTypeVisitor.doVisit(sequenceType);
        var asnType = newAsnType(sequenceType);

        asnClassDescriptor.setIdentifier(asnType);
        asnClassDescriptor.setAsnType(ASN1Sequence.class.getSimpleName());
        asnClassDescriptor.setFields(asnFieldDescriptors);

        return asnClassDescriptor;
    }

    private String newAsnType(SequenceTypeContext sequenceType) {
        var tokens = getAncestors(sequenceType, NamedTypeContext.class, TypeAssignmentContext.class)
                             .stream()
                             .map(this::mapNamedType)
                             .map(this::mapTypeAssignment)
                             .map(String::valueOf)
                             .collect(Collectors.toList());
        Collections.reverse(tokens);
        return String.join("", tokens);
    }

    private Object mapTypeAssignment(Object it) {
        var returnValue = it;

        if (it instanceof TypeAssignmentContext) {
            var typeAssignmentContext = (TypeAssignmentContext) it;
            var typereference = typeAssignmentContext.typereference();
            returnValue = typereference.getText();
        }

        return returnValue;
    }

    private Object mapNamedType(ParserRuleContext it) {
        Object returnValue = it;

        if (it instanceof NamedTypeContext) {
            var namedTypeContext = (NamedTypeContext) it;
            var identifier = namedTypeContext.identifier().getText();
            returnValue = JavaClass.identifier(identifier);
        }

        return returnValue;
    }

    public List<ParserRuleContext> getAncestors(ParserRuleContext context, Class<?>... filter) {
        var ancestors = new ArrayList<ParserRuleContext>();

        var parent = context.getParent();
        do {
            var current = parent;
            var isMatchedAncestor = Stream.of(filter).anyMatch(it -> it.isInstance(current));
            Optional.ofNullable(current)
                    .filter(it -> isMatchedAncestor)
                    .ifPresent(ancestors::add);

            parent = Optional.ofNullable(current)
                             .map(ParserRuleContext::getParent)
                             .orElse(null);
        } while (parent != null);

        return ancestors;
    }

}
