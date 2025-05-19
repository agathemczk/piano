package com.pianoo.view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;
import java.util.stream.Collectors;

public class ScoreChooserView extends JDialog implements IScoreChooserView {

    private JList<String> scoreList;
    private JButton playButton;
    private JButton cancelButton;
    // private String selectedScorePath = null; // Not strictly needed if we only use File
    private File selectedScoreFile = null; // To store the selected File object
    private Frame ownerFrame; // Store owner for re-display or other uses if needed

    private static final String PARTITIONS_DIR_PATH = "consignes/partitions"; // Relative to project root

    public ScoreChooserView(Frame owner) {
        super(owner, "Choisir une Partition", true); // true for modal dialog
        this.ownerFrame = owner;
        initComponents();
        loadPartitions();
        pack();
        setLocationRelativeTo(owner); // Center on owner frame
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Dispose on X or Escape
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10)); // Add some padding

        // Panel for the list
        JPanel listPanel = new JPanel(new BorderLayout(5,5));
        listPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding for the list panel
        JLabel titleLabel = new JLabel("Sélectionnez une partition à jouer :");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        listPanel.add(titleLabel, BorderLayout.NORTH);

        scoreList = new JList<>();
        scoreList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(scoreList);
        scrollPane.setPreferredSize(new Dimension(350, 200)); // Give the list a preferred size
        listPanel.add(scrollPane, BorderLayout.CENTER);
        add(listPanel, BorderLayout.CENTER);

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); // Padding for button panel

        playButton = new JButton("Jouer");
        playButton.addActionListener(e -> onPlay());

        cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(e -> onCancel());

        buttonPanel.add(playButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Disable play button until a selection is made
        playButton.setEnabled(false);
        scoreList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                playButton.setEnabled(scoreList.getSelectedIndex() != -1);
            }
        });
    }

    private void loadPartitions() {
        File partitionsDir = new File(PARTITIONS_DIR_PATH);
        if (partitionsDir.exists() && partitionsDir.isDirectory()) {
            File[] files = partitionsDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
            if (files != null) {
                Arrays.sort(files, Comparator.comparing(File::getName));
                Vector<String> fileNames = Arrays.stream(files)
                        .map(File::getName)
                        .collect(Collectors.toCollection(Vector::new));
                scoreList.setListData(fileNames);
            } else {
                scoreList.setListData(new Vector<>(java.util.List.of("Erreur: Impossible de lister les fichiers.")));
                playButton.setEnabled(false); // Disable play if error
            }
        } else {
            scoreList.setListData(new Vector<>(java.util.List.of("Répertoire des partitions non trouvé: " + PARTITIONS_DIR_PATH)));
            playButton.setEnabled(false); // Disable play if error
            System.err.println("Partition directory not found: " + partitionsDir.getAbsolutePath());
        }
    }

    private void onPlay() {
        String selectedFileName = scoreList.getSelectedValue();
        if (selectedFileName != null) {
            File dir = new File(PARTITIONS_DIR_PATH);
            this.selectedScoreFile = new File(dir, selectedFileName);
            if (!this.selectedScoreFile.exists() || !this.selectedScoreFile.isFile()) {
                JOptionPane.showMessageDialog(this,
                        "Le fichier de partition sélectionné n'existe pas ou n'est pas un fichier valide : " + this.selectedScoreFile.getAbsolutePath(),
                        "Erreur Fichier", JOptionPane.ERROR_MESSAGE);
                this.selectedScoreFile = null;
                return;
            }
        }
        dispose();
    }

    private void onCancel() {
        this.selectedScoreFile = null;
        dispose();
    }

    @Override
    public void displayView() {
        this.selectedScoreFile = null;
        loadPartitions();
        setLocationRelativeTo(this.ownerFrame);
        setVisible(true);
    }

    @Override
    public File getSelectedScoreFile() {
        return this.selectedScoreFile;
    }

    @Override
    public void setOwner(Frame owner) {
        this.ownerFrame = owner;
    }

    @Override
    public void closeView() {
        dispose();
    }
}