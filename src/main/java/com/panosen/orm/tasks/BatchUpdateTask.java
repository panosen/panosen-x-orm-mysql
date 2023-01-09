package com.panosen.orm.tasks;

import com.panosen.orm.EntityManager;
import com.panosen.orm.KeyHolder;

import java.io.IOException;
import java.util.List;

public class BatchUpdateTask extends SingleTask {

    public BatchUpdateTask(EntityManager entityManager) throws IOException {
        super(entityManager);
    }

    public <TEntity> int[] batchUpdate(List<TEntity> entityList) throws Exception {
        return batchInsert(entityList, null);
    }

    public <TEntity> int[] batchInsert(List<TEntity> entityList, KeyHolder keyHolder) throws Exception {

        int[] counts = new int[entityList.size()];
        for (int index = 0, length = entityList.size(); index < length; index++) {
            counts[index] = UpdateHelper.execute(dalClient, entityManager, entityList.get(index));
        }
        return counts;
    }
}
