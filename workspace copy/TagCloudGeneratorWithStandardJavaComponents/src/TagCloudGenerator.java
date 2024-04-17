import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeMap;

import components.utilities.Reporter;

/**
 * Simple HelloWorld program (clear of Checkstyle and SpotBugs warnings).
 *
 * @author Alexander Nistor & Akshay Anand
 */
public final class TagCloudGenerator {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private TagCloudGenerator() {
        // no code needed here
    }

    /**
     *
     */
    private static class AlphabeticalOrder implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }

    }

    /**
     *
     * @param in
     * @return
     */
    private TreeMap<String, Integer> generateMap(BufferedReader in) {
        TreeMap<String, Integer> map = new TreeMap(new AlphabeticalOrder());
        String line = null;
        try {
            line = in.readLine();
            while (line != null) {
                while (line.length() > 0) {
                    String word = line;
                    for (int i = 0; i < line.length(); i++) {
                        if (line.charAt(i) == '\t' || line.charAt(i) == '\n'
                                || line.charAt(i) == '\r'
                                || line.charAt(i) == ' '
                                || line.charAt(i) == '-'
                                || line.charAt(i) == '_'
                                || line.charAt(i) == '.'
                                || line.charAt(i) == ','
                                || line.charAt(i) == '!'
                                || line.charAt(i) == '?'
                                || line.charAt(i) == '\''
                                || line.charAt(i) == '\"'
                                || line.charAt(i) == ':'
                                || line.charAt(i) == ';'
                                || line.charAt(i) == '['
                                || line.charAt(i) == ']'
                                || line.charAt(i) == '{'
                                || line.charAt(i) == '}'
                                || line.charAt(i) == '('
                                || line.charAt(i) == ')') {

                            word = line.substring(0, i);
                            i = line.length();

                        }
                    }

                    if (!word.equals("")) {
                        if (map.containsKey(word)) {
                            Integer num = map.get(word) + 1;
                            map.replace(word, num);
                        } else {
                            map.put(word, 1);
                        }
                    }

                    if (line.length() > word.length()) {
                        line = line.substring(word.length() + 1);
                    } else {
                        line = "";
                    }

                }
                line = in.readLine();
            }

        } catch (IOException e) {
            System.err.println("Error reading from file");
        }

        return map;
    }

    /**
     *
     * @param map
     * @param out
     * @param num
     * @param inputFileName
     */
    private void printMap(TreeMap<String, Integer> map, BufferedWriter out,
            int num, String inputFileName) {
        try {
            out.write("<html>\n" + "<head><title>Top " + num + " words in "
                    + inputFileName + "</title>\n"
                    + "<link href=\"http://web.cse.ohio-state.edu/software/2231/web-sw2/"
                    + "assignments/projects/tag-cloud-generator/data/tagcloud.css\" "
                    + "rel=\"stylesheet\" type=\"text/css\">\n"
                    + "<link href=\"tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">"
                    + "</head>\n" + "<body><h2>Top " + num + " words in "
                    + inputFileName + "</h2><hr>\n"
                    + "<div class = \"cdiv\"><p class = \"cbox\">\n");
        } catch (IOException e) {
            System.err.println("Error writing to file");
        }

        int highest = map.firstEntry().getValue();
        int lowest = map.firstEntry().getValue();
        for (String word : map.keySet()) {
            if (map.get(word) > highest) {
                highest = map.get(word);
            }

            if (map.get(word) < lowest) {
                lowest = map.get(word);
            }
        }

        final int maxFontSize = 48;
        final int minFontSize = 11;
        for (String word : map.keySet()) {
            int fontSize = maxFontSize - minFontSize;
            fontSize *= (map.get(word) - lowest);
            fontSize /= (highest - lowest);
            fontSize += minFontSize;
            try {
                out.write("<span style =\"cursor:default\" class=\"f" + fontSize
                        + "\" title=\"count: " + map.get(word) + "\">" + word
                        + "</span>\n");
            } catch (IOException e) {
                System.err.println("Error writing to file");
            }
        }

        try {
            out.write("</p></div></body></html>");
        } catch (IOException e) {
            System.err.println("Error writing to file");
        }

        return;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        System.out.println("enter a txt file: ");
        Scanner in = new Scanner(System.in);
        String fileIn = in.nextLine();
        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader(fileIn));
        } catch (IOException e) {
            System.err.println("Error opening file input");
        }

        System.out.println("enter a destination file: ");
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(in.nextLine()));
        } catch (IOException e) {
            System.err.println("Error opening file desination");
        }

        System.out.println("How many words do you want to display? ");
        int numWords = in.nextInt();
        Reporter.assertElseFatalError(numWords > 0,
                "Number of words displayed must be positive");

        TagCloudGenerator generator = new TagCloudGenerator();
        TreeMap<String, Integer> wordMap = generator.generateMap(input);
        generator.printMap(wordMap, output, numWords, fileIn);

        in.close();
        try {
            input.close();
        } catch (IOException e) {
            System.err.println("Error closing file input");
        }

        try {
            output.close();
        } catch (IOException e) {
            System.err.println("Error closing file desitnation");
        }

    }

}