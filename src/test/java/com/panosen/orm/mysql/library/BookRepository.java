package com.panosen.orm.mysql.library;

import com.panosen.orm.DalTableDao;
import com.panosen.orm.mysql.library.entity.BookEntity;

import java.io.IOException;

public class BookRepository extends DalTableDao<BookEntity> {
    public BookRepository(Class<? extends BookEntity> clazz) throws IOException {
        super(clazz);
    }
}
