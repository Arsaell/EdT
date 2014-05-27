package DATA;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class HashMap<K, V> implements Map<K, V>, Iterable {

	private ArrayList<K> keys = new ArrayList<K>();
	private ArrayList<V> values = new ArrayList<V>();

	@Override
	public int size() {
		return keys.size();
	}
	
	@Override
	public boolean isEmpty() {
		return keys.size() == 0;
	}

	@Override
	public boolean containsKey(Object key) {
		return keys.contains(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return values.contains(value);
	}

	public K getKey(int index)	{
		return this.keys.get(index);
	}
	
	@Override
	public V get(Object key) {
		if (keys.indexOf(key) != -1)
			return values.get(keys.indexOf(key));
		return null;
	}

	@Override
	public V put(K key, V value) {
		int index = keys.indexOf(key);
		
		if (index == -1)	{
			keys.add(key);
			values.add(value);
		}
		
		else	{
			values.remove(index);
			values.add(index, value);
		}
		return value;
	}

	@Override
	public V remove(Object key) {
		int index = keys.indexOf(key);
		if (index != - 1)	{
			V temp = values.get(index);
			keys.remove(index);
			values.remove(index);
			return temp;
		}
		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {

		for (K k : m.keySet())	{
			keys.add(k);
			values.add(m.get(k));
		}
	}

	@Override
	public void clear() {
		keys.clear();
		values.clear();
	}

	@Override
	public Set<K> keySet() {
		CopyOnWriteArraySet<K> temp = new CopyOnWriteArraySet<K>(keys);
		return temp;
	}

	@Override
	public Collection<V> values() {
		return values;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return null;
	}

	@Override
	public Iterator<K> iterator() {
		return this.keys.iterator();
	}
	
	public K[] toArray()	{
		return (K[]) this.keys.toArray();
	}
}
