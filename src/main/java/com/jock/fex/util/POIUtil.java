package com.jock.fex.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class POIUtil {
    //创建工作本   TOS
    public static HSSFWorkbook demoWorkBook = new HSSFWorkbook();

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
            style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);

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
            for (int i = 0; i < exportField.length; i++) {
                sheet.autoSizeColumn((short) i);
            }
        }
    }

    /**
     * 设置与表头对应的值 list sql查询出的数据 sheet 工作薄 valueMap 与表头对应的字段
     */
    public final static void setTitleValuesByObj(List<?> objList, HSSFSheet sheet, String[] values, int rowNum,
                                                 Integer dateForm) {
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
                            cell.setCellValue(MyDateUtil.getDateTime((Date) o, dateForm == null ? 13 : dateForm));
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
    public static Method getGetMethod(Class obj, String key, String getSet) {
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
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static HSSFWorkbook getHSSFWorkbook(List exportList, String sheetname, String[] title, String[] values) {
        // 导出
        // 声明一个工作薄
        final HSSFWorkbook wb = new HSSFWorkbook();
        // 创建一个sheet并命名
        final HSSFSheet sheet = wb.createSheet(sheetname);
        // 默认列宽
        sheet.setDefaultColumnWidth(20);

        int rowNum = 0;
        final HSSFRow row = sheet.createRow(rowNum);
        rowNum++;
        POIUtil.setTitle(wb, row, title);
        POIUtil.setTitleValuesByObjWb(wb, exportList, sheet, values, rowNum, 3);

        return wb;
    }

    /**
     * HSSFWorkbook 抽出
     *
     * @param exportList 查询到的数据
     * @param sheetname  表名
     * @param title      抬头名
     * @param values     数据名
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static HSSFWorkbook getHSSFWorkbookByObj(List exportList, String sheetname, String[] title,
                                                    String[] values) {
        // 导出
        // 声明一个工作薄
        final HSSFWorkbook wb = new HSSFWorkbook();
        // 创建一个sheet并命名
        final HSSFSheet sheet = wb.createSheet(sheetname);
        // 默认列宽
        sheet.setDefaultColumnWidth(20);

        int rowNum = 0;
        final HSSFRow row = sheet.createRow(rowNum);
        rowNum++;
        POIUtil.setTitle(wb, row, title);
        POIUtil.setTitleValuesByObj(exportList, sheet, values, rowNum, 3);

        return wb;
    }

    /**
     * 设置多表头
     *
     * @param sheet
     * @param rowNum  行号
     * @param cellNum 列号
     * @param title   表头数组
     * @param headNum 表头对应的坐标
     * @author zgr
     * @创建时间：2019年1月21日 @param wb
     */
    public static void setTitles(HSSFWorkbook wb, HSSFSheet sheet, int rowNum, int cellNum, String[] title,
                                 String[] headNum) {
        if (title != null && title.length > 0) {
            // 创建居中样式
            HSSFFont headfont = wb.createFont();
            headfont.setFontName("宋体");
            // headfont.setFontHeightInPoints((short) 22);// 字体大小
            final HSSFCellStyle headstyle = wb.createCellStyle();
            headstyle.setFont(headfont);
            headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
            headstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
            headstyle.setLocked(true);

            HSSFRow row = sheet.createRow(rowNum);
            HSSFCell cell = null;
            for (String t : title) {
                cell = row.createCell(cellNum);
                cellNum++;
                cell.setCellValue(t);
                cell.setCellStyle(headstyle);
            }

            if (headNum != null && headNum.length > 0) {
                for (int i = 0; i < headNum.length; i++) {// 合并单元格
                    String[] temp = headNum[i].split(",");
                    Integer startrow = Integer.parseInt(temp[0]);
                    Integer overrow = Integer.parseInt(temp[1]);
                    Integer startcol = Integer.parseInt(temp[2]);
                    Integer overcol = Integer.parseInt(temp[3]);
                    sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
                }
            }
        }

    }

    /**
     * @param wb
     * @param exportList 查询到的数据
     * @param sheet
     * @param values
     * @param rowNum
     * @param dateForm   * @param exportList
     *                   *
     *                   * @param sheetname
     *                   *            表名
     *                   * @param title
     *                   *            抬头名
     *                   * @param values
     *                   *            数据名
     */
    public static void setTitleValuesByObjWb(HSSFWorkbook wb, List<?> exportList, HSSFSheet sheet, String[] values, int rowNum,
                                             Integer dateForm) {
        if (!CollectionUtils.isEmpty(exportList) && values != null && values.length > 0) {
            HSSFRow row = null;
            HSSFCell cell = null;
            HSSFCellStyle cellStyle2 = wb.createCellStyle();
            HSSFDataFormat format = wb.createDataFormat();
            cellStyle2.setDataFormat(format.getFormat("@"));

            for (Object object : exportList) {

                // 写数据到Excel，逐行写
                row = sheet.createRow(rowNum);
                rowNum++;
                int i = 0;
                for (String valStr : values) {
                    cell = row.createCell(i);
                    cell.setCellStyle(cellStyle2);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    Object o = null;
                    try {
                        o = PropertyUtils.getProperty(object, valStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (o != null) {
                        if (o instanceof Date) {
                            cell.setCellValue(MyDateUtil.getDateTime((Date) o, dateForm == null ? 13 : dateForm));
                        } else if (o instanceof Double) {
                            Double value = (Double) o;
                            BigDecimal bd1 = new BigDecimal(Double.toString(value));
                            cell.setCellValue(bd1.toPlainString().replaceAll("0+?$", "").replaceAll("[.]$", ""));
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
}
