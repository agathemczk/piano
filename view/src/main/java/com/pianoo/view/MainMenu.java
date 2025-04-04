package com.pianoo.view;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;

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
                            drawDrums(g);
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
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("PIANO", 160, 30);

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
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("XYLOPHONE", 130, 30);

        g.setColor(Color.RED);
        g.fillRect(85, 60, 30, 200);


        g.setColor(Color.ORANGE);
        g.fillRect(125, 70, 30, 180);


        g.setColor(Color.YELLOW);
        g.fillRect(165, 80, 30, 160);


        g.setColor(Color.GREEN);
        g.fillRect(205, 90, 30, 140);


        g.setColor(Color.BLUE);
        g.fillRect(245, 100, 30, 120);


        g.setColor(new Color(128, 0, 128));
        g.fillRect(285, 110, 30, 100);
    }



    private void drawVideoGames(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("VIDEO GAMES", 120, 30);

        int pacmanX = 180;
        int pacmanY = 130;
        int pacmanRadius = 50;

        g.setColor(Color.YELLOW);
        g.fillArc(pacmanX - pacmanRadius, pacmanY - pacmanRadius, pacmanRadius * 3, pacmanRadius * 3, 35, 290);

        int pupilRadius = 10;
        int eyeX = pacmanX + 20;
        int eyeY = pacmanY - 30;
        g.setColor(Color.BLACK);
        g.fillOval(eyeX - pupilRadius, eyeY - pupilRadius, pupilRadius * 2, pupilRadius * 2);
    }

    private void drawOrgue(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("ORGAN", 160, 30);

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

    private void drawDrums(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("DRUMS", 160, 30);
        // Dessiner la base du tambour (rectangle)
        g.setColor(Color.RED);
        g.fillRect(100, 150, 200, 100); // Le corps du tambour (rectangle)

        g.setColor(Color.RED);
        g.fillOval(100, 240, 200, 20); // La surface inférieure du tambour

        // Dessiner la partie supérieure du tambour (cercle supérieur)
        g.setColor(Color.WHITE);
        g.fillOval(100, 140, 200, 20); // La surface supérieure du tambour


        // Dessiner les cordes du tambour
        g.setColor(Color.BLACK);
        g.drawLine(100, 150, 100, 250);
        g.drawLine(300, 150, 300, 250);
        g.drawLine(150, 158, 150, 258);
        g.drawLine(200, 160, 200, 260);
        g.drawLine(250, 158, 250, 258);


        g.setColor(new Color(139, 69, 19));

        // Première baguette inclinée (à gauche)
        g.fillRect(140, 100, 5, 120);
        g.fillOval(140 - 5, 100 - 5, 15, 15);

        // Deuxième baguette inclinée (à droite)
        g.fillRect(250, 100, 5, 120);
        g.fillOval(250 - 5, 100 - 5, 15, 15);
    }


    private void drawCat(Graphics g) {
        // Dessiner la tête du chat (un disque noir)
        g.setColor(Color.GRAY);
        g.fillOval(150, 100, 100, 100); // Tête du chat

        // Dessiner et faire pivoter l'oreille gauche
        int[] x1 = {160, 130, 180}; // Points originaux de l'oreille gauche
        int[] y1 = {90, 120, 120};
        drawRotatedEar(g, x1, y1, 70); // Rotation de 70° pour l'oreille gauche

        // Dessiner et faire pivoter l'oreille droite
        int[] x2 = {240, 260, 220}; // Points originaux de l'oreille droite
        int[] y2 = {93, 123, 123};
        drawRotatedEar(g, x2, y2, -70); // Rotation de 70° pour l'oreille droite

        // Yeux du chat (deux cercles blancs)
        g.setColor(Color.WHITE);
        g.fillOval(160, 100, 30, 30); // Œil gauche
        g.fillOval(210, 100, 30, 30); // Œil droit

        // Pupilles du chat (petits cercles noirs)
        g.setColor(Color.BLACK);
        g.fillOval(170, 110, 10, 10); // Pupille gauche
        g.fillOval(220, 110, 10, 10); // Pupille droite

        // Nez du chat (petit triangle rose)
        g.setColor(Color.PINK);
        int[] x3 = {200, 210, 190};
        int[] y3 = {150, 170, 170};
        g.fillPolygon(x3, y3, 3); // Nez

        // Bouche du chat (courbe)
        g.setColor(Color.BLACK);
        g.drawLine(200, 170, 200, 180); // Ligne centrale de la bouche
        g.drawArc(195, 180, 10, 10, 0, -180); // Sourire gauche
        g.drawArc(200, 180, 10, 10, 0, -180); // Sourire droit

        // Moustaches du chat (lignes)
        g.drawLine(140, 160, 100, 150); // Moustache gauche haut
        g.drawLine(140, 170, 100, 170); // Moustache gauche bas
        g.drawLine(260, 160, 300, 150); // Moustache droite haut
        g.drawLine(260, 170, 300, 170); // Moustache droite bas
    }

    // Fonction pour dessiner une oreille avec rotation autour de son centre
    private void drawRotatedEar(Graphics g, int[] x, int[] y, double angleDegrees) {
        double angle = Math.toRadians(angleDegrees); // Convertir l'angle en radians

        // Calculer le centre du triangle (milieu de l'oreille)
        int centerX = (x[0] + x[1] + x[2]) / 3;
        int centerY = (y[0] + y[1] + y[2]) / 3;

        // Appliquer la transformation de rotation autour du centre du triangle
        for (int i = 0; i < 3; i++) {
            int tempX = x[i] - centerX;  // Déplacer par rapport au centre
            int tempY = y[i] - centerY;

            // Appliquer la rotation
            x[i] = (int) (centerX + tempX * Math.cos(angle) - tempY * Math.sin(angle));
            y[i] = (int) (centerY + tempX * Math.sin(angle) + tempY * Math.cos(angle));
        }

        // Dessiner l'oreille après rotation
        g.setColor(Color.GRAY);
        g.fillPolygon(x, y, 3); // Dessiner le triangle tourné
    }



}
