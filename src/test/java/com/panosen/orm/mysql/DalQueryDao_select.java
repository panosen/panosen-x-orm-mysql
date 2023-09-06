package com.panosen.orm.mysql;

import com.google.common.collect.Lists;
import com.panosen.codedom.mysql.builder.SelectSqlBuilder;
import com.panosen.orm.DalQueryDao;
import com.panosen.orm.KeyHolder;
import com.panosen.orm.mysql.school.StudentRepository;
import com.panosen.orm.mysql.school.entity.StudentEntity;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.sql.Types;
import java.util.List;
import java.util.stream.Collectors;

public class DalQueryDao_select {
    private final StudentRepository studentRepository = new StudentRepository();

    private final DalQueryDao dalQueryDao = new DalQueryDao(DBNameConst.school);

    public DalQueryDao_select() throws IOException {
    }

    @Test
    public void test() throws Exception {
        List<Integer> ids;

        List<StudentEntity> studentEntityList = Lists.newArrayList();
        for (int i = 0; i < 3; i++) {
            StudentEntity studentEntity = new StudentEntity();
            studentEntity.setName("tom_" + i);
            studentEntity.setDataStatus(1);
            studentEntityList.add(studentEntity);
        }

        //insert
        {
            KeyHolder keyHolder = new KeyHolder();

            int count = studentRepository.batchInsert(studentEntityList, keyHolder);

            Assert.assertEquals(3, count);
            Assert.assertTrue(keyHolder.getPrimaryKey() != null && keyHolder.getPrimaryKey().intValue() > 0);

            ids = keyHolder.getPrimaryKeys().stream().map(Number::intValue).collect(Collectors.toList());
        }

        //selectListByIds
        {
            SelectSqlBuilder selectSqlBuilder = new SelectSqlBuilder();
            selectSqlBuilder.from("student");
            selectSqlBuilder.where().equal("data_status", Types.INTEGER, 1);

            List<StudentEntity> actual = dalQueryDao.queryList(selectSqlBuilder, StudentEntity.class);

            Assert.assertNotNull(actual);
            Assert.assertEquals(3, actual.size());

            Assert.assertEquals(actual.get(0).getName(), "tom_0");
            Assert.assertEquals(actual.get(1).getName(), "tom_1");
            Assert.assertEquals(actual.get(2).getName(), "tom_2");
        }

        //delete
        {
            int count = studentRepository.deleteByPrimaryKeys(ids);
            Assert.assertEquals(3, count);
        }
    }
}
