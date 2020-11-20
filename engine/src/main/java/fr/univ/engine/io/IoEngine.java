package fr.univ.engine.io;

import javafx.scene.input.KeyEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IoEngine {
    private static ConcurrentHashMap<String, Status> keysStatus = new ConcurrentHashMap<>();
    /**
     * Handle keyboard key events
     * @param keyEvent the key event, which is the key the user pressed
     */
    public static void pressedKeys(KeyEvent keyEvent) {
        String key = keyEvent.getText().toLowerCase();
        switch (key) {
            case "q":
                updateMap(key, Status.PRESSED_FRAME);
                break;
            case "d":
                updateMap(key, Status.PRESSED_FRAME);
                break;
            case "z":
                updateMap(key, Status.PRESSED_FRAME);
                break;
            case "s":
                updateMap(key,  Status.PRESSED_FRAME);
                break;
            default:
                //do nothing
        }
    }

    /**
     *  Handle keyboard key events
     * @param keyEvent the key event, when the users relase the key
     */
    public static void releasedKeys(KeyEvent keyEvent) {
        String key = keyEvent.getText().toLowerCase();
        switch (key) {
            case "q":
                updateMap(key, Status.RELEASED_FRAME);
                break;
            case "d":
                updateMap(key,  Status.RELEASED_FRAME);
                break;
            case "z":
                updateMap(key,  Status.RELEASED_FRAME);
                break;
            case "s":
                updateMap(key,  Status.RELEASED_FRAME);
                break;
            default:
                //do nothing
        }

    }

    /**
     * Update map , so the value "true" is set when a key is currently being pressed
     * Its creating an entry if the key has never been pressed, or its updating its value
     * @param key the key
     * @param value the value of the key
     */
    private static void updateMap (String key, Status value) {

        Status check = keysStatus.putIfAbsent(key, value); // Create value if the key has never been pressed
        if(check != null) {
            keysStatus.replace(key,value);
        }
        System.out.println(keysStatus);

    }

    /**
     * @return Return the hash map
     */
    public static ConcurrentHashMap<String, Status> getKeysStatus() {
        return keysStatus;
    }

    public void start() {

    }

}
