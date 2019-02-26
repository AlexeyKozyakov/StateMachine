import koziakov.compillers.statemachine.exceptions.FileParseException;
import koziakov.compillers.statemachine.exceptions.UnknownSymbolException;
import koziakov.compillers.statemachine.model.State;
import koziakov.compillers.statemachine.model.StateMachine;
import org.junit.Assert;
import org.junit.Test;

import java.io.StringReader;
import java.util.Map;

public class Tests {

    @Test
    public void testStateMain() {
        State state = new State(0, false);
        Assert.assertEquals(0, state.getNumber());
        Assert.assertFalse(state.isTerminal());
    }

    @Test
    public void testStateTransition() throws UnknownSymbolException {
        State state1 = new State(0, false);
        State state2 = new State(1, true);
        state1.setTransition('a', state2);
        State nextState = state1.next('a');
        Assert.assertEquals(state2, nextState);

    }

    @Test
    public void testLoad() throws FileParseException, UnknownSymbolException {
        String description = "1\n3\n1 a 2\n1 b 1\n2 a 3\n2 b 1\n3 b 1\n3 a 3";
        StateMachine stateMachine = new StateMachine();
        stateMachine.load(new StringReader(description));
        Map<Integer, State> states = stateMachine.getStates();
        Assert.assertFalse(states.get(1).isTerminal());
        Assert.assertFalse(states.get(2).isTerminal());
        Assert.assertTrue(states.get(3).isTerminal());
        Assert.assertEquals(2, states.get(1).next('a').getNumber());
        Assert.assertEquals(1, states.get(1).next('b').getNumber());
        Assert.assertEquals(3, states.get(2).next('a').getNumber());
        Assert.assertEquals(1, states.get(2).next('b').getNumber());
        Assert.assertEquals(3, states.get(3).next('a').getNumber());
        Assert.assertEquals(1, states.get(3).next('b').getNumber());
    }

    @Test
    public void testParse() throws FileParseException, UnknownSymbolException {
        String description = "1\n3\n1 a 2\n1 b 1\n2 a 3\n2 b 1\n3 b 1\n3 a 3";
        String text = "aaababaabaaaaaa";
        StateMachine stateMachine = new StateMachine();
        stateMachine.load(new StringReader(description));
        stateMachine.parse(new StringReader(text));
        Assert.assertTrue(stateMachine.getCurrentState().isTerminal());
    }
}
