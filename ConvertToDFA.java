import java.util.*;

public class ConvertToDFA {

     NFA nfa ;
     public int counter = 0;

     public ConvertToDFA(NFA nfa){
          this.nfa = nfa;
     }

     public ArrayList<NFAState> move(ArrayList<NFAState> states, char symbol) {
          
          ArrayList<NFAState> dest = new ArrayList<NFAState>();

          for (NFAState s:states){
               for (NFATransition t:s.transitions){
                    if (t.symbol == symbol){

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


     public boolean checkFinalState(ArrayList<NFAState> list){
          for (NFAState a: nfa.getAcceptStates() ){
               
               if (list.contains(a)){
               return true; 
               }
               
          }
          return false;

     }

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
                    // System.out.print(top.id + " --" + sym + "--> { ");

                    // for (NFAState s : transtionStates) {
                    // System.out.print(s.id + " ");
                    // }

                    // System.out.println("}");

                    DFAState check = dfa.checkDFAStatePresent(transtionStates);
                    if (check == null){
                         boolean finalState = checkFinalState(transtionStates);
                         check = new DFAState("S"+counter++,transtionStates,false,finalState);
                         DFS.push(check);
                         dfa.states.add(check);
                    }

                    // System.out.print("Adding transition: ");
                    // System.out.print(top.id);
                    // System.out.print(" --");
                    // System.out.print(sym);
                    // System.out.print("--> ");
                    // System.out.println(check.id);

                    top.addTransition(sym,check);

               }

          }
          return dfa; 
     }

     
}
