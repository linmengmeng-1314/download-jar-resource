package com.lin.test.excel.write;

import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.exception.ExcelGenerateException;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;

public class CustomEasyExcel extends EasyExcel {
	
	private static String DEFAULT_SHEET_NAME = "Sheet1";
	
	private static Integer DEFAULT_SHEET_NO = 0;
	
	protected WriteSheet getWriteSheet(){
		//ExcelWriterSheetBuilder excelWriterSheetBuilder = MyCustomEasyExcel.writerSheet(DEFAULT_SHEET_NO, DEFAULT_SHEET_NAME);
		WriteSheet writeSheet = new WriteSheet();
		writeSheet.setSheetNo(DEFAULT_SHEET_NO);
		writeSheet.setSheetName(DEFAULT_SHEET_NAME);
		return writeSheet;
	}
	
	/**
	 * 自定义Excel样式内容
	 * @return
	 */
	protected HorizontalCellStyleStrategy customCellStyle(){
		return getDefaultStyle();
	}
	
	/**
	 * 自定义Excel样式 头的策略
	 * @return
	 */
	protected WriteCellStyle customHeadWriteCellStyle(WriteCellStyle headWriteCellStyle){
		if (headWriteCellStyle == null) {
			headWriteCellStyle = new WriteCellStyle();
			// 背景色
	        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	        WriteFont headWriteFont = new WriteFont();
	        headWriteFont.setFontHeightInPoints((short) 12);
	        headWriteCellStyle.setWriteFont(headWriteFont);
		}
		return headWriteCellStyle;
	}
	
	/**
	 * 自定义Excel样式 内容的策略
	 * @return
	 */
	protected WriteCellStyle customContentWriteCellStyle(WriteCellStyle contentWriteCellStyle){
		if (contentWriteCellStyle == null) {
			contentWriteCellStyle = new WriteCellStyle();
			WriteFont contentWriteFont = new WriteFont();
	        // 字体大小
	        contentWriteFont.setFontHeightInPoints((short) 12);
	        contentWriteCellStyle.setWriteFont(contentWriteFont);
	        //设置 自动换行
	        contentWriteCellStyle.setWrapped(true);
	        //设置 垂直居中
	        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//	        //设置 水平居中
	        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
//	        //设置边框样式
	        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
	        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
	        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
	        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
		}
		return contentWriteCellStyle;
	}
	
	
	/**
	 * 默认Excel样式内容
	 * 默认灰色背景表头，宋体 12号 表头字体加粗 单线边框
	 * @return
	 */
	private HorizontalCellStyleStrategy getDefaultStyle(){
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现,自己实现后将实现类传入writeSheet.registerWriteHandler()
		return new HorizontalCellStyleStrategy(customHeadWriteCellStyle(null), customContentWriteCellStyle(null));
	}

	/**
	 * 单个数据源，写入单个sheet里面
	 * @auther linmengmeng
	 * @Date 2020-08-18 下午3:47:29
	 * @param objectList
	 * @param customEasyExcel
	 */
	public void customWriteOneSheet(List<? extends BaseModel> objectList, CustomEasyExcel customEasyExcel, ExcelWriter excelWriter){
    	if (customEasyExcel == null) {
			throw new IllegalArgumentException("customEasyExcel can not be null");
		}
    	List<? extends BaseModel> data = customEasyExcel.formateData(objectList);
    	if (data == null || data.isEmpty()) {
			throw new ExcelGenerateException("the output must not be empty");
		}
    	WriteSheet writeSheet;
    	writeSheet = EasyExcel.writerSheet(0, "默认sheetName")
                .head(objectList.get(0).getClass())
//                .registerWriteHandler(this.customCellStyle())
                .registerWriteHandler(customEasyExcel.customCellStyle())
                //.registerWriteHandler(new CustomCellWriteHandler())
                .build();
        // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
    	//List<MyUser> data = OldTestWriteExcel.getUser();
        excelWriter.write(data, writeSheet);
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
}
