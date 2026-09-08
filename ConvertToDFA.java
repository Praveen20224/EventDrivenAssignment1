import java.util.*;

public class ConvertToDFA {

     NFA nfa ;
     public int counter = 0;

     public ConvertToDFA(NFA nfa){
          this.nfa = nfa;
     }


     // Succesively checking all the nfa subsets for a given DFA state, 
     // and their inner to transitions and adding it to destination states.
     public ArrayList<NFAState> move(ArrayList<NFAState> states, char symbol) {
          
          ArrayList<NFAState> dest = new ArrayList<NFAState>();

          for (NFAState s:states){
               for (NFATransition t:s.transitions){
                    if (t.symbol == symbol){
                         // Check the state's outgoing transition for the symbol.
                         for (NFAState inner : t.to){

                                   if(!dest.contains(inner)){
                                   dest.add(inner);
                              }
                         }
                         
                    }
                    
                    
               }
          }
          return dest;

     }

     // AN helper method used in marking if a particular DFA state is final.
     public boolean checkFinalState(ArrayList<NFAState> list){
          for (NFAState a: nfa.getAcceptStates() ){
               if (list.contains(a)){
               return true; 
               }   
          }
          return false;
     }

     // building DFA using Depth-First-Search method.
     public DFA buildDFA(){
          
          DFA dfa = new DFA();
          dfa.symbols = new ArrayList<Character>(nfa.symbols);
          Stack <DFAState> DFS = new Stack<DFAState>();
          DFAState start = new DFAState("S"+counter++ , nfa.getStartStates() , true , checkFinalState(nfa.getStartStates()));
          DFS.push(start);
          
          dfa.states.add(start);

          while (!DFS.isEmpty()){

               DFAState top = DFS.pop();
               for (Character sym : nfa.symbols){
                    
                    ArrayList<NFAState> transtionStates = move(top.subset,sym);
                    DFAState check = dfa.checkDFAStatePresent(transtionStates);

                    // If an existing state is found, then check would'nt be null
                    // and directly go to add the transition.

                    if (check == null){
                         boolean finalState = checkFinalState(transtionStates);
                         check = new DFAState("S"+counter++,transtionStates,false,finalState);
                         // If a new state is to be formed , then push into the stack and states list.
                         DFS.push(check);
                         dfa.states.add(check);
                    }
                    top.addTransition(sym,check);
               }
          }
          return dfa; 
     }
}
