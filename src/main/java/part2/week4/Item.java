package part2.week4;

public class Item {
    int[][] dfa;
    String word;
    int length;
    int score = 0;

    Item(String word) {
        this.length = word.length();
        this.word = word;
        dfa = new int[26][length];
        buildDFA();
        calculateScore();
    }

    void calculateScore() {
        int measure = length;
        for (int j = 0; j < length; j++) {
            if (word.charAt(j) == ('Q'-'A')) measure++;
        }

        if (measure == 3 || measure == 4) this.score = 1;
        else if (measure == 5) this.score = 2;
        else if (measure == 6) this.score = 3;
        else if (measure == 7) this.score = 5;
        else if (measure >= 8) this.score = 11;
    }

    int move(int from, int c) {
        return dfa[c][from];
    }

    void buildDFA() {
        int m = length;
        dfa = new int[26][m];
        int index = word.charAt(0) - 'A';
        dfa[index][0] = 1;
        for (int x = 0, j = 1; j < m; j++) {
            for (int c = 0; c < 26; c++)
                dfa[c][j] = dfa[c][x];     // Copy mismatch cases.
            index = word.charAt(j) - 'A';
            dfa[index][j] = j+1;      // Set match case.
            x = dfa[index][x];        // Update restart state.
        }
    }
}
