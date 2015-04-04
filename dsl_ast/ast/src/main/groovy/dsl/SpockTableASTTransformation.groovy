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

        def headers = processTableHeaders(existingStatements[0])
        def mapToSet = processTableBody(headers, existingStatements[1..-1])

        existingStatements.clear()

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

    def processTableHeaders(Statement headerStatement) {
        return _preOrderBinary([], headerStatement.expression)
    }

    def processTableBody(List headers, List<Statement> bodyStatements) {
        def currentMap = [:]
        def rows = bodyStatements.collect { _preOrderBinary([], it.expression) }
        headers.eachWithIndex { item, index->
            currentMap[item] = rows.collect { it[index] }
        }
        return currentMap
    }

    def _preOrderBinary(List acc, def expression) {
        if (expression instanceof VariableExpression) {
            acc.push(expression.name)
        } else if (expression instanceof ConstantExpression) {
            acc.push(expression.value)
        } else if(expression instanceof BinaryExpression){
            _preOrderBinary(acc, expression.leftExpression)
            _preOrderBinary(acc, expression.rightExpression)
        } else {
            throw new RuntimeException("Error of type: ${expression.class}")
        }
        return acc
    }

}
