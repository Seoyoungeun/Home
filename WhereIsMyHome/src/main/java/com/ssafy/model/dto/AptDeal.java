package com.ssafy.model.dto;

import lombok.Data;

@Data
public class AptDeal {
	private int deal_year;
	private int deal_month;
	private int deal_day;
	private String deal_amount;
	private String floor;
	private String exclu_use_ar;
	private String apt_dong;
}
