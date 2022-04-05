package com.panosen.orm;

import com.google.common.collect.Lists;

import java.util.List;

public class KeyHolder {

    private List<Number> keys = Lists.newArrayList();

    public Number getPrimaryKey() {
        if (keys.size() < 1) {
            return null;
        }
        return keys.get(0);
    }

    public List<Number> getPrimaryKeys() {
        return keys;
    }

    public void setPrimaryKeys(List<Number> primaryKeys) {
        this.keys = primaryKeys;
    }
}
