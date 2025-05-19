package com.pianoo.view;

import com.pianoo.controller.IController;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class OrganFrame extends InstrumentFrame implements IInstrumentFrame, KeyListener {

    private final int WHITE_KEYS_PER_OCTAVE = 7;
    private final int OCTAVE_COUNT = 5;
    private final int TOTAL_WHITE_KEYS = WHITE_KEYS_PER_OCTAVE * OCTAVE_COUNT;

    private final String[] WHITE_KEY_NAMES = {"C", "D", "E", "F", "G", "A", "B"};

    private final List<PianoKey> keys = new ArrayList<>();
    private Integer currentPlayingNote = null;

    public OrganFrame() {
        super();
        setFocusable(true);
        addKeyListener(this);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMousePress(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                handleMouseRelease();
            }
        });
    }

    @Override
    public void setKeyListener(IController controller) {
        this.controller = controller;
    }

    private void handleMousePress(MouseEvent e) {
        for (PianoKey key : keys) {
            if (key.contains(e.getPoint())) {
                boolean isUpperKeyboard = e.getY() < getHeight() / 2;
                String noteName = key.note;

                if (controller != null) {
                    int baseMidiNote = controller.getMidiNoteFromKeyName(noteName);
                    int midiNote = controller.adjustMidiNoteForKeyboard(baseMidiNote, isUpperKeyboard);

                    if (midiNote != -1) {
                        controller.onOrganKeyPressed(midiNote);
                        currentPlayingNote = midiNote;
                        repaint();

                        System.out.println(noteName + " (MIDI: " + midiNote +
                                ") " + (isUpperKeyboard ? "supérieur" : "inférieur"));
                    }
                }
                break;
            }
        }
    }

    private void handleMouseRelease() {
        if (currentPlayingNote != null && controller != null) {
            controller.onOrganKeyReleased(currentPlayingNote);
            currentPlayingNote = null;
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (controller != null) {
            int midiNote = controller.getMidiNoteForKeyCode(e.getKeyCode());
            if (midiNote != -1) {
                controller.onOrganKeyPressed(midiNote);
                repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (controller != null) {
            int midiNote = controller.getMidiNoteForKeyCode(e.getKeyCode());
            if (midiNote != -1) {
                controller.onOrganKeyReleased(midiNote);
                repaint();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        keys.clear();

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        int margin = 20;
        int buttonHeight = 40;
        int availableHeight = panelHeight - buttonHeight - (3 * margin);

        int availableWidth = panelWidth - 2 * margin;
        int keyWidth = availableWidth / TOTAL_WHITE_KEYS;
        int keyHeight = availableHeight / 2;
        int blackKeyWidth = (int) (keyWidth * 0.66);
        int blackKeyHeight = (int) (keyHeight * 0.66);

        int y1 = buttonHeight + margin;
        drawKeyboard(g, margin, y1, keyWidth, keyHeight, blackKeyWidth, blackKeyHeight);

        int y2 = y1 + keyHeight + margin;
        drawKeyboard(g, margin, y2, keyWidth, keyHeight, blackKeyWidth, blackKeyHeight);
    }

    private void drawKeyboard(Graphics g, int xOffset, int yOffset, int keyWidth, int keyHeight, int blackKeyWidth, int blackKeyHeight) {
        int whiteKeyCount = 0;
        for (int octave = 0; octave < OCTAVE_COUNT; octave++) {
            for (int i = 0; i < WHITE_KEYS_PER_OCTAVE; i++) {
                int x = xOffset + whiteKeyCount * keyWidth;
                Rectangle rect = new Rectangle(x, yOffset, keyWidth, keyHeight);
                String note = WHITE_KEY_NAMES[i] + octave;
                PianoKey pianoKey = new PianoKey(rect, false, note);
                keys.add(pianoKey);

                boolean isUpperKey = yOffset < getHeight()/2;
                boolean isActive = false;

                if (controller != null) {
                    int baseMidiNote = controller.getMidiNoteFromKeyName(note);
                    int midiNote = controller.adjustMidiNoteForKeyboard(baseMidiNote, isUpperKey);
                    isActive = controller.isNoteActive(midiNote);
                }

                if (isActive) {
                    g.setColor(new Color(173, 216, 230));
                } else {
                    g.setColor(Color.WHITE);
                }

                g.fillRect(rect.x, rect.y, rect.width, rect.height);
                g.setColor(Color.BLACK);
                g.drawRect(rect.x, rect.y, rect.width, rect.height);

                g.setFont(new Font("Arial", Font.PLAIN, 10));
                g.drawString(note, rect.x + keyWidth / 3, rect.y + keyHeight - 10);

                whiteKeyCount++;
            }
        }

        whiteKeyCount = 0;
        for (int octave = 0; octave < OCTAVE_COUNT; octave++) {
            for (int i = 0; i < WHITE_KEYS_PER_OCTAVE; i++) {
                boolean hasBlackKey = i == 0 || i == 1 || i == 3 || i == 4 || i == 5;

                if (hasBlackKey && whiteKeyCount < TOTAL_WHITE_KEYS - 1) {
                    int x = xOffset + whiteKeyCount * keyWidth + (keyWidth - blackKeyWidth / 2);
                    Rectangle rect = new Rectangle(x, yOffset, blackKeyWidth, blackKeyHeight);

                    String noteName;
                    switch (i) {
                        case 0:
                            noteName = "C#" + octave;
                            break;
                        case 1:
                            noteName = "D#" + octave;
                            break;
                        case 3:
                            noteName = "F#" + octave;
                            break;
                        case 4:
                            noteName = "G#" + octave;
                            break;
                        case 5:
                            noteName = "A#" + octave;
                            break;
                        default:
                            noteName = "X#" + octave;
                            break;
                    }

                    PianoKey pianoKey = new PianoKey(rect, true, noteName);
                    keys.add(0, pianoKey);

                    boolean isUpperKey = yOffset < getHeight()/2;
                    boolean isActive = false;

                    if (controller != null) {
                        int baseMidiNote = controller.getMidiNoteFromKeyName(noteName);
                        int midiNote = controller.adjustMidiNoteForKeyboard(baseMidiNote, isUpperKey);
                        isActive = controller.isNoteActive(midiNote);
                    }

                    if (isActive) {
                        g.setColor(new Color(100, 100, 180));
                    } else {
                        g.setColor(Color.BLACK);
                    }

                    g.fillRect(rect.x, rect.y, rect.width, rect.height);
                }

                whiteKeyCount++;
            }
        }
    }

    private static class PianoKey {
        Rectangle bounds;
        boolean isBlack;
        String note;

        public PianoKey(Rectangle bounds, boolean isBlack, String note) {
            this.bounds = bounds;
            this.isBlack = isBlack;
            this.note = note;
        }

        public boolean contains(Point p) {
            return bounds.contains(p);
        }
    }
}