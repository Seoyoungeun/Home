package com.ssafy.model.dao;

import java.util.List;

public class PagedResponse<T> {
	private List<T> items;
	private int totalCount;
	private int page;
	private int perPage;
	
	public PagedResponse(List<T> items, int page, int perPage) {
        this.items = items;
        this.totalCount = items != null ? items.size() : 0;
        this.page = page;
        this.perPage = perPage;
    }
}
