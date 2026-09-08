import java.util.*;

public class NFA {


     ArrayList<NFAState> states;
     ArrayList<Character> symbols;

     public NFA( ArrayList<Character> symbols){
          this.states = new ArrayList<NFAState>();
          this.symbols = new ArrayList<>(symbols);
     }

     // A method which i found necessary while implementing NFA->DFA

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

     // To print table
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

     // To remove all unreachable states in the current NFA
     public void removeUnreachableStates() {

          ArrayList<NFAState> reachable = new ArrayList<>();
          Stack<NFAState> stack = new Stack<>();

          // Push all start states
          for (NFAState state : states) {
               if (state.start) {
                    reachable.add(state);
                    stack.push(state);
               }
          }

          // Using DFS method , search for states who have no transitions
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

     // An useful getter method to get start states 
     public ArrayList<NFAState> getStartStates(){
          ArrayList<NFAState> startList = new ArrayList<NFAState>();
          for (NFAState s : states){
               if (s.start){
                    startList.add(s);
               }
          }
          return startList;
     }
     
     // An useful getter method to get accept states 
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
