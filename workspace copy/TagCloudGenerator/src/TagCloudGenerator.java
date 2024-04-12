import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine2;
import components.utilities.Reporter;

/**
 * Simple HelloWorld program (clear of Checkstyle and SpotBugs warnings).
 *
 * @author Akshay Anand and Alexander Nistor
 */
public final class TagCloudGenerator {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private TagCloudGenerator() {
        // no code needed here
    }

    /**
     * Implements comparator interface and contains compare method for integers.
     */
    private static class IntegerSorter
            implements Comparator<Map.Pair<String, Integer>> {

        /**
         * Compares which of two string count values are bigger.
         *
         * @param pair1
         *            first pair
         *
         * @param pair2
         *            second pair
         *
         * @return integer indicating if pair1.value() > pair2.value(),
         *         pair2.value() < pair1.value(), or pair1.value() =
         *         pair2.value() based on respective integers 1, -1, and 0
         *
         * @ensure #pair1 = pair1 and #pair2 = pair2
         */
        @Override
        public int compare(Map.Pair<String, Integer> pair1,
                Map.Pair<String, Integer> pair2) {
            //Returns integer based on pair values aka the count for each word
            return pair2.value().compareTo(pair1.value());
        }

    }

    /**
     * Implements comparator interface and contains compare method for strings.
     */
    private static class StringSorter
            implements Comparator<Map.Pair<String, Integer>> {

        /**
         * Compares which of two pair key strings are bigger
         * non-lexicographically.
         *
         * @param pair1
         *            first pair
         *
         * @param pair2
         *            second pair
         *
         * @return integer indicating if pair1.key() > pair2.key(), pair2.key()
         *         < pair1.key(), or pair1.key() = pair2.key() based on
         *         respective integers 1, -1, and 0
         *
         * @ensure #pair1 = pair1 and #pair2 = pair2
         */
        @Override
        public int compare(Map.Pair<String, Integer> pair1,
                Map.Pair<String, Integer> pair2) {
            //Returns integer based on pair keys aka each string word
            return pair1.key().compareTo(pair2.key());
        }

    }

    /**
     * Method generates a set of word separators.
     *
     * @return separators Set of characters containing many possible word
     *         separators
     */
    private static Set<Character> generateSeparatorSet() {

        //String containing possible separators
        String separatorString = "0123456789()-_=+@#$%^&*. ,:;'{][}|/><?!`~";

        //Instantiate set to contain separators
        Set<Character> separators = new Set1L<>();

        for (int i = 0; i < separatorString.length(); i++) {
            separators.add(separatorString.charAt(i));
        }

        return separators;
    }

    /**
     * Reads input file and stores words into a map.
     *
     * @param inputFile
     *            SimpleReader object for the input file
     * @param outputFile
     *            SimpleWriter object for the output file
     * @param wordCount
     *            Integer representing the number of words to be displayed on
     *            the html website
     * @ensures #wordCount = wordCount
     *
     */
    private void readingInput(SimpleReader inputFile, SimpleWriter outputFile,
            int wordCount) {

        //Instantiates map store words and their counts
        Map<String, Integer> allWords = new Map1L<>();

        //Stores set of all separators for use in reading input file
        Set<Character> separators = generateSeparatorSet();

        //While-loop keeps reading file until end of file is reached
        while (!inputFile.atEOS()) {

            //Stores term in queue
            String line = inputFile.nextLine().toLowerCase();

            //Initialize word temp variable to store words as they are read
            String word = "";

            //For-loop continues until end of line is reached
            for (int i = 0; i < line.length(); i++) {

                //If current char is a separator the word "created" will either
                //be added to the map of words or its count will be incremented
                if (separators.contains(line.charAt(i))) {
                    if (word.length() > 0) {
                        if (allWords.hasKey(word)) {
                            int x = allWords.value(word) + 1;
                            allWords.replaceValue(word, x);
                        } else {
                            allWords.add(word, 1);
                        }
                    }

                    //Resets temp word variable back to an empty string
                    word = "";

                } else {
                    word += line.charAt(i);
                }
            }

            //If their is a final word that hasn't been read, this if-statement
            //catches it and either puts it in the map or increments its count
            //value
            if (word.length() > 0) {
                if (allWords.hasKey(word)) {
                    int x = allWords.value(word) + 1;
                    allWords.replaceValue(word, x);
                } else {
                    allWords.add(word, 1);
                }
            }

        }

        //Sorts all words and finishes the html file for the website
        processWords(allWords, outputFile, wordCount);

        //Closes reader and writer objects
        inputFile.close();
        outputFile.close();
    }

    /**
     * Method prints the beginning html code to the output file.
     *
     * @param inputFileName
     *            Name of the inputFileName entered by user before
     * @param out
     *            SimpleWriter object for the output file
     * @param n
     *            Integer representing the number of words to display on the
     *            html website
     */
    private void generateHeader(String inputFileName, SimpleWriter out,
            Integer n) {

        //Prints all headers and starting html website code
        out.println("<html>");
        out.println("<head><title>Top " + n + " words in " + inputFileName
                + "</title>");

        //Links CSS files to get the format achieved
        out.println(
                "<link href=\"http://web.cse.ohio-state.edu/software/2231/web-sw2/"
                        + "assignments/projects/tag-cloud-generator/data/tagcloud.css\" "
                        + "rel=\"stylesheet\" type=\"text/css\">");
        out.println(
                "<link href=\"tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">"
                        + "</head>");
        out.println("<body><h2>Top " + n + " words in " + inputFileName
                + "</h2><hr>");
        out.println("<div class = \"cdiv\"><p class = \"cbox\">");

    }

    /**
     * Method sorts all words, generates their font size, and prints their
     * respective html code to the output file.
     *
     * @param allWords
     *            Map of each string word in input file to their respective word
     *            counts
     * @param out
     *            SimplerWriter object for the output file
     * @param n
     *            Integer representing the number of words displayed on the html
     *            website
     */
    private static void processWords(Map<String, Integer> allWords,
            SimpleWriter out, Integer n) {

        //Instantiates final min and max font sizes for use later
        final int maxFontSize = 48;
        final int minFontSize = 11;

        //Instantiate all variables for finding the font sizes of words
        int highest = 0;
        int lowest = 0;

        //Instantiates SortingMachine objects to sort map pairs by key and value
        Comparator<Map.Pair<String, Integer>> integerOrder = new IntegerSorter();
        SortingMachine<Map.Pair<String, Integer>> sortedValues = new SortingMachine2<>(
                integerOrder);
        Comparator<Map.Pair<String, Integer>> stringOrder = new StringSorter();
        SortingMachine<Map.Pair<String, Integer>> sortedKeys = new SortingMachine2<>(
                stringOrder);

        //Stores all map pairs in the integer sorting machine
        for (Map.Pair<String, Integer> pair : allWords) {
            sortedValues.add(pair);
        }

        //Swaps integer sorting machine into extraction mode
        sortedValues.changeToExtractionMode();

        //Stores the highest value in integer sorting machine in string sorting machine
        if (sortedValues.size() > 0) {
            Map.Pair<String, Integer> temp1 = sortedValues.removeFirst();
            sortedKeys.add(temp1);
            highest = temp1.value();
        }

        //Adds as many words to sorted string list as allowed by the user
        int count = 0;
        while (sortedValues.size() > 1 && count < n - 2) {
            Map.Pair<String, Integer> temp2 = sortedValues.removeFirst();
            sortedKeys.add(temp2);
            count++;
        }

        //Stores the lowest value after all n-values removed, in integer
        //sorting machine in string sorting machine
        if (sortedValues.size() > 0) {
            Map.Pair<String, Integer> temp3 = sortedValues.removeFirst();
            sortedKeys.add(temp3);
            lowest = temp3.value();
        }

        //Changes string sorting machine to extraction mode
        sortedKeys.changeToExtractionMode();

        //Goes through string sorting machine to calculate font size for each word
        //and print out its html code to the output file
        int length = sortedKeys.size();
        for (int i = 0; i < length; i++) {
            Map.Pair<String, Integer> pair = sortedKeys.removeFirst();

            int fontSize = maxFontSize - minFontSize;
            fontSize *= (pair.value() - lowest);
            fontSize /= (highest - lowest);
            fontSize += minFontSize;

            out.println("<span style =\"cursor:default\" class=\"f" + fontSize
                    + "\" title=\"count: " + pair.value() + "\">" + pair.key()
                    + "</span>");
        }

        //Prints out the ending html code to the output file
        out.println("</p></div></body></html>");

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {

        //Instantiates SimpleReader and SimpleWriter objects for getting and
        //storing user input
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        //Gets input file name, and stores in string and SimpleReader object to
        //check for validity
        out.println("Enter the input file name:");
        String inputFileName = in.nextLine();
        SimpleReader inputFile = new SimpleReader1L(inputFileName);

        //Gets output file name, and stores in string and SimpleReader object to
        //check for validity
        out.println("Enter the output file name:");
        String outputFileName = in.nextLine();
        SimpleReader outputFileChecker = new SimpleReader1L(outputFileName);
        outputFileChecker.close();
        SimpleWriter outputFile = new SimpleWriter1L(outputFileName);

        //Gets number of words to be displayed on html website and checks if it
        //is valid
        out.println("Enter the number of words you want displayed:");
        int n = in.nextInteger();
        Reporter.assertElseFatalError(n > 0,
                "Number of words displayed must " + "be positive");

        //Instantiates TagCloudGenerator object
        TagCloudGenerator gen = new TagCloudGenerator();

        //Create tag cloud generator based on inputed information
        gen.generateHeader(inputFileName, outputFile, n);
        gen.readingInput(inputFile, outputFile, n);

        //Close SimpleReader and SimpleWriter objects after use
        in.close();
        out.close();
    }

}
