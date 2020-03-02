package sequence;

import descriptor.AsnClassDescriptor;
import generated.asn1.antlr.ASN1BaseVisitor;
import generated.asn1.antlr.ASN1Parser.AssignmentContext;
import generated.asn1.antlr.ASN1Parser.AssignmentListContext;
import generated.asn1.antlr.ASN1Parser.TypeAssignmentContext;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SequenceOfBuiltinTypeAssignmentListVisitor
        extends ASN1BaseVisitor<List<AsnClassDescriptor>> {

    public static List<AsnClassDescriptor> doVisit(AssignmentListContext context) {
        return context.accept(new SequenceOfBuiltinTypeAssignmentListVisitor());
    }

    @Override
    public List<AsnClassDescriptor> visitAssignmentList(AssignmentListContext ctx) {
        return ctx.assignment().stream()
                  .map(AssignmentContext::typeAssignment)
                  .filter(SequenceOfBuiltinTypeAssignmentVisitor::accepts)
                  .map(this::doVisitTypeAssignment)
                  .filter(Objects::nonNull)
                  .collect(Collectors.toList());
    }

    private AsnClassDescriptor doVisitTypeAssignment(TypeAssignmentContext typeAssignmentContext) {
        return SequenceOfBuiltinTypeAssignmentVisitor.doVisit(typeAssignmentContext);
    }

}

