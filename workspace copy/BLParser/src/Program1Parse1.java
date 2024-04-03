import components.map.Map;
import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Akshay Anand and Alexander Nistor
 *
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     *
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces body
     * @updates tokens
     * @requires <pre>
     * [<"INSTRUCTION"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an instruction string is a proper prefix of #tokens]  and
     *    [the beginning name of this instruction equals its ending name]  and
     *    [the name of this instruction does not equal the name of a primitive
     *     instruction in the BL language] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to the block string that is the body of
     *          the instruction string at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [report an appropriate error message to the console and terminate client]
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens,
            Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION") : ""
                + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";

        //Checks if identifier and tokens are valid, if not then it sends an error
        String start = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(tokens.dequeue()),
                "Invalid Identifier");
        Reporter.assertElseFatalError(tokens.dequeue().equals("IS"),
                "Invalid Token");

        //Parse block
        body.parseBlock(tokens);

        //Checks if last 2 tokens are the correct token and identifier at the
        //end of the queue
        Reporter.assertElseFatalError(tokens.dequeue().equals("END"),
                "Invalid Token");
        String end = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(end),
                "Invalid Identifier");
        Reporter.assertElseFatalError(start.equals(end),
                "More than one identifier used for instruction name");

        //Returns identifier
        return start;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        //Checks conditions for first 3 values in tokens queue
        Reporter.assertElseFatalError(tokens.dequeue().equals("PROGRAM"),
                "Invalid Token");

        String start = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(start),
                "Invalid Identifier");

        Reporter.assertElseFatalError(tokens.dequeue().equals("IS"),
                "Invalid Token");

        //Initialize map for contexts for use later
        Map<String, Statement> context = this.newContext();

        //While-loop parses all instructions
        while (!tokens.front().equals("BEGIN")) {

            //Initialize temp body variable
            Statement tempBody = this.newBody();

            //Stores instruction received from outside method
            String instruction = parseInstruction(tokens, tempBody);

            //Checks identifier required properties
            Reporter.assertElseFatalError(!Tokenizer.isKeyword(instruction),
                    "Keyword cannot be an identifier");
            Reporter.assertElseFatalError(!context.hasKey(instruction),
                    "Cannot use duplicate identifer as instruction name");

            //Adds instruction name and body to context
            context.add(instruction, tempBody);
        }

        //Checks beginning to be valid
        Reporter.assertElseFatalError(tokens.dequeue().equals("BEGIN"),
                "Invalid Token");

        //Parse tokens and body
        Statement blockMain = this.newBody();
        blockMain.parseBlock(tokens);

        //Checks properties of last 3 values in tokens queue
        Reporter.assertElseFatalError(tokens.dequeue().equals("END"),
                "Invalid Token");

        String end1 = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(end1),
                "Invalid Identifier");
        Reporter.assertElseFatalError(start.equals(end1),
                "Cannot use multiple " + "identifiers as program name");

        String end2 = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.END_OF_INPUT.equals(end2),
                "Program does not end correctly");

        //Puts information into this
        this.setName(start);
        this.swapBody(blockMain);
        this.swapContext(context);

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
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out);

        in.close();
        out.close();
    }

}
