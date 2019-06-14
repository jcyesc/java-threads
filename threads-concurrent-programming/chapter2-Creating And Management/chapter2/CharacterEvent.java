package chapter2;

public class CharacterEvent {
    
	public CharacterSource source;
    public int character;

    public CharacterEvent(CharacterSource cs, int character) {
        source = cs;
        this.character = character;
    }
}
