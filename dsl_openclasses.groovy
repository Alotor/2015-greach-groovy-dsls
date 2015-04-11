Number.metaClass.randomTimes = { Closure cls->
   def randomValue = (new Random().nextInt(delegate)) +1
   randomValue.times(cls)
}

10.randomTimes {
  println "x"
}

class Velocity {
   Distance distance
   TimeUnits perUnit
}

class Distance {
  Integer meters
  
  Velocity div(TimeUnits tu) {
      new Velocity(distance: this, perUnit: tu)
  }
}

enum TimeUnits {
   HOUR, SECOND
}

def h = TimeUnits.HOUR
def s = TimeUnits.SECOND
Number.metaClass.getKm = {
   return new Distance(meters: delegate * 1000)
}

Number.metaClass.getM = {
   return new Distance(meters: delegate)
}

def velocity1 = 60.km/h
def velocity2 = 1000.m/s

class Quantity {
   String container
   Integer quantity
}

class Order {
   Quantity quantity
   String product
   
   def of(String product) {
       this.product = product
       return this
   }
}

Integer.metaClass.getBottles = {
   return new Quantity(quantity: delegate, container: "bottle")
}

def buy(Quantity toBuy) {
   new Order(quantity: toBuy)
}

def order = buy 10.bottles of "milk"
