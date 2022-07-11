package readability;

import java.util.function.ToDoubleFunction;

public class TextReadabilityScore {
    public static final ToDoubleFunction<TextScore> ARI_FORMULA = (text) ->
            4.71 * text.getChars() / text.getWords()
                    + 0.5 * text.getWords() / text.getSentences() - 21.43;

    public static final ToDoubleFunction<TextScore> FK_FORMULA = (text) ->
            0.39 * text.getWords() / text.getSentences()
                    + 11.8 * text.getSyllables() / text.getWords() - 15.59;

    public static final ToDoubleFunction<TextScore> SMOG_FORMULA = text ->
            1.043 * Math.sqrt(text.getPolysyllables() * 30. / text.getSentences()) + 3.1291;

    public static final ToDoubleFunction<TextScore> CL_FORMULA = text ->
    {
        final double l = 100. * text.getChars() / text.getWords();
        final double s = 100. * text.getSentences() / text.getWords();
        return 0.0588 * l - 0.296 * s - 15.8;
    };


    public static enum ScoreIndex {
        ARI("Automated Readability Index", ARI_FORMULA),
        FK("Flesch–Kincaid readability tests", FK_FORMULA),
        SMOG("Simple Measure of Gobbledygook", SMOG_FORMULA),
        CL("Coleman–Liau index", CL_FORMULA);
        private final String name;
        private final ToDoubleFunction<TextScore> formula;

        ScoreIndex(String name, ToDoubleFunction<TextScore> formula) {
            this.name = name;
            this.formula = formula;
        }

        public String getName() {
            return name;
        }

        static int calculateAge(final double score) {
            final int level = Math.min(14, Math.max(1, (int) Math.ceil(score))) - 1;
            return new int[]{6, 7, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 24, 25}[level];
        }

        String getScoreAndAge(final TextScore text) {
            final double score = formula.applyAsDouble(text);
            return String.format("%s: %.2f (about %d year olds).%n", name, score, calculateAge(score));
        }

        int getAge(final TextScore text) {
            return calculateAge(formula.applyAsDouble(text));
        }
    }


}
