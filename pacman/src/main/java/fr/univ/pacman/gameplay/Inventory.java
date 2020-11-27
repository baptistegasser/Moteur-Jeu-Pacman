package fr.univ.pacman.gameplay;


public class Inventory {

    /**
     * The gameplay of inventory
     */
    GamePlay gamePlay;

    /**
     * The player score
     */
    private int score;

    private int life;

    public Inventory(GamePlay gamePlay) {
        this.gamePlay = gamePlay;
        this.score = 0;
        this.life = 3;
    }

    /**
     * Add score to player
     *
     * @param score the score to gain
     */
    public void addScore(int score) {
        this.score += score;
        gamePlay.getGameView().setScoreText(this.score);
    }

    /**
     * Remove one life and checked the game over
     */
    public void lostLife() {
        life -= 1;
        if (life == 0) {
            System.out.println("GAME OVER");
        }
        gamePlay.getGameView().updateLifeView();
    }

    public int getLife() {
        return life;
    }
}
