package com.lin.test.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lin.test.excel.write.BaseModel;

public class MyUser extends  BaseModel {
	@ExcelProperty(value ={"82班记录表","姓名"},index = 0)
	private String name;
	@ExcelProperty(value ={"82班记录表","年龄"},index = 1)
	private Integer age;
	@ExcelProperty(value ={"82班记录表","学号"},index = 2)
	private String idNum;
	
	public MyUser() {
		super();
	}
	public MyUser(String name, Integer age, String idNum) {
		super();
		this.name = name;
		this.age = age;
		this.idNum = idNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"name\":\"");
		builder.append(name);
		builder.append("\", \"age\":\"");
		builder.append(age);
		builder.append("\", \"idNum\":\"");
		builder.append(idNum);
		builder.append("\"}");
		return builder.toString();
	}
}