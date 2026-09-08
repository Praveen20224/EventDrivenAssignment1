import java.util.*;

public class BuildENFA {

     String regex;     // Store Input Regex
     int stateCounter; // Counter to assign unique IDs to states
     Stack<Fragment> fragmentStack = new Stack<>(); // Used in Thompson's nfa construction
     public static final char ep = '\u03B5'; // epsilon character
     ArrayList<State> allStates = new ArrayList<>(); // To store all E-NFA states
     ArrayList<Character> allSymbols = new ArrayList<>(); // To store all the symbols seen

     public BuildENFA(String regex) { //Construtor Intialization
          this.regex = regex;
          this.stateCounter = 0;
          allSymbols.add(ep);

     }

     public Fragment buildEnfa(){

          for (int c =0; c<regex.length(); c++){
               char current = regex.charAt(c);
               if (Character.isLetterOrDigit(current) || current == ' '){ // Also treating space as a valid input symbol

                    if (!allSymbols.contains(current)) {
                         allSymbols.add(current);
                    }

                    State first = new State("S"+stateCounter++,false);
                    State last = new State("S"+stateCounter++,true);
                    allStates.add(first); 
                    allStates.add(last);                                   // Applying Thompson's rule

                    first.addTransition(last,current);
                    Fragment symbol = new Fragment(first,last); 
                    // System.out.println("Pushing Fragment of "+ current);
                    fragmentStack.push(symbol);
                    // System.out.println("After Pushing start: "+ fragmentStack.peek().start.id + "Transition with symbol :" + fragmentStack.peek().start.transitions.get(0).symbol + " to " + fragmentStack.peek().end.id);

               } else if (current == '.'){
                    Fragment second = fragmentStack.pop();
                    Fragment first = fragmentStack.pop();
                    first.end.accept = false;
                    first.end.addTransition(second.start,ep);              // Applying Thompson's rule of kleene dot
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
                    second.end.addTransition(newEnd,ep);                   // Applying Thompson's rule of kleene or

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
                                                                           // Applying Thompson's rule of kleene star
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
                                                                           // Applying Thompson's rule of +
                    Fragment newPlus = new Fragment(newStart,newEnd);
                    fragmentStack.push(newPlus);
               }


          }

          return fragmentStack.pop(); // Return the final fragment which contains the final E-NFA
     }

     public ArrayList<State> getAllStates() {
          return allStates;           // An usefull getter method                                
     }
     
     public ArrayList<Character> getallSymbols() {
          return allSymbols;            // An usefull getter method
     }

     public String getDestinations(State a, char s){
          String destinationString = "";

          for (Transition t :a.transitions) {
               if (t.symbol == s){
                    if (!destinationString.isEmpty()) {
                         destinationString += ",";
                    }
                    destinationString +=t.to.id;
               }
          }
          return destinationString;
     }                                  // An useful repeated method used while printing table.

     public void printENFATable(Fragment enfa){

          System.out.print("\t");

          for (char symbol : allSymbols) {

               System.out.print(symbol + "\t");
          }

          System.out.println();

          for (State current : allStates){
               String finalPrintString = "";
               if (current == enfa.start){
                    finalPrintString +=">";
               }
               if (current.accept){
                    finalPrintString +="*";
               }
               finalPrintString += current.id + "\t";

               for (char s : allSymbols){

                    String destinations = getDestinations(current,s);
                    if (destinations.equals("")){
                         finalPrintString +="\t";
                    }else{
                         finalPrintString += destinations+"\t";
                    }
               }

               System.out.println(finalPrintString);

          }



     }


     
}
