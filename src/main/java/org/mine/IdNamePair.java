package org.mine;

public class IdNamePair {
    private int id;
    private Object data;

    public IdNamePair(int id, Object data) {
        this.id   = id;
        this.data = data;
    }

    public int id() {
        return id;
    }

    public Object data() {
        return data;
    }

    public int updateId(int id) {
        return this.id = id;
    }
}
