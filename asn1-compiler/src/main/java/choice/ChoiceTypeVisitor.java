package choice;

import descriptor.AsnFieldDescriptor;
import generated.asn1.antlr.ASN1BaseVisitor;
import generated.asn1.antlr.ASN1Parser.ChoiceTypeContext;
import types.NamedTypes;

import java.util.List;
import java.util.stream.Collectors;

public class ChoiceTypeVisitor
        extends ASN1BaseVisitor<List<AsnFieldDescriptor>> {

    public static List<AsnFieldDescriptor> doVisit(ChoiceTypeContext choiceType) {
        return new ChoiceTypeVisitor().visitChoiceType(choiceType);
    }

    @Override
    public List<AsnFieldDescriptor> visitChoiceType(ChoiceTypeContext choiceType) {
        return choiceType.alternativeTypeLists().rootAlternativeTypeList()
                         .alternativeTypeList().namedType().stream()
                         .map(NamedTypes::visit)
                         .collect(Collectors.toList());
    }

}
