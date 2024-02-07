import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Alexander Nistor & Akshay Anand
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, value,
    // hasKey, and size

    /*
     * --------------- tests for constructors ---------------
     */

    /**
     * test for the default no-argument constructor.
     */
    @Test
    public final void testConstructorEmpty() {
        Map<String, String> m1 = this.createFromArgsRef();
        Map<String, String> m2 = this.createFromArgsTest();
        assertEquals(m1, m2);
    }

    /*
     * --------------- tests for add() ---------------
     */

    /**
     * test for add with m1 = <()> and add("1", "one").
     */
    @Test
    public final void testAdd1() {
        Map<String, String> m1 = this.createFromArgsRef("1", "one");
        Map<String, String> m2 = this.createFromArgsTest();
        m2.add("1", "one");
        assertEquals(m1, m2);
    }

    /**
     * test for add with m1 = <("1", "one")> and add("2", "two").
     */
    @Test
    public final void testAdd2() {
        Map<String, String> m1 = this.createFromArgsRef("1", "one", "2", "two");
        Map<String, String> m2 = this.createFromArgsTest("1", "one");
        m2.add("2", "two");
        assertEquals(m1, m2);
    }

    /**
     * test for add with m1 = <("a", "A"), ("b", "B"), ("c", "C"), ("d", "D"),
     * ("e", "E"), ("f", "F"), ("g", "G"), ("h", "H"), ("i", "I")> and add("j",
     * "J").
     */
    @Test
    public final void testAdd3() {
        Map<String, String> m1 = this.createFromArgsRef("a", "A", "b", "B", "c",
                "C", "d", "D", "e", "E", "f", "F", "g", "G", "h", "H", "i", "I",
                "j", "J");
        Map<String, String> m2 = this.createFromArgsTest("a", "A", "b", "B",
                "c", "C", "d", "D", "e", "E", "f", "F", "g", "G", "h", "H", "i",
                "I");
        m2.add("j", "J");
        assertEquals(m1, m2);
    }

    /*
     * --------------- tests for remove() ---------------
     */

    /**
     * test for remove with m1 = <()> and remove("1", "one").
     */
    @Test
    public final void testRemove1() {
        Map<String, String> m1 = this.createFromArgsRef();
        Map<String, String> m2 = this.createFromArgsTest("1", "one");
        String val1 = "one";
        String val2 = m2.remove("1").value();
        assertEquals(m1, m2);
        assertEquals(val1, val2);
    }

    /**
     * test for remove with m1 = <("a", "A"), ("b", "B"), ("c", "C"), ("d",
     * "D"), ("e", "E"), ("f", "F"), ("g", "G"), ("h", "H"), ("i", "I"), ("j",
     * "J")> and remove("j", "J").
     */
    @Test
    public final void testRemove2() {
        Map<String, String> m1 = this.createFromArgsRef("a", "A", "b", "B", "c",
                "C", "d", "D", "e", "E", "f", "F", "g", "G", "h", "H", "i",
                "I");
        Map<String, String> m2 = this.createFromArgsTest("a", "A", "b", "B",
                "c", "C", "d", "D", "e", "E", "f", "F", "g", "G", "h", "H", "i",
                "I", "j", "J");
        String val1 = "J";
        String val2 = m2.remove("j").value();
        assertEquals(m1, m2);
        assertEquals(val1, val2);
    }

    /*
     * --------------- tests for removeAny() ---------------
     */

    /**
     * test for removeAny with m1 = <("1", "one")>.
     */
    @Test
    public final void testRemoveAny1() {
        Map<String, String> m1 = this.createFromArgsRef();
        Map<String, String> m2 = this.createFromArgsTest("1", "one");
        String val1 = "one";
        String val2 = m2.removeAny().value();
        assertEquals(m1, m2);
        assertEquals(val1, val2);
    }

    /*
     * --------------- tests for value() ---------------
     */

    /**
     * test for value with m1 = <("1", "one")> and value("1").
     */
    @Test
    public final void testValue1() {
        Map<String, String> m1 = this.createFromArgsRef("1", "one");
        Map<String, String> m2 = this.createFromArgsTest("1", "one");
        String val1 = "one";
        String val2 = m2.value("1");
        assertEquals(m1, m2);
        assertEquals(val1, val2);
    }

    /**
     * test for value with m1 = <("a", "A"), ("b", "B"), ("c", "C"), ("d", "D"),
     * ("e", "E"), ("f", "F"), ("g", "G"), ("h", "H"), ("i", "I"), ("j", "J")>
     * and value("j").
     */
    @Test
    public final void testValue2() {
        Map<String, String> m1 = this.createFromArgsRef("a", "A", "b", "B", "c",
                "C", "d", "D", "e", "E", "f", "F", "g", "G", "h", "H", "i", "I",
                "j", "J");
        Map<String, String> m2 = this.createFromArgsTest("a", "A", "b", "B",
                "c", "C", "d", "D", "e", "E", "f", "F", "g", "G", "h", "H", "i",
                "I", "j", "J");
        String val1 = "J";
        String val2 = m2.value("j");
        assertEquals(m1, m2);
        assertEquals(val1, val2);
    }

    /*
     * --------------- tests for hasKey() ---------------
     */

    /**
     * test for value with m1 = <("1", "one")> and hasKey("1").
     */
    @Test
    public final void testhasKey1() {
        Map<String, String> m = this.createFromArgsRef("1", "one");
        boolean val1 = true;
        boolean val2 = m.hasKey("1");
        assertEquals(val1, val2);
    }

    /**
     * test for value with m1 = <("1", "one")> and hasKey("one").
     */
    @Test
    public final void testhasKey2() {
        Map<String, String> m = this.createFromArgsRef("1", "one");
        boolean val1 = false;
        boolean val2 = m.hasKey("one");
        assertEquals(val1, val2);
    }

    /**
     * test for value with m1 = <()> and hasKey("1").
     */
    @Test
    public final void testhasKey3() {
        Map<String, String> m = this.createFromArgsRef();
        boolean val1 = false;
        boolean val2 = m.hasKey("1");
        assertEquals(val1, val2);
    }

    /**
     * test for value with m1 = <("a", "A"), ("b", "B"), ("c", "C"), ("d", "D"),
     * ("e", "E"), ("f", "F"), ("g", "G"), ("h", "H"), ("i", "I"), ("j", "J")>
     * and hasKey("j").
     */
    @Test
    public final void testhasKey4() {
        Map<String, String> m = this.createFromArgsRef("a", "A", "b", "B", "c",
                "C", "d", "D", "e", "E", "f", "F", "g", "G", "h", "H", "i", "I",
                "j", "J");
        boolean val1 = true;
        boolean val2 = m.hasKey("j");
        assertEquals(val1, val2);
    }

    /**
     * test for value with m1 = <("a", "A"), ("b", "B"), ("c", "C"), ("d", "D"),
     * ("e", "E"), ("f", "F"), ("g", "G"), ("h", "H"), ("i", "I"), ("j", "J")>
     * and hasKey("J").
     */
    @Test
    public final void testhasKey5() {
        Map<String, String> m = this.createFromArgsRef("a", "A", "b", "B", "c",
                "C", "d", "D", "e", "E", "f", "F", "g", "G", "h", "H", "i", "I",
                "j", "J");
        boolean val1 = true;
        boolean val2 = m.hasKey("J");
        assertEquals(val1, val2);
    }

    /**
     * test for value with m1 = <("a", "A"), ("b", "B"), ("c", "C"), ("d", "D"),
     * ("e", "E"), ("f", "F"), ("g", "G"), ("h", "H"), ("i", "I"), ("j", "J")>
     * and hasKey("k").
     */
    @Test
    public final void testhasKey6() {
        Map<String, String> m = this.createFromArgsRef("a", "A", "b", "B", "c",
                "C", "d", "D", "e", "E", "f", "F", "g", "G", "h", "H", "i", "I",
                "j", "J");
        boolean val1 = false;
        boolean val2 = m.hasKey("k");
        assertEquals(val1, val2);
    }

    /*
     * --------------- tests for size() ---------------
     */

    /**
     * test for size with m1 = <()>.
     */
    @Test
    public final void testSize1() {
        Map<String, String> m = this.createFromArgsRef();
        int val1 = 0;
        int val2 = m.size();
        assertEquals(val1, val2);
    }

    /**
     * test for size with m1 = <("1", "one")>.
     */
    @Test
    public final void testSize2() {
        Map<String, String> m = this.createFromArgsRef("1", "one");
        int val1 = 1;
        int val2 = m.size();
        assertEquals(val1, val2);
    }

    /**
     * test for size with m1 = <("a", "A"), ("b", "B"), ("c", "C"), ("d", "D"),
     * ("e", "E"), ("f", "F"), ("g", "G"), ("h", "H"), ("i", "I"), ("j", "J")>.
     */
    @Test
    public final void testSize3() {
        Map<String, String> m = this.createFromArgsRef("a", "A", "b", "B", "c",
                "C", "d", "D", "e", "E", "f", "F", "g", "G", "h", "H", "i", "I",
                "j", "J");
        int val1 = 10;
        int val2 = m.size();
        assertEquals(val1, val2);
    }

}
