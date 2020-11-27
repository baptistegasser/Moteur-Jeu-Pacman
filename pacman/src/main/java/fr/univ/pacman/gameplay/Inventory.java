package fr.univ.pacman.gameplay;

import fr.univ.pacman.GamePlay;

public class Inventory {
    /**
     * The player score
     */
    private int score;

    private int life;

    public Inventory() {
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
        GamePlay.getMenuView().setScoreText(this.score);
    }

    /**
     * Remove one life and checked the game over
     */
    public void lostLife() {
        life -= 1;
        if (life == 0) {
            System.out.println("GAME OVER");
        }
        GamePlay.getMenuView().updateLifeView();
    }

    public int getLife() {
        return life;
    }
}
