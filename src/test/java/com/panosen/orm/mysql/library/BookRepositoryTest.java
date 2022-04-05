package com.panosen.orm.mysql.library;

import com.panosen.orm.mysql.library.entity.BookEntity;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class BookRepositoryTest {

    @Test
    public void test() throws IOException {
        BookRepository bookRepository = new BookRepository(BookEntity.class);
        Assert.assertNotNull(bookRepository);
    }
}