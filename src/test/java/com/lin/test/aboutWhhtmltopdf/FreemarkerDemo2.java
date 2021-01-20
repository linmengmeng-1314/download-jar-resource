package com.lin.test.aboutWhhtmltopdf;
 
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
 
/**
 * @author xch
 * @description freemarker工具
 * @date 2019-10-14
 */
public class FreemarkerDemo2 {
 
    public static void test(String path){
        Map<String,Object> dataMap = new HashMap<>();
        try {
            /*dataMap.put("name", "张三");
            dataMap.put("idnumber", "411322199308302019");
            dataMap.put("result1", "□");
            dataMap.put("result2", (char)8730);// (char)8730 对号
            dataMap.put("result3", "□");
            dataMap.put("result4", "□");
            dataMap.put("result5", "□");
            dataMap.put("date", new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
            dataMap.put("ipAddress", "110.0.0.1");
            dataMap.put("confirmedBy", "xch");
            dataMap.put("ipAddress", "110.0.0.1");
            dataMap.put("datetime", new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));*/
        	
        	dataMap.put("name", "Tom");
        	dataMap.put("sex", "man");
        	dataMap.put("dept", "丹灵公司");
        	
        	JSONObject jsonObject = new JSONObject();
    		
    		JSONObject cover = new JSONObject();
    		cover.put("mandator", "Tom");
    		cover.put("candidate", "lmm");
    		jsonObject.put("cover", cover);
 
            Configuration configuration = new Configuration(new Version("2.3.0"));
            configuration.setDefaultEncoding("utf-8");
 
            //.ftl配置文件所在路径
            //第一种使用相对一个java文件的相对路径
//          configuration.setClassForTemplateLoading(new FreemarkerDemo().getClass(), "/doc/");
 
            //第二种使用绝对路径	C:/Users/jadl/Desktop/detail.html
//            configuration.setDirectoryForTemplateLoading(new File("c:/image/"));
            configuration.setDirectoryForTemplateLoading(new File("C:/Users/jadl/Desktop/"));
 
            //第三种使用web项目的ServletContext路径
//          configuration.setServletContextForTemplateLoading(null, "/doc/");
 
            //以utf-8的编码读取ftl模板文件
//            Template template = configuration.getTemplate("freemarker.ftl", "utf-8");
            Template template = configuration.getTemplate("detail.ftl", "utf-8");
 
            //输出文档路径及名称
            File outFile = new File(path);
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), 
                    "utf-8"), 10240);
            
            template.process(jsonObject, out);
//            template.process(dataMap, out);
            
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    public static void main(String[] args) {
//        String path = "C:\\image\\doc\\"+System.currentTimeMillis()+".doc";
//    	String path = "C:\\Users\\jadl\\Desktop\\"+System.currentTimeMillis()+".doc";
        String path = "C:\\Users\\jadl\\Desktop\\"+System.currentTimeMillis()+".pdf";
        test(path);
 
    }
}