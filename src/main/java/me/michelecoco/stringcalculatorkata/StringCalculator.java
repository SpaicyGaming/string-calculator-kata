package me.michelecoco.stringcalculatorkata;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {

    /**
     * Default delimiter used when no custom delimiter is specified.
     */
    private static final String DEFAULT_DELIMITER = ",";

    /**
     * Pattern used to extract the custom delimiter and the numbers from the input string.
     * The first group is the custom delimiter, the second group is the numbers.
     */
    private static final Pattern CUSTOM_DELIMITER_PATTERN = Pattern.compile("//(.+)\n(?s)(.*)");

    public static void main(String[] args) {
        StringCalculator calculator = new StringCalculator();
        String numbers = String.join("", args);
        System.out.println(calculator.add(numbers));
    }

    /**
     * Adds the numbers in the input string.
     * <p>
     * The numbers to be added are separated by a delimiter, which can be a comma, a new line or a custom delimiter.
     * The custom delimiter can be specified by using the following syntax: {@code //<delimiter>\n<numbers>}
     * The custom delimiter can be of any length and can contain any character, including regex metacharacters.
     * Negative numbers are not allowed. If any negative number is found, an IllegalArgumentException is thrown.
     * Numbers greater than 1000 are ignored.
     * <p>
     * Examples:
     * <ul>
     *     <li>{@code ""} returns {@code 0}</li>
     *     <li>{@code "1"} returns {@code 1}</li>
     *     <li>{@code "1,2"} returns {@code 3}</li>
     *     <li>{@code "1001,2"} returns {@code 2}</li>
     *     <li>{@code "1\n2,3"} returns {@code 6}</li>
     *     <li>{@code "//;\n1;2"} returns {@code 3}</li>
     *     <li>{@code "//[***]\n1[***]2[***]3"} returns {@code 6}</li>
     * </ul>
     *
     * @param numbers the input string
     * @return the sum of the numbers, or 0 if the input string is empty
     */
    public int add(String numbers) {
        if (numbers.isEmpty()) {
            return 0;
        }

        // look for custom delimiter
        String input = numbers;
        String delimiter = DEFAULT_DELIMITER;
        Matcher matcher = CUSTOM_DELIMITER_PATTERN.matcher(input);
        if (matcher.matches()) {
            delimiter = matcher.group(1);
            input = matcher.group(2);
        }

        // extract numbers from the input string ignoring numbers greater than 1000
        List<Integer> values = Arrays.stream(input.replace("\n", delimiter).split(Pattern.quote(delimiter)))
                .map(Integer::valueOf)
                .filter(i -> i <= 1000)
                .toList();

        // check for negative numbers and throw an exception if any
        String negatives = values.stream()
                .filter(i -> i < 0)
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        if (!negatives.isEmpty()) {
            throw new IllegalArgumentException("negatives not allowed: " + negatives);
        }

        // sum all numbers
        return values.stream().mapToInt(Integer::intValue).sum();
    }

}