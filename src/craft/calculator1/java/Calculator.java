package craft.calculator1.java;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class Calculator {

    public int add(String numbers) {
        Objects.requireNonNull(numbers);
        if (numbers.isBlank()) {
            return 0;
        }

        final String[] lines = numbers.split("\n");

        final Optional<String> potentialDelimiter = extractDelimiter(lines[0]);
        Stream<String> stream = Arrays.stream(lines);
        if (potentialDelimiter.isPresent()) {
            stream = stream.skip(1);
        }
        final String delimiter = potentialDelimiter.orElse(",");

        List<String> allNumbersAsString =  stream
                .map(s -> this.SplitLineInNumbers(s, delimiter))
                .flatMap(List::stream).toList();

        checkNegativesNumbers(allNumbersAsString);

        return allNumbersAsString.stream().map(Integer::parseInt).reduce(0, Integer::sum);
    }

    private void checkNegativesNumbers(List<String> allNumbersAsString) {
        List<String> allNegativeNumbers = allNumbersAsString.stream()
                .filter(s -> s.startsWith("-"))
                .toList();

        if(!allNegativeNumbers.isEmpty()){
            throw new IllegalArgumentException(
                    "negatives not allowed : "+  String.join(", ", allNegativeNumbers));
        }
    }

    private Optional<String> extractDelimiter(String firstLine) {
        if (firstLine.startsWith("//")) {
            return Optional.of(firstLine.substring(2));
        }
        return Optional.empty();
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
