package fr.univ.engine.io;

import fr.univ.engine.render.JFXApp;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Engine charged of inputs and outputs.
 */
public class IoEngine implements KeyEventHandler {
    /**
     * The input/output engine is a singleton to allow game to
     * access it without passing references.
     */
    private static IoEngine instance;

    /**
     * Store the {@link KeyCode} and their current status.
     */
    private ConcurrentHashMap<KeyCode, EnumSet<Status>> keysStatus;

    /**
     * Queue of {@link KeyBoardEvent} to be treated.
     */
    private final ConcurrentLinkedDeque<KeyBoardEvent> queue = new ConcurrentLinkedDeque<>();

    private IoEngine() {}

    /**
     * @return the instance of the engine
     */
    public static IoEngine getInstance() {
        if (instance == null) {
            instance = new IoEngine();
        }
        return instance;
    }

    /**
     * Method in charge of linking the engine to the app.
     */
    public void start() {
        JFXApp.setKeyEventHandler(this);
        // Init the map size. Prevent resizing the map's bucket pool too soon.
        keysStatus = new ConcurrentHashMap<>(40);
    }

    /**
     * Update the key status based on the queue of keyboard event, the current
     * status of a key and the knowledge that a frame passed.
     */
    public void nextFrame() {
        // Take a capture of the event put in the queue since the last frame and clear it
        Deque<KeyBoardEvent> snapshot;
        synchronized (queue) {
            snapshot = new ArrayDeque<>(queue);
            queue.removeAll(snapshot);
        }

        // Update key status that contain status related to a specific (old) frame
        updateFrameStatus();

        // For each event, process it to update key status
        snapshot.forEach(this::processKeyBoardEvent);
    }

    /**
     * Update the status of key that are linked to a specific frame.
     * ie: remove the UP status as releasing a key last one frame only.
     */
    private void updateFrameStatus() {
        for (EnumSet<Status> status : keysStatus.values()) {
            if (status.contains(Status.UP)) {
                // Clear all status if released the key
                status.removeAll(Status.ALL);
            } else if (status.contains(Status.DOWN)) {
                // If key was down last frame, assume is held
                status.remove(Status.DOWN);
                status.add(Status.HELD);
            }
        }
    }

    /**
     * For a given KeyBoard event, update the status of the affected key.
     *
     * @param event the event to process
     */
    private void processKeyBoardEvent(KeyBoardEvent event) {
        EnumSet<Status> status = getStatus(event.code);

        if (event.status == Status.DOWN) {
            if (status.contains(Status.DOWN)) {
                status.remove(Status.DOWN);
                status.add(Status.HELD);
            } else if (!status.contains(Status.HELD)) {
                status.add(Status.DOWN);
            }
        } else /* UP */ {
            status.add(Status.UP);
        }
    }

    @Override
    public void onKeyPressed(KeyEvent keyEvent) {
        queue.add(new KeyBoardEvent(keyEvent.getCode(), Status.DOWN));
    }

    @Override
    public void onKeyReleased(KeyEvent keyEvent) {
        queue.add(new KeyBoardEvent(keyEvent.getCode(), Status.UP));
    }

    /**
     * @param code the key code
     * @return true while the user holds down the key
     */
    public static boolean getKey(KeyCode code) {
        return instance.getStatus(code).contains(Status.HELD) || getKeyDown(code);
    }

    /**
     * @param code the key code
     * @return true during the frame the user releases the key
     */
    public static boolean getKeyUp(KeyCode code) {
        return instance.getStatus(code).contains(Status.UP);
    }

    /**
     * @param code the key code
     * @return true during the frame the user starts pressing down the key
     */
    public static boolean getKeyDown(KeyCode code) {
        return instance.getStatus(code).contains(Status.DOWN);
    }

    /**
     * Get the status of a key.
     *
     * @param code the code of this key
     * @return the set of current applied status.
     */
    private EnumSet<Status> getStatus(KeyCode code) {
        EnumSet<Status> status = keysStatus.get(code);
        if (status == null) {
            status = Status.NONE.clone();
            keysStatus.put(code, status);
        }
        return status;
    }
}
