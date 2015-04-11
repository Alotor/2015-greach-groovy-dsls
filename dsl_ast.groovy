import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.ast.builder.*

import org.codehaus.groovy.control.*
import org.codehaus.groovy.syntax.*
import org.codehaus.groovy.transform.*

import java.lang.annotation.*


def getTablePostAST() {
    [ 
        value1 : [1, 2, 2],
        value2 : [2, 1, 2],
        max : [2, 2, 2]
    ]
}

def getTablePreAST() {
   value1 | value2  || max
   1      | 2       || 2
   2      | 1       || 2
   2      | 2       || 2
}

println "OK"
