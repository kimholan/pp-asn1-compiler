package namedtype;

import descriptor.AsnFieldDescriptor;
import generated.asn1.antlr.ASN1Parser.NamedTypeContext;
import jinja.JavaClass;
import types.NamedTypes;
import types.ReferencedTypes;

import java.util.Optional;


public class TypeReferenceNamedType
        extends NamedTypeVisitor {

    @Override
    public AsnFieldDescriptor doVisit(NamedTypeContext it) {
        return new TypeReferenceNamedType().visit(it);
    }

    @Override
    public boolean accepts(NamedTypeContext namedType) {
        return Optional.ofNullable(namedType)
                       .map(NamedTypes::referencedType)
                       .map(ReferencedTypes::typeReference)
                       .isPresent();
    }

    @Override
    public AsnFieldDescriptor visitNamedType(NamedTypeContext namedType) {
        var asnFieldDescriptor = super.visitNamedType(namedType);

        var referencedType = namedType.type().referencedType();
        var typereference = ReferencedTypes.typeReference(referencedType);
        var type = typereference.getText();
        var identifier = JavaClass.identifier(type);

        asnFieldDescriptor.setSchemaType(true);
        asnFieldDescriptor.setAsnType(identifier);
        asnFieldDescriptor.setJavaType(identifier);

        return asnFieldDescriptor;
    }

}
