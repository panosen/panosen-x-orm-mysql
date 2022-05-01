package com.panosen.orm.mysql;

import com.google.common.collect.Lists;
import com.panosen.orm.KeyHolder;
import com.panosen.orm.mysql.school.StudentRepository;
import com.panosen.orm.mysql.school.entity.StudentEntity;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DalTableDaoTest3 {
    private final StudentRepository studentRepository = new StudentRepository();

    public DalTableDaoTest3() throws IOException {
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
            List<StudentEntity> actual = studentRepository.selectListByIds(null);
        }

        //delete
//        {
//            int count = studentRepository.batchDelete(null);
//            Assert.assertEquals(1, count);
//        }

        //selectSingle
//        {
//            StudentEntity actual = studentRepository.selectSingle(null);
//
//            Assert.assertNull(actual);
//        }
    }
}
