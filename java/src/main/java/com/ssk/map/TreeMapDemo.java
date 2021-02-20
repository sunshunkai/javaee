package com.ssk.map;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author ssk
 * @date 2020/12/17
 */
public class TreeMapDemo {
    
    
    public static void main(String[] args) {
        TreeMap<Integer,Integer> treeMap = new TreeMap<>();
        for (int i = 0 ;i<1000;){
            treeMap.put(i,i);
            i = i+2;
        }
        SortedMap<Integer, Integer> integerIntegerSortedMap = treeMap.tailMap(301);

    }
}
