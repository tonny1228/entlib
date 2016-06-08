package org.llama.library.utils;

import java.util.ArrayList;

/**
 * 翻页列表
 *
 * @param <T>
 * @author Tonny
 * @version 1.0
 * @ClassName: PageList
 * @Description:
 * @date 2012-4-18 下午4:54:39
 */
public class PagedList<T> extends ArrayList<T> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 总记录数
     */
    private int total;
    /**
     * 页号
     */
    private int page;
    /**
     * 跳过条数
     */
    private int offset;

    /**
     * 每页显示记录数
     */
    private int pagesize;

    public int getTotal() {
        return total;
    }

    public int getPage() {
        return page;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    /**
     * @return the offset
     */
    public int getOffset() {
        if (page > 1) {
            offset = (page - 1) * pagesize;
        }
        return offset;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }
}
