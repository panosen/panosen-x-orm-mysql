package com.panosen.orm.mysql.library.entity;

import org.junit.Assert;
import org.junit.Test;

public class BookEntityTest {

    @Test
    public void test() {

        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(1);
        bookEntity.setName("tom");

        Assert.assertEquals(1, (int)bookEntity.getId());
        Assert.assertEquals("tom", bookEntity.getName());
    }
}