package com.pianoo.view;

import com.pianoo.controller.IController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class PianoFrame extends JPanel implements IPianoFrame, KeyListener, IMenuNavigationListener {

    private final JPanel pianoPanel;
    private IMenuNavigationListener menuNavigationListener;
    private final JComboBox<Integer> octaveSelector;
    private final int WHITE_KEYS_PER_OCTAVE = 7;
    private final int REFERENCE_OCTAVE = 4;
    private IController controller;
    private RecordButton recordButton;
    private TopPanel topPanel;

    private class NoteKey {
        final int note;
        final int octave;

        NoteKey(int note, int octave) {
            this.note = note;
            this.octave = octave;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NoteKey noteKey = (NoteKey) o;
            return note == noteKey.note && octave == noteKey.octave;
        }

        @Override
        public int hashCode() {
            return 31 * note + octave;
        }
    }

    private final Map<NoteKey, Boolean> activeKeys = new HashMap<>();

    public PianoFrame() {
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Sélecteur d'octaves à gauche
        Integer[] octaves = new Integer[]{2, 3, 4, 5, 6, 7};
        octaveSelector = new JComboBox<>(octaves);
        octaveSelector.setSelectedItem(4);

        // ===== Piano centré =====

        pianoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawPiano(g, (int) octaveSelector.getSelectedItem());
            }
        };
        pianoPanel.setFocusable(true);
        add(pianoPanel, BorderLayout.CENTER);

        octaveSelector.addActionListener(e -> {
            pianoPanel.repaint();
            pianoPanel.requestFocusInWindow();
        });

        setFocusable(true);
        requestFocusInWindow();

        pianoPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int selectedOctaves = getSelectedOctave();

                int[] noteAndOctave = getNoteAndOctaveFromMouseClick(x, y, selectedOctaves);
                int note = noteAndOctave[0];
                int octave = noteAndOctave[1];

                if (note != -1) {
                    controller.onKeyPressed(note, octave);
                    highlightKey(note, octave);
                    pianoPanel.repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int selectedOctaves = getSelectedOctave();

                int[] noteAndOctave = getNoteAndOctaveFromMouseClick(x, y, selectedOctaves);
                int note = noteAndOctave[0];
                int octave = noteAndOctave[1];

                if (note != -1) {
                    controller.onKeyReleased(note, octave);
                    resetKey(note, octave);
                    pianoPanel.repaint();
                }
            }
        });

        // Focus listener pour réinitialiser les touches si le panel perd le focus
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                System.out.println("PianoFrame: Focus lost. Resetting all key states.");
                resetAllKeyStates();
            }
        });
    }

    @Override
    public void setListener(IMenuNavigationListener listener) {
        this.menuNavigationListener = listener;
        if (this.controller != null && this.topPanel == null) {
            initializeTopPanel();
        }
    }

    @Override
    public void setController(final IController controller) {
        this.controller = controller;
        if (this.menuNavigationListener != null || this instanceof IMenuNavigationListener) {
            initializeTopPanel();
        }
    }

    private void initializeTopPanel() {
        IMenuNavigationListener actualListener = (this.menuNavigationListener != null) ? this.menuNavigationListener : this;
        this.topPanel = new TopPanel(this.controller, actualListener);
        this.recordButton = this.topPanel.getRecordButtonInstance();

        JPanel northPanelContainer = new JPanel(new BorderLayout());
        northPanelContainer.setOpaque(false);
        northPanelContainer.add(this.octaveSelector, BorderLayout.WEST);
        northPanelContainer.add(this.topPanel, BorderLayout.CENTER);

        add(northPanelContainer, BorderLayout.NORTH);
        revalidate();
        repaint();
    }

    public void updateRecordButtonState(boolean isRecording) {
        if (recordButton != null) {
            recordButton.setVisualRecordingState(isRecording);
        }
    }

    @Override
    public void onReturnMainMenu() {
        if (menuNavigationListener != null && menuNavigationListener != this) {
            menuNavigationListener.onReturnMainMenu();
        } else if (controller != null) {
            controller.showMainMenu();
        }
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    private void drawPiano(Graphics g, int octaves) {
        int totalWhiteKeys = WHITE_KEYS_PER_OCTAVE * octaves;
        int whiteKeyWidth = getWidth() / totalWhiteKeys;
        int whiteKeyHeight = getHeight();

        int firstOctave = REFERENCE_OCTAVE - octaves / 2;

        for (int o = 0; o < octaves; o++) {
            int currentOctave = firstOctave + o;

            for (int i = 0; i < WHITE_KEYS_PER_OCTAVE; i++) {
                int whiteKeyIndex = o * WHITE_KEYS_PER_OCTAVE + i;
                int[] whiteNotes = {0, 2, 4, 5, 7, 9, 11};
                int note = whiteNotes[i];

                NoteKey noteKey = new NoteKey(note, currentOctave);
                boolean isActive = activeKeys.getOrDefault(noteKey, false);

                if (isActive) {
                    g.setColor(new Color(200, 200, 200));
                } else {
                    g.setColor(Color.WHITE);
                }

                g.fillRect(whiteKeyIndex * whiteKeyWidth, 0, whiteKeyWidth, whiteKeyHeight);
                g.setColor(Color.BLACK);
                g.drawRect(whiteKeyIndex * whiteKeyWidth, 0, whiteKeyWidth, whiteKeyHeight);

                if (i == 0) {
                    g.setColor(Color.GRAY);
                    g.drawString("O:" + currentOctave, whiteKeyIndex * whiteKeyWidth + 3, whiteKeyHeight - 10);
                }
            }
        }

        int[] blackKeyPositions = {0, 1, 3, 4, 5};
        int[] blackNotes = {1, 3, 6, 8, 10};

        for (int o = 0; o < octaves; o++) {
            int currentOctave = firstOctave + o;

            for (int i = 0; i < blackKeyPositions.length; i++) {
                int note = blackNotes[i];
                int keyIndex = o * WHITE_KEYS_PER_OCTAVE + blackKeyPositions[i];
                int x = (keyIndex + 1) * whiteKeyWidth - (whiteKeyWidth / 4);
                int width = whiteKeyWidth / 2;
                int height = (int) (whiteKeyHeight * 0.6);

                NoteKey noteKey = new NoteKey(note, currentOctave);
                boolean isActive = activeKeys.getOrDefault(noteKey, false);

                if (isActive) {
                    g.setColor(new Color(80, 80, 80));
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(x, 0, width, height);
                g.setColor(Color.DARK_GRAY);
                g.drawRect(x, 0, width, height);
            }
        }
    }

    public void highlightKey(int note, int octave) {
        NoteKey noteKey = new NoteKey(note, octave);
        activeKeys.put(noteKey, true);
        pianoPanel.repaint();
    }

    public void resetKey(int note, int octave) {
        NoteKey noteKey = new NoteKey(note, octave);
        activeKeys.put(noteKey, false);
        pianoPanel.repaint();
    }

    // Méthode pour réinitialiser l'état visuel de toutes les touches
    public void resetAllKeyStates() {
        if (!activeKeys.isEmpty()) {
            boolean needsRepaint = false;
            for (Boolean isActive : activeKeys.values()) {
                if (isActive) {
                    needsRepaint = true;
                    break;
                }
            }
            // Efface la map pour que getOrDefault retourne false partout,
            // ou met explicitement toutes les valeurs à false.
            // Effacer est plus simple si la map ne sert qu'à ça.
            activeKeys.clear();
            if (needsRepaint) {
                System.out.println("PianoFrame: Resetting all key visuals and repainting.");
                pianoPanel.repaint();
            }
        }
    }

    private boolean isBlackKey(int note) {
        int noteValue = note % 12;
        return noteValue == 1 || noteValue == 3 || noteValue == 6 || noteValue == 8 || noteValue == 10;
    }

    @Override
    public void addKeyListenerToFrame(KeyListener listener) {
        this.addKeyListener(listener);
        pianoPanel.addKeyListener(listener);
    }

    @Override
    public void setKeyListener(final IController controller) {
        this.controller = controller;
    }

    @Override
    public int getSelectedOctave() {
        return (int) octaveSelector.getSelectedItem();
    }

    private int[] getNoteAndOctaveFromMouseClick(int x, int y, int totalOctaves) {
        int totalWhiteKeys = WHITE_KEYS_PER_OCTAVE * totalOctaves;
        int whiteKeyWidth = getWidth() / totalWhiteKeys;
        int whiteKeyHeight = getHeight();

        int clickedWhiteKeyIndex = x / whiteKeyWidth;
        int relativeOctave = clickedWhiteKeyIndex / WHITE_KEYS_PER_OCTAVE;
        int halfOctaves = totalOctaves / 2;
        int absoluteOctave = REFERENCE_OCTAVE - halfOctaves + relativeOctave;
        int posInOctave = clickedWhiteKeyIndex % WHITE_KEYS_PER_OCTAVE;
        int[] whiteNotes = {0, 2, 4, 5, 7, 9, 11};
        int[] blackKeyPositions = {0, 1, 3, 4, 5};
        int[] blackNotes = {1, 3, 6, 8, 10};

        for (int i = 0; i < blackKeyPositions.length; i++) {
            int octaveOffset = relativeOctave * WHITE_KEYS_PER_OCTAVE;
            int keyIndex = octaveOffset + blackKeyPositions[i];
            int blackX = (keyIndex + 1) * whiteKeyWidth - (whiteKeyWidth / 4);
            int blackWidth = whiteKeyWidth / 2;
            int blackHeight = (int) (whiteKeyHeight * 0.6);

            if (x >= blackX && x <= blackX + blackWidth && y <= blackHeight) {
                return new int[] {blackNotes[i], absoluteOctave};
            }
        }

        if (posInOctave >= 0 && posInOctave < whiteNotes.length) {
            return new int[] {whiteNotes[posInOctave], absoluteOctave};
        }

        return new int[] {-1, -1};
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}