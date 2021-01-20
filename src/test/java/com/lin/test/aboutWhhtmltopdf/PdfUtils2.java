package com.lin.test.aboutWhhtmltopdf;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import freemarker.template.Configuration;
import freemarker.template.Template;

/** 
 * @ClassName: HtmlToPdf 
 * @Description: TODO() 
 * @author xsw
 * @date 2016-12-8 上午10:14:54 
 *  
 */

public class PdfUtils2 {
    //wkhtmltopdf在系统中的路径
	
	private static final Logger logger = LoggerFactory.getLogger(PdfUtils2.class);
	
	
	@SuppressWarnings("deprecation")
	private void test(){
		Configuration configuration = new Configuration();
		
//		File file = new File("C:/Users/jadl/Desktop/detail.html");
//		String resource = "C:/Users/jadl/Desktop/detail.ftl";//mars正常文件
		String resource = "C:/Users/jadl/Desktop/word.ftl";//测试申请表
		
		JSONObject jsonObject = new JSONObject();
		
		JSONObject data = new JSONObject();
		data.put("name", "蔺猛猛");
		data.put("sex", "男");
		jsonObject.put("res", data);
		
//		ClassPathResource resource = new ClassPathResource("templates/detail.ftl");
		Template temp = null;
		String reportName = "";
//		FileInputStream stream = null;
//		String rootPath = OSUtils.isLinux()?"/":"C:\\";
		try {
//			TemplateLoader loader = new FileTemplateLoader(resource.getFile().getParentFile());
//			configuration.setTemplateLoader(loader);
			temp = configuration.getTemplate(resource);
			//以classpath下面的static目录作为静态页面的存储目录，同时命名生成的静态html文件名称
			StringWriter out = new StringWriter();
			Map<String,Object> hashMap = new HashMap<String,Object>();
//			hashMap.put("res", jsonObject);
			hashMap.put("res", data);
			temp.process(hashMap, out);
			
			String srcPath = temp.toString();
			String destPath = "E:/baidu" + System.currentTimeMillis() + ".pdf";
			String toolSrc = "D:\\wkhtmltopdf\\bin\\wkhtmltopdf.exe";
	    	boolean convert = PdfUtils2.convert(srcPath, destPath, toolSrc);
	    	System.out.println(convert);
//			reportName = "report/"+candidateInfoEntity.getFkDeptId()+"/"+candidateInfoEntity.getFkOrderId();
//			logger.info("生成报告", reportName, "com.csi.modules.orderDetail.service.impl.OrderDetailServiceImpl.createPdf", this.getClass(), false);
//			OSSUtils.upload(out.toString(), reportName+".html");
//			logger.info("报告上传至阿里云", reportName+".html", "com.csi.modules.orderDetail.service.impl.OrderDetailServiceImpl.createPdf", this.getClass(), false);

//			String url = OSSUtils.getUrl(reportName+".html",1000*3600);
//			logger.info("获取报告路径", url, "com.csi.modules.orderDetail.service.impl.OrderDetailServiceImpl.createPdf", this.getClass(), false);

			
//			PdfUtils.convert(url, rootPath+reportName+".pdf",pdfToolSrc);
//			logger.info("生成PDF", url, "com.csi.modules.orderDetail.service.impl.OrderDetailServiceImpl.createPdf", this.getClass(), false);

//			stream = new FileInputStream(new File(rootPath+reportName+".pdf"));
//			logger.info("上传PDF", reportName+".pdf", "com.csi.modules.orderDetail.service.impl.OrderDetailServiceImpl.createPdf", this.getClass(), false);

		} catch (Exception e) {
			logger.error("生成PDF错误", e, false);
			e.printStackTrace();
		}finally {
//			File file = new File(rootPath+reportName+".pdf");
			File file = new File("rootPath"+reportName+".pdf");
			if(file.isFile()) {
				file.delete();
			}
			/*if(stream == null) {
				try {
					stream.close();
				} catch (IOException e) {
				}
			}*/
		}
	}
	
	
	
    
    /**
     * html转pdf
     * @param srcPath html路径，可以是硬盘上的路径，也可以是网络路径
     * @param destPath pdf保存路径
     * @param toolSrc 工具位置
     * @return 转换成功返回true
     */
    public static boolean convert(String srcPath, String destPath,String toolSrc){
        File file = new File(destPath);
        File parent = file.getParentFile();
        //如果pdf保存路径不存在，则创建路径
        if(!parent.exists()){
            parent.mkdirs();
        }
        
        StringBuilder cmd = new StringBuilder();
        cmd.append(toolSrc);
		cmd.append(" ");
		// cmd.append(" --header-line");// 页眉下面的线
		// cmd.append(" --title 生成的PDF文件的标题 ");//生成的PDF文件的标题
		// cmd.append(" --header-center 这这里是页眉这里是页眉 ");//页眉中间内容
		cmd.append("  --margin-top 20mm ");// 设置页面上边距 (default 10mm)
		cmd.append("  --margin-bottom 20mm ");// 设置页面下边距 (default 10mm)
		cmd.append("  --margin-left 25mm ");// 设置页面上边距 (default 10mm)
		cmd.append("  --margin-right 25mm ");// 设置页面上边距 (default 10mm)
		// cmd.append(" --header-html file://"https://blog.csdn.net/x6582026/article/details/53835835"); (添加一个HTML页眉,后面是网址)
		cmd.append(" --header-spacing 10 ");// (设置页眉和内容的距离,默认0)
		// cmd.append(" --footer-center 第[page]页／共[topage]页");//设置在中心位置的页脚内容
		// cmd.append(" --footer-line");// * 显示一条线在页脚内容上)
		cmd.append(" --footer-spacing 10 ");// (设置页脚和内容的距离)
		cmd.append(srcPath);
		cmd.append(" ");
		cmd.append(destPath);

		boolean result = true;
        try{
            Process proc = Runtime.getRuntime().exec(cmd.toString());
//            HtmlToPdfInterceptor error = new HtmlToPdfInterceptor(proc.getErrorStream());
//            HtmlToPdfInterceptor output = new HtmlToPdfInterceptor(proc.getInputStream());
//            error.start();
//            output.start();
            proc.waitFor();
        }catch(Exception e){
            result = false;
            logger.error("PDF生成错误", e, false);;
        }
        
        return result;
    }
    
    public static void main1(String[] args) {
    	PdfUtils2 pdfUtils = new PdfUtils2();
    	pdfUtils.test();
	}
    
    public static void main(String[] args) {
    	//String srcPath = "http://img.huoxingbeidiao.com/report/53/601106647783444480.html?Expires=1563439218&OSSAccessKeyId=LTAI3pOddsvvLO0c&Signature=TFGJplcT%2FpHkdoetTyXOwH2%2FcDI%3D";
    	String srcPath = "www.baidu.com";
    	
    	File file = new File("C:/Users/jadl/Desktop/detail.html");
//    	File file = new File("E:/baidu/1610446490743word.html");
    	
    	
    	
    	srcPath = file.toString();
    	
    	String destPath = "E:/baidu/" + System.currentTimeMillis() + ".pdf";
//    	String toolSrc = "E:\\wkhtmltopdf\\bin\\wkhtmltopdf.exe";
    	String toolSrc = "D:\\wkhtmltopdf\\bin\\wkhtmltopdf.exe";
    	boolean convert = PdfUtils2.convert(srcPath, destPath, toolSrc);
    	System.out.println(convert);
    }
    static class HtmlToPdfInterceptor extends Thread {
    	private InputStream is;
    	
    	public HtmlToPdfInterceptor(InputStream is){
    		this.is = is;
    	}
    	
    	public void run(){
    		try{
    			InputStreamReader isr = new InputStreamReader(is, "utf-8");
    			BufferedReader br = new BufferedReader(isr);
    			String line = null;
    			while ((line = br.readLine()) != null) {
    				System.out.println(line.toString()); //输出内容
    			}
    		}catch (IOException e){
    			e.printStackTrace();
    		}
    	}
    }
}
