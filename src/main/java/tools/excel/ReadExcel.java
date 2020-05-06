package tools.excel;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ReadExcel {
    public static void main(String[] args) throws IOException, InvalidFormatException {


        List<String> readexcel = readexcel("/Users/everywherewego/Desktop/字段信息2.xlsx", 0);
        Map<String, String> readexcel2 = readexcel2("/Users/everywherewego/Desktop/爬虫数据表结构.xlsx", 1);


        for (String value : readexcel) {
            System.out.print(value + "\t");
            if (readexcel2.containsKey(value)) {
                System.out.println(readexcel2.get(value));
            }
        }
    }


    public static List<String> readexcel(String path, int sheetPage) throws IOException, InvalidFormatException {
        List<String> list = new LinkedList<>();
        Workbook wb = readFile(path);
        //读取sheet页
        Sheet sheet = wb.getSheetAt(sheetPage);
        //起始行
        int firstRowIndex = sheet.getFirstRowNum() + 1;
        //结束行
        int lastRowIndex = sheet.getLastRowNum();

        //遍历行
        for (int i = firstRowIndex; i <= lastRowIndex; i++) {
            Row row = sheet.getRow(i);
            list.add(row.getCell(1).toString());
        }
        return list;
    }


    public static Map<String, String> readexcel2(String path, int sheetPage) throws IOException, InvalidFormatException {
        Map<String, String> map = new HashMap<>();
        Workbook wb = readFile(path);
        //读取sheet页
        Sheet sheet = wb.getSheetAt(sheetPage);
        //起始行
        int firstRowIndex = sheet.getFirstRowNum() + 1;
        //结束行
        int lastRowIndex = sheet.getLastRowNum();

        //遍历行
        for (int i = firstRowIndex; i <= lastRowIndex; i++) {
            Row row = sheet.getRow(i);
            map.put(row.getCell(1).toString(), row.getCell(3).toString());
        }
        return map;
    }


    public static Workbook readFile(String excelPath) throws IOException, InvalidFormatException {
        File excel = new File(excelPath);
        //.是特殊字符，需要转义！！！！！
        String[] split = excel.getName().split("\\.");
        Workbook wb;
        //根据文件后缀（xls/xlsx）进行判断
        if ("xls".equals(split[1])) {
            //文件流对象
            FileInputStream fis = new FileInputStream(excel);
            wb = new HSSFWorkbook(fis);
            fis.close();
        } else if ("xlsx".equals(split[1])) {
            wb = new XSSFWorkbook(excel);
        } else {
            System.out.println("文件类型错误!");
            return null;
        }
        wb.close();
        return wb;
    }
}
