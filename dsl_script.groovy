zimport org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ImportCustomizer
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer
import org.codehaus.groovy.control.customizers.SecureASTCustomizer

import groovy.util.logging.Log

/*
apply plugin: 'groovy'

repositories {
    mavenCentral()
}

dependencies {
    compile 'org.codehaus.groovy:groovy-all:2.4.3'
    compile project(':ast')
    compile project(':application')
}
*/


/*
// First try. Use Binding

def binding = new Binding(
   apply: { Map args -> println args},
   repositories: { Closure dsl -> println "repositories"},
   dependencies: { Closure dsl -> println "dependencies" }
)
def shell = new GroovyShell(binding)
shell.evaluate(new File("/home/alotor/Projects/Talks/dsling_groovy/dsl_ast/build.gradle"))
*/

// Second try. Use a DSL Object
class MyGradle {
   void apply(Map toApply) {
       println "PLUGINS $toApply"
   }
   void repositories(Closure dslRepositories) {
       println ">> REPOSITORIES"
   }
   
   void dependencies(Closure dslDependencies) {
       println ">> DEPENDENCIES"
   }
}

CompilerConfiguration configuration = new CompilerConfiguration();
configuration.setScriptBaseClass(DelegatingScript.class.getName());

// Adding imports
def imports = new ImportCustomizer()
imports.addStaticStar('java.util.Calendar')
configuration.addCompilationCustomizers(imports)

// Adding AST Cusomizers
configuration.addCompilationCustomizers(new ASTTransformationCustomizer(Log))

// Adding Script security
def secure = new SecureASTCustomizer()
secure.methodDefinitionAllowed = false
configuration.addCompilationCustomizers(secure)

GroovyShell shell = new GroovyShell(new Binding(),configuration);
DelegatingScript script = (DelegatingScript)shell.parse(new File("/home/alotor/Projects/Talks/dsling_groovy/dsl_ast/build.gradle"))
script.setDelegate(new MyGradle())
script.run()

