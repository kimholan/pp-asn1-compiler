package choice;

import descriptor.AsnClassDescriptor;
import generated.asn1.antlr.ASN1BaseVisitor;
import generated.asn1.antlr.ASN1Parser.BuiltinTypeContext;
import generated.asn1.antlr.ASN1Parser.ChoiceTypeContext;
import generated.asn1.antlr.ASN1Parser.TypeAssignmentContext;
import generated.asn1.antlr.ASN1Parser.TypeContext;
import org.bouncycastle.asn1.ASN1Choice;

import java.util.Optional;

public class ChoiceTypeAssignmentVisitor
        extends ASN1BaseVisitor<AsnClassDescriptor> {

    static boolean accepts(TypeAssignmentContext typeAssignmentContext) {
        return Optional.ofNullable(typeAssignmentContext)
                       .map(ChoiceTypeAssignmentVisitor::choiceType)
                       .isPresent();
    }

    static AsnClassDescriptor doVisit(TypeAssignmentContext typeAssignmentContext) {
        return new ChoiceTypeAssignmentVisitor().visitTypeAssignment(typeAssignmentContext);
    }

    private static ChoiceTypeContext choiceType(TypeAssignmentContext typeAssignmentContext) {
        return Optional.ofNullable(typeAssignmentContext)
                       .map(TypeAssignmentContext::type)
                       .map(TypeContext::builtinType)
                       .map(BuiltinTypeContext::choiceType)
                       .orElse(null);
    }

    @Override
    public AsnClassDescriptor visitTypeAssignment(TypeAssignmentContext typeAssignmentContext) {
        var descriptor = new AsnClassDescriptor();

        // Choice type name
        var typereferenceContext = typeAssignmentContext.typereference();
        var typeName = typereferenceContext.getText();

        descriptor.setAsnType(ASN1Choice.class.getSimpleName());
        descriptor.setIdentifier(typeName);

        // Choice components
        var choiceType = choiceType(typeAssignmentContext);
        var fieldDescriptors = ChoiceTypeVisitor.doVisit(choiceType);

        descriptor.setFields(fieldDescriptors);

        return descriptor;
    }

}
