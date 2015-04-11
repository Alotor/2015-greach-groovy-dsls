class EmailSender {
   Map attributes = [:]

   def send(Closure cls) {
       def handler = new EmailHandler()
       def code = cls.rehydrate(handler, null, null)
       code.resolveStrategy = Closure.DELEGATE_ONLY
       code.call()
       println handler.attributes
   }
   
   class EmailHandler {
       void from(String value) { 
           attributes["from"] = value 
       }
       void to(String value) { 
           attributes["to"] = value 
       }
       void subject(String value) { 
           attributes["subject"] = value 
       }
       void body(Closure body) {
           attributes["body"] = [:]
           
           def handler = new BodyHandler()
           def code = body.rehydrate(handler, null, null)
           code.resolveStrategy = Closure.DELEGATE_ONLY
           code.call()
       }
   }
   
   class BodyHandler {
   
      def methodMissing(String name, def args) {
          attributes["body"][name] = args
      }
   }
}

def email = new EmailSender()
email.send {
   from    'dsl@groovy.com'
   to      'greach@greachconf.com'
   subject 'Easy DSL'

   body {
       p 'Welcome DSL'
   }
}
