import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;

/**
 * Customized JUnit test fixture for {@code NaturalNumber3}.
 */
public class NaturalNumber3Test extends NaturalNumberTest {

    @Override
    protected final NaturalNumber constructorTest() {

        // TODO - fill in body
        NaturalNumber n = new NaturalNumber3();

        // This line added just to make the component compilable.
        return n;
    }

    @Override
    protected final NaturalNumber constructorTest(int i) {

        // TODO - fill in body
        NaturalNumber n = new NaturalNumber3(i);

        // This line added just to make the component compilable.
        return n;
    }

    @Override
    protected final NaturalNumber constructorTest(String s) {

        // TODO - fill in body
        NaturalNumber n = new NaturalNumber3(s);

        // This line added just to make the component compilable.
        return n;
    }

    @Override
    protected final NaturalNumber constructorTest(NaturalNumber n) {

        // TODO - fill in body
        NaturalNumber n2 = new NaturalNumber3(n);

        // This line added just to make the component compilable.
        return n2;
    }

    @Override
    protected final NaturalNumber constructorRef() {

        // TODO - fill in body
        NaturalNumber n = new NaturalNumber1L();

        // This line added just to make the component compilable.
        return n;
    }

    @Override
    protected final NaturalNumber constructorRef(int i) {

        // TODO - fill in body
        NaturalNumber n = new NaturalNumber1L(i);

        // This line added just to make the component compilable.
        return n;
    }

    @Override
    protected final NaturalNumber constructorRef(String s) {

        // TODO - fill in body
        NaturalNumber n = new NaturalNumber1L(s);

        // This line added just to make the component compilable.
        return n;
    }

    @Override
    protected final NaturalNumber constructorRef(NaturalNumber n) {

        // TODO - fill in body
        NaturalNumber n2 = new NaturalNumber1L(n);

        // This line added just to make the component compilable.
        return n2;
    }

}
