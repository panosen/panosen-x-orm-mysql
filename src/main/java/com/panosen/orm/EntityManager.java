package com.panosen.orm;

import com.google.common.collect.Maps;
import com.panosen.orm.annonation.*;

import java.lang.reflect.Field;
import java.util.Map;

public class EntityManager {

    private final Class<?> clazz;
    private final String dataSourceName;
    private final String tableName;
    private final Map<String, EntityColumn> columnMap;
    private final EntityColumn primaryKeyColumn;

    public EntityManager(Class<?> clazz) {
        this.clazz = clazz;

        DataSource dataSource = clazz.getAnnotation(DataSource.class);
        this.dataSourceName = dataSource != null ? dataSource.name() : null;

        Table table = clazz.getAnnotation(Table.class);
        this.tableName = table != null ? table.name() : null;

        this.columnMap = buildFieldMap(clazz);

        this.primaryKeyColumn = buildPrimaryKey(clazz);
    }

    private static <TEntity> Map<String, EntityColumn> buildFieldMap(Class<TEntity> clazz) {
        Map<String, EntityColumn> columnNames = Maps.newHashMap();
        Class<?> currentClass = clazz;
        while (currentClass != null) {

            Field[] fields = currentClass.getDeclaredFields();
            for (Field field : fields) {
                Column column = field.getAnnotation(Column.class);
                if (column == null) {
                    continue;
                }

                Type type = field.getAnnotation(Type.class);
                if (type == null) {
                    continue;
                }

                field.setAccessible(true);

                EntityColumn entityColumn = new EntityColumn();
                entityColumn.setColumnName(column.name());
                entityColumn.setType(type.type());
                entityColumn.setField(field);
                columnNames.putIfAbsent(column.name(), entityColumn);
            }

            currentClass = currentClass.getSuperclass();
        }

        return columnNames;
    }

    private static <TEntity> EntityColumn buildPrimaryKey(Class<TEntity> clazz) {
        Class<?> currentClass = clazz;
        while (currentClass != null) {

            Field[] fields = currentClass.getDeclaredFields();
            for (Field field : fields) {
                Column column = field.getAnnotation(Column.class);
                if (column == null) {
                    continue;
                }

                Id id = field.getAnnotation(Id.class);
                if (id == null) {
                    continue;
                }

                Type type = field.getAnnotation(Type.class);
                if (type == null) {
                    continue;
                }

                field.setAccessible(true);

                EntityColumn entityColumn = new EntityColumn();
                entityColumn.setColumnName(column.name());
                entityColumn.setField(field);
                entityColumn.setType(type.type());

                return entityColumn;
            }

            currentClass = currentClass.getSuperclass();
        }

        return null;
    }

    // region getters

    public String getDataSourceName() {
        return dataSourceName;
    }

    public String getTableName() {
        return tableName;
    }

    public Map<String, EntityColumn> getColumnMap() {
        return columnMap;
    }

    public EntityColumn getPrimaryKeyColumn() {
        return primaryKeyColumn ;
    }

    // endregion

    public Object createInstance() throws IllegalAccessException, InstantiationException {
        return this.clazz.newInstance();
    }
}
