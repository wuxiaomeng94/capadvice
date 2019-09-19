package com.capinfo.utils;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.ResourceUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelUtils {

    public final static String EXCEPT_TYPE_XLSX = ".xlsx";

    public final static String EXCEPT_TYPE_XLS = ".xls";

    public final static String XLS_MODEL_PATH = "xlsmodel";

    public final static String XLS_MODEL_TEMP_PATH = "temp";
    /**
     * 设置缓冲值
     */
    static final int BUFFER = 1024;


    /**
     * export导出请求头设置
     *
     * @param response
     * @param workbook
     * @param fileName
     * @throws Exception
     */
    public static void export(HttpServletResponse response, Workbook workbook, String fileName) throws Exception {
        response.reset();
        response.setContentType("application/x-msdownload");
        fileName = fileName + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO-8859-1") + ".xlsx");
        ServletOutputStream outStream = null;
        try {
            outStream = response.getOutputStream();
            workbook.write(outStream);
        } finally {
            outStream.close();
        }
    }



    public static void exportToLoaclFile(Workbook workbook, String filePath, String fileName) {
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(filePath+fileName);
            workbook.write(output);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



    /*public void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setHeader("content-Type", "application/vnd.ms-excel");
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            //throw new NormalException(e.getMessage());
            e.printStackTrace();
        }
    }*/


    public static void dowmloadTemplate(HttpServletRequest request, HttpServletResponse response, String tempFileName, String type) {

        //Resource resource = new ClassPathResource("xlsmodel/muban.xls");
        ServletOutputStream os = null;
        BufferedInputStream bis = null;
        try {
            //File sourceFile = resource.getFile();
            File sourceFile = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "xlsmodel/muban.xls");
            //InputStream inputStream = new FileInputStream(sourceFile);
            os = response.getOutputStream();
            response.reset();
            /*response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");*/
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename="
                    + new String(tempFileName.getBytes(), "ISO-8859-1") + type);
            bis = new BufferedInputStream(new FileInputStream(sourceFile), BUFFER);
            byte data[] = new byte[BUFFER];
            int b;
            while ((b = bis.read(data, 0, BUFFER)) != -1) {
                os.write(data, 0, b);
            }
            //IOUtils.copy(inputStream, os);
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (Exception e) {

            }
        }

    }



}
