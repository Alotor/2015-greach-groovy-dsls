package dsl

class Main {
    @SpockTable
    def getTable() {
        value1 | value2 | value3 || max
        1      | 2      | 3      || 3
        2      | 1      | 0      || 2
        2      | 2      | 1      || 2
    }

    public static void main(def args) {
        def tableData = new Main().getTable()
        println ">> ${tableData['value1']}"
        println ">> ${tableData['value2']}"
        println ">> ${tableData['value3']}"
        println ">> ${tableData['max']}"
    }
}