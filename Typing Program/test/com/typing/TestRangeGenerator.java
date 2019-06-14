package com.typing;

import org.junit.Test;

public class TestRangeGenerator {
	
	@Test
	public void testname() throws Exception {
		GenerateCharacter gen = new RangeGenerator();
		for(int i = 0; i < 10; i++) {
			System.out.print(gen.getCharacter() + " ");
		}
		
		System.out.println();
		gen.setLevel(3);
		for(int i = 0; i < 10; i++) {
			System.out.print(gen.getCharacter() + " ");
		}
	}
}
