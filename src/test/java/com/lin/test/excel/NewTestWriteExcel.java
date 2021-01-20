package com.lin.test.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteWorkbook;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.lin.test.excel.entity.MyUser;
import com.lin.test.excel.write.BaseExcelWriter;
import com.lin.test.excel.write.CustomEasyExcel;

/**
 * 使用目前最新版本2.2.6版本的easyExcel
 * @author linmengmeng
 * @date 2020年8月13日 下午9:19:55
 */
public class NewTestWriteExcel{


	// 文件输出位置
    private static String outPath_xlsx = "C:\\Users\\jadl\\Desktop\\testWrite.xlsx";
    
    private static String outPath_xls = "C:\\Users\\jadl\\Desktop\\testWrite.xls";
    
    public static void main(String[] args) {
		//consumTest();
    	//consumTestStyle();
    	customMyResourceCode();
    	System.out.println("-----0k-----");
	}
    
    public static void customMyResourceCode() {
    	FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(outPath_xls);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		List<MyUser> userList = OldTestWriteExcel.getUserAll();
		
		WriteWorkbook writeWorkbook = new WriteWorkbook();
		writeWorkbook.setClazz(MyUser.class);
		writeWorkbook.setExcelType(ExcelTypeEnum.XLS);
		writeWorkbook.setOutputStream(outputStream);
		
		WriteSheet writeSheet = new WriteSheet();
		writeSheet.setSheetNo(1);
		writeSheet.setSheetName("1手动设置sheetName");
		
		ExcelWriter excelWriter = new ExcelWriter(writeWorkbook);
		excelWriter.write(userList, writeSheet);
		excelWriter.write(userList, writeSheet);
		
		excelWriter.finish();
		try {
			outputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void customBaseTestStyle() {
    	
    	FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(outPath_xlsx);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
		CustomEasyExcel customEasyExcel = new CustomEasyExcel();
		List<MyUser> userList = OldTestWriteExcel.getUser();
		customEasyExcel.customWriteOneSheet(userList, customEasyExcel, excelWriter);
	    // finish 关闭流
	    excelWriter.finish();
    	
    }
    
    /**
     * 带样式的输出   不同的对象可以输出到同一个excel
     * https://www.cnblogs.com/Hizy/p/11825886.html
     * @auther linmengmeng
     * @Date 2020-08-13 下午7:55:50
     */
    public static void consumTestStyle() {
    	
    	// 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 12);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
//        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
//        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 背景绿色
//        contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        // 字体策略
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 12);
        contentWriteCellStyle.setWriteFont(contentWriteFont);

        //设置 自动换行
        contentWriteCellStyle.setWrapped(true);
        //设置 垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//        //设置 水平居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
//        //设置边框样式
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);

        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        // 方法3 如果写到不同的sheet 不同的对象
        // 这里 指定文件
        ExcelWriter excelWriter = EasyExcel.write(outPath_xlsx).build();
        // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
        WriteSheet writeSheet;
        List<MyUser> userList = OldTestWriteExcel.getUser();
        //List<SheetAndMapListDto> testList = excelData.getSheetAndMapListDtoList();
        for (int i = 0; i < userList.size(); i++) {
            // 每次都要创建writeSheet 这里注意必须指定sheetNo。这里注意DemoData.class 可以每次都变，我这里为了方便 所以用的同一个class 实际上可以一直变
        	//并且不同sheet的sheetName一定要不同，否则即使指定了sheetNo，但是内容还是会输出到同一个sheet
            writeSheet = EasyExcel.writerSheet(i, "默认sheetName" + i)
                    .head(MyUser.class)
                    .registerWriteHandler(horizontalCellStyleStrategy)
                    //.registerWriteHandler(new CustomCellWriteHandler())
                    .build();
            // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
            excelWriter.write(userList, writeSheet);
        }
        // finish 关闭流
        excelWriter.finish();
        System.out.println("------ok-----");
    }
    public static void consumTest() {
    	FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(outPath_xlsx);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	List<MyUser> userList = OldTestWriteExcel.getUser();
    	WriteWorkbook writeWorkbook = new WriteWorkbook();
    	writeWorkbook.setClazz(MyUser.class);
    	writeWorkbook.setExcelType(ExcelTypeEnum.XLSX);
    	writeWorkbook.setOutputStream(outputStream);
    	BaseExcelWriter baseExcelWriter = new BaseExcelWriter(writeWorkbook);
    	baseExcelWriter.consumeWrite(userList , baseExcelWriter );
    	baseExcelWriter.finish();
    	System.out.println("------ok-----");
	}
    
    public static void simpleTest() {
    	FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(outPath_xlsx);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	List<MyUser> userList = OldTestWriteExcel.getUser();
    	
		// 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = EasyExcel.write(outputStream, MyUser.class).build();
        // 这里注意 如果同一个sheet只要创建一次
        WriteSheet writeSheet = EasyExcel.writerSheet("模板1").build();
		
        //writeSheet.setSheetName("模板");
        excelWriter.write(userList, writeSheet);
//        for (int i = 0; i < 5; i++) {
//            // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
//            //List<DemoData> data = data();
//        }
        /// 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
        
        System.out.println("------ok-----");
	}
}
