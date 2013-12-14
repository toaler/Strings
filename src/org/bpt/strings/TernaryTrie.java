package org.bpt.strings;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * In {@code TernaryTrie} each node has a character, three node references and a
 * value. The three references correspond to the characters that are less than,
 * equal and greater than the current nodes character.
 * 
 * Space is an attractive property of {@code TernaryTrie} since each node has
 * only three references compared to other Trie implementation which eagerly
 * allocate space for references for the entire alphabet.
 * 
 * Search misses are likely to miss higher in the tree resulting in small amount
 * of compares, while hits require one compare per key character due to most of
 * the characters corresponding to deeper nodes that have a single branch.
 * 
 * {@code TernaryTrie} Trie's are well suited arbitrary strings with characters
 * corresponding to large alphabets with nonrandom strings. Other Trie
 * implementation eagerly allocate memory for each character in the alphabet per
 * node, which increases the space requirement. Also {@code TernaryTrie} don't
 * require any knowledge of the corresponding alphabet.
 * 
 * In terms of runtime performance, the cost of a search miss is 1.39*log(n).
 * 
 * @author toal
 * @param <K>
 * @param <V>
 * 
 */

// TODO : Implement Trie interface
public class TernaryTrie<K extends String, V> implements Map<K, V> {
	private Node<V> root;
	private int nodes;
	private int size;

	private static class Node<V> {
		private final char c;
		private V value;
		private Boolean terminal;
		private Node<V> left, equals, right;

		private Node(char c) {
			this.c = c;
		}

		@Override
		public String toString() {
			return "char = " + c + " term = " + terminal;
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean containsKey(Object key) {
		return get(key) != null;
	}

	@Override
	public boolean containsValue(Object value) {
		return containsValue(root, value);
	}

	/**
	 * Perform a depth first search looking for the value provided. The search
	 * will stop once the first match is made. Runtime is linear and
	 * proportional to the number of nodes in the tree.
	 * 
	 * @param cur
	 *            - current node being examined
	 * @param value
	 *            - search value
	 * @return - true if {@code value} is found, otherwise false on misses.
	 */
	private boolean containsValue(Node<V> cur, Object value) {
		if (cur == null)
			return false;

		if (cur.value != null && cur.value.equals(value))
			return true;

		if (containsValue(cur.left, value)) {
		}

		if (containsValue(cur.equals, value)) {
			return true;
		}

		if (containsValue(cur.right, value)) {
			return true;
		}

		return false;
	}

	@Override
	public V get(Object key) {
		String k = (String) key;

		Node<V> cur = root;
		int pos = 0;

		while (cur != null) {
			int v = compareTo(k.charAt(pos), cur.c);

			if (v < 0) {
				cur = cur.left;
			} else if (v > 0) {
				cur = cur.right;
			} else {
				pos++;

				if (pos == k.length()) {
					return cur.value;
				}

				cur = cur.equals;
			}
		}

		return null;
	}

	@Override
	public V put(K key, V value) {

		if (key == null || value == null)

			throw new IllegalArgumentException((key == null ? "key" : "value")
					+ " cannot be null.");

		int pos = 0;

		if (root == null) {
			root = new Node<V>(key.charAt(pos));
			nodes++;
		}

		// Traverse tree as far as possible

		Node<V> cur = root;

		while (pos < key.length()) {
			int rv = compareTo(key.charAt(pos), cur.c);

			if (rv < 0) {
				if (cur.left == null) {
					cur.left = new Node<V>(key.charAt(pos));
					nodes++;
				}

				cur = cur.left;
			} else if (rv > 0) {
				if (cur.right == null) {

					cur.right = new Node<V>(key.charAt(pos));
					nodes++;
				}

				cur = cur.right;
			} else {
				if (key.length() - 1 == pos) {

					break;
				}

				pos++;
				if (cur.equals == null) {
					cur.equals = new Node<V>(key.charAt(pos));
					nodes++;
				}

				cur = cur.equals;
			}
		}

		if (cur.value == null) {
			size++;
		}

		cur.value = value;

		return null;
	}

	private int compareTo(char c1, char c2) {
		return c1 - c2;
	}

	@Override
	public V remove(Object key) {
		V value = null;

		if (root == null) {
			return null;
		}

		Pair<Boolean, V> rv = remove(root, (String) key, 0, value);
		if (rv.first == true)
			root = null;

		return rv.second;
	}

	private Pair<Boolean, V> remove(Node<V> cur, String key, int pos, V value) {
		int v = compareTo(key.charAt(pos), cur.c);

		Pair<Boolean, V> rv = null;
		if (v < 0) {
			if ((rv = remove(cur.left, key, pos, value)).first == true) {
				cur.left = null;
				rv.first = false;
			}
		} else if (v > 0) {
			if ((rv = remove(cur.right, key, pos, value)).first == true) {
				cur.right = null;
				rv.first = false;
			}
		} else {
			pos++;

			if (pos == key.length()) {
				V prev = cur.value;
				cur.value = null;

				if (cur.left == null && cur.equals == null && cur.right == null) {
					return new Pair<>(true, prev);
				}
				return new Pair<>(false, prev);
			}

			if ((rv = remove(cur.equals, key, pos, value)).first == true) {
				cur.equals = null;
				rv.first = false;
			}
		}

		return rv;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		// TODO Auto-generated method stub
	}

	@Override
	public void clear() {
		root = null;
		size = 0;
		nodes = 0;
	}

	@Override
	public Set<K> keySet() {
		if (root == null) {
			return Collections.emptySet();
		}

		return keySetTraverse(root, new HashSet<K>(), new StringBuilder());
	}

	private Set<K> keySetTraverse(Node<V> cur, Set<K> keys, StringBuilder key) {
		// Traverse left
		if (cur.left != null) {
			keySetTraverse(cur.left, keys, key);
		}

		// Push current char
		key.append(cur.c);

		// If current node is a key, add to key set
		if (cur.value != null) {
			keys.add((K) key.toString());
		}

		// Traverse equals
		if (cur.equals != null) {
			keySetTraverse(cur.equals, keys, key);
		}
		// Pop cur char
		key.deleteCharAt(key.length() - 1);

		// Traverse right
		if (cur.right != null) {
			keySetTraverse(cur.right, keys, key);
		}

		return keys;
	}

	@Override
	public Collection<V> values() {
		Set<V> values = new HashSet<V>();
		gatherValues(root, values);
		return values;
	}

	private void gatherValues(Node<V> cur, Set<V> values) {
		if (cur == null)
			return;

		if (cur.value != null)
			values.add(cur.value);

		if (cur.left != null)
			gatherValues(cur.left, values);

		if (cur.equals != null)
			gatherValues(cur.equals, values);

		if (cur.right != null)
			gatherValues(cur.right, values);
	}

	@Override
	public String toString() {
		return "keys = " + size + " nodes = " + nodes;
	}

	private class Pair<F, S> {
		private F first;
		private S second;

		private Pair(F first, S second) {
			this.first = first;
			this.second = second;
		}
	}

	// TODO : Add tests
	// TODO : Add comment about how values(), keys() and entrySet() are
	// inefficient
	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		Set<Map.Entry<K, V>> entries = new HashSet<Map.Entry<K, V>>();
		gatherEntries(root, entries, new StringBuilder());
		return entries;
	}

	private void gatherEntries(Node<V> cur, Set<Map.Entry<K, V>> entries,
			StringBuilder key) {
		// Traverse left
		if (cur.left != null) {
			gatherEntries(cur.left, entries, key);
		}

		// Push current char
		key.append(cur.c);

		// If current node is a key, generate entry and add to key set
		if (cur.value != null) {
			entries.add(new Entry<K, V>((K) key.toString(), cur.value));
		}

		// Traverse equals
		if (cur.equals != null) {
			gatherEntries(cur.equals, entries, key);
		}

		// Pop cur char
		key.deleteCharAt(key.length() - 1);

		// Traverse right
		if (cur.right != null) {
			gatherEntries(cur.right, entries, key);
		}
	}

	private static class Entry<K, V> implements Map.Entry<K, V> {
		private K key;
		private V value;

		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			V prev = this.value;
			this.value = value;
			return prev;
		}
	}
}