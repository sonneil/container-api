package com.container.sdk.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
	private Long id;
	private String username;
	private String userPassword;
	private Long partyId;
	private List<RoleDTO> roles;
	private UserStatusDTO status;
	
}
