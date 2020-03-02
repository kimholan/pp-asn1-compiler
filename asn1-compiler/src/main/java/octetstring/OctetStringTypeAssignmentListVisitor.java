package octetstring;

import descriptor.AsnClassDescriptor;
import generated.asn1.antlr.ASN1BaseVisitor;
import generated.asn1.antlr.ASN1Parser.AssignmentContext;
import generated.asn1.antlr.ASN1Parser.AssignmentListContext;
import generated.asn1.antlr.ASN1Parser.TypeAssignmentContext;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSequenceParser;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.DLSequence;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OctetStringTypeAssignmentListVisitor
        extends ASN1BaseVisitor<List<AsnClassDescriptor>> {

    public static List<AsnClassDescriptor> doVisit(AssignmentListContext assignmentListContext) {
        return new OctetStringTypeAssignmentListVisitor().visitAssignmentList(assignmentListContext);
    }

    @Override
    public List<AsnClassDescriptor> visitAssignmentList(AssignmentListContext ctx) {
        return ctx.assignment().stream()
                  .map(AssignmentContext::typeAssignment)
                  .filter(OctetStringTypeAssignmentVisitor::accepts)
                  .map(this::doVisitTypeAssignment)
                  .filter(Objects::nonNull)
                  .collect(Collectors.toList());
    }

    private AsnClassDescriptor doVisitTypeAssignment(TypeAssignmentContext typeAssignmentContext) {
        return OctetStringTypeAssignmentVisitor.doVisit(typeAssignmentContext);

    }

}

