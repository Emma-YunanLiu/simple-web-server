package cache;

import liteweb.http.Response;

class Cache
{
    private String key;
    private Response value;
    Cache(String key, Response value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Response getValue() {
        return value;
    }

    public void setValue(Response value) {
        this.value = value;
    }
}
