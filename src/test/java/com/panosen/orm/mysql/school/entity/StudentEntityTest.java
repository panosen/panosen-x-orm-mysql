package com.panosen.orm.mysql.school.entity;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;

public class StudentEntityTest {

    @Test
    public void test() {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(1);
        studentEntity.setName("tom");
        studentEntity.setDataStatus(1);
        studentEntity.setCreateTime(timestamp);
        studentEntity.setLastUpdateTime(timestamp);

        Assert.assertEquals(1, (int)studentEntity.getId());
        Assert.assertEquals("tom", studentEntity.getName());
        Assert.assertEquals(1, (int)studentEntity.getDataStatus());
        Assert.assertEquals(timestamp, studentEntity.getCreateTime());
        Assert.assertEquals(timestamp, studentEntity.getLastUpdateTime());
    }
}