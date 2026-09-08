// A simple transition object which stores from, to and the symbol that triggers the transition.

public class Transition {
     State from;
     State to;
     char symbol;

     Transition(State from, State to, char symbol){
          this.from = from;
          this.to = to;
          this.symbol = symbol;
     }
     
     public void printTransition(){
          System.out.println("Transition from state " + from.id + " to state " + to.id + " on symbol '" + symbol + "'");
     }
}
