package com.fgroupindonesia.fgimobilebaru.object;

public class Attendance {

	private int id;
	private String username;
	private String class_registered;
	private String status;
	private String signature;
	private String date_created;
	private String date_modified;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getClass_registered() {
		return class_registered;
	}

	public void setClass_registered(String class_registered) {
		this.class_registered = class_registered;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getDate_created() {
		return date_created;
	}

	public void setDate_created(String date_created) {
		this.date_created = date_created;
	}

	public String getDate_modified() {
		return date_modified;
	}

	public void setDate_modified(String date_modified) {
		this.date_modified = date_modified;
	}
}
