package com.yiyiglobal.dp.util;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public class PageEntity<T> {
    @NotNull(message = "页码数不能为空")
	@Min(value = 0,message = "页码数最小为0")
	private Integer offset; // 起始行号

	@Min(value = 0,message = "每页大小最小为0")
	@NotNull(message = "每页大小不能为空")
	private Integer pageSize; // 每页大小

	private T params; // 传入的参数

	private  String orderColumn;


	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public T getParams() {
		return params;
	}
	public void setParams(T params) {
		this.params = params;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}
}
