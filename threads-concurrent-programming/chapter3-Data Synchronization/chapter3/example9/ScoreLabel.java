
package chapter3.example9;

import javax.swing.*;
import java.awt.event.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import chapter3.*;

public class ScoreLabel extends JLabel implements CharacterListener {
    
    private volatile int score = 0;
    private int char2type = -1;
    private CharacterSource generator = null, typist = null;
    private Lock adminLock = new ReentrantLock();
    private Lock charLock = new ReentrantLock();
    private Lock scoreLock = new ReentrantLock();


    public ScoreLabel (CharacterSource generator, CharacterSource typist) {
        this.generator = generator;
        this.typist = typist;

        if (generator != null)
            generator.addCharacterListener(this);
        if (typist != null)
             typist.addCharacterListener(this);       
    }

    public ScoreLabel () {
        this(null, null);
    }

    public void resetGenerator(CharacterSource newGenerator) {
        try {
            adminLock.lock();
            if (generator != null)
                generator.removeCharacterListener(this);

            generator = newGenerator;
            if (generator != null)
                generator.addCharacterListener(this);
        } finally {
            adminLock.unlock();
        }
    }

    public void resetTypist(CharacterSource newTypist) {
        try {
            adminLock.lock();
            if (typist != null)
                typist.removeCharacterListener(this);

            typist = newTypist;
            if (typist != null)
                typist.addCharacterListener(this);
        } finally {
            adminLock.unlock();
        }
    }

    public void resetScore() {
        try {
            scoreLock.lock();
            charLock.lock();
            score = 0;
            char2type = -1;
            setScore();
        } finally {
            charLock.unlock();
            scoreLock.unlock();
        }
    }

    private void setScore() {
        // This method will be explained later in chapter 7
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                setText(Integer.toString(score));
            }
        });
    }

    public void newCharacter(CharacterEvent ce) {
        try {
            scoreLock.lock();
            charLock.lock();
            // Previous character not typed correctly - 1 point penalty
            if (ce.source == generator) {
                if (char2type != -1) {
                    score--;
                    setScore();
                }
                char2type = ce.character;
            }

            // If character is extraneous - 1 point penalty
            // If character does not match - 1 point penalty
            else {
                if (char2type != ce.character) {
                    score--;
                } else {
                    score++;
                    char2type = -1;
                }
                setScore();
            }
        } finally {
            scoreLock.unlock();
            charLock.unlock();
        }
    } 
}
