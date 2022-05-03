package com.panosen.orm;

import com.panosen.codedom.mysql.builder.SelectSqlBuilder;
import com.panosen.orm.tasks.*;

import java.io.IOException;
import java.util.List;

public class DalTableDao<TEntity> {

    private final EntityManager entityManager;

    private final InsertTask insertTask;
    private final UpdateTask updateTask;
    private final DeleteTask deleteTask;
    private final BatchInsertTask batchInsertTask;
    private final SelectListTask selectListTask;
    private final SelectSingleTask selectSingleTask;
    private final SelectSingleByIdTask selectSingleByIdTask;
    private final SelectListByIdsTask selectListByIdsTask;
    private final DeleteByIdsTask deleteByIdsTask;

    public DalTableDao(Class<? extends TEntity> clazz) throws IOException {
        this.entityManager = EntityManagerFactory.getOrCreateManager(clazz);

        this.insertTask = new InsertTask(entityManager);
        this.updateTask = new UpdateTask(entityManager);
        this.deleteTask = new DeleteTask(entityManager);
        this.batchInsertTask = new BatchInsertTask(entityManager);
        this.selectListTask = new SelectListTask(entityManager);
        this.selectSingleTask = new SelectSingleTask(entityManager);
        this.selectSingleByIdTask = new SelectSingleByIdTask(entityManager);
        this.selectListByIdsTask = new SelectListByIdsTask(entityManager);
        this.deleteByIdsTask = new DeleteByIdsTask(entityManager);
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
        return this.deleteTask.execute(entity);
    }

    public int batchInsert(List<TEntity> entityList) throws Exception {
        return this.batchInsertTask.batchInsert(entityList);
    }

    public int batchInsert(List<TEntity> entityList, KeyHolder keyHolder) throws Exception {
        return this.batchInsertTask.batchInsert(entityList, keyHolder);
    }

    //TODO add Transaction
    public int[] batchUpdate(List<TEntity> entityList) throws Exception {
        int[] counts = new int[entityList.size()];
        for (int index = 0, length = entityList.size(); index < length; index++) {
            counts[index] = new UpdateTask(entityManager).execute(entityList.get(index));
        }
        return counts;
    }

    public int[] batchDelete(List<TEntity> entityList) throws Exception {
        int[] counts = new int[entityList.size()];
        for (int index = 0, length = entityList.size(); index < length; index++) {
            counts[index] = new DeleteTask(entityManager).execute(entityList.get(index));
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

    public <TId> List<TEntity> selectListByIds(List<TId> ids) throws Exception {
        return selectListByIdsTask.selectListByIds(ids);
    }

    public <TId> int deleteByIds(List<TId> ids) throws Exception {
        return deleteByIdsTask.deleteByIds(ids);
    }
}
