import java.util.*;

public class ConvertToNFA{

     Fragment enfa;
     // public static ArrayList<Character> symbols;
     public static final char ep = '\u03B5';

     public ConvertToNFA(Fragment enfa){
          this.enfa = enfa;
     }

     public static ArrayList<State> epsilonClosure(State start){

          ArrayList<State> closure = new ArrayList<>();
          Stack<State> trackState = new Stack<>();

          closure.add(start);
          trackState.push(start);

          while (!trackState.isEmpty()){
               State current = trackState.pop();

               for (Transition t : current.transitions){
                    if (t.symbol == ep){
                         if(!closure.contains(t.to)){
                              closure.add(t.to);
                              trackState.push(t.to);
                         }
                    }
               }
          }

          return closure;
     }

     public ArrayList<State> move(ArrayList<State> states, char s){
          ArrayList<State> reachable = new ArrayList<State>();

          for (State c : states){
               for (Transition t : c.transitions){
                    if (t.symbol == s){
                         if (!reachable.contains(t.to)){
                              reachable.add(t.to);
                         }
                    }
               }
          }
          return reachable;
     }

     public NFA buildNFA(ArrayList<State> allStates,ArrayList<Character> allSymbols){

          ArrayList<Character> symbols = new ArrayList<>(allSymbols);
          symbols.remove(Character.valueOf(ep));
          State start = enfa.start;
          State end = enfa.end;
          ArrayList<State> startEPClosure = epsilonClosure(start);

          // System.out.println("Start ε-closure:");

          // for (State s : startEPClosure) {
          //      System.out.println(s.id);
          // }

          NFA nfa = new NFA(symbols);
          int counter = 0;
          HashMap<State, NFAState> ENFA_NFA_Map = new HashMap<>();

          for (State s: allStates){
               boolean st =false;
               boolean accept = false;

               if(startEPClosure.contains(s)){
                    st=true;
               }

               ArrayList<State> epClosure = epsilonClosure(s);

               if (epClosure.contains(end)){
                    accept=true;
               }

               NFAState newNFAState = new NFAState("S"+counter++,st,accept);
               ENFA_NFA_Map.put(s,newNFAState);
               nfa.states.add(newNFAState);
          }


          for (State s : allStates){

               NFAState currentNFAState = ENFA_NFA_Map.get(s);
               ArrayList<State> epClosure = epsilonClosure(s);
               for (Character sym : symbols){
                    ArrayList<State> reachable = move(epClosure,sym);
                    ArrayList<NFAState> reachableNFA = new ArrayList<NFAState>();
                    for (State c:reachable){
                         reachableNFA.add(ENFA_NFA_Map.get(c));
                    }

                    currentNFAState.addTransition(sym,reachableNFA);      
               }
               
          }

          return nfa;
     }
}