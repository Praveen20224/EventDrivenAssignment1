// To represent an NFA fragment, recommended by Thompsons Visualization.

public class Fragment {

    public final State start;
    public final State end;

    public Fragment(State start, State end) {
        this.start = start;
        this.end = end;
    }
}