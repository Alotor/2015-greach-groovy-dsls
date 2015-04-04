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
        existingStatements.add(createMapStatement())
    }

    private static Statement createMapStatement() {
        new ExpressionStatement(
            new MapExpression(
                [new MapEntryExpression(
                        new ConstantExpression("value1"),
                        new ListExpression(
                            [new ConstantExpression(1),
                             new ConstantExpression(2),
                             new ConstantExpression(2)]
                        )
                    ),
                 new MapEntryExpression(
                     new ConstantExpression("value2"),
                     new ListExpression(
                         [new ConstantExpression(2),
                          new ConstantExpression(1),
                          new ConstantExpression(2)]
                     )
                 ),
                 new MapEntryExpression(
                     new ConstantExpression("max"),
                     new ListExpression(
                         [new ConstantExpression(2),
                          new ConstantExpression(2),
                          new ConstantExpression(2)]
                     )
                 )]
            )
        )
    }
}
