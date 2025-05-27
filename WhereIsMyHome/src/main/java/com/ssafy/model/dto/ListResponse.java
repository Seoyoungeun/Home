package com.ssafy.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class ListResponse<T> {
	private List<T> items;
	private int totalCount;
	
	public ListResponse(List<T> items) {
        this.items = items;
        this.totalCount = items != null ? items.size() : 0;
    }
}
