package choice;

import descriptor.AsnClassDescriptor;
import descriptor.AsnFieldDescriptor;
import generated.asn1.antlr.ASN1BaseVisitor;
import generated.asn1.antlr.ASN1Parser;
import generated.asn1.antlr.ASN1Parser.AssignmentListContext;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChoiceTypeAssignmentListVisitor
        extends ASN1BaseVisitor<List<AsnClassDescriptor>> {

    private static List<AsnClassDescriptor> hoist(AsnClassDescriptor it) {
        return Stream.concat(
                Stream.of(it),
                hoist(it.getFields()).stream()
        ).collect(Collectors.toList());
    }

    private static List<AsnClassDescriptor> hoist(List<AsnFieldDescriptor> fieldDescriptors) {
        var classDescriptors = fieldDescriptors.stream()
                                               .map(AsnFieldDescriptor::getClassDescriptor)
                                               .filter(Objects::nonNull)
                                               .collect(Collectors.toList());

        return Stream.concat(
                classDescriptors.stream(),
                classDescriptors.stream().map(ChoiceTypeAssignmentListVisitor::hoist).flatMap(Collection::stream)
        ).collect(Collectors.toList());
    }

    public static List<AsnClassDescriptor> doVisit(AssignmentListContext assignmentListContext) {
        return new ChoiceTypeAssignmentListVisitor().visitAssignmentList(assignmentListContext).stream()
                                                    .map(ChoiceTypeAssignmentListVisitor::hoist)
                                                    .flatMap(Collection::stream)
                                                    .collect(Collectors.toList());
    }

    @Override
    public List<AsnClassDescriptor> visitAssignmentList(AssignmentListContext ctx) {
        return ctx.assignment().stream()
                  .map(ASN1Parser.AssignmentContext::typeAssignment)
                  .filter(ChoiceTypeAssignmentVisitor::accepts)
                  .map(this::doVisitTypeAssignment)
                  .filter(Objects::nonNull)
                  .collect(Collectors.toList());
    }

    private AsnClassDescriptor doVisitTypeAssignment(ASN1Parser.TypeAssignmentContext typeAssignmentContext) {
        return ChoiceTypeAssignmentVisitor.doVisit(typeAssignmentContext);

    }

}
