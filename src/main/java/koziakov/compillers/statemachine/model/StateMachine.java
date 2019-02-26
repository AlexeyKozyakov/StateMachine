package koziakov.compillers.statemachine.model;

import koziakov.compillers.statemachine.exceptions.FileParseException;
import koziakov.compillers.statemachine.exceptions.UnknownSymbolException;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StateMachine {

    private State initialState;
    private State currentState;

    private Map<Integer, State> states = new HashMap<>();

    private int lineNum;

    public void load(Reader reader) throws FileParseException {
        states.clear();
        lineNum = 0;
        try(Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                loadLine(line);
            }
        }
    }

    public void parse(Reader reader) throws UnknownSymbolException {
        currentState = initialState;
        try(Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                parseLine(line);
            }
        }
        if (currentState.isTerminal()) {
            System.out.println("Valid text");
        } else {
            System.out.println("Invalid text");
        }
    }

    private void parseLine(String line) throws UnknownSymbolException {
        for (int i = 0; i < line.length(); i++) {
            currentState = currentState.next(line.charAt(i));
        }
    }

    private void loadLine(String line) throws FileParseException {
        String [] words = line.split(" ");
        if (lineNum == 0) {
            int number = parseInt(words[0], 0);
            initialState = new State(number, false);
            states.put(number, initialState);
        } else if (lineNum == 1) {
            for (int wordNum = 0; wordNum < words.length; wordNum++) {
                int number = parseInt(words[wordNum], wordNum);
                if (states.containsKey(number)) {
                    throw new FileParseException(lineNum, wordNum);
                }
                states.put(number, new State(number, true));
            }
        } else {
            if (words.length < 3) {
                throw new FileParseException(lineNum, words.length - 1);
            }
            int stateNum = parseInt(words[0], 0);
            int nextNum = parseInt(words[2], 2);
            if (!states.containsKey(stateNum)) {
                states.put(stateNum, new State(stateNum, false));
            }
            if (!states.containsKey(nextNum)) {
                states.put(nextNum, new State(nextNum, false));
            }
            if (words[1].isEmpty()) {
                throw new FileParseException(lineNum, 1);
            }
            states.get(stateNum).setTransition(words[1].charAt(0),
                    states.get(nextNum));
        }
        ++lineNum;
    }

    private int parseInt(String str, int wordNum) throws FileParseException {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new FileParseException(lineNum, wordNum);
        }
    }

    public State getCurrentState() {
        return currentState;
    }

    public State getInitialState() {
        return initialState;
    }

    public Map<Integer, State> getStates() {
        return states;
    }
}
