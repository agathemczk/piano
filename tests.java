import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class tests {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PianoFrame();
        });
    }
}

class PianoFrame extends JFrame {
    private Synthesizer synthesizer;
    private MidiChannel channel;
    private final int[] notes = {60, 62, 64, 65, 67, 69, 71, 72}; // Do, Ré, Mi, Fa, Sol, La, Si, Do (MIDI)
    private final String[] labels = {"Do", "Ré", "Mi", "Fa", "Sol", "La", "Si", "Do"};
    private final char[] keys = {'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K'}; // Correspondance des touches clavier

    public PianoFrame() {
        // Initialisation de la fenêtre
        setTitle("Simulateur de Piano avec Clavier");
        setSize(800, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 8));

        // Initialisation du synthétiseur MIDI
        try {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            channel = synthesizer.getChannels()[0];
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }

        // Créer des boutons pour afficher les notes
        for (int i = 0; i < notes.length; i++) {
            JButton button = new JButton(labels[i] + " (" + keys[i] + ")");
            button.setFont(new Font("Arial", Font.BOLD, 16));
            final int note = notes[i];
            button.addActionListener(e -> playPiano(note));
            add(button);
        }

        // Ajout d'un écouteur clavier
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                char key = Character.toUpperCase(e.getKeyChar());
                for (int i = 0; i < keys.length; i++) {
                    if (key == keys[i]) {
                        playPiano(notes[i]);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        // Activer le focus pour capturer les touches du clavier
        setFocusable(true);
        setVisible(true);
    }

    // Méthode pour jouer un son de piano
    private void playPiano(int note) {
        if (channel != null) {
            channel.noteOn(note, 600); // Note activée avec une vélocité fixe
            try {
                Thread.sleep(200); // Durée du son
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            channel.noteOff(note); // Note désactivée
        }
    }

    // Fermer le synthétiseur lorsque la fenêtre est fermée
    @Override
    public void dispose() {
        super.dispose();
        if (synthesizer != null) {
            synthesizer.close();
        }
    }
}
