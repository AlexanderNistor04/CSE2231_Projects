import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine1L;

/**
 * Simple HelloWorld program (clear of Checkstyle and SpotBugs warnings).
 *
 * @author P. Bucci
 */
public final class TagCloudGenerator {

    private final class Alphabetical implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }

    }

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private TagCloudGenerator() {
        // no code needed here
    }

    private static Map<String, Integer> generateWordMap(SimpleReader in) {
        Map<String, Integer> wordMap = new Map1L<String, Integer>();
        while (!in.atEOS()) {
            String line = in.nextLine();
            String word = nextWord(line);
            line = line.substring(line.indexOf(word) + word.length());
            addWordToMap(word, wordMap);
        }
        return wordMap;
    }

    private static String nextWord(String line) {
        String word = "";
        while (isSeperator(line.charAt(0))) {
            line = line.substring(1);
        }
        for (int i = 0; i < line.length(); i++) {
            if (isSeperator(line.charAt(0))) {
                word = line.substring(0, i);
            }
        }
        return word;
    }

    private static boolean isSeperator(char c) {
        return c == ' ' || c == '-' || c == '_' || c == '.' || c == ','
                || c == '!' || c == '?' || c == '/';
    }

    private static void addWordToMap(String word, Map<String, Integer> map) {
        if (map.hasKey(word)) {
            int x = map.value(word) + 1;
            map.replaceValue(word, x);
        } else {
            map.add(word, 1);
        }
    }

    private static SortingMachine<String> generateSortingMachine(
            Comparator<String> order, Map<String, Integer> map) {
        SortingMachine<String> sorter = new SortingMachine1L<String>(order);
        return sorter;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();
        out.println("name an input .txt file: ");
        String input = in.nextLine();
        out.println("name an output folder: ");
        String output = in.nextLine();
        SimpleReader inputFile = new SimpleReader1L(input);
        Map<String, Integer> map = generateWordMap(inputFile);
        //SortingMachine<String> sorter = new SortingMachine1L(new Alphabetical());
    }

}
