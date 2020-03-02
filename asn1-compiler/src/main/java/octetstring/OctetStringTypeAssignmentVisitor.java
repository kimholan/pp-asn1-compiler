package octetstring;

import descriptor.AsnClassDescriptor;
import generated.asn1.antlr.ASN1BaseVisitor;
import generated.asn1.antlr.ASN1Parser.TypeAssignmentContext;
import org.bouncycastle.asn1.DEROctetString;
import types.TypeAssignments;

import java.util.Optional;

public class OctetStringTypeAssignmentVisitor
        extends ASN1BaseVisitor<AsnClassDescriptor> {

    static boolean accepts(TypeAssignmentContext typeAssignmentContext) {
        return Optional.ofNullable(typeAssignmentContext)
                       .map(TypeAssignments::octetString)
                       .isPresent();
    }

    static AsnClassDescriptor doVisit(TypeAssignmentContext typeAssignmentContext) {
        return new OctetStringTypeAssignmentVisitor().visitTypeAssignment(typeAssignmentContext);
    }

    @Override
    public AsnClassDescriptor visitTypeAssignment(TypeAssignmentContext typeAssignmentContext) {
        var descriptor = new AsnClassDescriptor();

        // OctetString type name
        var typereferenceContext = typeAssignmentContext.typereference();
        var typeName = typereferenceContext.getText();

        descriptor.setAsnType(DEROctetString.class.getSimpleName());
        descriptor.setIdentifier(typeName);
        descriptor.setJavaType("byte[]");

        // OctetString components

        return descriptor;
    }

}
