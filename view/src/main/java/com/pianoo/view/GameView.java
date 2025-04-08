package com.pianoo.view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.net.URL;


public class GameView {
    // Noms des fichiers images (correspondant à votre structure)
    private static final String[] GAME_IMAGES = {
            "BellaCiaoView.jpg",
            "Mario2View.png",
            "MarioView.png",
            "Pirate2View.jpg",
            "QueenView.png",
            "StarWarsView.png"
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Création de la fenêtre
            JFrame frame = new JFrame("Jeux Musicaux");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 500);

            // Panel principal
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBackground(new Color(240, 240, 240));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

            // Ajout des rangées de jeux
            mainPanel.add(createGameRow(0, 2));  // Ligne du haut
            mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
            mainPanel.add(createGameRow(3, 5));  // Ligne du bas

            frame.add(mainPanel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static JPanel createGameRow(int start, int end) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        row.setOpaque(false);

        for (int i = start; i <= end; i++) {
            ImageIcon icon = loadImage(GAME_IMAGES[i]);
            RoundButton button = new RoundButton(icon);
            button.setPreferredSize(new Dimension(150, 150));
            row.add(button);
        }
        return row;
    }

    private static ImageIcon loadImage(String filename) {
        try {
            // Chemin correct selon votre structure
            URL imageUrl = GameView.class.getResource("/imagesGame/" + filename);
            if (imageUrl != null) {
                System.out.println("Image trouvée: " + imageUrl);// Debug

                ImageIcon icon = new ImageIcon(imageUrl);

                return resizeIcon(icon, 150, 150);
            } else {
                System.err.println("Image non trouvée: /imageGame/" + filename);
            }
        } catch (Exception e) {
            System.err.println("Erreur chargement image: " + e.getMessage());
        }
        return createDefaultIcon(filename);

    }

    private static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        double ratio = Math.min(
                (double)width / icon.getIconWidth(),
                (double)height / icon.getIconHeight()
        );
        int newWidth = (int)(icon.getIconWidth() * ratio);
        int newHeight = (int)(icon.getIconHeight() * ratio);
        return new ImageIcon(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
    }

    private static ImageIcon createDefaultIcon(String name) {
        BufferedImage img = new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();

        // Fond rond
        g.setColor(new Color(200, 200, 200));
        g.fillOval(0, 0, 150, 150);

        // Texte
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        String text = name.replace(".png", "").replace(".jpg", "");
        int x = 75 - g.getFontMetrics().stringWidth(text)/2;
        g.drawString(text, x, 80);

        g.dispose();
        return new ImageIcon(img);
    }

    static class RoundButton extends JButton {
        public RoundButton(ImageIcon icon) {
            super(icon);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Masque rond
            Shape circle = new Ellipse2D.Double(0, 0, getWidth(), getHeight());
            g2.setClip(circle);

            // Image centrée
            ImageIcon icon = (ImageIcon)getIcon();
            if (icon != null) {
                int x = (getWidth() - icon.getIconWidth())/2;
                int y = (getHeight() - icon.getIconHeight())/2;
                g2.drawImage(icon.getImage(), x, y, null);
            }

            // Effet survol
            if (getModel().isRollover()) {
                g2.setColor(new Color(255, 255, 255, 50));
                g2.fill(circle);
            }

            g2.dispose();
        }

        @Override
        public boolean contains(int x, int y) {
            return new Ellipse2D.Double(0, 0, getWidth(), getHeight()).contains(x, y);
        }
    }
}