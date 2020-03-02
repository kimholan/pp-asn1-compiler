package set;

import descriptor.AsnClassDescriptor;
import generated.asn1.antlr.ASN1BaseVisitor;
import generated.asn1.antlr.ASN1Parser.AssignmentContext;
import generated.asn1.antlr.ASN1Parser.AssignmentListContext;
import generated.asn1.antlr.ASN1Parser.TypeAssignmentContext;
import org.bouncycastle.asn1.DERTaggedObject;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SetOfConstrainedTypeAssignmentListVisitor
        extends ASN1BaseVisitor<List<AsnClassDescriptor>> {

    public static List<AsnClassDescriptor> doVisit(AssignmentListContext context) {
        return context.accept(new SetOfConstrainedTypeAssignmentListVisitor());
    }

    @Override
    public List<AsnClassDescriptor> visitAssignmentList(AssignmentListContext ctx) {
        return ctx.assignment().stream()
                  .map(AssignmentContext::typeAssignment)
                  .filter(SetOfConstrainedTypeAssignmentVisitor::accepts)
                  .map(this::doVisitTypeAssignment)
                  .filter(Objects::nonNull)
                  .collect(Collectors.toList());
    }

    private AsnClassDescriptor doVisitTypeAssignment(TypeAssignmentContext typeAssignmentContext) {
        return SetOfConstrainedTypeAssignmentVisitor.doVisit(typeAssignmentContext);
    }

}

