import java.util.*;

public class BuildENFA {

     String regex;
     int stateCounter; // Counter to assign unique IDs to states
     Stack<Fragment> fragmentStack = new Stack<>();
     public static final char ep = '\u03B5';
     ArrayList<State> allStates = new ArrayList<>();

     public BuildENFA(String regex) {
          this.regex = regex;
          this.stateCounter = 0;

     }

     public void buildEnfa(){

          for (int c =0; c<regex.length(); c++){
               char current = regex.charAt(c);
               if (Character.isLetterOrDigit(current) || current == ' '){
                    State first = new State("S"+stateCounter++,false);
                    State last = new State("S"+stateCounter++,true);
                    allStates.add(first);
                    allStates.add(last);
                    
                    first.addTransition(last,current);
                    Fragment symbol = new Fragment(first,last);
                    // System.out.println("Pushing Fragment of "+ current);
                    fragmentStack.push(symbol);
                    // System.out.println("After Pushing start: "+ fragmentStack.peek().start.id + "Transition with symbol :" + fragmentStack.peek().start.transitions.get(0).symbol + " to " + fragmentStack.peek().end.id);

               } else if (current == '.'){
                    Fragment second = fragmentStack.pop();
                    Fragment first = fragmentStack.pop();
                    first.end.accept = false;
                    first.end.addTransition(second.start,ep);
                    Fragment addConcatOp = new Fragment(first.start,second.end);
                    fragmentStack.push(addConcatOp);

               }else if (current == '|'){
                    Fragment second = fragmentStack.pop();
                    Fragment first = fragmentStack.pop();
                    first.end.accept = false;
                    second.end.accept = false;

                    State newStart = new State("S"+stateCounter++,false);
                    State newEnd = new State("S"+stateCounter++,true);
                    allStates.add(newStart);
                    allStates.add(newEnd);

                    newStart.addTransition(first.start,ep);
                    newStart.addTransition(second.start,ep);
                    first.end.addTransition(newEnd,ep);
                    second.end.addTransition(newEnd,ep);

                    Fragment newOR = new Fragment(newStart,newEnd);
                    fragmentStack.push(newOR);
               }else if (current == '*'){
                    Fragment first = fragmentStack.pop();
                    

                    first.end.accept = false;
                    
                    State newStart = new State("S"+stateCounter++,false);
                    State newEnd = new State("S"+stateCounter++,true);
                    allStates.add(newStart);
                    allStates.add(newEnd);

                    newStart.addTransition(newEnd,ep);
                    newStart.addTransition(first.start,ep);
                    first.end.addTransition(first.start,ep);
                    first.end.addTransition(newEnd,ep);

                    Fragment newStar = new Fragment(newStart,newEnd);
                    fragmentStack.push(newStar);
               }else if (current == '+'){
                    Fragment first = fragmentStack.pop();
                    

                    first.end.accept = false;
                    
                    State newStart = new State("S"+stateCounter++,false);
                    State newEnd = new State("S"+stateCounter++,true);
                    allStates.add(newStart);
                    allStates.add(newEnd);

                    newStart.addTransition(first.start,ep);
                    first.end.addTransition(first.start,ep);
                    first.end.addTransition(newEnd,ep);

                    Fragment newPlus = new Fragment(newStart,newEnd);
                    fragmentStack.push(newPlus);
               }


          }
     }

     public ArrayList<State> getAllStates() {
          return allStates;
     }
     

     
}
