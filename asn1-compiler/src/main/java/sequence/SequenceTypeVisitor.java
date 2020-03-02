package sequence;

import descriptor.AsnFieldDescriptor;
import generated.asn1.antlr.ASN1BaseVisitor;
import generated.asn1.antlr.ASN1Parser.ComponentTypeContext;
import generated.asn1.antlr.ASN1Parser.SequenceTypeContext;
import types.NamedTypes;

import java.util.List;
import java.util.stream.Collectors;

public class SequenceTypeVisitor
        extends ASN1BaseVisitor<List<AsnFieldDescriptor>> {

    public static List<AsnFieldDescriptor> doVisit(SequenceTypeContext sequenceType) {
        return new SequenceTypeVisitor().visitSequenceType(sequenceType);
    }

    @Override
    public List<AsnFieldDescriptor> visitSequenceType(SequenceTypeContext sequenceType) {
        var rootComponentType = sequenceType.componentTypeLists().rootComponentTypeList().get(0);
        var componentTypes = rootComponentType.componentTypeList().componentType();

        return componentTypes.stream()
                             .map(ComponentTypeContext::namedType)
                             .map(NamedTypes::visit)
                             .collect(Collectors.toList());
    }

}
