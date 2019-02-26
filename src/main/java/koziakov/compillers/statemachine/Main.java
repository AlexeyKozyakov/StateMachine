package koziakov.compillers.statemachine;

import koziakov.compillers.statemachine.exceptions.FileParseException;
import koziakov.compillers.statemachine.exceptions.UnknownSymbolException;
import koziakov.compillers.statemachine.model.StateMachine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class Main {

    private static void printUsage() {
        System.out.println("Usage: java Main <state_machine_desc_file> <text_file>");
    }

    public static void main(String[] args) {
        StateMachine machine = new StateMachine();
        if (args.length < 2) {
            printUsage();
            return;
        }
        try {
            machine.load(new InputStreamReader(new FileInputStream(args[0])));
            machine.parse(new InputStreamReader(new FileInputStream(args[1])));
        } catch (FileParseException | UnknownSymbolException | FileNotFoundException e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

}
