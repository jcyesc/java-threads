package com.typing;

public class RangeGenerator implements GenerateCharacter {
	private static String []characters = {"asdf", "jkl–", "asdfghjkl–",
				"asdfg", "hjkl–", "qwert", "yuiop", "qwertyuiop", "zxcvb",
				"zxcvbnm,.", "asdfgqwertzxcvb", "hjkl–yuiopnm,.",
                                "abcdefghijklmn–opqrstuvwxyz,."};
	
	private int level = 0;
	
	public char getCharacter() {
		String string = characters[getLevel()];
		
		char c = string.charAt((int)(Math.random() * string.length()));
		
		return c;
	}

	public int getNumberLevels() {
		return characters.length;
	}
	
	public int getLevel() {
            if(level < 0)
                return 0;

            return level;
	}


	public void setLevel(int level) {
                level = level -1;
		if(level >= this.characters.length) {
			this.level = this.characters.length -1;
		} else {
			this.level = level;
		}
	}

}
