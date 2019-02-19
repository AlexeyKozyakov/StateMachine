package koziakov.compillers.statemachine.exceptions;

public class UnknownSymbolException extends Exception {

    public UnknownSymbolException(int state) {
        super("Failed in state " + state +", unknown symbol");
    }

}
