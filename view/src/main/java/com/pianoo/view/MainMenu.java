package com.pianoo.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu extends JFrame implements IMainMenu {
    private IOnChoiceSelectedListener listener;
    private JPanel mainPanel;

    public MainMenu() {
        setTitle("MusicaLau - Menu Principal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout()); // Assurez-vous d'utiliser BorderLayout


        // Initialisation de mainPanel
        this.mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel); // Définit mainPanel comme le conteneur principal


        // ===== Panneau supérieur avec la croix rouge =====
        /*JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false); // Transparent pour ne pas masquer le fond
        RoundCloseButton closeButton = new RoundCloseButton();
        closeButton.addActionListener(e -> System.exit(0));
        topPanel.add(closeButton);
        add(topPanel, BorderLayout.NORTH); // Ajout en haut de la fenêtre*/

        // ===== Panneau principal =====
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 3, 20, 20)); // Grille 2x3 pour 6 cases
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] instruments = {"Piano", "Xylophone", "VideoGames", "Organ", "Drums", "Cat"};


        for (int i = 0; i < instruments.length; i++) {
            int index = i;
            String instrumentName = instruments[i];

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
                            break;
                    }
                }
            };

            instrumentPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (listener != null) {
                        listener.onInstrumentSelected(instrumentName);
                        System.out.println("Instrument sélectionné!!!!! : " + instrumentName);
                    }
                }
            });

            instrumentPanel.setBackground(Color.LIGHT_GRAY);
            instrumentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            instrumentPanel.setPreferredSize(new Dimension(200, 150));

            panel.add(instrumentPanel);
        }

        mainPanel.add(panel, BorderLayout.CENTER);
    }


    public void setInstrumentSelectedListener(IOnChoiceSelectedListener listener) {
        this.listener = listener;
    }

    @Override
    public JPanel getMainPanel() {
        return mainPanel; // Retourne le panel indépendant
    }

    public void initializeUI() {
        // Récupérer le contentPane
        Container contentPane = this.getContentPane();

        // Définir le layout du contentPane
        contentPane.setLayout(new BorderLayout());

        // Créer le panneau des instruments
        JPanel instrumentsPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        instrumentsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Réutiliser exactement le même code que dans le constructeur
        String[] instruments = {"Piano", "Xylophone", "VideoGames", "Organ", "Drums", "Cat"};

        for (int i = 0; i < instruments.length; i++) {
            final int index = i;
            final String instrumentName = instruments[i];

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
                            break;
                    }
                }
            };

            instrumentPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (listener != null) {
                        listener.onInstrumentSelected(instrumentName);
                        System.out.println("Instrument sélectionné!!!!! : " + instrumentName);
                    }
                }
            });

            instrumentPanel.setBackground(Color.LIGHT_GRAY);
            instrumentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            instrumentPanel.setPreferredSize(new Dimension(200, 150));

            instrumentsPanel.add(instrumentPanel);
        }

        // Ajouter le panneau d'instruments au contentPane
        contentPane.add(instrumentsPanel, BorderLayout.CENTER);
    }
    @Override
    public void add(final JPanel panel) {
        mainPanel.add(panel, BorderLayout.CENTER); // Ajoute le panneau au conteneur principal
        mainPanel.revalidate(); // Revalide le conteneur principal
        mainPanel.repaint(); // Repeint le conteneur principal
    }

    @Override
    public JPanel getContentPane() {
        return (JPanel) super.getContentPane(); // Utilise directement la méthode de JFrame
    }

    @Override
    public void revalidate() {
        super.revalidate(); // Utilise directement la méthode de JFrame
    }

    @Override
    public void repaint() {
        super.repaint(); // Utilise directement la méthode de JFrame
    }

    private void drawPiano(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("PIANO", 160, 30);

        int pianoWidth = 7 * 40;
        int pianoHeight = 100;

        int panelWidth = 400;
        int panelHeight = 150;


        int x = (panelWidth / 2) - (pianoWidth / 2);
        int y = (panelHeight+50) - (pianoHeight);

        // Dessiner le fond du piano (la base des touches blanches)
        g.setColor(Color.WHITE);
        g.fillRect(x, y, pianoWidth, pianoHeight);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, pianoWidth, pianoHeight);

        // Largeur d'une touche blanche
        int keyWidth = pianoWidth / 7; // 7 touches blanches au total

        // Dessiner les touches blanches
        for (int i = 0; i < 7; i++) {
            int whiteKeyX = x + i * keyWidth;
            g.setColor(Color.WHITE);
            g.fillRect(whiteKeyX, y, keyWidth, pianoHeight);
            g.setColor(Color.BLACK);
            g.drawRect(whiteKeyX, y, keyWidth, pianoHeight);
        }

        // Dessiner les touches noires
        int blackKeyWidth = keyWidth / 2; // Largeur d'une touche noire
        int blackKeyHeight = pianoHeight / 2; // Hauteur des touches noires

        for (int i = 0; i < 7; i++) {
            if (i != 2 && i != 6) {
                int blackKeyX = x + i * keyWidth + keyWidth - blackKeyWidth / 2;
                g.setColor(Color.BLACK);
                g.fillRect(blackKeyX, y, blackKeyWidth, blackKeyHeight);
            }
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



        // Pupilles du chat (petits cercles noirs)
        g.setColor(Color.BLACK);
        g.fillOval(170, 130, 10, 10); // Pupille gauche
        g.fillOval(220, 130, 10, 10); // Pupille droite

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


