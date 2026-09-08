// A simple State with basic necessary parameters used for representing E-NFA states.

import java.util.ArrayList;

public class State {
     public String id;
     public boolean accept;
     public ArrayList<Transition> transitions;

     public State(String id, boolean accept){
          this.id = id;
          this.accept = accept;
          this.transitions = new ArrayList<Transition>();
     }

     public void addTransition(State to, char symbol){
          Transition transition = new Transition(this, to, symbol);
          this.transitions.add(transition);
     }
}

