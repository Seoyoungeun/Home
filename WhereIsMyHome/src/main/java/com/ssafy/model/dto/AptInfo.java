package com.ssafy.model.dto;

import lombok.Data;

@Data
public class AptInfo {
	private String apt_seq;
	private String apt_nm;
	private int build_year;
	private String latitude;
	private String longitude;
}
