package fr.univ.engine.io;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class IoEngine {
    public static void handleKeys(KeyEvent keyEvent) {
        //System.out.println(keyEvent.getText().toLowerCase());
        if(keyEvent.getCode() == KeyCode.ESCAPE) {}
        String key = keyEvent.getText().toLowerCase();
        switch (key) {
            case "q":
                System.out.println("q");
                break;
            case "d":
                System.out.println("d");
                break;
            case "z":
                System.out.println("z");
                break;
            case "s":
                System.out.println("s");
                break;
            default:
                //do nothing
        }
    }
}
