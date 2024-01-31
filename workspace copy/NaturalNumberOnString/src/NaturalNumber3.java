import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumberSecondary;

/**
 * {@code NaturalNumber} represented as a {@code String} with implementations of
 * primary methods.
 *
 * @convention <pre>
 * [all characters of $this.rep are '0' through '9']  and
 * [$this.rep does not start with '0']
 * </pre>
 * @correspondence <pre>
 * this = [if $this.rep = "" then 0
 *         else the decimal number whose ordinary depiction is $this.rep]
 * </pre>
 *
 * @author Akshay Anand and Alexander Nistor
 *
 */
public class NaturalNumber3 extends NaturalNumberSecondary {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Representation of {@code this}.
     */
    private String rep;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {

        //Creates "zero" string
        this.rep = "";

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public NaturalNumber3() {

        //Creates zero string
        this.createNewRep();

    }

    /**
     * Constructor from {@code int}.
     *
     * @param i
     *            {@code int} to initialize from
     */
    public NaturalNumber3(int i) {
        assert i >= 0 : "Violation of: i >= 0";

        //Checks if inputed integer is 0 to create integer string or empty string
        if (i != 0) {
            this.rep = String.valueOf(i);
        } else {
            this.createNewRep();
        }

    }

    /**
     * Constructor from {@code String}.
     *
     * @param s
     *            {@code String} to initialize from
     */
    public NaturalNumber3(String s) {
        assert s != null : "Violation of: s is not null";
        assert s.matches("0|[1-9]\\d*") : ""
                + "Violation of: there exists n: NATURAL (s = TO_STRING(n))";

        //If-statement checks if string is 0 to make empty string or store
        //inputed string into this.rep
        if (!s.equals("0")) {
            this.rep = s;
        } else {
            this.createNewRep();
        }

    }

    /**
     * Constructor from {@code NaturalNumber}.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     */
    public NaturalNumber3(NaturalNumber n) {
        assert n != null : "Violation of: n is not null";

        //Converts nn to string and stores it
        String nn = n.toString();

        //If-statement checks if inputed nn is 0 before converting to empty string
        //or string with nn's value
        if (!nn.equals("0")) {
            this.rep = nn;
        } else {
            this.createNewRep();
        }

    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @Override
    public final NaturalNumber newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(NaturalNumber source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof NaturalNumber3 : ""
                + "Violation of: source is of dynamic type NaturalNumberExample";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case.
         */
        NaturalNumber3 localSource = (NaturalNumber3) source;
        this.rep = localSource.rep;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void multiplyBy10(int k) {
        assert 0 <= k : "Violation of: 0 <= k";
        assert k < RADIX : "Violation of: k < 10";

        //If-statement checks if current string's lenght is 0 before adding onto it
        //or assigning it an empty string value or integer string value
        if (this.rep.length() != 0) {
            this.rep += "" + k;
        } else {
            if (k == 0) {
                this.createNewRep();
            } else {
                this.rep = Integer.toString(k);
            }
        }

    }

    @Override
    public final int divideBy10() {

        //Initializes variable to store remainder value
        int num = 0;

        //If-statement checks length of string to see whether to shorten it or not
        if (this.rep.length() > 0) {
            //Stores last character (digit) in variable to be returned
            num = Integer.parseInt(this.rep.substring(this.rep.length() - 1));

            //Sets string to be its same value, with the last digit cut off
            this.rep = this.rep.substring(0, this.rep.length() - 1);
        }

        return num;
    }

    @Override
    public final boolean isZero() {

        //Returns true or false if length of string is 0 indicating NN is 0
        return this.rep.length() == 0;
    }

}
