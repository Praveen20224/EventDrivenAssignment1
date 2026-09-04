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
          
          // System.out.println(parser.getProcessedRegex());

          BuildENFA build = new BuildENFA(parser.getProcessedRegex());
          Fragment enfa =  build.buildEnfa();
          build.printENFATable(enfa);
          
          
     }
}
