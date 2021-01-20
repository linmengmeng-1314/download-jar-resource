package com.lin.test.aboutWhhtmltopdf;

import java.io.IOException;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param;


/**
 * 需要将exe文件 添加到环境变量中
 * @author linmengmeng
 * @date 2021年1月12日 下午2:14:49
 */
public class TestWhHtmlToPDF {

	public static void main(String[] args) {
		Pdf pdf = new Pdf();
		pdf.addPageFromString("<html><head><meta charset=\"utf-8\"></head><h1>Müller</h1></html>");
		//pdf.addPageFromUrl("http://www.google.com");

		// Add a Table of Contents
		pdf.addToc();

		String toolSrc = "D:\\wkhtmltopdf\\bin\\wkhtmltopdf.exe";
		pdf.addParam(new Param(toolSrc));
		
		// The `wkhtmltopdf` shell command accepts different types of options such as global, page, headers and footers, and toc. Please see `wkhtmltopdf -H` for a full explanation.
		// All options are passed as array, for example:
		pdf.addParam(new Param("--no-footer-line"), new Param("--header-html", "file:///header.html"));
		pdf.addParam(new Param("--enable-javascript"));

		// Add styling for Table of Contents
		pdf.addTocParam(new Param("--xsl-style-sheet", "my_toc.xsl"));

		// Save the PDF
		try {
			pdf.saveAs("output.pdf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
