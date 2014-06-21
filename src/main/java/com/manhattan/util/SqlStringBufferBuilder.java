package com.manhattan.util;

/**
 * Created by Administrator on 2014/6/21 0021.
 */
public class SqlStringBufferBuilder {
    private final static StringBuffer query = new StringBuffer("select");
    private final static StringBuffer insert = new StringBuffer("insert");
    private final static StringBuffer update = new StringBuffer("update");

    public static StringBuffer getCountSQL(StringBuffer from) {
        return query.append(from);
    }



}
