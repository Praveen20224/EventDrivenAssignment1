import java.util.Stack; // for converting infix to postfix

public class RegexParser {

     String input_regex;
     String processed_regex; // The regex after adding explicit concatenation operators
     int length;
     RegexParser(String regex) {
          this.input_regex = regex;
          this.processed_regex = ""; // Initialize the processed regex
          this.length = regex.length();
     }

     public boolean isValid(){
          if (length == 0) {
               return false; // Empty regex is not valid
          }
          char f = input_regex.charAt(0);
          char l = input_regex.charAt(length-1); 
          // check if the first or last character is an operator that cannot be at the start or end of a regex
          if (f == '*' || f == '+' || f == ')' || f == '|' || l == '(' || l == '|'  ){
               return false;
          }
          
          // check for consecutive operators that are not allowed
          for (int i = 0; i < length - 1; i++) {
               char current = input_regex.charAt(i);
               char next = input_regex.charAt(i + 1);

               if ((current == '*' || current == '+' || current == '(' || current == '|') && (next == '*' || next == '+' || next == ')' || next == '|')) {
                    return false;
               }
          }

          // check for balanced parentheses
          int balance = 0;
          for (int i = 0; i < length; i++) {
               char c = input_regex.charAt(i);
               if (c == '(') {
                    balance++;
               } else if (c == ')') {
                    balance--;
                    if (balance < 0) {
                         return false; // More closing parentheses than opening
                    }
               }
          }
          if (balance != 0) {
               return false; // Unbalanced parentheses
          }
          return true;
     }

     public String addConcatenationOperator(){
          for (int i = 0; i < length; i++) {
                    char c = input_regex.charAt(i);
                    processed_regex +=c;
                    // System.out.println(processed_regex);
                    if (i+1<length){
                        char next = input_regex.charAt(i+1);
                        if (Character.isLetterOrDigit(c) || c == ' '){
                            if (Character.isLetterOrDigit(next) || next == ' '){
                              //   System.out.println("Adding . bcoz next is character : " + next);
                                processed_regex +=".";
                            }
                            if ( next == '('){
                              //   System.out.println("Adding . bcoz next is ( or space :");
                                processed_regex +=".";
                            }
                            
                        }
                        if ((c == ')' || c == '*' || c == '+' ) && (Character.isLetterOrDigit(next) || next == '(' || next == ' ')) {

                         //    System.out.println("Adding . bcoz next is character or ( " + next);
                            processed_regex +=".";
                        }

                    }
                    
        }
        return processed_regex;
     }

     public int checkPrecedence(char op){
          switch (op) {
               case '*':
               case '+':
                    return 3;
               case '.':
                    return 2;
               case '|':
                    return 1;
               default:
                    return 0; // For non-operators
          }
     }

     public String infixToPostfix (String infix) {  
          Stack<Character> operator = new Stack<>();
          String outputQueue = "";
          for (int i = 0; i < infix.length(); i++) {
               char c = infix.charAt(i);
               if (Character.isLetterOrDigit(c) || c == ' ') {
                    outputQueue += c;
               } 
               else if (c == '(') {
                    operator.push(c);
               } 
               else if (c == ')') {
                    while (!operator.isEmpty() && operator.peek() != '(') {
                         outputQueue += operator.pop();
                    }
                    operator.pop(); // Pop the '('
               }
               else { // Operator push
                    while (!operator.isEmpty() && checkPrecedence(operator.peek()) >= checkPrecedence(c)) {
                         outputQueue += operator.pop();
                    }
                    operator.push(c);
               }
               
     }

     while (!operator.isEmpty()) {
          outputQueue += operator.pop();
     }

     return outputQueue;
     }

     public String getProcessedRegex() {
          if(isValid()){
               return infixToPostfix(addConcatenationOperator());
          }
          else{
               return "Invalid regex";
          }
     }
}