package com.xc.util.page;

import java.util.List;

/**
 * 列表分页。包含list属性。
 * 
 * @author caixt@broada.com
 * 
 */
public class Pagination<E> extends SimplePage implements java.io.Serializable,
		Paginable {


	private static final long serialVersionUID = 1L;

	public Pagination() {
	}

	/**
	 * 构造器
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页几条数据
	 * @param totalCount
	 *            总共几条数据
	 */
	public Pagination(Integer pageNo, Integer pageSize, Integer totalCount) {
		super(pageNo, pageSize, totalCount);
	}

	/**
	 * 构造器
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页几条数据
	 * @param totalCount
	 *            总共几条数据
	 * @param list
	 *            分页内容
	 */
	public Pagination(Integer pageNo, Integer pageSize, Integer totalCount, List<E> data) {
		super(pageNo, pageSize, totalCount);
		this.data = data;
	}

	/**
	 * 第一条数据位置
	 * 
	 * @return
	 */
	public int getFirstResult() {
		return (pageNo - 1) * pageSize;
	}

	/**
	 * 当前页的数据
	 */
	private List<E> data;

	/**
	 * 获得分页内容
	 * 
	 * @return
	 */
	public List<E> getData() {
		return data;
	}

	/**
	 * 设置分页内容
	 * 
	 * @param list
	 */

	public void setData(List<E> data) {
		this.data = data;
	}
	
	
	
	public Output formate(){
		Output output = new Output();
		output.data = this.data;
		output.total = this.totalCount;
		return output;
	}
	
	
	public class Output {
		private int total;
		private List<?> data;
		public int getTotal() {
			return total;
		}
		public List<?> getData() {
			return data;
		}
	}
}
