package chapter7.example3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import chapter7.*;

public class SwingTypeTester extends JFrame implements CharacterSource {

    protected RandomCharacterGenerator producer;
    private AnimatedCharacterDisplayCanvas displayCanvas;
    private CharacterDisplayCanvas feedbackCanvas;
    private JButton quitButton;
    private JButton startButton;
    private JButton stopButton;
    private CharacterEventHandler handler;
    private ScoreLabel score;
    private SwingTypeTester parent;

    public SwingTypeTester() {
        initComponents();
    }

    private void initComponents() {
        parent = this;
        handler = new CharacterEventHandler();
        producer = new RandomCharacterGenerator();
        producer.setDone(true);
        producer.start();
        displayCanvas = new AnimatedCharacterDisplayCanvas(producer);
        feedbackCanvas = new CharacterDisplayCanvas(this);
        quitButton = new JButton();
        startButton = new JButton();
        stopButton = new JButton();
        score = new ScoreLabel(producer, this);

        Container pane = getContentPane();
        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.PAGE_AXIS));
        p1.add(displayCanvas);
        p1.add(feedbackCanvas);

        JPanel p2 = new JPanel();
        score.setText("      ");
        score.setFont(new Font("MONOSPACED", Font.BOLD, 30));
        p2.add(score);
        startButton.setText("Start");
        p2.add(startButton);
        stopButton.setText("Stop");
        stopButton.setEnabled(false);
        p2.add(stopButton);
        quitButton.setText("Quit");
        p2.add(quitButton);
        p1.add(p2);
        pane.add(p1, BorderLayout.NORTH);
        pack();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                quit();
            }
        });
        feedbackCanvas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                char c = ke.getKeyChar();
                if (c != KeyEvent.CHAR_UNDEFINED)
                    newCharacter((int) c);
            }
        });
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                new FeedbackFrame(parent).show();
            }
        });
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                producer.setDone(true);
                displayCanvas.setDone(true);
                feedbackCanvas.setEnabled(false);
            }
        });
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                quit();
            }
        });
    }

    void setupDone() {
        displayCanvas.setDone(false);
        producer.setDone(false);
        score.resetScore();
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        feedbackCanvas.setEnabled(true);
        feedbackCanvas.requestFocus();
    }

    void setupCancelled() {
    }

    private void quit() {
        System.exit(0);
    }

    public void addCharacterListener(CharacterListener cl) {
        handler.addCharacterListener(cl);
    }

    public void removeCharacterListener(CharacterListener cl) {
        handler.removeCharacterListener(cl);
    }

    public void newCharacter(int c) {
        handler.fireNewCharacter(this, c);
    }

    public void nextCharacter() {
        throw new IllegalStateException("We don't produce on demand");
    }
    
    public static void main(String args[]) {
        new SwingTypeTester().show();
    }
}
