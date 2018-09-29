package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class keeps track of the saving history for each wish across each executed command.
 */
public class VersionedWishTransaction extends WishTransaction implements VersionedModel {

    /**
     * Stores the log of wish histories for each state.
     */
    private final List<WishTransaction> wishStateList;

    /**
     * Index to the current referenced state.
     */
    private int referencePointer;


    public VersionedWishTransaction() {
        wishStateList = new ArrayList<>();
        referencePointer = -1;
    }

    public VersionedWishTransaction(WishTransaction wishTransaction) {
        super(wishTransaction);
        wishStateList = new ArrayList<>();
        wishStateList.add(wishTransaction);
        referencePointer = 0;
    }

    @Override
    public void commit() {
        removeStatesAfterCurrentPointer();
        wishStateList.add(this);
        referencePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        wishStateList.subList(referencePointer + 1, wishStateList.size()).clear();
    }

    @Override
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        referencePointer--;
        resetData(wishStateList.get(referencePointer));
    }

    @Override
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        referencePointer++;
        resetData(wishStateList.get(referencePointer));
    }

    /**
     * Returns true if {@code undo()} has address book states to undo.
     */
    public boolean canUndo() {
        return referencePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has address book states to redo.
     */
    public boolean canRedo() {
        return referencePointer < wishStateList.size() - 1;
    }

    public List<WishTransaction> getWishStateList() {
        return wishStateList;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of wishState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of wishState list, unable to redo.");
        }
    }
}
