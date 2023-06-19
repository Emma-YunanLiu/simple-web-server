package cache;

import liteweb.http.Response;

class Cache
{
    String key;
    Response value;
    Cache(String key, Response value) {
        this.key = key;
        this.value = value;
    }
}
