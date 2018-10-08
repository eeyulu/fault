package com.xxl.sso.core.user;

import java.io.Serializable;

/**
 * xxl sso user
 *
 */
public class XxlUser implements Serializable {
    private static final long serialVersionUID = 42L;

    private String userid;
    private String username;
    private String staffNo;
    private String realname;
    private String photo;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    
    public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	
}
