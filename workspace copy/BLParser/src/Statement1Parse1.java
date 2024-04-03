import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.statement.Statement1;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary methods {@code parse} and
 * {@code parseBlock} for {@code Statement}.
 *
 * @author Akshay Anand and Alexander Nistor
 *
 */
public final class Statement1Parse1 extends Statement1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Converts {@code c} into the corresponding {@code Condition}.
     *
     * @param c
     *            the condition to convert
     * @return the {@code Condition} corresponding to {@code c}
     * @requires [c is a condition string]
     * @ensures parseCondition = [Condition corresponding to c]
     */
    private static Condition parseCondition(String c) {
        assert c != null : "Violation of: c is not null";
        assert Tokenizer
                .isCondition(c) : "Violation of: c is a condition string";
        return Condition.valueOf(c.replace('-', '_').toUpperCase());
    }

    /**
     * Parses an IF or IF_ELSE statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires <pre>
     * [<"IF"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an if string is a proper prefix of #tokens] then
     *  s = [IF or IF_ELSE Statement corresponding to if string at start of #tokens]  and
     *  #tokens = [if string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    private static void parseIf(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("IF") : ""
                + "Violation of: <\"IF\"> is proper prefix of tokens";

        //Checks properties of beginning kind and condition
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(tokens.dequeue()),
                "Invalid Token");

        String conditionTemp = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(conditionTemp),
                "Invalid Condition");

        //Stores parsed condition for later
        Condition c = parseCondition(conditionTemp);

        Reporter.assertElseFatalError(tokens.dequeue().equals("THEN"),
                "Invalid Token");

        //Parse if-statement and possibly else-statement as well
        Statement temp1 = s.newInstance();
        temp1.parseBlock(tokens);

        if (tokens.front().equals("ELSE")) {

            Reporter.assertElseFatalError(tokens.dequeue().equals("ELSE"),
                    "Invalid Token");

            //Parse blocks
            Statement temp2 = s.newInstance();
            temp2.parseBlock(tokens);

            //Assemble statement
            s.assembleIfElse(c, temp1, temp2);
        } else {
            s.assembleIf(c, temp1);
        }

        //Checks ending conditions for queue tokens
        Reporter.assertElseFatalError(tokens.dequeue().equals("END"),
                "Invalid Token");
        Reporter.assertElseFatalError(Tokenizer.isKeyword(tokens.dequeue()),
                "Invalid Token");

    }

    /**
     * Parses a WHILE statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires <pre>
     * [<"WHILE"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [a while string is a proper prefix of #tokens] then
     *  s = [WHILE Statement corresponding to while string at start of #tokens]  and
     *  #tokens = [while string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    private static void parseWhile(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("WHILE") : ""
                + "Violation of: <\"WHILE\"> is proper prefix of tokens";

        //Checks properties of beginning kind and condition
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(tokens.dequeue()),
                "Invalid Token");

        String conditionTemp = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(conditionTemp),
                "Invalid Condition");

        //Stores parsed condition for later
        Condition c = parseCondition(conditionTemp);

        Reporter.assertElseFatalError(tokens.dequeue().equals("THEN"),
                "Invalid Token");

        //Parse while statement and after assemble it
        Statement temp1 = s.newInstance();
        temp1.parseBlock(tokens);
        s.assembleWhile(c, temp1);

        //Checks ending conditions for queue tokens
        Reporter.assertElseFatalError(tokens.dequeue().equals("END"),
                "Invalid Token");
        Reporter.assertElseFatalError(Tokenizer.isKeyword(tokens.dequeue()),
                "Invalid Token");

    }

    /**
     * Parses a CALL statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires [identifier string is a proper prefix of tokens]
     * @ensures <pre>
     * s =
     *   [CALL Statement corresponding to identifier string at start of #tokens]  and
     *  #tokens = [identifier string at start of #tokens] * tokens
     * </pre>
     */
    private static void parseCall(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0
                && Tokenizer.isIdentifier(tokens.front()) : ""
                        + "Violation of: identifier string is proper prefix of tokens";

        //Stores name of call to be checked and used later
        String nameOfCall = tokens.dequeue();

        //Check if name is invalid
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(nameOfCall),
                "Invalid Identifier");

        //Assembles call
        s.assembleCall(nameOfCall);

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Statement1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        //Store name to check if it is a keyword or identifier and later use it
        //to parse
        String name = tokens.dequeue();

        //Checks if name is a keyword or identifier else reports an error
        Reporter.assertElseFatalError(
                Tokenizer.isKeyword(name) || Tokenizer.isIdentifier(name),
                "Invalid Token");

        //If-else if-else statement parses based on identifier/keyword
        if (name.equals("IF")) {
            parseIf(tokens, this);
        } else if (name.equals("WHILE")) {
            parseWhile(tokens, this);
        } else {
            parseCall(tokens, this);
        }

    }

    @Override
    public void parseBlock(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        //while loop parses until some form of an ending is reached
        Statement temp = this.newInstance();
        while (!(tokens.front().equals("ELSE") || tokens.front().equals("END")
                || tokens.front().equals(Tokenizer.END_OF_INPUT))) {

            //Parse through tokens queue
            this.parse(tokens);

            //Add parsed part to AST
            temp.addToBlock(temp.lengthOfBlock(), this);
        }

        //Transfer AST to this
        this.transferFrom(temp);

    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("Enter valid BL statement(s) file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Statement s = new Statement1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        s.parse(tokens); // replace with parseBlock to test other method
        /*
         * Pretty print the statement(s)
         */
        out.println("*** Pretty print of parsed statement(s) ***");
        s.prettyPrint(out, 0);

        in.close();
        out.close();
    }

}
