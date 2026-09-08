import java.util.*;

public class RegexEngine {
     public static void main(String[] args) {
          boolean verbose = false;

          // Check for verbose mode

          if (args.length > 0 && args[0].equals("-v")) {
               verbose = true;
          }
          Scanner scanner = new Scanner(System.in);

          String regex = scanner.nextLine();

          RegexParser parser = new RegexParser(regex); // Parser object , used in validating input and then infix->postfix 


          BuildENFA build = new BuildENFA(parser.getProcessedRegex()); // To build E-NFA


          Fragment enfa =  build.buildEnfa(); //To store E-NFA
          

          ConvertToNFA convert_to_nfa = new ConvertToNFA(enfa); // Convert E-NFA to NFA object.
          
          NFA nfa = convert_to_nfa.buildNFA(build.allStates,build.allSymbols);

          // After noticing a few unreachable stated being printed in the NFA transition table,
          // I thought it would be better to remove them.
          nfa.removeUnreachableStates(); 


          ConvertToDFA convert_to_DFA = new ConvertToDFA(nfa);// Convert E-NFA to DFA object.

          DFA dfa = convert_to_DFA.buildDFA();

          

          if (verbose){
               build.printENFATable(enfa);
               nfa.printNFATable();
               dfa.printDFATable();
          }

          System.out.println("Ready");

          while(true){
               String input = scanner.nextLine();
               dfa.validateString(input,verbose);

          }

     }    
}

