package com.lin.test.aboutExcel;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.fastjson.JSONObject;

public class TestExcel {

	
	/**
	 * 办证数量降序排列,排名前10名得4分
	 */
	private static final BigDecimal BIGDECIMAL_FOUR = new BigDecimal(4);
	
	/**
	 * 办证数量降序排列,排名11到20名得3分
	 */
	private static final BigDecimal BIGDECIMAL_THREE = new BigDecimal(3);
	
	/**
	 * 办证数量降序排列,排名21名以后的得2分、 证件全部交回的得2分
	 */
	private static final BigDecimal BIGDECIMAL_TWO = new BigDecimal(2);
	
	/**
	 * 交回比率≧60%且<100%得1分
	 */
	private static final BigDecimal BIGDECIMAL_SIXTY = new BigDecimal(60);
	
	/**
	 * 百分比100%
	 */
	private static final BigDecimal BIGDECIMAL_ONE_HUNDRED = new BigDecimal(100);
	
	private static CellStyle cellStyleCenter = null;
	
	public TestExcel(){
		
	}
	
	public TestExcel(Workbook workbook){
		cellStyleCenter = workbook.createCellStyle();
		cellStyleCenter.setAlignment(CellStyle.ALIGN_CENTER);
		setBaseCellStyle(cellStyleCenter);
		cellStyleCenter.setWrapText(true);//设置自动换行
	}
	
	private static void setBaseCellStyle(CellStyle baseCellStyle) {
		baseCellStyle.setBorderTop(CellStyle.BORDER_THIN); // 上边框
		baseCellStyle.setBorderBottom(CellStyle.BORDER_THIN); // 下边框
		baseCellStyle.setBorderLeft(CellStyle.BORDER_THIN); // 左边框
		baseCellStyle.setBorderRight(CellStyle.BORDER_THIN); // 右边框
	}
	
	private static void setCellStyle(Workbook workbook){
		cellStyleCenter = workbook.createCellStyle();
		cellStyleCenter.setAlignment(CellStyle.ALIGN_CENTER);
		setBaseCellStyle(cellStyleCenter);
		cellStyleCenter.setWrapText(true);//设置自动换行
	}
	
	
	/**
	 * 读取文件流
	 * @auther linmengmeng
	 * @Date 2020-12-30 上午10:22:36
	 * @param path
	 * @return
	 */
	public static FileInputStream getFile(String path){
		FileInputStream fileInputStream = null;
        try {
        	fileInputStream = new FileInputStream(path); // 内容是：abc
            return fileInputStream;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //这种写法，保证了即使遇到异常情况，也会关闭流对象。
                if (fileInputStream != null) {
                	fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileInputStream;
	}
	
	/**
	 * 获取单元格信息
	 * @auther linmengmeng
	 * @Date 2020-12-30 上午10:23:01
	 * @param cell
	 * @return
	 */
	private static BigDecimal getCellValueToBigDecimal(Cell cell){
		if(cell==null){
            return null;
        }
        BigDecimal returnValue = BigDecimal.ZERO;
        switch (cell.getCellType()) {
            case 0://数字
            	cell.setCellType(Cell.CELL_TYPE_STRING);
            	if (StringUtils.isNumeric(cell.getStringCellValue())) {
            		returnValue = new BigDecimal(cell.getStringCellValue());
				}
                break;
            case 1://字符串
            	returnValue = new BigDecimal(cell.getStringCellValue());
                break;
            default:
            	returnValue = BigDecimal.ZERO;
        }
        
        return returnValue;
	}
	
	public static void main(String[] args) {
		try {
			getFs();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
//		BigDecimal bigDecimal = new BigDecimal("43");
//		BigDecimal bigDecimal2 = new BigDecimal("551");
//		System.out.println(bigDecimal.divide(bigDecimal2, 4, BigDecimal.ROUND_HALF_UP));
	}
	
	@Deprecated
	protected static void fillWorkBookWithFormula123(Sheet sheet, int row, int col,
			String formula, CellStyle cellStyle) {
		Row rows = sheet.getRow(row);
		if (rows == null) {
			rows = sheet.createRow(row);
		}
		Cell cell = rows.createCell(col);
		//cell.setCellStyle(cellStyle);
		cell.setCellType(Cell.CELL_TYPE_FORMULA);
		cell.setCellFormula(formula);
	}
	
	protected static void fillWorkBook(Sheet sheet, int row, int col, String value,
			CellStyle cellStyle) {
		Row rows = sheet.getRow(row);
		if (rows == null) {
			rows = sheet.createRow(row);
		}
		Cell cell = rows.createCell(col);
		cell.setCellStyle(cellStyle);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(value);
	}
	
	@SuppressWarnings("resource")
	public static void getFs() throws FileNotFoundException {
		String path = "E:/丹灵云/公安部考勤评分/GA-副本.xls";
		//FileInputStream fileInputStream = getFile(path);
		FileInputStream fileInputStream = new FileInputStream(path);
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
			//Sheet gq_sheet = workbook.getSheet("公务用枪考评统计表");
			Sheet mq_sheet = workbook.getSheet("民用枪支考评统计表");
			Sheet fs_sheet = workbook.getSheet("分数表");
			
			//int rowCount = gq_sheet.getPhysicalNumberOfRows();
			//System.out.println(rowCount);
			
			//Row gq_rows = null;
			/*for(int i = 6;i < 36;i ++){
				gq_rows = gq_sheet.getRow(i);
				String sf = getCellValueToString1(gq_rows.getCell(1));
				System.out.println(sf);
			}*/
			LinkedHashMap<String, BigDecimal> mqHffxScoreMap = getMqHffxScore(mq_sheet, fs_sheet);
			System.out.println("mqHffxScoreMap:" + JSONObject.toJSONString(mqHffxScoreMap));
			//System.out.println("1111:" + getCellValueToString1(fs_sheet.getRow(5).getCell(29)));
//			TestExcel testExcel = new TestExcel(workbook);
			setCellStyle(workbook);
			
			int fs_row_qsh = 4;
			for (Map.Entry<String,BigDecimal> tempEntry : mqHffxScoreMap.entrySet()) {
				fillWorkBook(fs_sheet, fs_row_qsh, 12,  tempEntry.getValue().toString(), cellStyleCenter);
				fs_row_qsh++;
			}
			//System.out.println("1111:" + getCellValueToString(fs_sheet.getRow(5).getCell(12)));
			
			fileInputStream.close();
			long currentTimeMillis = System.currentTimeMillis();
			if (path.indexOf(".xls") > -1) {
				path = path.substring(0, path.length()-4) + "-" + currentTimeMillis + path.substring(path.length()-4, path.length());
			}
			if (path.indexOf(".xlsx") > -1) {
				path = path.substring(0, path.length()-5) + "-" + currentTimeMillis + path.substring(path.length()-5, path.length());
			}
			System.out.println(path);
			//输出文件
			FileOutputStream fileOutputStream = new FileOutputStream(path);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			workbook.write(byteArrayOutputStream);
			fileOutputStream.write(byteArrayOutputStream.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 计算民枪三证总数排名(降序排列)的内部类
	 * @author linmengmeng
	 * @date 2020年12月29日 下午6:04:57
	 */
	static class ZjTotalEntity implements Comparable<ZjTotalEntity>{
		private String sfName;
		private BigDecimal total;
		public String getSfName() {
			return sfName;
		}
		public void setSfName(String sfName) {
			this.sfName = sfName;
		}
		public BigDecimal getTotal() {
			return total;
		}
		public void setTotal(BigDecimal total) {
			this.total = total;
		}
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("{\"sfName\":\"");
			builder.append(sfName);
			builder.append("\", \"total\":\"");
			builder.append(total);
			builder.append("\"}");
			return builder.toString();
		}
		
		@Override
		public int compareTo(ZjTotalEntity zjEntity) {
			//降序排序
			return zjEntity.getTotal().compareTo(this.getTotal());
		}
		
	}
	
	/**
	 * 对map中的值降序排列
	 * @auther linmengmeng
	 * @Date 2020-12-28 下午8:57:37
	 * @param map
	 * @return
	 */
	private static LinkedHashMap<String, BigDecimal> sortMapValueDesc(Map<String,BigDecimal> zjTotalMap){
		LinkedHashMap<String,BigDecimal> linkedHashMap = new LinkedHashMap<String, BigDecimal>();
		
		List<ZjTotalEntity> zjTotalList = new ArrayList<ZjTotalEntity>();
		ZjTotalEntity zjTotalEntity = null;
		for (Map.Entry<String,BigDecimal> tempEntry : zjTotalMap.entrySet()) {
			zjTotalEntity = new ZjTotalEntity();
			zjTotalEntity.setSfName(tempEntry.getKey());
			zjTotalEntity.setTotal(tempEntry.getValue());
			zjTotalList.add(zjTotalEntity);
		}
        Collections.sort(zjTotalList);
        for (ZjTotalEntity entry : zjTotalList) {
        	linkedHashMap.put(entry.getSfName(), entry.getTotal());
		}
		return linkedHashMap;
	}

	
	/**
	 * 计算民枪核发分项得分
	 * @auther linmengmeng
	 * @Date 2020-12-29 上午9:56:07
	 * @param mq_sheet
	 * @param fs_sheet
	 * @return
	 */
	private static LinkedHashMap<String, BigDecimal> getMqHffxScore(Sheet mq_sheet, Sheet fs_sheet){
		Row mq_rows = null;
		LinkedHashMap<String,BigDecimal> zjTotalMap = new LinkedHashMap<String, BigDecimal>(42);//证件总数Map
		Map<String,BigDecimal> hjTotalMap = new HashMap<String, BigDecimal>(42);//回交总数Map
		Map<String,BigDecimal> hjbMap = new HashMap<String, BigDecimal>(42);//回交比Map
		LinkedHashMap<String,BigDecimal> lastScoreMap = new LinkedHashMap<String, BigDecimal>(42);//最后得分Map
		
		BigDecimal zj_total = BigDecimal.ZERO;
		BigDecimal hj_total = BigDecimal.ZERO;
		String sfName = null;
		for(int i = 4;i < mq_sheet.getPhysicalNumberOfRows();i ++){
			mq_rows = mq_sheet.getRow(i);
			sfName = getCellValueToString(mq_rows.getCell(1));
			lastScoreMap.put(sfName, BigDecimal.ZERO);
			
			mq_rows = mq_sheet.getRow(i);
			zj_total = getCellValueToBigDecimal(mq_rows.getCell(10));
			zj_total = zj_total.add(getCellValueToBigDecimal(mq_rows.getCell(12)));
			zj_total = zj_total.add(getCellValueToBigDecimal(mq_rows.getCell(14)));
			zjTotalMap.put(sfName, zj_total);
			
			hj_total = getCellValueToBigDecimal(mq_rows.getCell(11));
			hj_total = hj_total.add(getCellValueToBigDecimal(mq_rows.getCell(13)));
			hj_total = hj_total.add(getCellValueToBigDecimal(mq_rows.getCell(15)));
			hjTotalMap.put(sfName, hj_total);
			
			hjbMap.put(sfName, zj_total.compareTo(BigDecimal.ZERO)==0?BigDecimal.ZERO:hj_total.divide(zj_total, 4, BigDecimal.ROUND_HALF_UP).multiply(BIGDECIMAL_ONE_HUNDRED));
		}
		System.out.println("zjTotalMap:" + JSONObject.toJSONString(zjTotalMap));
		System.out.println("hjTotalMap:" + JSONObject.toJSONString(hjTotalMap));
		System.out.println("hjbMap:" + JSONObject.toJSONString(hjbMap));
		
		//三证总数倒序排列
		zjTotalMap = sortMapValueDesc(zjTotalMap);
		System.out.println("sort zjTotalMap desc:" + JSONObject.toJSONString(zjTotalMap));
		
		int index = 0;
		BigDecimal tempValue = BigDecimal.ZERO;
		String tempSfName = "";
		
		/**
		 * 计算三证总数得分: 办证数量降序排列，排名前10名得4分，排名11到20名得3分，排名21名以后的得2分
		 */
		for (Map.Entry<String,BigDecimal> tempEntry : zjTotalMap.entrySet()) {
			tempSfName = tempEntry.getKey();
			tempValue = tempEntry.getValue();
			if (BigDecimal.ZERO.compareTo(tempValue) == 0) {//如果总数为零， 不考虑排名，直接加两分
				lastScoreMap.put(tempSfName, BIGDECIMAL_TWO);
				index++;
				continue;
			}
			if (index < 10) {
				lastScoreMap.put(tempSfName, BIGDECIMAL_FOUR);
			}else if (index >= 10 && index < 20) {
				lastScoreMap.put(tempSfName, BIGDECIMAL_THREE);
			}else {
				lastScoreMap.put(tempSfName, BIGDECIMAL_TWO);
			}
			index++;
		}
		System.out.println("sanZhengScore:" + JSONObject.toJSONString(lastScoreMap));
		
		/**
		 * 2. 在规定时间，全部交回的得2分，交回比率≧60%且<100%得1分，交回比率低于60%不得分；
		 */
		for (Map.Entry<String,BigDecimal> tempEntry : hjbMap.entrySet()) {
			tempSfName = tempEntry.getKey();
			tempValue = tempEntry.getValue();
			if (BIGDECIMAL_SIXTY.compareTo(tempValue) > 0) {
				continue;
			}
			if (BIGDECIMAL_ONE_HUNDRED.compareTo(tempValue) == 0) {
				lastScoreMap.put(tempSfName, lastScoreMap.get(tempSfName).add(BIGDECIMAL_TWO));
			}else {
				lastScoreMap.put(tempSfName, lastScoreMap.get(tempSfName).add(BigDecimal.ONE));
			}
		}
		
		System.out.println("lastScoreMap:" + JSONObject.toJSONString(lastScoreMap));
		return lastScoreMap;
	}
	
	private static String getCellValueToString(Cell cell){
		if(cell==null){
            return null;
        }
        String returnValue = null;
        switch (cell.getCellType()) {
            case 0://数字
            	cell.setCellType(Cell.CELL_TYPE_STRING);
            	returnValue = cell.getStringCellValue();
                break;
            case 1://字符串
                returnValue = cell.getStringCellValue();
                break;
            default:
        }
        
        return returnValue;
	}
}