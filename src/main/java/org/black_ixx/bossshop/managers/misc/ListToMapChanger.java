package org.black_ixx.bossshop.managers.misc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListToMapChanger {
    public static Map<String,String> listToMap(List<String> list){
        Map<String,String> map = new HashMap<>();
        for(String str:list) {
            String[] parts = str.split(":");
            map.put(parts[0],parts[1]);
        }
        return map;
    }
    public static Map<String,Integer> listToMapInt(List<String> list){
        Map<String,Integer> map = new HashMap<>();
        for(String str:list) {
            String[] parts = str.split(":");
            map.put(parts[0], Integer.valueOf(parts[1]));
        }
        return map;
    }
    public static Map<String,Boolean> listToMapBoolean(List<String> list){
        Map<String,Boolean> map = new HashMap<>();
        for(String str:list) {
            String[] parts = str.split(":");
            map.put(parts[0], Boolean.valueOf(parts[1]));
        }
        return map;
    }
}
