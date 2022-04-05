package com.panosen.orm;

import java.lang.reflect.Field;

public class EntityColumn {

    private int type;

    private Field field;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
