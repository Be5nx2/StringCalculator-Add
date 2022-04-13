package craft.calculator1.java;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Calculator {

    public static final int MAX_NUMBER_VALUE = 1000;

    public int add(String numbers) {
        Objects.requireNonNull(numbers);
        if (numbers.isBlank()) {
            return 0;
        }

        final List<String> allNumbers = extractAllNumbersAsStringList(numbers);

        checkNegativesNumbers(allNumbers);

        return allNumbers.stream()
                .map(Integer::parseInt)
                .filter(number -> number <= MAX_NUMBER_VALUE)
                .reduce(0, Integer::sum);
    }

    private void checkNegativesNumbers(List<String> allNumbersAsString) {
        final List<String> allNegativeNumbers = allNumbersAsString.stream()
                .filter(s -> s.startsWith("-"))
                .toList();

        if (!allNegativeNumbers.isEmpty()) {
            throw new IllegalArgumentException(
                    "negatives not allowed : " + String.join(", ", allNegativeNumbers));
        }
    }

    private List<String> extractAllNumbersAsStringList(String numbers) {
        final String[] lines = numbers.split("\n");

        final List<String> delimiters = extractDelimiters(lines[0]);
        Stream<String> stream = Arrays.stream(lines);
        if (!delimiters.isEmpty()) {
            stream = stream.skip(1);
        } else {
            delimiters.add(",");
        }

        return stream
                .map(s -> this.splitLineInNumbers(s, delimiters))
                .flatMap(List::stream).toList();
    }

    private List<String> extractDelimiters(String firstLine) {
        List<String> delimiters = new ArrayList<>();
        if (firstLine.startsWith("//")) {
            final Pattern pattern = Pattern.compile("\\[(.*?)]");
            final Matcher matcher = pattern.matcher(firstLine);
            while (matcher.find()) {
                final String potentialDelimiter = firstLine.substring(matcher.start() + 1, matcher.end() - 1);
                delimiters.add(escapeSpecialCharacters(potentialDelimiter));
            }
            if (delimiters.isEmpty()) {
                delimiters.add(firstLine.substring(2));
            }
        }
        return delimiters;
    }

    // methode find on https://stackoverflow.com/questions/32498432/add-escape-in-front-of-special-character-for-a-string
    private String escapeSpecialCharacters(String input) {
        List<String> specialCharacters = List.of("\\", "^", "$", "{", "}", "[", "]", "(", ")", ".", "*", "+", "?", "|", "<", ">", "-", "&", "%");
        return Arrays.stream(input.split("")).map((c) -> {
            if (specialCharacters.contains(c)) return "\\" + c;
            else return c;
        }).collect(Collectors.joining());
    }

    private List<String> splitLineInNumbers(String line, List<String> delimiters) {
        checkLineNotFinishWithDelimiter(line, delimiters);
        List<String> result = new ArrayList<>();
        result.add(line);
        for (String delimiter : delimiters) {
            result = result.stream()
                    .map(s -> s.split(delimiter))
                    .flatMap(Arrays::stream)
                    .toList();
        }
        return result;
    }

    private void checkLineNotFinishWithDelimiter(String line, List<String> delimiters) {
        for (String delimiter : delimiters) {
            if (line.endsWith(delimiter)) {
                throw new IllegalArgumentException("Input cannot finish per " + delimiter);
            }
        }

    }

}
