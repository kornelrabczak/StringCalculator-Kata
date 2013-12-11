package hackathon;


import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.sun.istack.internal.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

    public static final Pattern INPUT_REGEX = Pattern.compile("//([^\n]*)\n(.*)");
    public static final int MAX_ACCEPTED_NUMBER = 1000;
    private Collection<String> separators;

    public StringCalculator(Collection<String> separators) {
        this.separators = separators;
    }

    public int add(String inputString) {
        String numbersString = parseCustomSeparator(inputString);
        Collection<Integer> numbers = parseNumbers(numbersString);
        assertNoNegatives(numbers);
        Collection<Integer> filteredNumbers = filterBigNumbers(numbers);
        return sumNumbers(filteredNumbers);
    }

    private Collection<Integer> parseNumbers(String numbersString) {
        String[] numbers = splitNumbers(numbersString);
        List<String> numberList = Arrays.asList(numbers);
        return Collections2.transform(numberList, new Function<String, Integer>() {
            @Override
            public Integer apply(String number) {
                return parseNumber(number);
            }
        });
    }

    private Integer parseNumber(String number) {
        if (number.isEmpty()) {
            return 0;
        }
        return Integer.valueOf(number);
    }

    private String[] splitNumbers(String numbersString) {
        StringBuilder joinedSeparators = new StringBuilder();
        for (String separator : separators) {
            joinedSeparators.append("\\[" + separator + "\\]");
        }
        String separatorsRegex = "[" + joinedSeparators + "]";
        return numbersString.split(separatorsRegex);
    }

    private String parseCustomSeparator(String inputString) {
        Matcher matcher = INPUT_REGEX.matcher(inputString);
        if (matcher.matches()) {
            String customDelimiter = matcher.group(1);
            separators.add(customDelimiter);
            inputString = matcher.group(2);
        }
        return inputString;
    }

    private void assertNoNegatives(Collection<Integer> numbers) {
        Collection<Integer> negatives = Collections2.filter(numbers, new Predicate<Integer>() {
            @Override
            public boolean apply(@Nullable Integer integer) {
                return integer < 0;
            }
        });
        if (!negatives.isEmpty()) {
            throw new IllegalArgumentException("Negative numbers are not supported. Negatives=" + negatives);
        }
    }

    private Collection<Integer> filterBigNumbers(Collection<Integer> numbers) {
        return Collections2.filter(numbers, new Predicate<Integer>() {
            @Override
            public boolean apply(@Nullable Integer integer) {
                return integer <= MAX_ACCEPTED_NUMBER;
            }
        });
    }

    private int sumNumbers(Collection<Integer> numbers) {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }


}