package com.panosen.orm.tasks;

import com.panosen.orm.EntityManager;

import java.io.IOException;

public class UpdateTask extends SingleTask {

    public UpdateTask(EntityManager entityManager) throws IOException {
        super(entityManager);
    }

    public <TEntity> int execute(TEntity entity) throws Exception {
        return UpdateHelper.execute(dalClient, entityManager, entity);
    }
}
