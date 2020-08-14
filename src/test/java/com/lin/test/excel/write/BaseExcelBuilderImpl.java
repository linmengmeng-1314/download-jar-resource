package com.lin.test.excel.write;

/**
 * 
 * 忘了这个类是干嘛使得了
 * 
 * 应该是导出excel文件时用到的中间类  后来放弃自定义了
 */

//import java.util.List;
//
//import org.apache.commons.beanutils.BeanUtils;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//
//import com.alibaba.excel.context.WriteContext;
//import com.alibaba.excel.metadata.ExcelColumnProperty;
//import com.alibaba.excel.metadata.Sheet;
//import com.alibaba.excel.metadata.Table;
//import com.alibaba.excel.write.ExcelBuilderImpl;
//import com.alibaba.excel.write.metadata.WriteWorkbook;
//
//public class BaseExcelBuilderImpl extends ExcelBuilderImpl {
//
//    public BaseExcelBuilderImpl(WriteWorkbook writeWorkbook) {
//		super(writeWorkbook);
//		// TODO Auto-generated constructor stub
//	}
//
//	private WriteContext context;
//	
//    public void addContent(List data, Sheet sheetParam, Table table) {
//        context.buildCurrentSheet(sheetParam);
//        context.buildTable(table);
//        addContent(data);
//    }
//    
//    public void addContent(List data) {
//        if (data != null && data.size() > 0) {
//            int rowNum = context.getCurrentSheet().getLastRowNum();
//            if (rowNum == 0) {
//                Row row = context.getCurrentSheet().getRow(0);
//                if(row ==null) {
//                    if (context.getExcelHeadProperty() == null || !context.needHead()) {
//                        rowNum = -1;
//                    }
//                }
//            }
//            for (int i = 0; i < data.size(); i++) {
//                int n = i + rowNum + 1;
//                addOneRowOfDataToExcel(data.get(i), n);
//            }
//        }
//    }
//    
//    private void addOneRowOfDataToExcel(Object oneRowData, Row row) {
//        int i = 0;
//        for (ExcelColumnProperty excelHeadProperty : context.getExcelHeadProperty().getColumnPropertyList()) {
//            Cell cell = row.createCell(i);
//            cell.setCellStyle(context.getCurrentContentStyle());
//            String cellValue = null;
//            try {
//                cellValue = BeanUtils.getProperty(oneRowData, excelHeadProperty.getField().getName());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            if (cellValue != null) {
//                cell.setCellValue(cellValue);
//            } else {
//                cell.setCellValue("");
//            }
//            i++;
//        }
//    }
//
//    private void addOneRowOfDataToExcel(Object oneRowData, int n) {
//        Row row = context.getCurrentSheet().createRow(n);
//        if (oneRowData instanceof List) {
//            addOneRowOfDataToExcel((List<String>)oneRowData, row);
//        } else {
//            addOneRowOfDataToExcel(oneRowData, row);
//        }
//    }
//}
