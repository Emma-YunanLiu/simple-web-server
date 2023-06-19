package cache;

import liteweb.http.Response;

import java.util.*;

public class LRUCache {
    static Deque<String> q = new LinkedList<>();
    static Map<String, Cache> map = new HashMap<>();
    int CACHE_CAPACITY = 3;
    public Response getElementFromCache(String key)
    {
        if(map.containsKey(key))
        {
            Cache current = map.get(key);
            q.remove(current.key);
            q.addFirst(current.key);
            return current.value;
        }
        return null;
    }
    public void putElementInCache(String key, Response value)
    {
        if(map.containsKey(key))
        {
            Cache curr = map.get(key);
            q.remove(curr.key);
        }
        else
        {
            if(q.size() == CACHE_CAPACITY)
            {
                String temp = q.removeLast();
                map.remove(temp);
            }
        }
        Cache newItem = new Cache(key, value);
        q.addFirst(newItem.key);
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