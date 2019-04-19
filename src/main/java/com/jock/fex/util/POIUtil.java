package com.jock.fex.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 2019年2月28日<br>
 * Excel导出工具类
 *
 * @author 0
 */
public class POIUtil {

    /**
     * 设置表头
     *
     * @param wb
     * @param row
     * @param title
     */
    public final static void setTitle(HSSFWorkbook wb, HSSFRow row, String[] title) {
        if (title != null && title.length > 0) {
            // 创建居中样式
            final HSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);

            int cellNum = 0;
            HSSFCell cell = null;
            for (String t : title) {
                cell = row.createCell(cellNum);
                cellNum++;
                cell.setCellValue(t);
                cell.setCellStyle(style);
            }
        }
    }

    /**
     * 设置与表头对应的值 mapList sql查询出的数据 sheet 工作薄 valueMap 与表头对应的字段
     */
    public final static void setTitleValues(List<Map<String, Object>> mapList, HSSFSheet sheet, String[] values,
                                            int rowNum) {
        if (!CollectionUtils.isEmpty(mapList) && values != null && values.length > 0) {
            HSSFRow row = null;
            HSSFCell cell = null;
            for (Map<String, Object> m : mapList) {

                // 写数据到Excel，逐行写
                row = sheet.createRow(rowNum);
                rowNum++;
                int i = 0;
                for (String valStr : values) {
                    cell = row.createCell(i);
                    cell.setCellValue(StringUtils.ifNull(m.get(valStr)));
                    i++;
                }
            }

            // 设置列宽
            for (int i = 0; i < values.length; i++) {
                sheet.autoSizeColumn((short) i);
            }
        }
    }

    /**
     * 把数据写入sheet
     *
     * @param sheet
     * @param data       数据源
     * @param columnName 列明
     * @param rowNum     行号，从第几行开始
     * @param columnWith 列宽
     */
    public final static void writeSheet(HSSFSheet sheet, List<Map<String, Object>> data, String[] columnName,
                                        int rowNum, int[] columnWith) {
        if (!CollectionUtils.isEmpty(data) && columnName != null && columnName.length > 0) {
            // 写数据
            setTitleValues(data, sheet, columnName, rowNum);

            // 设置列宽
            if (columnWith != null && columnWith.length > 0) {
                for (int i = 0; i < columnWith.length; i++) {
                    sheet.setColumnWidth(i, columnWith[i] * 2 * 256);
                }
            }
        }
    }

    /**
     * 2018年10月25日<br>
     * 写Excel表格
     *
     * @param sheet
     * @param data        导出的数据
     * @param exportField 导出的字段
     * @param rowNum      起始行号
     */
    public final static void writeSheet(HSSFSheet sheet, List<?> data, String[] exportField, int rowNum) {
        if (!CollectionUtils.isEmpty(data) && exportField != null && exportField.length > 0) {
            // 写数据
            HSSFRow row = null;
            HSSFCell cell = null;
            for (Object o : data) {

                // 写数据到Excel，逐行写
                row = sheet.createRow(rowNum);
                rowNum++;
                int i = 0;
                for (String field : exportField) {
                    cell = row.createCell(i);
                    try {
                        final Method m = getGetMethod(o.getClass(), field, "get");
                        cell.setCellValue(StringUtils.ifNull(m != null ? m.invoke(o, new Object[0]) : ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    i++;
                }
            }

            // 设置列宽
            autoSizeColumn(sheet, 0, exportField.length);
        }
    }

    /**
     * 处理map集合
     * @param sheet
     * @param data
     * @param exportField
     * @param rowNum
     */
    public final static void writeSheet( List<Map<String, Object>> data, String[] exportField, int rowNum,HSSFSheet sheet) {
        if (!CollectionUtils.isEmpty(data) && exportField != null && exportField.length > 0) {
            // 写数据
            HSSFRow row = null;
            HSSFCell cell = null;

            for (Map<String, Object> m : data) {

                // 写数据到Excel，逐行写
                row = sheet.createRow(rowNum);
                rowNum++;
                int i = 0;
                for (String valStr : exportField) {
                    cell = row.createCell(i);
                    cell.setCellValue(StringUtils.ifNull(m.get(valStr)));
                    i++;
                }
            }

            // 设置列宽
            autoSizeColumn(sheet, 0, exportField.length);
        }
    }

    /**
     * 设置与表头对应的值 list sql查询出的数据 sheet 工作薄 valueMap 与表头对应的字段
     */
    public final static void setTitleValuesByObj(List<?> objList, HSSFSheet sheet, String[] values, int rowNum) {
        if (!CollectionUtils.isEmpty(objList) && values != null && values.length > 0) {
            HSSFRow row = null;
            HSSFCell cell = null;
            for (Object object : objList) {

                // 写数据到Excel，逐行写
                row = sheet.createRow(rowNum);
                rowNum++;
                int i = 0;
                for (String valStr : values) {
                    cell = row.createCell(i);
                    Object o = null;
                    try {
                        o = PropertyUtils.getProperty(object, valStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (o != null) {
                        if (o instanceof Date) {
                            cell.setCellValue(MyDateUtil.getDateTime((Date) o, 13));
                        } else {
                            cell.setCellValue(StringUtils.ifNull(o));
                        }
                    } else {
                        cell.setCellValue("");
                    }
                    i++;
                }

            }
        }
    }

    /**
     * Excel下载
     *
     * @param response
     * @param wb
     */
    public final static void excelDownload(HttpServletResponse response, HSSFWorkbook wb, String fileName) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            // Excel写入输出流
            final ByteArrayOutputStream os = new ByteArrayOutputStream();
            wb.write(os);
            final InputStream is = new ByteArrayInputStream(os.toByteArray());

            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            final String name = new String((fileName + ".xls").getBytes(), "iso-8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + name);

            // 响应流
            final ServletOutputStream out = response.getOutputStream();
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
                // 清楚垃圾
                System.gc();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 2018年8月11日<br>
     * 反射方法
     *
     * @param obj
     * @param key
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public final static Method getGetMethod(Class obj, String key, String getSet) {
        StringBuffer sb = new StringBuffer();
        sb.append(getSet).append(key.substring(0, 1).toUpperCase()).append(key.substring(1));
        try {
            return obj.getMethod(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * HSSFWorkbook 抽出
     *
     * @param exportList 查询到的数据
     * @param sheetname  表名
     * @param title      抬头名
     * @param values     数据名
     *                   POIUtil.getHSSFWorkbook(ExamMessageTrans.examMessagesList(examMessages),"中介报名考试信息",title,values)
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public final static HSSFWorkbook getHSSFWorkbook(List exportList, String sheetname, String[] title,
                                                     String[] values) {
        // 导出
        // 声明一个工作薄
        final HSSFWorkbook wb = new HSSFWorkbook();
        // 创建一个sheet并命名
        final HSSFSheet sheet = wb.createSheet(sheetname);
        // 默认列宽
        sheet.setDefaultColumnWidth(200);

        int rowNum = 0;
        final HSSFRow row = sheet.createRow(rowNum);
        rowNum++;
        POIUtil.setTitle(wb, row, title);
        POIUtil.setTitleValues(exportList, sheet, values, rowNum);

        return wb;
    }

    /**
     * 2019年2月28日<br>
     * 针对模板文件写数据
     *
     * @param sheet
     * @param data
     * @param exportField 字段名
     * @param rowStart    起始行
     * @param cellStart   起始列
     */
    public final static void writeSheet(HSSFSheet sheet, List<?> data, String[] exportField, int rowStart,
                                        int cellStart) {
        if (!CollectionUtils.isEmpty(data) && exportField != null && exportField.length > 0) {
            // 写数据
            HSSFRow row = null;
            HSSFCell cell = null;
            for (Object o : data) {

                // 写数据到Excel，逐行写
                if ((row = sheet.getRow(rowStart)) == null) {
                    continue;
                }

                rowStart++;
                int i = cellStart;
                for (String field : exportField) {
                    cell = row.createCell(i);
                    try {
                        final Method m = getGetMethod(o.getClass(), field, "get");
                        cell.setCellValue(StringUtils.ifNull(m != null ? m.invoke(o, new Object[0]) : ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    i++;
                }
            }

            autoSizeColumn(sheet, cellStart, exportField.length);
        }
    }

    /**
     * 处理map集合
     *
     * @param sheet
     * @param data
     * @param exportField
     * @param rowStart
     * @param cellStart
     */
    public final static void writeSheet(List<Map<String, Object>> data, String[] exportField, int rowStart, int cellStart, HSSFSheet sheet) {
        if (!CollectionUtils.isEmpty(data) && exportField != null && exportField.length > 0) {
            // 写数据
            HSSFRow row = null;
            HSSFCell cell = null;

            for (Map<String, Object> m : data) {

                // 写数据到Excel，逐行写
                if ((row = sheet.getRow(rowStart)) == null) {
                    continue;
                }

                rowStart++;
                int i = cellStart;
                for (String valStr : exportField) {
                    cell = row.createCell(i);
                    cell.setCellValue(StringUtils.ifNull(m.get(valStr)));
                    i++;
                }
            }

            autoSizeColumn(sheet, cellStart, exportField.length);
        }
    }

    /**
     * 2019年2月28日<br>
     * 自动设置Excel列宽
     *
     * @param sheet
     * @param cellStart  起始列
     * @param cellLength 列长
     */
    public final static void autoSizeColumn(HSSFSheet sheet, int cellStart, int cellLength) {
        // 设置列宽
        for (int i = cellStart; i < cellLength; i++) {
            sheet.autoSizeColumn((short) i);
        }
    }

    /**
     * 2019年2月28日<br>
     * 设置单元格的值
     *
     * @param sheet
     * @param rowNum  行号
     * @param cellNum 列号
     * @param value   值
     * @param align   -1:居左，0-居中，1-居右
     */
    public final static void setCellValue(HSSFSheet sheet, int rowNum, int cellNum, String value) {
        final HSSFRow row = sheet.getRow(rowNum);
        final HSSFCell cell = row.getCell(cellNum);
        cell.setCellValue(value);
    }
}
