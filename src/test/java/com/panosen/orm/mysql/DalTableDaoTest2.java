package com.panosen.orm.mysql;

import com.panosen.orm.KeyHolder;
import com.panosen.orm.mysql.school.StudentRepository;
import com.panosen.orm.mysql.school.entity.StudentEntity;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class DalTableDaoTest2 {
    private final StudentRepository studentRepository = new StudentRepository();

    public DalTableDaoTest2() throws IOException {
    }

    @Test
    public void test() throws Exception {
        int id;

        final StudentEntity studentEntity = new StudentEntity();
        studentEntity.setName("tom");
        studentEntity.setDataStatus(1);

        //insert
        {
            KeyHolder keyHolder = new KeyHolder();

            int count = studentRepository.insert(studentEntity, keyHolder);

            Assert.assertEquals(1, count);
            Assert.assertTrue(keyHolder.getPrimaryKey() != null && keyHolder.getPrimaryKey().intValue() > 0);

            id = keyHolder.getPrimaryKey().intValue();
        }

        //selectById
        {
            StudentEntity actual = studentRepository.selectSingleById(id);

            Assert.assertNotNull(actual);
            Assert.assertEquals(studentEntity.getName(), actual.getName());
            Assert.assertEquals(studentEntity.getDataStatus(), actual.getDataStatus());
        }

        //delete
        {
            studentEntity.setId(id);
            int count = studentRepository.delete(studentEntity);
            Assert.assertEquals(1, count);
        }

        //selectSingle
        {
            StudentEntity example = new StudentEntity();
            example.setId(id);
            StudentEntity actual = studentRepository.selectSingle(example);

            Assert.assertNull(actual);
        }
    }
}
