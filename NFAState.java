import java.util.ArrayList;

public class NFAState {
     
     public String id;
     public boolean start;
     public boolean accept;
     public ArrayList<NFATransition> transitions;

     public NFAState(String id, boolean start, boolean  accept ){
          this.id = id;
          this.start = start;
          this.accept = accept;
          this.transitions = new ArrayList<NFATransition>();
     }

     public void addTransition(char symbol, ArrayList<NFAState> destinations) {

          transitions.add(new NFATransition(symbol, destinations));
          
     } 

}
