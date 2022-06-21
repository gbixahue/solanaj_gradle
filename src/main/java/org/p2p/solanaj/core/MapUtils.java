package org.p2p.solanaj.core;

import java.util.Map;

/**
 * Created by Anton Zhilenkov on 21/06/2022.
 */
public class MapUtils {

    public static <K, V> V getOrDefault(Map<K, V> map, K key, V defaultValue) {
        V value;
        return (value = map.get(key)) == null && !map.containsKey(key) ? defaultValue : value;
    }
}
