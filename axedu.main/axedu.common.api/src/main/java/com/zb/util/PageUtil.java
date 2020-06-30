package com.zb.util;

import java.util.ArrayList;
import java.util.List;

public class PageUtil<T> {
	private int index=1;//当前页码数
	private int size=2;//每页显示的条数
	private int count;//总记录数
	private int total;//总页数
	private List<T> data = new ArrayList<T>();
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getTotal() {
		if(count!=0){
			return (this.count%this.size==0)?(this.count/this.size):(this.count/this.size)+1;
		}
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
