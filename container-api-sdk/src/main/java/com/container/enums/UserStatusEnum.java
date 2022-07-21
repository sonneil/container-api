package com.container.enums;

import lombok.Getter;

@Getter
public enum UserStatusEnum {
	ENABLED(1);
	
	private UserStatusEnum(Integer status) {
		this.status = status;
	}
	
	private Integer status;

}
