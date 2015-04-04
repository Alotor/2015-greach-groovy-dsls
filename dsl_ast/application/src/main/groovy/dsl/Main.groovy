package dsl

class Main {
    @SpockTable
    def getTable() {
        value1 | value2  || max
        1      | 2       || 2
        2      | 1       || 2
        2      | 2       || 2
    }

    public static void main(def args) {
        def tableData = new Main().getTable()
        println ">> ${tableData['value1']}"
        println ">> ${tableData['value2']}"
        println ">> ${tableData['max']}"
    }
}