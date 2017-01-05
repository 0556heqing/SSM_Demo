package com.heqing.bean;

public class Role_Privilege {

	public Role role;
	public Privilege privilege;
	
	public Role_Privilege(Role role, Privilege privilege) {
		this.role = role;
		this.privilege = privilege;
	}
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Privilege getPrivilege() {
		return privilege;
	}
	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}
	
	
}
