package com.pianoo.view;

import java.io.File;
import java.awt.Frame;

public interface IScoreChooserView {
    /**
     * Makes the score chooser dialog visible to the user.
     * This call might block until the user makes a selection or cancels the dialog.
     */
    void displayView();

    /**
     * Returns the File object 피부of the score selected by the user.
     * Should be called after displayView() has completed (i.e., the dialog has been closed).
     *
     * @return The selected File, or null if no file was selected or the dialog was cancelled.
     */
    File getSelectedScoreFile();

    /**
     * Sets the owner frame for this dialog.
     * This is important for proper dialog behavior (e.g., modality, positioning).
     * @param owner The parent Frame.
     */
    // void setOwner(Frame owner); 
    void setOwner(Frame owner); // The constructor will handle setting the owner for now.

    /**
     * Closes/disposes the dialog window.
     */
    void closeView();
}
