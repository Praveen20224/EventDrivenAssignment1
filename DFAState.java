// A class for DFA state, with parameters id,start,accept, a subset of NFA States and its correspoding list of transitions
import java.util.*;

public class DFAState {

    public String id;
    public boolean start;
    public boolean accept;

    // The NFA states combined to form DFA state
    public ArrayList<NFAState> subset;

    public ArrayList<DFATransition> transitions;

    public DFAState(String id,
                    ArrayList<NFAState> subset,
                    boolean start,
                    boolean accept) {

        this.id = id;
        this.subset = new ArrayList<>(subset);;
        this.start = start;
        this.accept = accept;
        this.transitions = new ArrayList<>();
    }

    public void addTransition(char symbol, DFAState to) {
        transitions.add(new DFATransition(symbol, to));
    }
}