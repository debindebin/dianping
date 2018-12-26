package com.yiyiglobal.dp.util;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.math.BigDecimal;
import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PageResult<T> {

	// 总共记录条数
	private int totalSize;
	// 结果集
	private List<T> resultList;


	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

	
}
