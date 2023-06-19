package cache;

import liteweb.http.Response;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LRUCache {
    private Deque<String> queue = new LinkedList<>();
    private Map<String, Cache> map = new HashMap<>();
    private static final int CACHE_CAPACITY = 3;
    public synchronized Response getElementFromCache(String key)
    {
        if(map.containsKey(key))
        {
            Cache current = map.get(key);
            queue.remove(current.getKey());
            queue.addFirst(current.getKey());
            return current.getValue();
        }
        return null;
    }
    public synchronized void putElementInCache(String key, Response value)
    {
        if(map.containsKey(key))
        {
            Cache curr = map.get(key);
            queue.remove(curr.getKey());
        }
        else
        {
            if(queue.size() == CACHE_CAPACITY)
            {
                String temp = queue.removeLast();
                map.remove(temp);
            }
        }
        Cache newItem = new Cache(key, value);
        queue.addFirst(newItem.getKey());
        map.put(key, newItem);
    }
//    public static void main(String[] args)
//    {
//        LRUCache cache = new LRUCache();
//        cache.putElementInCache("1", "Value_1");
//        cache.putElementInCache("2", "Value_2");
//        cache.putElementInCache("3", "Value_3");
//        cache.putElementInCache("4", "Value_4");
//        System.out.println(cache.getElementFromCache(2));
//        System.out.println();
//        System.out.println(q);
//        System.out.println();
//        System.out.println(cache.getElementFromCache(5));
//        cache.putElementInCache("5","Value_5");
//        System.out.println();
//        System.out.println(q);
//        System.out.println();
//    }
}