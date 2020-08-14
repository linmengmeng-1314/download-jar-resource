package com.lin.test.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.lin.test.excel.entity.MyUser;
import com.lin.test.excel.write.OldBaseExcelWriter;

/**
 * 使用1.0.4版本的easyExcel
 * 
 * 由于目前版本不支持参数校验，所以读取上传excel的内容时，可以自定义参数校验
 * 
 * 此版本不支持表格内容格式自定义 表头默认  居中 宋体 12  灰色背景色
 * 
 * @author linmengmeng
 * @date 2020年8月13日 下午9:19:55
 */
public class OldTestWriteExcel {

	// 文件输出位置
    private static String outPath = "C:\\Users\\jadl\\Desktop\\testWrite.xlsx";
    
    public static void main(String[] args) {
    	FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(outPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	OldBaseExcelWriter<MyUserHead> excelWriter = new OldBaseExcelWriter<MyUserHead>(outputStream, ExcelTypeEnum.XLSX, true);
    	//excelWriter.consumeWrite(getUser());
    	// 记得 释放资源
        excelWriter.finish();
        
        System.out.println("------ok-----");
	}
	
	public static void test1() {
		List<MyUser> userList = getUser();
		
		//ExcelWriter excelWriter = EasyExcelFactory.getWriter(new FileOutputStream(outPath));
		
		try {
			FileOutputStream outputStream = new FileOutputStream(outPath);
			ExcelWriter excelWriter = new ExcelWriter(outputStream, ExcelTypeEnum.XLSX, true);
			// 表单
            Sheet sheet = new Sheet(1,0);
            sheet.setSheetName("第一个Sheet");
            // 创建一个表格
            Table table = new Table(1);
            // 动态添加 表头 headList --> 所有表头行集合
            List<List<String>> headList = new ArrayList<List<String>>();
            // 第 n 行 的表头
            List<String> headTitle0 = new ArrayList<String>();
            List<String> headTitle1 = new ArrayList<String>();
            List<String> headTitle2 = new ArrayList<String>();
            List<String> headTitle3 = new ArrayList<String>();
            List<String> headTitle4 = new ArrayList<String>();
            List<String> headTitle5 = new ArrayList<String>();
            List<String> headTitle6 = new ArrayList<String>();
            headTitle0.add("枪支弹药本月明细");
            headTitle0.add("标识");
            headTitle1.add("枪支弹药本月明细");
            headTitle1.add("种类");
            headTitle2.add("枪支弹药本月明细");
            headTitle2.add("型号");
            headTitle3.add("枪支弹药本月明细");
            headTitle3.add("枪号");
            headTitle4.add("枪支弹药本月明细");
            headTitle4.add("生产日期");
            headTitle5.add("枪支弹药本月明细");
            headTitle5.add("生产厂家");
            headTitle6.add("枪支弹药本月明细");
            headTitle6.add("购买日期");

            headList.add(headTitle0);
            headList.add(headTitle1);
            headList.add(headTitle2);
            headList.add(headTitle3);
            headList.add(headTitle4);
            headList.add(headTitle5);
            headList.add(headTitle6);
            //table.setHead(headList);
            //table.setHead(MyUser.class);
            //table.setClazz(MyUser.class);

            excelWriter.write(userList,sheet,table);
            // 记得 释放资源
            excelWriter.finish();
            System.out.println("ok");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public static List<MyUser> getUser(){
		List<MyUser> myUsers = new ArrayList<MyUser>();
		MyUser myUser = null;
		for (int i = 0; i < 5; i++) {
			if (i == 2) {
				myUser = new MyUser();
			}else if (i == 3) {
				myUser = new MyUser("name" + i, null, "idNum" + i);
			}else {
				myUser = new MyUser("name" + i, i+1, "idNum" + i);
			}
			myUsers.add(myUser);
		}
		return myUsers;
	}
	
	public static List<List<String>> getBaseRowModelValue(List<BaseRowModel> modelList){
		List<List<String>> resultList = new ArrayList<List<String>>();
		List<String> tempModelValueList = new ArrayList<String>();
		if (modelList != null && modelList.size() > 0) {
			BaseRowModel tempRowModel = null;
			for (int i = 0; i < modelList.size(); i++) {
				tempRowModel = modelList.get(0);
				if (tempRowModel != null) {
					
				}
			}
			tempModelValueList = new ArrayList<String>();
		}
		return resultList;
	}
	
	public static class MyUserHead extends BaseRowModel{
		@ExcelProperty(value ={"82班记录表","姓名"},index = 0)
		private String name1;
		@ExcelProperty(value ={"82班记录表","年龄"},index = 1)
		private Integer age1;
		@ExcelProperty(value ={"82班记录表","学号"},index = 2)
		private String idNum1;
		public String getName1() {
			return name1;
		}
		public void setName1(String name1) {
			this.name1 = name1;
		}
		public Integer getAge1() {
			return age1;
		}
		public void setAge1(Integer age1) {
			this.age1 = age1;
		}
		public String getIdNum1() {
			return idNum1;
		}
		public void setIdNum1(String idNum1) {
			this.idNum1 = idNum1;
		}
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("{\"name1\":\"");
			builder.append(name1);
			builder.append("\", \"age1\":\"");
			builder.append(age1);
			builder.append("\", \"idNum1\":\"");
			builder.append(idNum1);
			builder.append("\"}");
			return builder.toString();
		}
	}
}
