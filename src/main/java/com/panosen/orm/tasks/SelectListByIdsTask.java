package com.panosen.orm.tasks;

import com.panosen.codedom.mysql.builder.SelectSqlBuilder;
import com.panosen.orm.EntityColumn;
import com.panosen.orm.EntityManager;

import java.io.IOException;
import java.util.List;

public class SelectListByIdsTask extends SelectListTask {
    public SelectListByIdsTask(EntityManager entityManager) throws IOException {
        super(entityManager);
    }

    public <TEntity> List<TEntity> selectListByIds(List<Object> ids) throws Exception {
        SelectSqlBuilder selectSqlBuilder = new SelectSqlBuilder()
                .from(entityManager.getTableName());

        EntityColumn primaryKeyColumn = entityManager.getPrimaryKeyColumn();
        if (primaryKeyColumn == null) {
            return null;
        }

        selectSqlBuilder.where()
                .in(primaryKeyColumn.getColumnName(), primaryKeyColumn.getType(), ids);

        return selectList(selectSqlBuilder);
    }
}
