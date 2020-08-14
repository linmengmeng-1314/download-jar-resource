package com.lin.test.excel.write;


import java.io.OutputStream;
import java.util.List;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.exception.ExcelGenerateException;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;

public class OldBaseExcelWriter<T extends BaseRowModel> extends ExcelWriter {
	
	protected static final String DEFAULT_SHEET_NAME = "Sheet1";
	
	protected Sheet sheet;
	
	protected Table table;
	
	private T t;
	
	protected Sheet getSheet(){
		sheet = new Sheet(1,0);
        sheet.setSheetName(DEFAULT_SHEET_NAME);
        return sheet;
	}
	
	protected Table getTable(){
		table = new Table(1);
		table.setClazz(t.getClass());
		return table;
	}

	public OldBaseExcelWriter(OutputStream outputStream, ExcelTypeEnum typeEnum) {
		super(outputStream, typeEnum);
	}

	public OldBaseExcelWriter(OutputStream outputStream, ExcelTypeEnum typeEnum, boolean needHead) {
		super(outputStream, typeEnum, needHead);
	}
	
    /**
     * 可生成多sheet,每个sheet多张表
     *
     * @param data  type 一个java模型一行数据
     * @param sheet data写入某个sheet
     * @param table data写入某个table
     * @return this（当前引用）
     */
    @SuppressWarnings("unchecked")
	public ExcelWriter consumeWrite(List<? extends BaseRowModel> data) {
    	if (data == null || data.size() <= 0) {
			throw new ExcelGenerateException("excel中待输出内容不能为空");
		}
    	t = (T) data.get(0);
    	super.write(data, getSheet(), getTable());
        return this;
    }
}
