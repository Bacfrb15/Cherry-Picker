package ex001;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author franz
 */
public class Animation implements KeyListener {

    private Character character;
    private MainMenuDLG dlg;
    private DataBase db;

    private int w, h;
    private boolean gameOver = false;
    private int threadTime = 50;
    private Canvas canvas;
    private boolean dialog = true;

    private String playerName;
    private int cherryAnz = 0;
    private int cherryScore = 0;

    public Animation() throws Exception {
        character = new Character();
        db = DataBase.getInstance();
        db.createTablePlayer();
    }

    public static void main(String[] args) throws Exception {
        Animation gui = new Animation();
        gui.go();
    }

    private void go() throws Exception {
        try {
            dlg = new MainMenuDLG(null, true);
            dlg.initializeLeaderboard(getPlayers());
            dlg.setVisible(dialog);
            if (dlg.isOk()) {
                canvas = new Canvas();
                JFrame frame = new JFrame();
                frame.setTitle("Cherry-Picker");
                frame.setSize(1000, 1000);
                frame.addKeyListener(this);
                frame.getContentPane().add(canvas);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(dialog);
                dialog = false;
                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        try {
                            db.insertTableData(playerName, cherryScore);
                        } catch (Exception ex) {
                            Logger.getLogger(Animation.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        System.exit(0);
                    }
                });
                gameLoop();
            }
        } catch (Exception ex) {
            System.out.println("Fehler");
            Logger.getLogger(Animation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void gameLoop() {
        while (!gameOver) {
            gameOverCheck();

            canvas.repaint();

            try {
                Thread.sleep(threadTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(Animation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    class Canvas extends JPanel {

        @Override
        /**
         * Zeichnung der Spielflaeche
         */
        protected void paintComponent(Graphics g) {
            w = this.getWidth();
            h = this.getHeight();
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            Stroke stroke1 = new BasicStroke(1);
            String cherryAnzString = cherryAnz + " / 5";
            String cherryScoreString = cherryScore + " / 500";
            playerName = dlg.getPlayerName();
            Font currentFont = g2d.getFont();
            Font newFont = currentFont.deriveFont(currentFont.getSize() * 3F);

            g2d.setFont(newFont);
            g2d.drawString(cherryAnzString, 1, 35);
            g2d.drawString(cherryScoreString, w - 150, 35);
            g2d.drawString(playerName, w / 2 - 70, 35);

            character.drawCharacter(g2d, character.getX(), character.getY(), character.getHeight(), character.getWidth());

            g2d.setStroke(stroke1);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                character.setX(character.getX() - character.getMoveSpeed());
                break;
            case KeyEvent.VK_D:
                character.setX(character.getX() + character.getMoveSpeed());
                break;
            case KeyEvent.VK_W:
                character.setY(character.getY() - character.getMoveSpeed());
                break;
            case KeyEvent.VK_S:
                character.setY(character.getY() + character.getMoveSpeed());
                break;
            case KeyEvent.VK_B:
                character.setX(w / 2);
                character.setY(h / 2);
                break;
            default:
                break;
        }
    }

    private void gameOverCheck() {
        if (cherryAnz == 5) {
            gameOver = true;
        }
        if (cherryScore == 500) {
            gameOver = true;
        }
    }

    public ArrayList<Player> getPlayers() throws Exception {
        ArrayList<Player> list = new ArrayList<>();
        for (Player pl : db.getPlayers()) {
            list.add(pl);
        }
        return list;
    }
}
