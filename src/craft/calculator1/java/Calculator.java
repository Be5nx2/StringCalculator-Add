package craft.calculator1.java;

import java.util.Arrays;
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


        return stream
                .map(line -> this.computeSumNumberOfOneLine(line, delimiter))
                .reduce(0, Integer::sum);
    }

    private Optional<String> extractDelimiter(String firstLine) {
        if (firstLine.startsWith("//")) {
            return Optional.of(firstLine.substring(2));
        }
        return Optional.empty();
    }

    private int computeSumNumberOfOneLine(String line, String delimiter) {
        checkLineNotFinishWithDelimiter(line);
        String[] split = line.split(delimiter);
        int sum = 0;
        for (String s : split) {
            sum = sum + Integer.parseInt(s);
        }
        return sum;
    }

    private void checkLineNotFinishWithDelimiter(String line) {
        if (line.charAt(line.length() - 1) == ',') {
            throw new IllegalArgumentException("Input cannot finish per `,`.");
        }
    }

}
