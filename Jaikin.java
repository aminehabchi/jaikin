import helpers.*;
import static helpers.Constants.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Jaikin extends JPanel implements KeyListener {

    private List<Point> points;
    private List<Point> chaikin;
    private int step;
    private boolean isAnimate;
    private Timer chaikinTimer = new Timer(TIMER_DELAY_MS, null);

    public Jaikin() {
        points = new ArrayList<>();
        setBackground(BACKGROUND_COLOR);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isAnimate) return;
                points.add(new Point(e.getX(), e.getY()));
                repaint();
            }
        });

        step = 0;
        isAnimate = false;
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);
    }

    public void stopChaikinAnimation() {
        if (chaikinTimer != null && chaikinTimer.isRunning()) {
            chaikinTimer.stop();
        }

        for (ActionListener al : chaikinTimer.getActionListeners()) {
            chaikinTimer.removeActionListener(al);
        }

        step = 0;
        chaikin = null;
        isAnimate = false;
    }

    public void applyChaikin() {
        if (points.size() < 3) return;

        stopChaikinAnimation();

        chaikin = new ArrayList<>(points);
        step = 0;
        isAnimate = true;
        repaint();

        ActionListener listener = e -> {
            if (step < MAX_CHAIKIN_STEPS) {
                chaikin = Chaikin.chaikin(chaikin);
                step++;
                repaint();
            } else {
                chaikin = new ArrayList<>(points);
                step = 0;
                repaint();
            }
        };

        chaikinTimer.addActionListener(listener);
        chaikinTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        DrawUtils.drawInstructionsPanel(g2);
        DrawUtils.drawStyledOriginalPoints(g2, points);

        if (isAnimate && chaikin != null && chaikin.size() > 1) {
            DrawUtils.drawStyledSmoothedCurve(g2, chaikin);
            DrawUtils.drawStatusInfo(g2, step, chaikin.size(), getWidth());
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        if (!isAnimate && points.size() >= 3) {
                applyChaikin();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }

        if (e.getKeyChar() == ' ') {
            stopChaikinAnimation();
            points.clear();
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Optional - left intentionally blank
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Optional - left intentionally blank
    }

    public static void main(String[] args) {
        // Enable modern rendering
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("JAIKIN");
            Jaikin anim = new Jaikin();

            frame.add(anim);
            frame.setSize(900, 650);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setResizable(true);

            frame.setVisible(true);
            anim.requestFocusInWindow();
        });
    }
}

