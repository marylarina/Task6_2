package ru.vsu.cs.course1;

import java.lang.reflect.Array;
import java.util.*;


public class SimpleHashMap<K, V>  {

    private class EntryListItem implements Map.Entry<K, V> {

        public K key;
        public V value;
        public EntryListItem next;

        public EntryListItem(K key, V value, EntryListItem next) {
            this.key = key;
            this.value = value;
            this.next = next;
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
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }

    protected EntryListItem[] table;
    protected int size = 0;

    public SimpleHashMap(int capacity) {
        table = (EntryListItem[]) Array.newInstance(EntryListItem.class, capacity);
    }

    public static String findPairMyHashMap(int[] array, int S){
        StringBuilder sb = new StringBuilder();
        SimpleHashMap<Integer, Integer> map = new SimpleHashMap<>(array.length);
        for (int i = 0; i < array.length; i++) {
            if (map.containsKey(S - array[i])) {
                sb.append(" (");
                sb.append(S-array[i]);
                sb.append(",");
                sb.append(array[i]);
                sb.append(") ");
            }
            map.put(array[i], i);
        }
        return sb.toString();
    }

    private int getIndex(Object key) {
        int index = key.hashCode() % table.length;
        if (index < 0) {
            index += table.length;
        }
        return index;
    }

    private EntryListItem getEntry(Object key, int index) {
        if (index < 0) {
            index = getIndex(key);
        }
        for (EntryListItem curr = table[index]; curr != null; curr = curr.next) {
            if (key.equals(curr.key)) {
                return curr;
            }
        }
        return null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() <= 0;
    }

    public boolean containsKey(Object key) {
        return getEntry(key, -1) != null;
    }

    public V get(Object key) {
        EntryListItem item = getEntry(key, -1);
        return (item == null) ? null : item.value;
    }

    public V put(K key, V value) {
        int index = getIndex(key);
        EntryListItem item = getEntry(key, index);
        if (item != null) {
            V oldValue = item.value;
            item.value = value;
            return oldValue;
        }
        table[index] = new EntryListItem(key, value, table[index]);
        size++;
        return null;
    }

    public V remove(Object key) {
        int index = getIndex(key);
        EntryListItem parent = null;
        for (EntryListItem curr = table[index]; curr != null; curr = curr.next) {
            if (key.equals(curr.key)) {
                if (parent == null) {
                    table[index] = curr.next;
                } else {
                    parent.next = curr.next;
                }
                size--;
                return curr.value;
            }
            parent = curr;
        }
        return null;
    }

    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

    public int getCapacity() {
        return table == null ? 0 : table.length;
    }

    public int getMaxCountInBuckets() {
        int maxCount = 0;
        for (EntryListItem curr : table) {
            int count = 0;
            for (; curr != null; curr = curr.next) {
                count++;
            }
            maxCount = Math.max(maxCount, count);
        }
        return maxCount;
    }


    public double getLoadedBucketsCount() {
        int count = 0;
        for (EntryListItem curr : table) {
            if (curr != null) {
                count++;
            }
        }
        return count;
    }


    public double getLoadFactor() {
        return ((double) getLoadedBucketsCount()) / getCapacity();
    }


    public double getAvgCountInBackets() {
        return ((double) size()) / getLoadedBucketsCount();
    }
}
