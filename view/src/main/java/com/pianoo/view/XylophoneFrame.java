package com.pianoo.view;

import com.pianoo.controller.IController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.Timer;

public class XylophoneFrame extends JPanel implements IXylophoneFrame, KeyListener, IMenuNavigationListener {

    private static final String[] NOTES = {"C", "D", "E", "F", "G", "A", "B"};
    private static final Color[] COLORS = {
            Color.RED, Color.ORANGE, Color.YELLOW,
            Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA
    };

    private final JPanel xylophonePanel;
    private IMenuNavigationListener menuNavigationListener;
    private IController controller;
    private final List<JButton> noteButtons = new ArrayList<>();
    private RecordButton recordButton;
    private TopPanel topPanel;

    public XylophoneFrame() {
        setLayout(new BorderLayout());
        setFocusable(true);
        requestFocusInWindow();

        // Ajouter cette ligne pour activer la détection des touches
        addKeyListener(this);
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                requestFocusInWindow();
            }
        });

        xylophonePanel = createXylophonePanel();
        add(xylophonePanel, BorderLayout.CENTER);
    }

    private JPanel createXylophonePanel() {
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        JPanel xylophonePanel = new JPanel();
        xylophonePanel.setLayout(new BoxLayout(xylophonePanel, BoxLayout.X_AXIS));
        xylophonePanel.setOpaque(false);

        int baseHeight = 250;
        int width = 90;

        // Création des touches du xylophone
        for (int i = 0; i < NOTES.length; i++) {
            JButton noteButton = new JButton(NOTES[i]);
            noteButton.setBackground(COLORS[i]);
            noteButton.setOpaque(true);
            noteButton.setBorderPainted(false);
            noteButton.setFont(new Font("Arial", Font.BOLD, 20));

            // Ajuster la hauteur pour simuler les barres du xylophone
            int buttonHeight = baseHeight - (i * 15);
            noteButton.setPreferredSize(new Dimension(width, buttonHeight));
            noteButton.setMaximumSize(new Dimension(width, buttonHeight));
            noteButton.setMinimumSize(new Dimension(width, buttonHeight));

            final int noteIndex = i;
            noteButton.addActionListener(e -> playNote(noteIndex));

            noteButtons.add(noteButton);

            JPanel wrapper = new JPanel();
            wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
            wrapper.setOpaque(false);
            wrapper.add(Box.createVerticalGlue());
            wrapper.add(noteButton);
            wrapper.add(Box.createVerticalGlue());

            xylophonePanel.add(wrapper);

            if (i < NOTES.length - 1) {
                xylophonePanel.add(Box.createRigidArea(new Dimension(5, 0)));
            }
        }

        centerPanel.add(xylophonePanel);
        return centerPanel;
    }

    private void playNote(int noteIndex) {
        if (controller != null) {
            controller.onNotePlayed(NOTES[noteIndex]);
        }

        highlightNote(noteIndex);

        Timer timer = new Timer(150, (actionEvent) -> {
            resetNote(noteIndex);
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    @Override
    public void addKeyListenerToFrame(KeyListener listener) {
        this.addKeyListener(listener);
        xylophonePanel.addKeyListener(listener);
    }

    @Override
    public void setKeyListener(final IController controller) {
        this.controller = controller;
    }

    @Override
    public void setController(final IController controller) {
        this.controller = controller;
        if (this.menuNavigationListener != null) {
            this.topPanel = new TopPanel(this.controller, this.menuNavigationListener);
            add(this.topPanel, BorderLayout.NORTH);
            this.recordButton = this.topPanel.getRecordButtonInstance();
            revalidate();
            repaint();
        } else if (this instanceof IMenuNavigationListener) {
            this.topPanel = new TopPanel(this.controller, this);
            add(this.topPanel, BorderLayout.NORTH);
            this.recordButton = this.topPanel.getRecordButtonInstance();
            revalidate();
            repaint();
        } else {
            System.err.println("IMenuNavigationListener not set for XylophoneFrame before controller. TopPanel not created.");
        }
    }

    @Override
    public void highlightNote(int note) {
        if (note >= 0 && note < noteButtons.size()) {
            noteButtons.get(note).setBackground(noteButtons.get(note).getBackground().darker());
        }
    }

    @Override
    public void resetNote(int note) {
        if (note >= 0 && note < noteButtons.size()) {
            noteButtons.get(note).setBackground(COLORS[note]);
        }
    }

    public void setListener(IMenuNavigationListener listener) {
        this.menuNavigationListener = listener;
        if (this.controller != null && this.topPanel == null) {
            this.topPanel = new TopPanel(this.controller, this.menuNavigationListener);
            add(this.topPanel, BorderLayout.NORTH);
            this.recordButton = this.topPanel.getRecordButtonInstance();
            revalidate();
            repaint();
        }
    }

    @Override
    public void onReturnMainMenu() {
        if (menuNavigationListener != null && menuNavigationListener != this) {
            menuNavigationListener.onReturnMainMenu();
        } else {
            System.out.println("XylophoneFrame: onReturnMainMenu called.");
            if (controller != null) {
                // controller.showMainMenu(); // This line will be addressed later
            }
        }
    }

    public void updateRecordButtonState(boolean isRecording) {
        if (recordButton != null) {
            recordButton.setVisualRecordingState(isRecording);
        }
    }

    @Override
    public void keyTyped(final KeyEvent e) {
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                // highlightNote(0); // Moved to playNote
                playNote(0);
                break;
            case KeyEvent.VK_S:
                // highlightNote(1); // Moved to playNote
                playNote(1);
                break;
            case KeyEvent.VK_D:
                // highlightNote(2); // Moved to playNote
                playNote(2);
                break;
            case KeyEvent.VK_F:
                // highlightNote(3); // Moved to playNote
                playNote(3);
                break;
            case KeyEvent.VK_G:
                // highlightNote(4); // Moved to playNote
                playNote(4);
                break;
            case KeyEvent.VK_H:
                // highlightNote(5); // Moved to playNote
                playNote(5);
                break;
            case KeyEvent.VK_J:
                // highlightNote(6); // Moved to playNote
                playNote(6);
                break;
        }
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        // Rétablir l'apparence normale de la touche
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A: resetNote(0); break;
            case KeyEvent.VK_S: resetNote(1); break;
            case KeyEvent.VK_D: resetNote(2); break;
            case KeyEvent.VK_F: resetNote(3); break;
            case KeyEvent.VK_G: resetNote(4); break;
            case KeyEvent.VK_H: resetNote(5); break;
            case KeyEvent.VK_J: resetNote(6); break;
        }
    }

    @Override
    public String[] getNotes() {
        return NOTES;
    }

}