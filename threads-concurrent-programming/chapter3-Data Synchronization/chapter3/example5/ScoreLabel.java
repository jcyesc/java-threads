
package chapter3.example5;

import javax.swing.*;
import java.awt.event.*;
import chapter3.*;

public class ScoreLabel extends JLabel implements CharacterListener {
    
    private volatile int score = 0;
    private int char2type = -1;
    private CharacterSource generator = null, typist = null;

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

    public synchronized void resetGenerator(CharacterSource newGenerator) {
        if (generator != null)
            generator.removeCharacterListener(this);
        generator = newGenerator;
        if (generator != null)
            generator.addCharacterListener(this);        
    }

    public synchronized void resetTypist(CharacterSource newTypist) {
        if (typist != null)
            typist.removeCharacterListener(this);
        typist = newTypist;
        if (typist != null)
            typist.addCharacterListener(this);
    }

    public synchronized void resetScore() {
        score = 0;
        char2type = -1;
        setScore();
    }

    private void setScore() {
        // This method will be explained later in chapter 7
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                setText(Integer.toString(score));
            }
        });
    }

    private synchronized void newGeneratorCharacter(int c) {
        if (char2type != -1) {
            score--;
            setScore();
        }
        char2type = c;
    }

    private synchronized void newTypistCharacter(int c) {
        if (char2type != c) {
            score--;
        } else {
            score++;
            char2type = -1;
        }
        setScore();
    }

    public synchronized void newCharacter(CharacterEvent ce) {
        // Previous character not typed correctly - 1 point penalty
        if (ce.source == generator) {
	    newGeneratorCharacter(ce.character);
        }

        // If character is extraneous - 1 point penalty
        // If character does not match - 1 point penalty
        else {
	    newTypistCharacter(ce.character);
        }
    } 
}
