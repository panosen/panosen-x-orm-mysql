package com.panosen.orm.mysql.school;

import com.panosen.orm.DalTableDao;
import com.panosen.orm.mysql.school.entity.StudentEntity;

import java.io.IOException;

public class StudentRepository extends DalTableDao<StudentEntity> {
    public StudentRepository() throws IOException {
        super(StudentEntity.class);
    }
}
