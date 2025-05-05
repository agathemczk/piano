package com.pianoo.view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.net.URL;


public class VideoGamesFrame extends JPanel implements IVideoGamesFrame {
    private JPanel panel;
    private IMenuNavigationListener listener;
    private RoundCloseButton closeButton;


    private static final String[] GAME_IMAGES = {
            "BellaCiaoView.jpg",
            "Mario2View.png",
            "MarioView.png",
            "Pirate2View.jpg",
            "QueenView.png",
            "StarWarsView.png"
    };

    public VideoGamesFrame() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(0, 0, 150, 0));

        // Panneau supérieur avec la croix rouge
        /*JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        RoundCloseButton closeButton = new RoundCloseButton();
        closeButton.addActionListener(e -> listener.onQuitButtonSelected("Quit")); // Notifie via l'interface

        JPanel closeButtonWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        closeButtonWrapper.setOpaque(false);
        closeButtonWrapper.add(closeButton);

        topPanel.add(closeButtonWrapper, BorderLayout.EAST);
        add(topPanel);*/

        // Ajout des rangées de jeux
        add(createGameRow(0, 2)); // Ligne du haut
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(createGameRow(3, 5)); // Ligne du bas

    }

    private JPanel createGameRow(int start, int end) {
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

    private ImageIcon loadImage(String filename) {
        try {
            URL imageUrl = VideoGamesFrame.class.getResource("/imagesGame/" + filename);
            if (imageUrl != null) {
                return resizeIcon(new ImageIcon(imageUrl), 150, 150);
            } else {
                System.err.println("Image non trouvée: /imagesGame/" + filename);
            }
        } catch (Exception e) {
            System.err.println("Erreur chargement image: " + e.getMessage());
        }
        return createDefaultIcon(filename);
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        double ratio = Math.min(
                (double) width / icon.getIconWidth(),
                (double) height / icon.getIconHeight()
        );
        int newWidth = (int) (icon.getIconWidth() * ratio);
        int newHeight = (int) (icon.getIconHeight() * ratio);
        return new ImageIcon(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
    }

    private ImageIcon createDefaultIcon(String name) {
        BufferedImage img = new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();

        g.setColor(new Color(200, 200, 200));
        g.fillOval(0, 0, 150, 150);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        String text = name.replace(".png", "").replace(".jpg", "");
        int x = 75 - g.getFontMetrics().stringWidth(text) / 2;
        g.drawString(text, x, 80);

        g.dispose();
        return new ImageIcon(img);
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    /*@Override
    public void setQuitButtonSelectedListener(IQuitButtonSelectedListener listener) {
        this.listener = listener;
        closeButton.addActionListener(e -> listener.onQuitButtonSelected("Quit"));
    }*/

    private static class RoundButton extends JButton {
        public RoundButton(ImageIcon icon) {
            super(icon);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Shape circle = new Ellipse2D.Double(0, 0, getWidth(), getHeight());
            g2.setClip(circle);

            ImageIcon icon = (ImageIcon) getIcon();
            if (icon != null) {
                int x = (getWidth() - icon.getIconWidth()) / 2;
                int y = (getHeight() - icon.getIconHeight()) / 2;
                g2.drawImage(icon.getImage(), x, y, null);
            }

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