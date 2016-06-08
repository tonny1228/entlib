package org.llama.library.utils;

import java.util.List;

/**
 * 翻页列表
 * 
 * @ClassName: PageList
 * @Description:
 * @author Tonny
 * @date 2012-4-18 下午4:54:39
 * @version 1.0
 * @param <T>
 */
public class PageList<T> {

	/**
	 * 对象集合
	 */
	private List<T> list;
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

	public List<T> getList() {
		return list;
	}

	public int getTotal() {
		return total;
	}

	public int getPage() {
		return page;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setList(List<T> list) {
		this.list = list;
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
		return offset;
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

}
