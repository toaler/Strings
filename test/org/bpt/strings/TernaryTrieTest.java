package org.bpt.strings;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.bpt.strings.TernaryTrie;

import junit.framework.TestCase;

public class TernaryTrieTest extends TestCase {

	public void testPut1() {
		TernaryTrie<String, Integer> dictionary = new TernaryTrie<String, Integer>();

		dictionary.put("b", 1);
		dictionary.put("a", 2);
		dictionary.put("c", 3);
		dictionary.put("ba", 4);
		dictionary.put("bc", 5);
		dictionary.put("bca", 6);
		dictionary.put("bcd", 7);
		dictionary.put("bcde", 8);
		dictionary.put("bcda", 9);
		
		assertEquals(9, dictionary.size());
		assertEquals(false, dictionary.isEmpty());

		assertEquals(new Integer(1), dictionary.get("b"));
		assertEquals(new Integer(2), dictionary.get("a"));
		assertEquals(new Integer(3), dictionary.get("c"));
		assertEquals(new Integer(4), dictionary.get("ba"));
		assertEquals(new Integer(5), dictionary.get("bc"));
		assertEquals(new Integer(6), dictionary.get("bca"));
		assertEquals(new Integer(7), dictionary.get("bcd"));
		assertEquals(new Integer(8), dictionary.get("bcde"));
		assertEquals(new Integer(9), dictionary.get("bcda"));
	}

	public void testPut2() {
		TernaryTrie<String, Integer> dictionary = new TernaryTrie<String, Integer>();

		dictionary.put("97", 1);
		dictionary.put("35", 2);
		dictionary.put("69", 3);
		dictionary.put("58", 4);
		dictionary.put("16", 5);
		
		assertEquals(5, dictionary.size());
		assertEquals(false, dictionary.isEmpty());

		assertEquals(new Integer(1), dictionary.get("97"));
		assertEquals(new Integer(2), dictionary.get("35"));
		assertEquals(new Integer(3), dictionary.get("69"));
		assertEquals(new Integer(4), dictionary.get("58"));
		assertEquals(new Integer(5), dictionary.get("16"));
	}

	public void testPutKeyNull() {
		try {
			TernaryTrie<String, Boolean> dictionary = new TernaryTrie<String, Boolean>();
			dictionary.put(null, true);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("key cannot be null.", e.getMessage());
		} catch (Exception e) {
			fail();
		}
	}

	public void testPutKeyValue() {
		try {
			TernaryTrie<String, Boolean> dictionary = new TernaryTrie<String, Boolean>();
			dictionary.put(null, true);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("key cannot be null.", e.getMessage());
		} catch (Exception e) {
			fail();
		}
	}
	
	public void testIsEmpty() {
		TernaryTrie<String, Integer> dictionary = new TernaryTrie<String, Integer>();
		assertEquals(true, dictionary.isEmpty());
		
		dictionary.put("foo", 1);
		assertEquals(false, dictionary.isEmpty());
	}
	
	public void testContainsKey() {
		TernaryTrie<String, Integer> dictionary = new TernaryTrie<String, Integer>();
		
		assertEquals(false, dictionary.containsKey("foo"));
		dictionary.put("foo", 1);
		assertEquals(true, dictionary.containsKey("foo"));
	}
	
	public void testContainsvalue() {
		TernaryTrie<String, Integer> dictionary = new TernaryTrie<String, Integer>();
		
		assertEquals(false, dictionary.containsValue(1));
		dictionary.put("foo", 1);
		assertEquals(true, dictionary.containsValue(1));
	}
	
	public void testRemove() {
		TernaryTrie<String, Integer> dictionary = new TernaryTrie<String, Integer>();
		
		assertEquals(false, dictionary.containsKey("foo"));
		assertNull(dictionary.put("foo", new Integer(1)));
		assertEquals(true, dictionary.containsKey("foo"));
		assertEquals(new Integer(1), dictionary.remove("foo"));
		assertEquals(false, dictionary.containsValue("foo"));
	}
	
	public void testClear() {
		TernaryTrie<String, Integer> dictionary = new TernaryTrie<String, Integer>();
		
		for (int i = 0; i < 10000; i++) {
			dictionary.put(StringGenerator.generateAlphaString(20), new Integer(i));
		}
		
		dictionary.clear();
		
		assertEquals(false, dictionary.containsKey("foo"));
		assertNull(dictionary.remove("foo"));
		assertEquals(0, dictionary.size());
	}
	
	public void testKeySet() {
		TernaryTrie<String, Integer> dictionary = new TernaryTrie<String, Integer>();

		final int totalKeys = 10000;
		Set<String> keys = new HashSet<String>(totalKeys);
		for (int i = 0; i < totalKeys; i++) {
			String key = StringGenerator.generateAlphaString(20);
			keys.add(key);
			
			dictionary.put(key, new Integer(i));
		}
		
		assertEquals(keys, dictionary.keySet());
	}
	
	
	public void testValues() {
		TernaryTrie<String, Integer> dictionary = new TernaryTrie<String, Integer>();

		final int totalKeys = 10000;
		Set<Integer> keys = new HashSet<Integer>(totalKeys);
		for (int i = 0; i < totalKeys; i++) {
			Integer value = new Integer(i);
			keys.add(value);
			
			dictionary.put(StringGenerator.generateAlphaString(20), value);
		}
		
		assertEquals(keys, dictionary.values());
	}

	public void testLargeMap() throws InterruptedException {
		Map<String, Integer> hash = new HashMap<>();
		Map<String, Integer> trie = new TernaryTrie<>();
	
		for (int i = 0; i < 100000; i++) {
			Integer val = (int) (Math.random() * 100000000);
			String key = val.toString();
	
			hash.put(key, val);
			trie.put(key, val);
		}
	
		assertEquals(hash.size(), trie.size());
		for (String key : hash.keySet()) {
			assertEquals(hash.get(key), trie.get(key));
		}
		
		for (String key : hash.keySet()) {
			assertEquals(hash.get(key), trie.remove(key));
			assertEquals(false, trie.containsKey(key));
		}
	}
	
	private static class StringGenerator {
		private static Random rand = new Random();
		private static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		
		public static String generateString(String characters, int length) {
			char[] text = new char[length];
			for (int i = 0; i < length; i++) {
				text[i] = characters.charAt(rand.nextInt(characters.length()));
			}
			return new String(text);
		}
		
		public static String generateAlphaString(int length) {
			return StringGenerator.generateString(ALPHABET, length);
		}
	}
}