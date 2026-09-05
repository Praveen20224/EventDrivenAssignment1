public class DFA {
     ArrayList<DFAState> states;
     ArrayList<Character> symbols;

     public DFA(){
          this.states = new ArrayList<DFAState>();
          this.symbols = new ArrayList<Character>();
     }

     public DFAState checkDFAStatePresent(ArrayList<NFAState> subset){
     
          for (DFAState s:states){
               int count=0;
               if (s.subset.size() == subset.size()){
                    for (NFAState q:subset){
                         if (s.subset.contains(q)){
                              count++;
                         }
                    }
               }
               if (count == s.subset.size() ){
                    
                    return s;
               }
          }
          return null;
     }
}
