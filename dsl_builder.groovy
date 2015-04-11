import groovy.transform.ToString

@ToString
class Container {
   String name
   Object value
   Map attributes
   List<Container> items = []
}

class BinaryTreeBuilderSupport extends BuilderSupport {
  def createNode(name, Map attributes=[:], value) {
     new Container(name: name, attributes: attributes, value: value)
  }

  void setParent(def parent, def child) {
     parent.items.push(child)
  }

  def createNode(name, Map attributes=[:]) {
     createNode(name, attributes, null)
  }
}

def builder = new BinaryTreeBuilderSupport()

def bookshelf = builder.bookshelf {
   author("George R. R. Martin") {
       books {
           "A Game Of Thrones" {
              pages 1000
              characters 57
              
              houses {
                  stark {
                      motto "Winter is comming"
                  }
              }
           }
       }
   }
}

/*
def criteria = new BinaryTreeBuilderSupport().list {
   between "balance", 500, 1000
   eq "branch", "London"
   or {
       like "holderFirstName", "Fred%"
       like "holderFirstName", "Barney%"
   }
   maxResults 10
   order "holderLastName", "desc"
}
*/

println bookshelf.items[0].items[0].items.collect { it.name }