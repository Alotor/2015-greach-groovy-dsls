package dsl

import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.control.*
import org.codehaus.groovy.syntax.*
import org.codehaus.groovy.transform.*

@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
class SpockTableASTTransformation implements ASTTransformation {
    @Override
    void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
        MethodNode method = (MethodNode) nodes[1]
        def existingStatements = ((BlockStatement)method.code).statements
        existingStatements.clear()

        def mapToSet = [
            value1: [1, 2, 2],
            value2: [2, 1, 2],
            max: [2, 2, 2]
        ]

        def mapExpression = createMapStatement(mapToSet)
        existingStatements.add(mapExpression)
    }

    private static Statement createMapStatement(Map value) {
        def entries = value.collect { curValue->
            def listValues = curValue.value.collect { listValue ->
                new ConstantExpression(listValue)
            }
            new MapEntryExpression(
                new ConstantExpression(curValue.key),
                new ListExpression(listValues)
            )
        }
        new ExpressionStatement(
            new MapExpression(entries)
        )
    }
}
