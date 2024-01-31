import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Alexander Nistor & Akshay Anand
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    // TODO - add test cases for four constructors, multiplyBy10, divideBy10, isZero

    //---------- constructor tests ----------
    /**
     * test of empty constructor.
     */
    @Test
    public final void testConstructorEmpty() {
        NaturalNumber n1 = this.constructorRef();
        NaturalNumber n2 = this.constructorTest();
        assertEquals(n1, n2);
    }

    /**
     * test of int constructor.
     */
    @Test
    public final void testConstructorInt() {
        NaturalNumber n1 = this.constructorRef(1);
        NaturalNumber n2 = this.constructorTest(1);
        assertEquals(n1, n2);
    }

    /**
     * test of String constructor.
     */
    @Test
    public final void testConstructorString() {
        NaturalNumber n1 = this.constructorRef("1");
        NaturalNumber n2 = this.constructorTest("1");
        assertEquals(n1, n2);
    }

    /**
     * test of NaturalNumber constructor.
     */
    @Test
    public final void testConstructorNN() {
        NaturalNumber n1 = this.constructorRef();
        NaturalNumber n2 = this.constructorTest();
        NaturalNumber n3 = this.constructorRef(n1);
        NaturalNumber n4 = this.constructorTest(n2);
        assertEquals(n3, n4);
    }

    //---------- multiplyBy10 tests ----------
    /**
     * test with this.entries = <> and multiplyBy10(0).
     */
    @Test
    public final void test1MultiplyBy10() {
        NaturalNumber n1 = this.constructorRef();
        n1.multiplyBy10(0);
        NaturalNumber n2 = this.constructorRef(0);
        assertEquals(n1, n2);
    }

    /**
     * test with this.entries = <> and multiplyBy10(1).
     */
    @Test
    public final void test2MultiplyBy10() {
        NaturalNumber n1 = this.constructorRef();
        n1.multiplyBy10(1);
        NaturalNumber n2 = this.constructorRef(1);
        assertEquals(n1, n2);
    }

    /**
     * test with this.entries = <1> and multiplyBy10(0).
     */
    @Test
    public final void test3MultiplyBy10() {
        NaturalNumber n1 = this.constructorRef(1);
        n1.multiplyBy10(0);
        NaturalNumber n2 = this.constructorRef(10);
        assertEquals(n1, n2);
    }

    /**
     * test with this.entries = <1> and multiplyBy10(1).
     */
    @Test
    public final void test4MultiplyBy10() {
        NaturalNumber n1 = this.constructorRef(1);
        n1.multiplyBy10(1);
        NaturalNumber n2 = this.constructorRef(11);
        assertEquals(n1, n2);
    }

    /**
     * test with this.entries = <1,2,3,4,5> and multiplyBy10(6).
     */
    @Test
    public final void test5MultiplyBy10() {
        NaturalNumber n1 = this.constructorRef(12345);
        n1.multiplyBy10(6);
        NaturalNumber n2 = this.constructorRef(123456);
        assertEquals(n1, n2);
    }

    //---------- divideBy10 tests ----------
    /**
     * test with this.entries = <1>.
     */
    @Test
    public final void test1divideBy10() {
        NaturalNumber n1 = this.constructorRef(1);
        NaturalNumber n2 = this.constructorRef(0);
        int i = n1.divideBy10();
        int j = 1;
        assertEquals(n1, n2);
        assertEquals(i, j);
    }

    /**
     * test with this.entries = <1,0>.
     */
    @Test
    public final void test2divideBy10() {
        NaturalNumber n1 = this.constructorRef(10);
        NaturalNumber n2 = this.constructorRef(1);
        int i = n1.divideBy10();
        int j = 0;
        assertEquals(n1, n2);
        assertEquals(i, j);
    }

    /**
     * test with this.entries = <1,2,3,4,5,6>.
     */
    @Test
    public final void test3divideBy10() {
        NaturalNumber n1 = this.constructorRef(123456);
        NaturalNumber n2 = this.constructorRef(12345);
        int i = n1.divideBy10();
        int j = 6;
        assertEquals(n1, n2);
        assertEquals(i, j);
    }

    //---------- isZero tests ----------
    /**
     * test with this.entries = <1>.
     */
    @Test
    public final void test1isZero() {
        NaturalNumber n1 = this.constructorRef(1);
        boolean a = n1.isZero();
        boolean b = false;
        assertEquals(a, b);
    }

    /**
     * test with this.entries = <0>.
     */
    @Test
    public final void test2isZero() {
        NaturalNumber n1 = this.constructorRef(0);
        boolean a = n1.isZero();
        boolean b = true;
        ;
        assertEquals(a, b);
    }

    /**
     * test with this.entries = <0>.
     */
    @Test
    public final void test3isZero() {
        NaturalNumber n1 = this.constructorRef(12345);
        boolean a = n1.isZero();
        boolean b = false;
        ;
        assertEquals(a, b);
    }

}
