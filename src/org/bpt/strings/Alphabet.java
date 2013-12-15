package org.bpt.strings;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * Used to define a custom alphabet of {@code radix} characters. Provides API's
 * to test membership and map characters ({@code radix}) to and from the
 * corresponding integral value (0 through {@code radix} - 1).
 * 
 */
public class Alphabet {
	public static final Alphabet BINARY = new Alphabet("01");
	public static final Alphabet OCTAL = new Alphabet("01234567");
	public static final Alphabet DECIMAL = new Alphabet("0123456789");
	public static final Alphabet HEXADECIMAL = new Alphabet("0123456789ABCDEF");
	public static final Alphabet LOWERCASE = new Alphabet("abcdefghijklmnopqrstuvwxyz");
	public static final Alphabet UPPERCASE = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	public static final Alphabet BASE64 = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/");
	public static final Alphabet ASCII = new Alphabet(128);
	public static final Alphabet EXTENDED_ASCII = new Alphabet(256);
	public static final Alphabet UNICODE16 = new Alphabet(65536);
    
    final private char[] alphabet;
    final private Map<Integer, Integer> inverse;
    final private int radix;               
    
    public Alphabet(final int radix) {
    	this.radix = radix;
    	alphabet = new char[radix];
    	inverse = new HashMap<>(radix);
    	
    	for (int i = 0; i < radix; i++) {
    		alphabet[i] = (char) i;
    		inverse.put(i, i);
    	}
	}
    
	public Alphabet(final String s) {
		Set<Character> seen = new HashSet<>();
		
		for (int i = 0; i < s.length(); i++)  {
			if (seen.contains(s.charAt(i))) {
				throw new IllegalArgumentException("Alphabet provided contains duplicate character = " + s.charAt(i));
			}
			
			seen.add(s.charAt(i));
		}
		
		this.radix = s.length();
		this.alphabet = s.toCharArray();
		this.inverse = new HashMap<>(radix);

		for (int i = 0; i < radix; i++) {
			inverse.put((int)alphabet[i], i);
		}
	}
	
    public Alphabet() {
        this(256);
    }
	
	public boolean contains(final char c) {
		return inverse.get((int)c) != null;
	}

	public int getRadix() {
		return radix;
	}

	public int getRadixLengthBits() {
		int i = 0;
		int r = radix;
		
		while (r >= 1) {
			r /= 2;
			i++;
		}
		return i;
	}

	public int toIndex(final char c) {
		if (inverse.get((int)c) == null) 
			throw new IllegalArgumentException(String.format("'%s' is not part of the alphabet.", c));
		
		return inverse.get((int)c);
	}
	
	public char toChar(final int i) {
		if (i < 0 || alphabet.length < i) {
			throw new IllegalArgumentException();
		}
		return alphabet[i];
	}
}
