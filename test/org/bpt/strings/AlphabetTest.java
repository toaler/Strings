package org.bpt.strings;

import junit.framework.TestCase;

public class AlphabetTest extends TestCase {
	
	public void testStringCtorWithDuplicateCharsInAlphabet() {
		try {
			Alphabet a = new Alphabet("011");
			fail();
		} catch (IllegalArgumentException e) {
			// no-op
		} catch (Exception e) {
			fail("Expected IllegalArgumentException");
		}
	}
	
	public void testIntegerConstructor() {
		Alphabet ascii = new Alphabet(128);
		assertTrue(ascii.contains((char) 100));
		assertFalse(ascii.contains((char) 256));
	}
	
	public void testContains() {
		assertTrue(Alphabet.BINARY.contains('0'));
		assertFalse(Alphabet.BINARY.contains('A'));
	}
	
	public void testRadix() {
		assertEquals(128, Alphabet.ASCII.getRadix());
	}
	
	public void testRadixBits() {
		assertEquals(8, Alphabet.ASCII.getRadixLengthBits());
	}
	
	public void testToIndex() {
		Alphabet a = new Alphabet("za");
		assertEquals(0, a.toIndex('z'));
		assertEquals(1, a.toIndex('a'));
	}
	
	public void testToIndexIllegalArgumentExceptionUnderflow() {
		Alphabet a = new Alphabet("za");
		try {
			a.toIndex((char) -1);
			fail();
		} catch (IllegalArgumentException e) {
			// no-op
		} catch (Exception e) {
			assertEquals(IllegalArgumentException.class, e.getClass());
		}
	}
	
	public void testToIndexIllegalArgumentExceptionOverflow() {
		Alphabet a = new Alphabet("za");
		try {
			a.toIndex((char)200);
			fail();
		} catch (IllegalArgumentException e) {
			// no-op
		} catch (Exception e) {
			assertEquals(IllegalArgumentException.class, e.getClass());
		}
	}
	
	public void testToIndexIllegalArgumentExceptionNonExistingChar() {
		Alphabet a = new Alphabet("za");
		try {
			a.toIndex('b');
			fail();
		} catch (IllegalArgumentException e) {
			// no-op
		} catch (Exception e) {
			assertEquals(IllegalArgumentException.class, e.getClass());
		}
	}
	
	public void testToChar() {
		Alphabet a = new Alphabet("za");
		assertEquals('z', a.toChar(0));
		assertEquals('a', a.toChar(1));
	}
	
	public void testToCharIllegalArgumentExceptionNonExistingCharUnderRun() {
		Alphabet a = new Alphabet("za");
		
		try {
			assertEquals('z', a.toChar(-1));	
		} catch (IllegalArgumentException e) {
			// no-op
		} catch (Exception e) {
			assertEquals(IllegalArgumentException.class, e.getClass());
		}
	}
	
	public void testToCharIllegalArgumentExceptionNonExistingCharOverRun() {
		Alphabet a = new Alphabet("za");
		
		try {
			assertEquals('z', a.toChar(3));	
		} catch (IllegalArgumentException e) {
			// no-op
		} catch (Exception e) {
			assertEquals(IllegalArgumentException.class, e.getClass());
		}
	}
}