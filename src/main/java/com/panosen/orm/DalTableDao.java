package com.panosen.orm;

import com.panosen.codedom.mysql.builder.SelectSqlBuilder;
import com.panosen.orm.tasks.*;

import java.io.IOException;
import java.util.List;

public class DalTableDao<TEntity> {

    private final EntityManager entityManager;

    private final InsertTask insertTask;
    private final BatchInsertTask batchInsertTask;

    private final UpdateTask updateTask;
    private final BatchUpdateTask batchUpdateTask;

    private final DeleteTask deleteTask;

    private final SelectListTask selectListTask;
    private final SelectSingleTask selectSingleTask;
    private final SelectSingleByIdTask selectSingleByIdTask;
    private final SelectListByPrimaryKeysTask selectListByPrimaryKeysTask;
    private final DeleteByPrimaryKeysTask deleteByPrimaryKeysTask;

    public DalTableDao(Class<? extends TEntity> clazz) throws IOException {
        this.entityManager = EntityManagerFactory.getOrCreateManager(clazz);

        this.insertTask = new InsertTask(entityManager);
        this.batchInsertTask = new BatchInsertTask(entityManager);

        this.updateTask = new UpdateTask(entityManager);
        this.batchUpdateTask = new BatchUpdateTask(entityManager);

        this.deleteTask = new DeleteTask(entityManager);

        this.selectListTask = new SelectListTask(entityManager);
        this.selectSingleTask = new SelectSingleTask(entityManager);
        this.selectSingleByIdTask = new SelectSingleByIdTask(entityManager);
        this.selectListByPrimaryKeysTask = new SelectListByPrimaryKeysTask(entityManager);
        this.deleteByPrimaryKeysTask = new DeleteByPrimaryKeysTask(entityManager);
    }

    public int insert(TEntity entity) throws Exception {
        return this.insertTask.execute(entity);
    }

    public int insert(TEntity entity, KeyHolder keyHolder) throws Exception {
        return this.insertTask.execute(entity, keyHolder);
    }

    public int update(TEntity entity) throws Exception {
        return this.updateTask.execute(entity);
    }

    public int delete(TEntity entity) throws Exception {
        return this.deleteTask.delete(entity);
    }

    public int batchInsert(List<TEntity> entityList) throws Exception {
        return this.batchInsertTask.batchInsert(entityList);
    }

    public int batchInsert(List<TEntity> entityList, KeyHolder keyHolder) throws Exception {
        return this.batchInsertTask.batchInsert(entityList, keyHolder);
    }

    public int[] batchUpdate(List<TEntity> entityList) throws Exception {
        return this.batchUpdateTask.batchUpdate(entityList);
    }

    public int[] batchDelete(List<TEntity> entityList) throws Exception {
        int[] counts = new int[entityList.size()];
        for (int index = 0, length = entityList.size(); index < length; index++) {
            counts[index] = new DeleteTask(entityManager).delete(entityList.get(index));
        }
        return counts;
    }

    public List<TEntity> selectList(TEntity entity) throws Exception {
        return this.selectListTask.selectList(entity);
    }

    public List<TEntity> selectListByPage(TEntity entity, Integer pageIndex, Integer pageSize) throws Exception {
        return this.selectListTask.selectListByPage(entity, pageIndex, pageSize);
    }

    public List<TEntity> selectList(SelectSqlBuilder selectSqlBuilder) throws Exception {
        selectSqlBuilder.from(entityManager.getTableName());
        return this.selectListTask.selectList(selectSqlBuilder);
    }

    public TEntity selectSingle(TEntity entity) throws Exception {
        return this.selectSingleTask.selectSingle(entity);
    }

    public TEntity selectSingle(SelectSqlBuilder selectSqlBuilder) throws Exception {
        selectSqlBuilder.from(entityManager.getTableName());
        return this.selectSingleTask.selectSingle(selectSqlBuilder);
    }

    public TEntity selectSingleById(Object id) throws Exception {
        return selectSingleByIdTask.selectSingleById(id);
    }

    public <TId> List<TEntity> selectListByPrimaryKeys(List<TId> ids) throws Exception {
        return selectListByPrimaryKeysTask.selectListByPrimaryKeys(ids);
    }

    public <TId> int deleteByPrimaryKeys(List<TId> ids) throws Exception {
        return deleteByPrimaryKeysTask.deleteByPrimaryKeys(ids);
    }
}
