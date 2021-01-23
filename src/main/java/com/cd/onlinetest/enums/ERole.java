package com.cd.onlinetest.enums;

import java.util.HashSet;
import java.util.Set;

public enum ERole {
  ROLE_STUDENT,
  ROLE_TEACHER,
  ROLE_ADMIN;
  
  public static Set<ERole> getERoles(Set<String> strRoles) {
		Set<ERole> roles = new HashSet<>();
		strRoles.forEach(role -> {
			switch (role) {
			case "admin":
				roles.add(ERole.ROLE_ADMIN);
				break;
			case "mod":
				roles.add(ERole.ROLE_TEACHER);
				break;
			default:
				roles.add(ERole.ROLE_STUDENT);
			}
		});
		return roles;
	}
}
