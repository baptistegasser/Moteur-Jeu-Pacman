package fr.univ.engine.io;

import fr.univ.engine.render.JFXApp;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EnumSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Engine charged of inputs and outputs.
 */
public class IOEngine implements KeyEventHandler {
    /**
     * Store the {@link KeyCode} and their current datas.
     */
    private final ConcurrentHashMap<KeyCode, KeyData> keys;

    /**
     * Queue of {@link KeyBoardEvent} to be treated.
     */
    private final ConcurrentLinkedDeque<KeyBoardEvent> queue;

    public IOEngine() {
        // Init the map size. Prevent resizing the map's bucket pool too soon.
        keys = new ConcurrentHashMap<>(40);
        queue = new ConcurrentLinkedDeque<>();
    }

    /**
     * Method in charge of linking the engine to the app.
     */
    public void start() {
        JFXApp.setKeyEventHandler(this);
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

        // Run every actions
        keys.forEach((keyCode, keyData) -> {
            if (keyData.statuses().contains(Status.DOWN)) {
                keyData.onPressed().forEach(Runnable::run);
            }
            if (keyData.statuses().contains(Status.UP)) {
                keyData.onReleased().forEach(Runnable::run);
            }
        });
    }

    /**
     * Register an action to commit when a key is pressed
     *
     * @param code the target key
     * @param r the action to run
     */
    public void onKeyPressed(KeyCode code, Runnable r) {
        KeyData data = keys.getOrDefault(code, new KeyData());
        data.onPressed().add(r);
        keys.put(code, data);
    }

    /**
     * Register an action to commit when a key is released
     *
     * @param code the target key
     * @param r the action to run
     */
    public void onKeyReleased(KeyCode code, Runnable r) {
        KeyData data = keys.getOrDefault(code, new KeyData());
        data.onReleased().add(r);
        keys.put(code, data);
    }

    /**
     * Update the status of key that are linked to a specific frame.
     * ie: remove the UP status as releasing a key last one frame only.
     */
    private void updateFrameStatus() {
        for (KeyData data : keys.values()) {
            EnumSet<Status> status = data.statuses();
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
        KeyData data = keys.getOrDefault(event.code, new KeyData());
        EnumSet<Status> status = data.statuses();

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
    public void notifyKeyPressed(KeyEvent keyEvent) {
        queue.add(new KeyBoardEvent(keyEvent.getCode(), Status.DOWN));
    }

    @Override
    public void notifyKeyReleased(KeyEvent keyEvent) {
        queue.add(new KeyBoardEvent(keyEvent.getCode(), Status.UP));
    }
}
