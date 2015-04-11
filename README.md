# DSL'ing your Groovy examples
This are some of the code examples of my talk Dsl'ing your Groovy

The four techniques that we checked were:

1. Closures
2. Builders
3. Open Classes
4. AST
5. Script

## AST examples

The code for the DSL is separated in 3 steps and there are git tags for each of the steps.

```git checkout step1```

Shows the first step where the AST code is hardcoded inside the transformation.


```git checkout step2```

The second step we generalized the AST code so it gets a map for input. That way we can transition quickly between both.


```git checkout master```

Transformation is finished with the generation of the map data from the original AST data.
