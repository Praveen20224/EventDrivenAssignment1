import java.util.*;

import javax.smartcardio.CardTerminals.State;

public class ConvertToNFA{

     Fragment enfa;

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

          allSymbols.remove(ep);
          State start = enfa.start;
          State end = enfa.end;
          ArrayList<State> startEPClosure = epsilonClosure(start);
          ArrayList<State> endEPClosure = epsilonClosure(end);
          NFA nfa = new NFA();
          int counter = 0;
          
          for (State s : allStates){
               
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

               for (Character sym : allSymbols){
                   newNFAState.addTransition(sym,move(epClosure,sym));      
               }
               nfa.states.add(newNFAState);
          }

          return nfa;
     }
}