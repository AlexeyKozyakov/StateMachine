package koziakov.compillers.statemachine.model;

import koziakov.compillers.statemachine.exceptions.UnknownSymbolException;

import java.util.HashMap;
import java.util.Map;

public class State {

    private int number;

    private boolean terminal;
    private final Map<Character, State> transitions = new HashMap<>();
    public State(int number, boolean terminal) {
        this.number = number;
        this.terminal = terminal;
    }

    public void setTransition(Character symbol, State state) {
        transitions.put(symbol, state);
    }

    public State next(char symbol) throws UnknownSymbolException {
        State nextState = transitions.get(symbol);
        if (nextState == null) {
            throw new UnknownSymbolException(number);
        }
        return nextState;
    }

    public boolean isTerminal() {
        return terminal;
    }

    public int getNumber() {
        return number;
    }
}
