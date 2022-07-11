package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String text = Files.readString(Path.of(args[0]));
            TextScore textScore = new TextScore(text);
            System.out.println(textScore);
            System.out.printf("Enter the score you want to calculate (%s, all):%n",
                    Stream.of(TextReadabilityScore.ScoreIndex.values()).map(Enum::toString)
                            .collect(Collectors.joining()));
            String name = scanner.nextLine().toUpperCase();
            boolean isAll = name.equals("ALL");
            Stream.of(TextReadabilityScore.ScoreIndex.values())
                    .filter(rs -> isAll || rs.name().equals(name))
                    .peek(rs -> System.out.println(rs.getScoreAndAge(textScore)))
                    .mapToInt(rs -> rs.getAge(textScore))
                    .average()
                    .ifPresentOrElse(Main::printAverage, Main::printErrorMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void printErrorMessage() {
        System.out.println("Wrong name of Readability Score!");
    }

    private static void printAverage(double averageAge) {
        System.out.printf("This text should be understood in average by %.2f year olds.", averageAge);
    }

}

