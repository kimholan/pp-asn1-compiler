package sequence;

import descriptor.AsnClassDescriptor;
import generated.asn1.antlr.ASN1BaseVisitor;
import generated.asn1.antlr.ASN1Parser.BuiltinTypeContext;
import generated.asn1.antlr.ASN1Parser.SequenceTypeContext;
import generated.asn1.antlr.ASN1Parser.TypeAssignmentContext;
import generated.asn1.antlr.ASN1Parser.TypeContext;
import org.bouncycastle.asn1.ASN1Sequence;

import java.util.Optional;

public class SequenceTypeAssignmentVisitor
        extends ASN1BaseVisitor<AsnClassDescriptor> {

    static boolean accepts(TypeAssignmentContext typeAssignmentContext) {
        return Optional.ofNullable(typeAssignmentContext)
                       .map(SequenceTypeAssignmentVisitor::sequenceType)
                       .isPresent();
    }

    static AsnClassDescriptor doVisit(TypeAssignmentContext typeAssignmentContext) {
        return new SequenceTypeAssignmentVisitor().visitTypeAssignment(typeAssignmentContext);
    }

    private static SequenceTypeContext sequenceType(TypeAssignmentContext typeAssignmentContext) {
        return Optional.ofNullable(typeAssignmentContext)
                       .map(TypeAssignmentContext::type)
                       .map(TypeContext::builtinType)
                       .map(BuiltinTypeContext::sequenceType)
                       .orElse(null);
    }

    @Override
    public AsnClassDescriptor visitTypeAssignment(TypeAssignmentContext typeAssignmentContext) {
        var descriptor = new AsnClassDescriptor();

        // Sequence type name
        var typereferenceContext = typeAssignmentContext.typereference();
        var typeName = typereferenceContext.getText();

        descriptor.setAsnType(ASN1Sequence.class.getSimpleName());
        descriptor.setIdentifier(typeName);

        // Sequence components
        var sequenceType = sequenceType(typeAssignmentContext);
        var fieldDescriptors = SequenceTypeVisitor.doVisit(sequenceType);

        descriptor.setFields(fieldDescriptors);

        return descriptor;
    }

}
