package ru.vsu.cs.course1;

import java.util.HashMap;
import java.util.Map;

public class Task {
    public static String findPair(int[] array, int S){
        StringBuilder sb = new StringBuilder();
        Map<Integer, Integer> map = new HashMap<>();
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

    /*public static String findPairOwnImplementation(int [] array, int S){
        return SimpleHashMap.findPairMyHashMap(array, S);
    }

     */
}
