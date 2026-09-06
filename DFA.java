import java.util.*;

public class DFA {
     ArrayList<DFAState> states;
     ArrayList<Character> symbols;

     public DFA(){
          this.states = new ArrayList<DFAState>();
          this.symbols = new ArrayList<Character>();
     }

     public DFAState checkDFAStatePresent(ArrayList<NFAState> subset){
          // System.out.print("Searching for { ");
          // for (NFAState n : subset){
          //      System.out.print(n.id + " ");
          //           System.out.println("}");
          // }
                    

          // for (DFAState s : states) {
          //           System.out.print(s.id + " = { ");
          //           for (NFAState n : s.subset)
          //                System.out.print(n.id + " ");
          //           System.out.println("}");
          //      }
          
          for (DFAState s:states){

               int count=0;
               if (s.subset.size() == subset.size()){
                    for (NFAState q:subset){
                         if (s.subset.contains(q)){
                              count++;
                         }
                    }

                    if (count == subset.size() ){
                    // System.out.print("Returning " + s.id + " for subset { ");
                    //      for (NFAState q : subset) {
                    //           System.out.print(q.id + " ");
                    //      }
                    //      System.out.println("}");
                         return s;
                    }
               }
               
          }
          // System.out.print("Returning XXX for subset { ");
          // for (NFAState q : subset) {
          //      System.out.print(q.id + " ");
          // }
          // System.out.println("}");
          return null;
     }

     public String getDestination(DFAState state, char symbol) {

          for (DFATransition t : state.transitions) {
               if (t.symbol == symbol) {
                    return t.to.id;
               }
          }

          return "";
     }

     public void printDFATable() {

          System.out.print("\t");

          for (char c : symbols) {
               System.out.print(c + "\t");
          }
          System.out.println();

          for (DFAState state : states) {

               String row = "";

               if (state.start)
                    row += ">";

               if (state.accept)
                    row += "*";

               row += state.id;

               for (char c : symbols) {
                    row += "\t" + getDestination(state, c);
               }

               System.out.println(row);
          }
     }

     public DFAState transitionFuntion(DFAState left, char symbol){
          for (DFATransition t :left.transitions){
               if (t.symbol == symbol){
                    return t.to;
               }
          }
          return null;
     }
     public void validateString(String input, boolean v){
          DFAState current = this.states.get(0);

          for (int i=0; i<input.length() ; i++){

               current = transitionFuntion(current,input.charAt(i));
               if (v){
                    System.out.println(current.accept);
               }
               if (current == null){
                    System.out.println(false);
                    break;
               }
               if (v){
                    System.out.println(input.charAt(i));
               }
          }

          if (current != null){
               System.out.println(current.accept);
          }
     }
}
