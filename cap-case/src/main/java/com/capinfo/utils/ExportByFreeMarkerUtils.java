package com.capinfo.utils;

import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.afterturn.easypoi.word.WordExportUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

public class ExportByFreeMarkerUtils {

	public final static String XLS_MODEL_PATH = "xlsmodel";

	public final static String XLS_MODEL_TEMP_PATH = "temp";

	private static Configuration configuration;

	public static void exportWord(HttpServletRequest request,HttpServletResponse response,Map<String,Object> map,String ftlTemplate, String fileName) {
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		fileName=fileName+DateUtils.SDF_DATE.format(new Date())+".doc";
		PrintWriter writer=null;
		String basePath = request.getSession().getServletContext().getRealPath(XLS_MODEL_PATH);
		//Resource resource = new ClassPathResource("xlsmodel/"+ftlTemplate);
		//String basePath =
		try {
			String userAgent = request.getHeader("user-agent").toLowerCase();
			if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			} else {
				fileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
			}
			//configuration.setClassForTemplateLoading(ExportByFreeMarkerUtils.class,"/");
			//File sourceFile = resource.getFile();
			configuration.setDirectoryForTemplateLoading(new File(basePath));
			configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
			//Template t = configuration.getTemplate(/*ftlTemplate*/basePath + File.separator + ftlTemplate, "utf-8");
			Template template = configuration.getTemplate(ftlTemplate, "utf-8");
			writer = response.getWriter();
			response.setHeader("Content-disposition","attachment;filename="+fileName);
			response.setContentType("application/msword;charset=UTF-8");
			template.process(map, writer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}




	public static void exportWordByEasyPoi(String templatePath, String temDir, String fileName, Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
		Assert.notNull(templatePath,"模板路径不能为空");
		Assert.notNull(temDir,"临时文件路径不能为空");
		Assert.notNull(fileName,"导出文件名不能为空");
		Assert.isTrue(fileName.endsWith(".docx"),"word导出请使用docx格式");
		if (!temDir.endsWith("/")){
			temDir = temDir + File.separator;
		}
		File dir = new File(temDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		OutputStream out = null;
		try {
			String userAgent = request.getHeader("user-agent").toLowerCase();
			if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			} else {
				fileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
			}
			XWPFDocument doc = WordExportUtil.exportWord07(templatePath, params);
			String tmpPath = temDir + fileName;
			FileOutputStream fos = new FileOutputStream(tmpPath);
			doc.write(fos);
			// 设置强制下载不打开
			response.setContentType("application/force-download");
			// 设置文件名
			response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
			out = response.getOutputStream();
			doc.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				delAllFile(temDir);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//delAllFile(temDir);//这一步看具体需求，要不要删
		}

	}


	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);//再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); //删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); //删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
