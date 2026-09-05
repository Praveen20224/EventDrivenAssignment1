import java.util.ArrayList;

public class NFATransition {

     public ArrayList<NFAState> to;
     public char symbol;

     NFATransition( char symbol , ArrayList<NFAState> to){
          this.to = to;
          this.symbol = symbol;
     }
     
}
