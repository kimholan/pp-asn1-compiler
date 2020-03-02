package objectidentifier;

import descriptor.AsnEnumDescriptor;
import descriptor.AsnFieldDescriptor;
import generated.asn1.antlr.ASN1BaseVisitor;
import generated.asn1.antlr.ASN1Parser;
import generated.asn1.antlr.ASN1Parser.AssignmentContext;
import generated.asn1.antlr.ASN1Parser.ValueAssignmentContext;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ObjectIdentifierValueAssignmentListVisitor
        extends ASN1BaseVisitor<AsnEnumDescriptor> {

    private Map<String, String> mappedValues = new HashMap<>();

    @Override
    public AsnEnumDescriptor visitAssignmentList(ASN1Parser.AssignmentListContext ctx) {
        var enumDescriptor = new AsnEnumDescriptor();
        var fieldDescriptors = ctx.assignment().stream()
                                  .map(AssignmentContext::valueAssignment)
                                  .filter(ObjectIdentifierValueAssignmentVisitor::accepts)
                                  .map(this::doVisitValueAssignment)
                                  .collect(Collectors.toList());

        enumDescriptor.setIdentifier("ObjectIdentifiers");
        enumDescriptor.setFields(fieldDescriptors);

        return enumDescriptor;
    }

    private AsnFieldDescriptor doVisitValueAssignment(ValueAssignmentContext valueAssignmentContext) {
        var objectIdentifierValueAssignmentVisitor = new ObjectIdentifierValueAssignmentVisitor(mappedValues);
        var asnFieldDescriptor = objectIdentifierValueAssignmentVisitor.visit(valueAssignmentContext);

        mappedValues.put(asnFieldDescriptor.getIdentifier(), asnFieldDescriptor.getValue());

        return asnFieldDescriptor;
    }

}

