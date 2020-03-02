package sequence;

import descriptor.AsnClassDescriptor;
import generated.asn1.antlr.ASN1BaseVisitor;
import generated.asn1.antlr.ASN1Parser.TypeAssignmentContext;
import org.bouncycastle.asn1.ASN1Sequence;
import types.TypeAssignments;

import java.util.Optional;

import static types.TypeAssignments.sequenceOfBuiltinType;

public class SequenceOfBuiltinTypeAssignmentVisitor
        extends ASN1BaseVisitor<AsnClassDescriptor> {

    static boolean accepts(TypeAssignmentContext typeAssignmentContext) {
        return Optional.ofNullable(typeAssignmentContext)
                       .map(TypeAssignments::sequenceOfBuiltinType)
                       .isPresent();
    }

    static AsnClassDescriptor doVisit(TypeAssignmentContext typeAssignmentContext) {
        return new SequenceOfBuiltinTypeAssignmentVisitor().visitTypeAssignment(typeAssignmentContext);
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
        var sequenceOfType = sequenceOfBuiltinType(typeAssignmentContext);
        var fieldDescriptors = SequenceOfBuiltinTypeVisitor.doVisit(sequenceOfType);

        descriptor.setFields(fieldDescriptors);

        return descriptor;
    }

}
