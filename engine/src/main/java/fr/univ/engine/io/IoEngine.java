package fr.univ.engine.io;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IoEngine {
    private static ConcurrentHashMap<String, Boolean> keysStatus = new ConcurrentHashMap<>();
    /**
     * Handle keyboard key events
     * @param keyEvent the key event, which is the key the user pressed
     */
    public static void pressedKeys(KeyEvent keyEvent) {
        String key = keyEvent.getText().toLowerCase();
        switch (key) {
            case "q":
                updateMap(key, true);
                break;
            case "d":
                updateMap(key, true);
                break;
            case "z":
                updateMap(key, true);
                break;
            case "s":
                updateMap(key, true);
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
                updateMap(key, false);
                break;
            case "d":
                updateMap(key, false);
                break;
            case "z":
                updateMap(key, false);
                break;
            case "s":
                updateMap(key, false);
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
    private static void updateMap (String key, Boolean value) {
        Boolean check = keysStatus.putIfAbsent(key, value);
        if(check != null) {
            keysStatus.replace(key,value);
        }
        //TODO peut etre checker si un utilisateur peux press deux touches en même temps (inutile)
        // mais cela pourrait faire buguer si deux valeurs sont à true, donc peut etre controller ça
    }

    /**
     * @return Return the hash map
     */
    public static ConcurrentHashMap<String, Boolean> getKeysStatus() {
        return keysStatus;
    }

    /**
     * @return  Return the true value in the hashmap
     */
    public static String getTrueValue () {
        for (Map.Entry<String, Boolean> entry :keysStatus.entrySet()) {
            if(entry.getValue()) return entry.getKey();
        }
        return "";
    }
}
