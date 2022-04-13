package craft.calculator1.java;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

        List<String> allNumbers = extractAllNumbersAsStringList(numbers);

        checkNegativesNumbers(allNumbers);

        return allNumbers.stream()
                .map(Integer::parseInt)
                .filter(number -> number <= MAX_NUMBER_VALUE)
                .reduce(0, Integer::sum);
    }

    private void checkNegativesNumbers(List<String> allNumbersAsString) {
        List<String> allNegativeNumbers = allNumbersAsString.stream()
                .filter(s -> s.startsWith("-"))
                .toList();

        if (!allNegativeNumbers.isEmpty()) {
            throw new IllegalArgumentException(
                    "negatives not allowed : " + String.join(", ", allNegativeNumbers));
        }
    }

    private List<String> extractAllNumbersAsStringList(String numbers) {
        final String[] lines = numbers.split("\n");

        final Optional<String> potentialDelimiter = extractDelimiter(lines[0]);
        Stream<String> stream = Arrays.stream(lines);
        if (potentialDelimiter.isPresent()) {
            stream = stream.skip(1);
        }
        final String delimiter = potentialDelimiter.orElse(",");

        List<String> allNumbersAsString = stream
                .map(s -> this.SplitLineInNumbers(s, delimiter))
                .flatMap(List::stream).toList();
        return allNumbersAsString;
    }

    private Optional<String> extractDelimiter(String firstLine) {
        String delimiter = null;
        if (firstLine.startsWith("//")) {
            final Pattern pattern = Pattern.compile("\\[(...)\\]");
            final Matcher matcher = pattern.matcher(firstLine);
            if (matcher.find()) {
                final String potentialDelimiter = firstLine.substring(matcher.start() + 1, matcher.end() - 1);
                delimiter = escapeSpecialCharacters(potentialDelimiter);
            }else {
                delimiter = firstLine.substring(2);
            }
        }
        return Optional.ofNullable(delimiter);
    }

    // methode find on https://stackoverflow.com/questions/32498432/add-escape-in-front-of-special-character-for-a-string
    private String escapeSpecialCharacters(String input) {
        List<String> specialCharacters = List.of("\\","^","$","{","}","[","]","(",")",".","*","+","?","|","<",">","-","&","%");
        return Arrays.stream(input.split("")).map((c) -> {
            if (specialCharacters.contains(c)) return "\\" + c;
            else return c;
        }).collect(Collectors.joining());
    }

    private List<String> SplitLineInNumbers(String line, String delimiter) {
        checkLineNotFinishWithDelimiter(line, delimiter);
        String[] split = line.split(delimiter);
        return Arrays.asList(split);
    }

    private void checkLineNotFinishWithDelimiter(String line, String delimiter) {
        if (line.endsWith(delimiter)) {
            throw new IllegalArgumentException("Input cannot finish per " + delimiter);
        }
    }

}
