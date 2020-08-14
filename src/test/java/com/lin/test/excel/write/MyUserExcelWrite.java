package com.lin.test.excel.write;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.write.metadata.WriteWorkbook;
import com.lin.test.excel.entity.MyUser;

public class MyUserExcelWrite extends BaseExcelWriter{

	public MyUserExcelWrite(WriteWorkbook writeWorkbook) {
		super(writeWorkbook);
	}

	@Override
	protected List<? extends BaseModel> formateData(List<? extends BaseModel> objectList) {
		List<MyUser> myUsers = new ArrayList<MyUser>();
		MyUser myUser = null;
		for (BaseModel baseModel : objectList) {
			if (baseModel instanceof MyUser) {
				myUser = (MyUser) baseModel;
				myUsers.add(myUser);
			}
		}
		return myUsers;
	}

}
