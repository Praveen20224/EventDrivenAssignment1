import java.util.*;

public class RegexEngine {
     public static void main(String[] args) {
          boolean verbose = false;
          if (args.length > 0 && args[0].equals("-v")) {
               verbose = true;
          }
          Scanner scanner = new Scanner(System.in);

          String regex = scanner.nextLine();

          RegexParser parser = new RegexParser(regex);

          BuildENFA build = new BuildENFA(parser.getProcessedRegex());
          Fragment enfa =  build.buildEnfa();
          

          ConvertToNFA convert_to_nfa = new ConvertToNFA(enfa);
          
          NFA nfa = convert_to_nfa.buildNFA(build.allStates,build.allSymbols);
          nfa.removeUnreachableStates();


          ConvertToDFA convert_to_DFA = new ConvertToDFA(nfa);

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

