package compiler;

import choice.ChoiceTypeAssignmentListVisitor;
import generated.asn1.antlr.ASN1Lexer;
import generated.asn1.antlr.ASN1Parser;
import jinja.JavaBuiltin;
import jinja.JavaChoice;
import jinja.JavaClass;
import jinja.JavaEnum;
import jinja.JavaSequence;
import jinja.JavaSequenceOf;
import objectidentifier.ObjectIdentifierValueAssignmentListVisitor;
import octetstring.OctetStringTypeAssignmentListVisitor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.codehaus.groovy.runtime.ResourceGroovyMethods;
import sequence.SequenceOfBuiltinTypeAssignmentListVisitor;
import sequence.SequenceTypeAssignmentListVisitor;

import java.io.File;
import java.io.IOException;

public class Compiler {

    /**
     * Compiles the pp.asn1-schema at the specified location.
     *
     * @param args Exactly 2 arguments are expected and required: [0] outputDirectory [1] pp.asn1-schemalocation.
     * @throws IOException Mistakes have been made.
     */
    public static void main(String[] args) throws IOException {
        // Create directories
        var outputDir = new File(new File(args[0]), "generated/asn1");
        outputDir.mkdirs();

        // ANTLR generator ASN.1-grammar
        var charStream = CharStreams.fromFileName(args[1]);
        var lexer = new ASN1Lexer(charStream);
        var tokens = new CommonTokenStream(lexer);
        var parser = new ASN1Parser(tokens);
        var assignmentList = parser.moduleDefinition().moduleBody(0).assignmentList();

        // Process OID values first
        var oidValueVisitor = new ObjectIdentifierValueAssignmentListVisitor();
        var enumDescriptor = assignmentList.accept(oidValueVisitor);
        var enumTypeName = JavaClass.identifier(enumDescriptor.getIdentifier());
        var enumBody = JavaEnum.render(enumDescriptor);
        ResourceGroovyMethods.write(new File(outputDir, enumTypeName + ".java"), enumBody);

        // Choices
        var choiceTypeAssignments = ChoiceTypeAssignmentListVisitor.doVisit(assignmentList);
        for (var item : choiceTypeAssignments) {
            var javaTypeName = JavaClass.identifier(item.getIdentifier());
            var classBody = JavaChoice.render(item);

            ResourceGroovyMethods.write(new File(outputDir, javaTypeName + ".java"), classBody);
        }

        // Sequences
        var sequenceTypeAssignments = SequenceTypeAssignmentListVisitor.doVisit(assignmentList);
        for (var item : sequenceTypeAssignments) {
            var javaTypeName = JavaClass.identifier(item.getIdentifier());
            var classBody = JavaSequence.render(item);

            ResourceGroovyMethods.write(new File(outputDir, javaTypeName + ".java"), classBody);
        }

        // Type assignments of builtins
        var octetstringTypeAssignments = OctetStringTypeAssignmentListVisitor.doVisit(assignmentList);
        for (var item : octetstringTypeAssignments) {
            var javaTypeName = JavaClass.identifier(item.getIdentifier());
            var classBody = JavaBuiltin.render(item);

            ResourceGroovyMethods.write(new File(outputDir, javaTypeName + ".java"), classBody);
        }

        // Sequence of types
        var sequenceOfTypeAssignments = SequenceOfBuiltinTypeAssignmentListVisitor.doVisit(assignmentList);
        for (var item : sequenceOfTypeAssignments) {
            var javaTypeName = JavaClass.identifier(item.getIdentifier());
            var classBody = JavaSequenceOf.render(item);

            ResourceGroovyMethods.write(new File(outputDir, javaTypeName + ".java"), classBody);
        }

    }

}

