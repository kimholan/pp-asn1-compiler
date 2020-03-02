package set;

import descriptor.AsnClassDescriptor;
import generated.asn1.antlr.ASN1BaseVisitor;
import generated.asn1.antlr.ASN1Parser.TypeAssignmentContext;
import org.bouncycastle.asn1.ASN1Set;
import types.TypeAssignments;

import java.util.Optional;

import static types.TypeAssignments.setOfConstrainedType;

public class SetOfConstrainedTypeAssignmentVisitor
        extends ASN1BaseVisitor<AsnClassDescriptor> {

    static boolean accepts(TypeAssignmentContext typeAssignmentContext) {
        return Optional.ofNullable(typeAssignmentContext)
                       .map(TypeAssignments::setOfConstrainedType)
                       .isPresent();
    }

    static AsnClassDescriptor doVisit(TypeAssignmentContext typeAssignmentContext) {
        return new SetOfConstrainedTypeAssignmentVisitor().visitTypeAssignment(typeAssignmentContext);
    }

    @Override
    public AsnClassDescriptor visitTypeAssignment(TypeAssignmentContext typeAssignmentContext) {
        var descriptor = new AsnClassDescriptor();

        // Set type name
        var typereferenceContext = typeAssignmentContext.typereference();
        var typeName = typereferenceContext.getText();

        descriptor.setAsnType(ASN1Set.class.getSimpleName());
        descriptor.setIdentifier(typeName);

        // Set components
        var setOfType = setOfConstrainedType(typeAssignmentContext);
        var fieldDescriptors = SetOfConstrainedTypeVisitor.doVisit(setOfType);

        descriptor.setFields(fieldDescriptors);

        return descriptor;
    }

}
