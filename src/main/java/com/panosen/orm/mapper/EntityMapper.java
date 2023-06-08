package com.panosen.orm.mapper;

import com.panosen.orm.EntityColumn;
import com.panosen.orm.EntityManager;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Map;

public class EntityMapper<TEntity> implements Mapper<TEntity> {

    private final EntityManager entityManager;

    public EntityMapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public TEntity map(ResultSet resultSet) throws SQLException, ReflectiveOperationException {
        Object entity = entityManager.createInstance();

        HashSet<String> columnNames = buildColumnNames(resultSet);

        for (Map.Entry<String, EntityColumn> entry : entityManager.getColumnMap().entrySet()) {
            if (!columnNames.contains(entry.getKey().toLowerCase())) {
                continue;
            }

            Field field = entry.getValue().getField();

            setValue(field, entity, resultSet, entry.getKey());
        }

        //noinspection unchecked
        return (TEntity) entity;
    }

    private HashSet<String> buildColumnNames(ResultSet resultSet) throws SQLException {
        HashSet<String> hashSet = new HashSet<>();
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columns = rsmd.getColumnCount();
        for (int index = 1; index <= columns; index++) {
            hashSet.add(rsmd.getColumnName(index).toLowerCase());
        }
        return hashSet;
    }

    private void setValue(Field field, Object entity, ResultSet resultSet, String columnName)
            throws ReflectiveOperationException, SQLException {

        Object value = resultSet.getObject(columnName);

        if (value == null) {
            return;
        }

        Class<?> clazz = field.getType();

        // The following order is optimized for most cases
        if (clazz.equals(Long.class) || clazz.equals(long.class)) {
            field.set(entity, ((Number) value).longValue());
            return;
        }
        if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
            field.set(entity, ((Number) value).intValue());
            return;
        }
        if (clazz.equals(Double.class) || clazz.equals(double.class)) {
            field.set(entity, ((Number) value).doubleValue());
            return;
        }
        if (clazz.equals(Float.class) || clazz.equals(float.class)) {
            field.set(entity, ((Number) value).floatValue());
            return;
        }
        if (clazz.equals(Byte.class) || clazz.equals(byte.class)) {
            field.set(entity, ((Number) value).byteValue());
            return;
        }
        if (clazz.equals(Short.class) || clazz.equals(short.class)) {
            field.set(entity, ((Number) value).shortValue());
            return;
        }
        if (clazz.equals(BigInteger.class)) {
            BigInteger bigIntegerValue = BigInteger.valueOf(((Number) value).longValue());
            field.set(entity, bigIntegerValue);
            return;
        }
        if (clazz.equals(Timestamp.class)) {
            field.set(entity, resultSet.getTimestamp(columnName));
            return;
        }
        field.set(entity, value);
    }
}
