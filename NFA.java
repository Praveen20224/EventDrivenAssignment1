import java.util.*;

public class NFA {


     ArrayList<NFAState> states;
     ArrayList<Character> symbols;

     public NFA( ArrayList<Character> symbols){
          this.states = new ArrayList<NFAState>();
          this.symbols = new ArrayList<>(symbols);
     }

     public String getDestinations(NFAState state, char symbol) {

          for (NFATransition t : state.transitions) {
               if (t.symbol == symbol) {

                    String result = "";

                    for (NFAState dest : t.to) {
                         if (!result.isEmpty()) {
                              result += ",";
                         }
                         result += dest.id;
                    }

                    return result;
               }
          }

          return "";
     }

     public void printNFATable() {

          System.out.print("\t");

          for (char c : symbols) {
               
               System.out.print(c + "\t");
               
          }
          System.out.println();

          for (NFAState state : states) {

               String row = "";

               if (state.start)
                    row += ">";

               if (state.accept)
                    row += "*";

               row += state.id;

               for (char c : symbols) {

                    row += "\t" + getDestinations(state, c);
               }

               System.out.println(row);
          }
     }

     public void removeUnreachableStates() {

          ArrayList<NFAState> reachable = new ArrayList<>();
          Stack<NFAState> stack = new Stack<>();

          // Add all start states
          for (NFAState state : states) {
               if (state.start) {
                    reachable.add(state);
                    stack.push(state);
               }
          }

          // DFS
          while (!stack.isEmpty()) {

               NFAState current = stack.pop();

               for (NFATransition t : current.transitions) {
                    for (NFAState next : t.to) {

                         if (!reachable.contains(next)) {
                              reachable.add(next);
                              stack.push(next);
                         }
                    }
               }
          }

          // Remove unreachable states
          states.removeIf(state -> !reachable.contains(state));
     }

     public ArrayList<NFAState> getStartStates(){
          ArrayList<NFAState> startList = new ArrayList<NFAState>();
          for (NFAState s : states){
               if (s.start){
                    startList.add(s);
               }
          }
          return startList;
     }
     
     public ArrayList<NFAState> getAcceptStates(){
          ArrayList<NFAState> acceptList = new ArrayList<NFAState>();
          for (NFAState s : states){
               if (s.accept){
                    acceptList.add(s);
                    // startList.add(s);
               }
          }
          return acceptList;
     }
}
