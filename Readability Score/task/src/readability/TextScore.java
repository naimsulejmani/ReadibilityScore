package readability;

import java.util.stream.Stream;

public class TextScore {
    private static final long DEFAULT = -1;
    private final String text;
    private long chars = DEFAULT;
    private long sentences = DEFAULT;
    private long words = DEFAULT;
    private long syllables = DEFAULT;
    private long polysyllables = DEFAULT;

    public TextScore(String text) {
        this.text = text;
        initialize();
    }

    public void initialize() {
        setChars();
        setWords();
        setSentences();
        setSyllables();
        setPolysyllables();
    }

    private Stream<String> getWordStream() {
        return Stream.of(text.split("[^\\p{Alpha}]+"));
    }

    private void setChars() {
        this.chars = text.replaceAll("\\s", "").length();
    }

    private void setWords() {
        this.words = getWordStream().count();
    }

    private static int countSyllables(final String word) {
        int syllable = word.toLowerCase()
                .replaceAll("e$", "")
                .replaceAll("[aeiouy]{2}", "a")
                .replaceAll("[^aeiouy]", "")
                .length();
        return syllable > 0 ? syllable : 1;
    }

    private static boolean isPolysyllable(final String word) {
        return countSyllables(word) > 2;
    }

    private void setSentences() {
        this.sentences = text.split("[!?.]+").length;
    }

    private void setSyllables() {
        this.syllables = getWordStream().mapToInt(TextScore::countSyllables).sum();
    }

    private void setPolysyllables() {
        this.polysyllables = getWordStream().filter(TextScore::isPolysyllable).count();
    }

    public String getText() {
        return text;
    }

    public long getChars() {
        return chars;
    }

    public long getSentences() {
        return sentences;
    }

    public long getWords() {
        return words;
    }

    public long getSyllables() {
        return syllables;
    }

    public long getPolysyllables() {
        return polysyllables;
    }

    @Override
    public String toString() {
        return String.format(String.join("%n",
                        "The text is: %n%s",
                        "Words: %d",
                        "Sentences: %d",
                        "Characters: %d",
                        "Syllables: %d",
                        "Polysyllables: %d%n"),
                getText(), getWords(), getSentences(), getChars(), getSyllables(), getPolysyllables());
    }
}
