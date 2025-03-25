package com.pianoo.view;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame implements IMainMenu {
    public MainMenu() {
        setTitle("MusicaLau - Menu Principal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 3, 20, 20)); // Grille 2x3 pour 6 cases
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int i = 0; i < 6; i++) {
            int index = i;
            JPanel instrumentPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    switch (index) {
                        case 0:
                            drawPiano(g);
                            break;
                        case 1:
                            drawXylophone(g);
                            break;
                        case 2:
                            drawVideoGames(g);
                            break;
                        case 3:
                            drawOrgue(g);
                            break;
                        case 4:
                            drawKazoo(g);
                            break;
                        case 5:
                            drawCat(g);
                            break;
                        default:
                            // draw
                            break;
                    }
                }
            };
            instrumentPanel.setBackground(Color.LIGHT_GRAY);
            instrumentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            instrumentPanel.setPreferredSize(new Dimension(200, 150));

            panel.add(instrumentPanel);
        }

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
    }

    private void drawPiano(Graphics g) {
        int pianoWidth = 7 * 20;
        int pianoHeight = 50;

        int panelWidth = 200;
        int panelHeight = 150;

        int x = (panelWidth / 2) - (pianoWidth / 3);
        int y = (panelHeight / 2) + (pianoWidth / 4);

        g.setColor(Color.WHITE);
        g.fillRect(x, y, pianoWidth, pianoHeight);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, pianoWidth, pianoHeight);

        int keyWidth = pianoWidth / 7;
        for (int j = 0; j < 7; j++) {
            if (j != 2 && j != 6) {
                int blackKeyX = x + (j + 1) * keyWidth - keyWidth / 4;
                g.setColor(Color.BLACK);
                g.fillRect(blackKeyX, y, keyWidth / 2, pianoHeight / 2);
            }
        }

        g.setColor(Color.BLACK);
        for (int i = 0; i < 7; i++) {
            int whiteKeyX = x + i * keyWidth;
            g.drawRect(whiteKeyX, y, keyWidth, pianoHeight);
        }
    }

    private void drawXylophone(Graphics g) {
        int numBars = 7;
        int barWidth = 20;
        int initialHeight = 30;
        int spacing = 5;

        int panelWidth = 200;
        int panelHeight = 150;

        int x = (panelWidth / 2) - (numBars * (barWidth + spacing) - spacing) / 2;
        int y = (panelHeight / 2) + 20;

        // Couleurs des barres
        int[] colors = {Color.RED.getRGB(), Color.ORANGE.getRGB(), Color.YELLOW.getRGB(), Color.GREEN.getRGB(),
                Color.CYAN.getRGB(), Color.BLUE.getRGB(), Color.MAGENTA.getRGB()};


        for (int i = 0; i < numBars; i++) {
            int currentHeight = initialHeight - (i * 3);
            g.setColor(new Color(colors[i]));
            g.fillRect(x + i * (barWidth + spacing), y - currentHeight, barWidth, currentHeight);
        }

        // Dessin de la moitié basse (symétrie axiale)
        int ybis = y + initialHeight + 5; // Décalage vertical ajusté pour que la moitié basse soit collée à la moitié haute

        for (int i = 0; i < numBars; i++) {
            int currentHeight = initialHeight - (i * 3);
            g.setColor(new Color(colors[i]));
            g.fillRect(x + i * (barWidth + spacing), ybis + currentHeight, barWidth, currentHeight);
        }
    }

    private void drawVideoGames(Graphics g) {
        int pacmanX = 110;
        int pacmanY = 135;
        int pacmanRadius = 50;

        g.setColor(Color.YELLOW);
        g.fillArc(pacmanX - pacmanRadius, pacmanY - pacmanRadius, pacmanRadius * 2, pacmanRadius * 2, 35, 290);

        int pupilRadius = 5;
        int eyeX = pacmanX + 20;
        int eyeY = pacmanY - 30;
        g.setColor(Color.BLACK);
        g.fillOval(eyeX - pupilRadius, eyeY - pupilRadius, pupilRadius * 2, pupilRadius * 2);
    }

    private void drawOrgue(Graphics g) {
        g.setColor(new Color(200, 200, 255));
        g.fillRect(100, 150, 200, 250);

        int[] xPoints = {100, 200, 300};
        int[] yPoints = {150, 50, 150};
        g.setColor(new Color(139, 69, 19));
        g.fillPolygon(xPoints, yPoints, 3);

        g.setColor(new Color(139, 69, 19));
        g.fillRect(175, 280, 50, 120);

        g.setColor(Color.WHITE);
        g.fillRect(125, 180, 40, 40);
        g.fillRect(235, 180, 40, 40);

        g.setColor(Color.WHITE);
        g.fillRect(190, 50, 20, 80);
        g.fillRect(160, 70, 80, 20);
    }

    private void drawKazoo(Graphics g){

    }

    private void drawCat(Graphics g){

    }






}
