package com.lin.test.excel.write;

import java.util.List;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.exception.ExcelGenerateException;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteWorkbook;

/**
 * 自定义 easyExcel 导出excel的工具类
 * @author linmengmeng
 * @date 2020年8月11日 下午2:48:07
 * @param <T> 表头实体类
 */
public class BaseExcelWriter extends ExcelWriter {
	
	public BaseExcelWriter(WriteWorkbook writeWorkbook) {
		super(writeWorkbook);
	}
	
	protected static final String DEFAULT_SHEET_NAME = "Sheet1";
	
	protected static final Integer DEFAULT_SHEET_NO = 1;
	
	protected static final int DEFAULT_HEAD_LINE_Num = 1;
	
	protected WriteSheet writeSheet;
	
	/**
	 * custom writeSheet
	 * @return
	 */
	protected void setWriteSheet(Integer sheetNo, String sheetName){
		WriteSheet writeSheet = new WriteSheet();
		writeSheet.setSheetNo(sheetNo);
		writeSheet.setSheetName(sheetName); 
		this.writeSheet = writeSheet;
	}
	
	protected WriteSheet getWriteSheet(){
		if (writeSheet == null) {
			setWriteSheet(DEFAULT_SHEET_NO, DEFAULT_SHEET_NAME);
		}
		return writeSheet;
	}
	
	
	/**
	 * custom headLine
	 * @return
	 */
	protected int getHeadLineNum(){
		return DEFAULT_HEAD_LINE_Num;
	}	
	
    /**
     * 由子类自定义实现 处理待输出的内容 
     * 	如：数据库查出来的code 处理成中文
     * @param objectList
     * @return
     */
    protected List<? extends BaseModel> formateData(List<? extends BaseModel> objectList){
    	return objectList;
    }
	
    /**
     * 输出缓冲层  往第一个sheet的第一张表中写入数据
     * 由于数据库查出来的是LinkedMap，这里加了formateData转换List<Object> objectList至List<T>
     * 未测试分页查询循环写入
     * @auther linmengmeng
     * @Date 2020-08-12 下午5:05:19
     * @param objectList
     * @param baseExcelWriter
     * @return
     */
	public ExcelWriter consumeWrite(List<? extends BaseModel> objectList, BaseExcelWriter baseExcelWriter) {
		if (objectList == null || objectList.isEmpty()) {
			return this;
		}
    	if (baseExcelWriter == null) {
			throw new IllegalArgumentException("baseExcelWriter can not be null");
		}
    	List<? extends BaseModel> data = baseExcelWriter.formateData(objectList);
    	if (data == null || data.isEmpty()) {
			throw new ExcelGenerateException("the output must not be empty");
		}
    	super.write(data, baseExcelWriter.getWriteSheet());
        return this;
    }
	
	/**
	 * 分页查询循环写入
	 */
//	public ExcelWriter repeatedWrite(List<Object> objectList, BaseExcelWriter<T> baseExcelWriter, WriteSheet writeSheet) {
//		this = consumeWrite(objectList, baseExcelWriter, writeSheet);
//		return baseExcelWriter;
//	}
    
}