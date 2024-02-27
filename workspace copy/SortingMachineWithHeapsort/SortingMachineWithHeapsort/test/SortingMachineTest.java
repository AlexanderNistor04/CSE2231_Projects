import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Alex Nistor and Akshay Anand
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    /**
     * constructor test.
     */
    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    /**
     * empty add test.
     */
    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    // TODO - add test cases for add, changeToExtractionMode, removeFirst,
    // isInInsertionMode, order, and size

    // --------------- add() tests ---------------

    /**
     * test with m = (ORDER, true, <"red", "blue">) and add("green").
     */
    @Test
    public final void testAdd1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "red",
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "red", "green", "blue");
        m.add("blue");
        assertEquals(mExpected, m);
    }

    /**
     * test with m = (ORDER, true, <"red", "orange", "yellow", "green", "blue">)
     * and add("purple").
     */
    @Test
    public final void testAdd2() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "red",
                "orange", "yellow", "green", "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "red", "orange", "yellow", "green", "blue", "purple");
        m.add("purple");
        assertEquals(mExpected, m);
    }

// --------------- changeToExtractionMode() tests ---------------

    /**
     * test with m = (ORDER, true, <>) and changeToExtractionMode().
     */
    @Test
    public final void testChangeToExtractionMode1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    /**
     * test with m = (ORDER, true, <"green">) and changeToExtractionMode().
     */
    @Test
    public final void testChangeToExtractionMode2() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green");
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    /**
     * test with m = (ORDER, true, <"red", "orange", "yellow", "green", "blue",
     * "purple">) and changeToExtractionMode().
     */
    @Test
    public final void testChangeToExtractionMode3() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "red",
                "orange", "yellow", "green", "blue", "purple");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "red", "orange", "yellow", "green", "blue", "purple");
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

// --------------- removeFirst() tests ---------------

    /**
     * test with m = (ORDER, false, <"green">) and removeFirst().
     */
    @Test
    public final void testRemoveFirst1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        String s = m.removeFirst();
        String sExpected = "green";
        assertEquals(mExpected, m);
        assertEquals(sExpected, s);
    }

    /**
     * test with m = (ORDER, false, <"red", "orange", "yellow", "green", "blue",
     * "purple">) and removeFirst().
     */
    @Test
    public final void testRemoveFirst2() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "red",
                "orange", "yellow", "green", "blue", "purple");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "red", "orange", "yellow", "green", "purple");
        String s = m.removeFirst();
        String sExpected = "blue";
        assertEquals(mExpected, m);
        assertEquals(sExpected, s);
    }

// --------------- isInInsertionMode() tests ---------------

    /**
     * test with m = (ORDER, true, <>) and isInInsertionMode().
     */
    @Test
    public final void testIsInInsertionMode1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        boolean b = m.isInInsertionMode();
        boolean bExpected = true;
        assertEquals(mExpected, m);
        assertEquals(bExpected, b);
    }

    /**
     * test with m = (ORDER, false, <>) and isInInsertionMode().
     */
    @Test
    public final void testIsInInsertionMode2() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        boolean b = m.isInInsertionMode();
        boolean bExpected = false;
        assertEquals(mExpected, m);
        assertEquals(bExpected, b);
    }

    /**
     * test with m = (ORDER, true, <"green">) and isInInsertionMode().
     */
    @Test
    public final void testIsInInsertionMode3() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        boolean b = m.isInInsertionMode();
        boolean bExpected = true;
        assertEquals(mExpected, m);
        assertEquals(bExpected, b);
    }

    /**
     * test with m = (ORDER, false, <"green">) and isInInsertionMode().
     */
    @Test
    public final void testIsInInsertionMode4() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green");
        boolean b = m.isInInsertionMode();
        boolean bExpected = false;
        assertEquals(mExpected, m);
        assertEquals(bExpected, b);
    }

// --------------- order() tests ---------------

    /**
     * test with m = (ORDER, true, <>) and order().
     */
    @Test
    public final void testOrder1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        Comparator<String> c = m.order();
        Comparator<String> cExpected = ORDER;
        assertEquals(mExpected, m);
        assertEquals(cExpected, c);
    }

    /**
     * test with m = (ORDER, true, <"red", "orange", "yellow", "green", "blue",
     * "purple">) and order().
     */
    @Test
    public final void testOrder2() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "red",
                "orange", "yellow", "green", "blue", "purple");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "red", "orange", "yellow", "green", "blue", "purple");
        Comparator<String> c = m.order();
        Comparator<String> cExpected = ORDER;
        assertEquals(mExpected, m);
        assertEquals(cExpected, c);
    }

    // --------------- size() tests ---------------

    /**
     * test with m = (ORDER, true, <>) and size().
     */
    @Test
    public final void testSize1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        int size = m.size();
        int sizeExpected = 0;
        assertEquals(mExpected, m);
        assertEquals(size, sizeExpected);
    }

    /**
     * test with m = (ORDER, true, <"red", "orange", "yellow", "green", "blue",
     * "purple">) and size().
     */
    @Test
    public final void testSize2() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "red",
                "orange", "yellow", "green", "blue", "purple");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "red", "orange", "yellow", "green", "blue", "purple");
        int size = m.size();
        int sizeExpected = 6;
        assertEquals(mExpected, m);
        assertEquals(size, sizeExpected);
    }

}
