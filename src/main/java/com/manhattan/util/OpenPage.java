package com.manhattan.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象
 *
 * @param <T> 装载的数据类型
 * @author openTeam@tbc
 * @version 1.0
 */
public class OpenPage<T> {

    public final static int DEFAULT_PAGE_SIZE = 10;
    /**
     * 页码
     */
    protected int pageNo = 1;
    /**
     * 每页的记录数
     */
    protected int pageSize = DEFAULT_PAGE_SIZE;
    protected boolean autoCount = true;
    protected boolean autoPaging = true;
    /**
     * 查询结果
     */
    protected List<T> rows = new ArrayList<T>();
    /**
     * 总记录数
     */
    protected long total = 0;


    public OpenPage() {
    }

    public OpenPage(int pageSize) {
        this.pageSize = pageSize;
    }


    /**
     * 获得当前页的页号,序号从1开始,默认为1.
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
     */
    public void setPageNo(final int pageNo) {
        this.pageNo = pageNo;

        if (pageNo < 1) {
            this.pageNo = 1;
        }
    }

    /**
     * 获得每页的记录数量, 默认为10，最大不能超过200，最大不能超过200.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页的记录数量.
     */
    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
     */
    public int getFirst() {
        return ((pageNo - 1) * pageSize) + 1;
    }

    /**
     * 获得查询对象时是否先自动执行count查询获取总记录数, 默认为false.
     */
    public boolean isAutoCount() {
        return autoCount;
    }

    /**
     * 设置查询对象时是否自动先执行count查询获取总记录数.
     */
    public void setAutoCount(final boolean autoCount) {
        this.autoCount = autoCount;
    }


    //-- 访问查询结果函数 --//

    /**
     * 获得页内的记录列表.
     */
    public List<T> getRows() {
        return rows;
    }

    /**
     * 设置页内的记录列表.
     */
    public void setRows(final List<T> result) {
        this.rows = result;
    }

    /**
     * 获得总记录数, 默认值为0.
     */
    public long getTotal() {
        return total;
    }

    /**
     * 设置总记录数.
     */
    public void setTotal(final long totalCount) {
        this.total = totalCount;
    }

    /**
     * 根据pageSize与totalCount计算总页数, 默认值为-1.
     */
    public long getTotalPages() {
        long count = total / pageSize;
        if (total % pageSize > 0) {
            count++;
        }
        return count;
    }

    /**
     * 是否还有下一页.
     */
    public boolean isHasNext() {
        return (pageNo + 1 <= getTotalPages());
    }

    /**
     * 取得下页的页号, 序号从1开始.
     * 当前页为尾页时仍返回尾页序号.
     */
    public int getNextPage() {
        if (isHasNext()) {
            return pageNo + 1;
        } else {
            return pageNo;
        }
    }

    /**
     * 是否还有上一页.
     */
    public boolean isHasPre() {
        return (pageNo - 1 >= 1);
    }

    /**
     * 取得上页的页号, 序号从1开始.
     * 当前页为首页时返回首页序号.
     */
    public int getPrePage() {
        if (isHasPre()) {
            return pageNo - 1;
        } else {
            return pageNo;
        }
    }


    public boolean isAutoPaging() {
        return autoPaging;
    }

    public void setAutoPaging(boolean autoPaging) {
        this.autoPaging = autoPaging;
    }

    @Override
    public String toString() {
        return "Page{" +
                "total=" + total +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", autoCount=" + autoCount +
                '}';
    }
}
