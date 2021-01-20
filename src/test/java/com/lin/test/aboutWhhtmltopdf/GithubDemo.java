package com.lin.test.aboutWhhtmltopdf;

import java.io.IOException;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.configurations.WrapperConfig;

/**
 * jar运行 需要依赖环境变量或者程序包exe
 * @author linmengmeng
 * @date 2021年1月13日 下午4:02:56
 */
public class GithubDemo {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		//String wkhtmltopdfCommand = "D:\\wkhtmltopdf\\bin\\wkhtmltoimage.exe";
		String wkhtmltopdfCommand = "D:\\wkhtmltopdf\\bin\\wkhtmltopdf.exe";
		
		WrapperConfig wrapperConfig = new WrapperConfig(wkhtmltopdfCommand);
		
		
		Pdf pdf = new Pdf(wrapperConfig);
		
		//Pdf pdf = new Pdf();

		//pdf.addPageFromString("<html><head><meta charset=\"utf-8\"></head><h1>Müller</h1></html>");
		//pdf.addPageFromUrl("https://www.baidu.com");
		pdf.addPageFromFile("E:/baidu/1610523364109word.html");

		// Add a Table of Contents
		pdf.addToc();

		// The `wkhtmltopdf` shell command accepts different types of options such as global, page, headers and footers, and toc. Please see `wkhtmltopdf -H` for a full explanation.
		// All options are passed as array, for example:
//		pdf.addParam(new Param("--no-footer-line"), new Param("--header-html", "file:///header.html"));
//		pdf.addParam(new Param("--enable-javascript"));

		// Add styling for Table of Contents
		//pdf.addTocParam(new Param("--xsl-style-sheet", "my_toc.xsl"));

		String path = "E:/baidu/";
		String outputFilename = System.currentTimeMillis() +"output.pdf";
		//pdf.set
		// Save the PDF
		//pdf.saveAs(path + outputFilename);
		pdf.saveAsDirect(path + outputFilename);
		System.out.println("success");
	}
}
