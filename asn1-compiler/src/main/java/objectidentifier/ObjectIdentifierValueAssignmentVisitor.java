package objectidentifier;

import descriptor.AsnFieldDescriptor;
import generated.asn1.antlr.ASN1BaseVisitor;
import generated.asn1.antlr.ASN1Parser;
import generated.asn1.antlr.ASN1Parser.ValueAssignmentContext;
import types.BuiltinTypes;

import java.util.Map;
import java.util.Optional;

public class ObjectIdentifierValueAssignmentVisitor
        extends ASN1BaseVisitor<AsnFieldDescriptor> {

    private Map<String, String> mappedValues;

    public ObjectIdentifierValueAssignmentVisitor(Map<String, String> mappedValues) {
        this.mappedValues = mappedValues;
    }

    public static boolean accepts(ValueAssignmentContext valueAssignmentContext) {
        return Optional.ofNullable(valueAssignmentContext)
                       .map(ValueAssignmentContext::type)
                       .map(ASN1Parser.TypeContext::builtinType)
                       .map(BuiltinTypes::objectIdentifierType)
                       .isPresent();
    }

    @Override
    public AsnFieldDescriptor visitValueAssignment(ASN1Parser.ValueAssignmentContext valueAssignmentContext) {
        var asnFieldDescriptor = new AsnFieldDescriptor();

        // Identifier
        var valuereference = valueAssignmentContext.valuereference();
        var identifier = valuereference.getText();
        asnFieldDescriptor.setIdentifier(identifier);

        // Value
        var objectIdentifierValueContext = valueAssignmentContext.value().builtinValue().objectIdentifierValue();
        var objectIdentifierValueVisitor = new ObjectIdentifierValueVisitor(mappedValues);
        var value = objectIdentifierValueVisitor.visit(objectIdentifierValueContext);
        asnFieldDescriptor.setValue(String.join(".", value));

        return asnFieldDescriptor;

    }

}
