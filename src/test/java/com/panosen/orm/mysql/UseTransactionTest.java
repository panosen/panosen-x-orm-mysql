package com.panosen.orm.mysql;

import com.panosen.codedom.mysql.builder.SelectSqlBuilder;
import com.panosen.orm.DalClient;
import com.panosen.orm.DalClientFactory;
import com.panosen.orm.mysql.school.StudentRepository;
import com.panosen.orm.mysql.school.entity.StudentEntity;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class UseTransactionTest {

    private final StudentRepository studentRepository = new StudentRepository();

    public UseTransactionTest() throws IOException {
    }

    @Test
    public void test() throws Exception {

        DalClient dalClient = DalClientFactory.getClient(DBNameConst.school);
        if (dalClient == null) {
            return;
        }

        dalClient.execute(this::insertSuccess);

        try {
            dalClient.execute(this::insertThenFail);
        } catch (Exception e) {
        }

        SelectSqlBuilder selectSqlBuilder = new SelectSqlBuilder();

        List<StudentEntity> studentEntityList = studentRepository.selectList(selectSqlBuilder);
        Assert.assertEquals(2, studentEntityList.size());

        studentRepository.batchDelete(studentEntityList);

        studentEntityList = studentRepository.selectList(selectSqlBuilder);
        Assert.assertEquals(0, studentEntityList.size());
    }

    private void insertSuccess() throws Exception {
        {
            StudentEntity studentEntity = new StudentEntity();
            studentEntity.setName("tom");
            studentEntity.setDataStatus(1);
            int count = studentRepository.insert(studentEntity);

            Assert.assertEquals(1, count);
        }

        {
            StudentEntity studentEntity = new StudentEntity();
            studentEntity.setName("jack");
            studentEntity.setDataStatus(1);
            int count = studentRepository.insert(studentEntity);

            Assert.assertEquals(1, count);
        }
    }

    private void insertThenFail() throws Exception {
        {
            StudentEntity studentEntity = new StudentEntity();
            studentEntity.setName("helen");
            studentEntity.setDataStatus(1);
            int count = studentRepository.insert(studentEntity);

            Assert.assertEquals(1, count);
        }

        throw new Exception("failed");
    }
}
