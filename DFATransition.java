public class DFATransition {

    public char symbol;
    public DFAState to;

    public DFATransition(char symbol, DFAState to) {
        this.symbol = symbol;
        this.to = to;
    }
}