package cache;

import liteweb.http.Response;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LRUCache {
    private Deque<String> queue = new LinkedList<>();
    private Map<String, Cache> map = new HashMap<>();
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty  = lock.newCondition();
    private static final int CACHE_CAPACITY = 3;
    public Response getElementFromCache(String key) throws InterruptedException {
        Response response = null;
        lock.lock();
        try {
            if(!map.containsKey(key)) {
                return response;
            }
            while (true) {
                if(map.containsKey(key)) {
                    Cache current = map.get(key);
                    queue.remove(current.getKey());
                    queue.addFirst(current.getKey());
                    response =  current.getValue();
                    break;
                }
                notEmpty.await();
            }
            return response;
        } finally {
            lock.unlock();
        }


    }
    public void putElementInCache(String key, Response value) throws InterruptedException {
        lock.lock();
        try {
            if(map.containsKey(key)) {
                Cache curr = map.get(key);
                queue.remove(curr.getKey());
            } else {
                if(queue.size() == CACHE_CAPACITY)
                {
                    String temp = queue.removeLast();
                    map.remove(temp);
                }
            }
            while (map.size() > CACHE_CAPACITY) {
                notFull.await();
            }
            Cache newItem = new Cache(key, value);
            queue.addFirst(newItem.getKey());
            map.put(key, newItem);
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }
}