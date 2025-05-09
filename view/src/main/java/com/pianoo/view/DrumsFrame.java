package com.pianoo.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class DrumsFrame extends JPanel implements IDrumsFrame {

    private IMenuNavigationListener listener;
    private final HashMap<String, DrumComponent> drums = new HashMap<>();
    private String hitDrum = null;
    private boolean kickPedalPressed = false;
    private double scaleFactor = 1.2;

    public DrumsFrame() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);
        RoundCloseButton closeButton = new RoundCloseButton();
        closeButton.setListener(() -> {
            if (listener != null) {
                listener.onReturnMainMenu();
            }
        });

        topPanel.add(closeButton);
        add(topPanel, BorderLayout.NORTH);

        setOpaque(false);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setupDrums();
            }
        });

        setupDrums();

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Point scaledPoint = convertPointToScaled(e.getPoint());

                for (DrumComponent drum : drums.values()) {
                    if (drum.contains(scaledPoint)) {
                        hitDrum = drum.label;
                        if (drum.isKick) kickPedalPressed = true;
                        repaint();
                        new Timer(120, evt -> {
                            hitDrum = null;
                            kickPedalPressed = false;
                            repaint();
                            ((Timer) evt.getSource()).stop();
                        }).start();
                        break;
                    }
                }
            }
        });
    }

    private Point convertPointToScaled(Point p) {
        int offsetX = (getWidth() - (int) (getPreferredSize().width * scaleFactor)) / 2;
        int offsetY = (getHeight() - (int) (getPreferredSize().height * scaleFactor)) / 2;
        return new Point(
                (int) ((p.x - offsetX) / scaleFactor),
                (int) ((p.y - offsetY) / scaleFactor)
        );
    }

    private void setupDrums() {
        drums.clear();

        int cx = 600; // centre logique basé sur PreferredSize
        int cy = 400;

        drums.put("kick", new DrumComponent("Grosse Caisse", cx, (int) (cy + 60 * scaleFactor), (int) (220 * scaleFactor), (int) (70 * scaleFactor), new Color(240, 240, 240), true));
        drums.put("snare", new DrumComponent("Caisse Claire", (int) (cx - 130 * scaleFactor), (int) (cy - 30 * scaleFactor), (int) (90 * scaleFactor), (int) (30 * scaleFactor), new Color(210, 197, 197), false));
        drums.put("hihat1", new DrumComponent("Hi-Hat", (int) (cx - 200 * scaleFactor), (int) (cy - 80 * scaleFactor), (int) (80 * scaleFactor), (int) (20 * scaleFactor), new Color(220, 220, 100), false));
        drums.put("tom1", new DrumComponent("Tom Alto", (int) (cx - 60 * scaleFactor), (int) (cy - 100 * scaleFactor), (int) (70 * scaleFactor), (int) (25 * scaleFactor), new Color(210, 197, 197), false));
        drums.put("tom2", new DrumComponent("Tom Medium", (int) (cx + 80 * scaleFactor), (int) (cy - 100 * scaleFactor), (int) (80 * scaleFactor), (int) (25 * scaleFactor), new Color(210, 197, 197), false));
        drums.put("floorTom", new DrumComponent("Tom Basse", (int) (cx + 130 * scaleFactor), (int) (cy - 20 * scaleFactor), (int) (100 * scaleFactor), (int) (30 * scaleFactor), new Color(210, 197, 197), false));
        drums.put("ride", new DrumComponent("Ride", (int) (cx + 200 * scaleFactor), (int) (cy - 120 * scaleFactor), (int) (90 * scaleFactor), (int) (20 * scaleFactor), new Color(240, 230, 150), false));
        drums.put("crash", new DrumComponent("Crash", (int) (cx - 180 * scaleFactor), (int) (cy - 150 * scaleFactor), (int) (90 * scaleFactor), (int) (20 * scaleFactor), new Color(255, 200, 100), false));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1200, 800);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int offsetX = (getWidth() - (int) (getPreferredSize().width * scaleFactor)) / 2;
        int offsetY = (getHeight() - (int) (getPreferredSize().height * scaleFactor)) / 2;

        g2.translate(offsetX, offsetY);
        g2.scale(scaleFactor, scaleFactor);

        drawVerticalBars(g2);

        for (DrumComponent drum : drums.values()) {
            drum.draw(g2, drum.label.equals(hitDrum));
        }

        drawKickPedal(g2);

        g2.dispose();
    }

    private void drawVerticalBars(Graphics2D g2) {
        g2.setColor(new Color(220, 220, 100));
        int barWidth = 3;
        int floorLevel = 800 - 240;

        DrumComponent crash = drums.get("crash");
        if (crash != null) {
            g2.fillRect(
                    crash.x - barWidth / 2,
                    crash.y + crash.height / 2,
                    barWidth,
                    floorLevel - (crash.y + crash.height / 2)
            );
        }

        DrumComponent hihat = drums.get("hihat1");
        if (hihat != null) {
            g2.fillRect(
                    hihat.x - barWidth / 2,
                    hihat.y + hihat.height / 2,
                    barWidth,
                    floorLevel - (hihat.y + hihat.height / 2)
            );
        }

        DrumComponent ride = drums.get("ride");
        if (ride != null) {
            g2.fillRect(
                    ride.x - barWidth / 2,
                    ride.y + ride.height / 2,
                    barWidth,
                    floorLevel - (ride.y + ride.height / 2)
            );
        }
    }

    private void drawKickPedal(Graphics2D g2) {
        DrumComponent kick = drums.get("kick");
        if (kick != null) {
            g2.setColor(Color.BLACK);
            int pedalWidth = 40, pedalHeight = 15;
            int pedalX = kick.x - pedalWidth / 2;
            int pedalY = kick.y + kick.height;
            g2.fillRoundRect(pedalX, pedalY, pedalWidth, pedalHeight, 5, 5);

            g2.fillRect(kick.x - 2, kick.y + kick.height, 4, 30);

            int beaterY = kickPedalPressed ? kick.y + kick.height - 5 : kick.y + kick.height - 15;
            g2.fillRect(kick.x - 10, beaterY, 20, 30);
        }
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    @Override
    public void setListener(final IMenuNavigationListener listener) {
        this.listener = listener;
    }

    static class DrumComponent {
        String label;
        int x, y, width, height;
        Color color;
        boolean isKick;

        DrumComponent(String label, int x, int y, int width, int height, Color color, boolean isKick) {
            this.label = label;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = color;
            this.isKick = isKick;
        }

        void draw(Graphics2D g2, boolean isHit) {
            int drawX = x;
            int drawY = y;

            if (isKick) {
                int bodyWidth = width + 40;
                int bodyHeight = height + 80;
                int bodyY = drawY - bodyHeight + 50;

                g2.setColor(new Color(180, 40, 40));
                g2.fillOval(drawX - bodyWidth / 2, bodyY, bodyWidth, bodyHeight);

                GradientPaint topGradient = new GradientPaint(drawX - width / 2, drawY - 10, isHit ? color.brighter() : new Color(250, 250, 250),
                        drawX + width / 2, drawY + height - 10, isHit ? color.darker() : new Color(230, 230, 230));
                g2.setPaint(topGradient);
                g2.fillOval(drawX - width / 2, drawY - 10, width, height);

                g2.setColor(new Color(101, 100, 100));
                g2.setStroke(new BasicStroke(3f));
                g2.drawOval(drawX - width / 2, drawY - 10, width, height);

            } else if (label.toLowerCase().contains("hi-hat") || label.toLowerCase().contains("ride") || label.toLowerCase().contains("crash")) {
                RadialGradientPaint cymbalGradient = new RadialGradientPaint(
                        new Point(drawX, drawY), width / 2,
                        new float[]{0.1f, 0.8f, 1.0f},
                        new Color[]{
                                isHit ? color.brighter() : new Color(255, 255, 255, 200),
                                isHit ? color : color.darker(),
                                isHit ? color.darker().darker() : new Color(50, 50, 50)
                        }
                );
                g2.setPaint(cymbalGradient);
                g2.fillOval(drawX - width / 2, drawY - height / 2, width, height);

            } else {
                int cylHeight = height + 70;
                int topY = drawY - cylHeight / 2;
                int bodyY = topY + height / 2;

                g2.setColor(new Color(200, 50, 50));
                g2.fillRoundRect(drawX - width / 2, bodyY, width, (cylHeight - height) / 2, width / 2, height / 2);

                GradientPaint topGradient = new GradientPaint(drawX - width / 2, topY, isHit ? color.brighter() : color,
                        drawX + width / 2, topY + height, isHit ? color.darker().darker() : new Color(180, 167, 167));
                g2.setPaint(topGradient);
                g2.fillOval(drawX - width / 2, topY, width, height);

                g2.setColor(new Color(100, 100, 100));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawOval(drawX - width / 2, topY, width, height);
            }

        }

        boolean contains(Point p) {
            int dx = p.x - x;
            int dy = p.y - y;
            if (isKick) {
                double rx = width / 2.0;
                double ry = height / 2.0;
                return (dx / rx) * (dx / rx) + (dy / ry) * (dy / ry) <= 1.0;
            } else if (label.toLowerCase().contains("hi-hat") || label.toLowerCase().contains("ride") || label.toLowerCase().contains("crash")) {
                return dx * dx + dy * dy <= (width / 2.0) * (width / 2.0);
            } else {
                return Math.abs(dx) <= width / 2 && Math.abs(dy) <= height / 2;
            }
        }
    }
}
