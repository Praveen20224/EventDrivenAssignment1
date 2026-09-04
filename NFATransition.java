import java.util.ArrayList;

public class NFATransition {

     public ArrayList<State> to;
     public char symbol;

     NFATransition( char symbol , ArrayList<State> to){
          this.to = to;
          this.symbol = symbol;
     }
     
}
