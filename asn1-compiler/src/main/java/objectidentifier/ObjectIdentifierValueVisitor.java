package objectidentifier;

import generated.asn1.antlr.ASN1BaseVisitor;
import generated.asn1.antlr.ASN1Parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ObjectIdentifierValueVisitor
        extends ASN1BaseVisitor<List<String>> {

    private final Map<String, String> mappedValues;

    public ObjectIdentifierValueVisitor(Map<String, String> mappedValues) {
        this.mappedValues = mappedValues;
    }

    @Override
    public List<String> visitNameForm(ASN1Parser.NameFormContext ctx) {
        var identifier = ctx.identifier().getText();
        var identifierValue = mappedValues.get(identifier);
        return List.of(identifierValue);
    }

    @Override
    public List<String> visitNameAndNumberForm(ASN1Parser.NameAndNumberFormContext ctx) {
        return List.of(ctx.numberForm().getText());
    }

    @Override
    public List<String> visitNumberForm(ASN1Parser.NumberFormContext ctx) {
        return List.of(ctx.getText());
    }

    @Override
    protected List<String> defaultResult() {
        return new ArrayList<>();
    }

    @Override
    protected List<String> aggregateResult(List<String> aggregate, List<String> nextResult) {
        return Stream.of(aggregate, nextResult)
                     .flatMap(Collection::stream)
                     .collect(Collectors.toList());

    }

}
