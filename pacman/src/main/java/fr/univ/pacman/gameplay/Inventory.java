package fr.univ.pacman.gameplay;

public class Inventory {
    /**
     * The player score
     */
    private int score;

    public Inventory(int score) {
        this.score = score;
    }

    /**
     * Add score to player
     *
     * @param score the score to gain
     */
    public void addScore(int score) {
        this.score += score;
        GamePlay.getMenuView().setScoreText(this.score);
    }
}
