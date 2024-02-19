import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Alexander Nistor & Akshay Anand
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, contains, and size

    //--------------- constructor tests ---------------
    /**
     * test of empty constructor.
     */
    @Test
    public final void testConstructorEmpty() {
        Set<String> s1 = this.constructorRef();
        Set<String> s2 = this.constructorTest();
        assertEquals(s1, s2);
    }

    //--------------- add tests ---------------
    /**
     * test of add("a") with this = <>.
     */
    @Test
    public final void testAdd1() {
        Set<String> s1 = this.createFromArgsRef("a");
        Set<String> s2 = this.createFromArgsTest();
        s2.add("a");
        assertEquals(s1, s2);
    }

    /**
     * test of add("a") with this = <"b">.
     */
    @Test
    public final void testAdd2() {
        Set<String> s1 = this.createFromArgsRef("a", "b");
        Set<String> s2 = this.createFromArgsTest("b");
        s2.add("a");
        assertEquals(s1, s2);
    }

    /**
     * test of add("a") with this = <"b", "c", "d", "e", "f", "g">.
     */
    @Test
    public final void testAdd3() {
        Set<String> s1 = this.createFromArgsRef("a", "b", "c", "d", "e", "f",
                "g");
        Set<String> s2 = this.createFromArgsTest("b", "c", "d", "e", "f", "g");
        s2.add("a");
        assertEquals(s1, s2);
    }

    /**
     * test of add(<"a">) with this = <>.
     */
    @Test
    public final void testAdd4() {
        Set<String> s1 = this.createFromArgsRef("a");
        Set<String> s2 = this.createFromArgsTest();
        Set<String> s3 = this.createFromArgsRef("a");
        s2.add(s3);
        assertEquals(s1, s2);
    }

    /**
     * test of add(<"a">) with this = <"b">.
     */
    @Test
    public final void testAdd5() {
        Set<String> s1 = this.createFromArgsRef("a", "b");
        Set<String> s2 = this.createFromArgsTest("b");
        Set<String> s3 = this.createFromArgsRef("a");
        s2.add(s3);
        assertEquals(s1, s2);
    }

    /**
     * test of add(<"a">) with this = <"b", "c", "d", "e", "f", "g">.
     */
    @Test
    public final void testAdd6() {
        Set<String> s1 = this.createFromArgsRef("a", "b", "c", "d", "e", "f",
                "g");
        Set<String> s2 = this.createFromArgsTest("b", "c", "d", "e", "f", "g");
        Set<String> s3 = this.createFromArgsRef("a");
        s2.add(s3);
        assertEquals(s1, s2);
    }

    /**
     * test of add(<"b", "c", "d", "e", "f", "g">) with this = <"a">.
     */
    @Test
    public final void testAdd7() {
        Set<String> s1 = this.createFromArgsRef("a", "b", "c", "d", "e", "f",
                "g");
        Set<String> s2 = this.createFromArgsTest("a");
        Set<String> s3 = this.createFromArgsRef("b", "c", "d", "e", "f", "g");
        s2.add(s3);
        assertEquals(s1, s2);
    }

    //--------------- remove tests ---------------
    /**
     * test of remove("a") with this = <"a">.
     */
    @Test
    public final void testRemove1() {
        Set<String> s1 = this.createFromArgsRef();
        Set<String> s2 = this.createFromArgsTest("a");
        String str1 = "a";
        String str2 = s2.remove("a");
        assertEquals(s1, s2);
        assertEquals(str1, str2);
    }

    /**
     * test of remove("a") with this = <"a", "b", "c", "d", "e", "f", "g">.
     */
    @Test
    public final void testRemove2() {
        Set<String> s1 = this.createFromArgsRef("b", "c", "d", "e", "f", "g");
        Set<String> s2 = this.createFromArgsTest("a", "b", "c", "d", "e", "f",
                "g");
        String str1 = "a";
        String str2 = s2.remove("a");
        assertEquals(s1, s2);
        assertEquals(str1, str2);
    }

    /**
     * test of remove(<"a">) with this = <"a">.
     */
    @Test
    public final void testRemove3() {
        Set<String> s1 = this.createFromArgsRef();
        Set<String> s2 = this.createFromArgsTest("a");
        Set<String> s3 = this.createFromArgsRef("a");
        Set<String> s4 = s2.remove(s3);
        assertEquals(s1, s2);
        assertEquals(s3, s4);
    }

    /**
     * test of remove(<"b", "c", "d", "e", "f", "g">) with this = <"a", "b",
     * "c", "d", "e", "f", "g">.
     */
    @Test
    public final void testRemove4() {
        Set<String> s1 = this.createFromArgsRef("a");
        Set<String> s2 = this.createFromArgsTest("a", "b", "c", "d", "e", "f",
                "g");
        Set<String> s3 = this.createFromArgsRef("b", "c", "d", "e", "f", "g");
        Set<String> s4 = s2.remove(s3);
        assertEquals(s1, s2);
        assertEquals(s3, s4);
    }

    //--------------- removeAny tests ---------------
    /**
     * test of removeAny() with this = <"a">.
     */
    @Test
    public final void testRemoveAny1() {
        Set<String> s1 = this.createFromArgsRef("a");
        Set<String> s2 = this.createFromArgsTest("a");
        String str2 = s2.removeAny();
        String str1 = s1.remove(str2);
        assertEquals(s1, s2);
        assertEquals(str1, str2);
    }

    /**
     * test of removeAny() with this = <"a", "b", "c", "d", "e", "f", "g">.
     */
    @Test
    public final void testRemoveAny2() {
        Set<String> s1 = this.createFromArgsRef("a", "b", "c", "d", "e", "f",
                "g");
        Set<String> s2 = this.createFromArgsTest("a", "b", "c", "d", "e", "f",
                "g");
        String str2 = s2.removeAny();
        String str1 = s1.remove(str2);
        assertEquals(s1, s2);
        assertEquals(str1, str2);
    }

    //--------------- contains tests ---------------
    /**
     * test of contains("a") with this = <"a">.
     */
    @Test
    public final void testContains1() {
        Set<String> s1 = this.createFromArgsRef("a");
        Set<String> s2 = this.createFromArgsTest("a");
        boolean b = s2.contains("a");
        assertEquals(s1, s2);
        assertTrue(b);
    }

    /**
     * test of contains("a") with this = <>.
     */
    @Test
    public final void testContains2() {
        Set<String> s1 = this.createFromArgsRef();
        Set<String> s2 = this.createFromArgsTest();
        boolean b = s2.contains("a");
        assertEquals(s1, s2);
        assertTrue(!b);
    }

    /**
     * test of contains("a") with this = <"a", "b", "c", "d", "e", "f", "g">.
     */
    @Test
    public final void testContains3() {
        Set<String> s1 = this.createFromArgsRef("a", "b", "c", "d", "e", "f",
                "g");
        Set<String> s2 = this.createFromArgsTest("a", "b", "c", "d", "e", "f",
                "g");
        boolean b = s2.contains("a");
        assertEquals(s1, s2);
        assertTrue(b);
    }

    /**
     * test of contains("a") with this = <"b", "c", "d", "e", "f", "g">.
     */
    @Test
    public final void testContains4() {
        Set<String> s1 = this.createFromArgsRef("b", "c", "d", "e", "f", "g");
        Set<String> s2 = this.createFromArgsTest("b", "c", "d", "e", "f", "g");
        boolean b = s2.contains("a");
        assertEquals(s1, s2);
        assertTrue(!b);
    }

    //--------------- size tests ---------------
    /**
     * test of size() with this = <>.
     */
    @Test
    public final void testSize1() {
        Set<String> s1 = this.createFromArgsRef();
        Set<String> s2 = this.createFromArgsTest();
        int size1 = 0;
        int size2 = s2.size();
        assertEquals(s1, s2);
        assertEquals(size1, size2);
    }

    /**
     * test of size() with this = <"a">.
     */
    @Test
    public final void testSize2() {
        Set<String> s1 = this.createFromArgsRef("a");
        Set<String> s2 = this.createFromArgsTest("a");
        int size1 = 1;
        int size2 = s2.size();
        assertEquals(s1, s2);
        assertEquals(size1, size2);
    }

    /**
     * test of size() with this = <"a", "b", "c", "d", "e", "f", "g">.
     */
    @Test
    public final void testSize3() {
        Set<String> s1 = this.createFromArgsRef("a", "b", "c", "d", "e", "f",
                "g");
        Set<String> s2 = this.createFromArgsTest("a", "b", "c", "d", "e", "f",
                "g");
        int size1 = 7;
        int size2 = s2.size();
        assertEquals(s1, s2);
        assertEquals(size1, size2);
    }

}
