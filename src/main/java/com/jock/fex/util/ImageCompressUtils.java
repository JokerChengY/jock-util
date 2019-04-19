package com.jock.fex.util;

import com.alibaba.simpleimage.ImageFormat;
import com.alibaba.simpleimage.SimpleImageException;
import com.alibaba.simpleimage.render.ReadRender;
import com.alibaba.simpleimage.render.WriteParameter;
import com.alibaba.simpleimage.render.WriteRender;
import org.apache.commons.io.IOUtils;

import java.io.*;

public class ImageCompressUtils {

//    /**
//     * 文件上传
//     *
//     * @param image
//     * @param request
//     * @param session
//     * @return
//     */
//    @RequestMapping(method = RequestMethod.POST, value = "/uploadfile")
//    @ResponseBody
//    public Map<String, Object> uploadFile(@RequestParam MultipartFile uploadFileName, HttpSession session) {
//        Map<String, Object> resultmap = new HashMap<String, Object>();
//        String realPath = session.getServletContext().getRealPath(Constant.IMGURL);
//        String headname = uploadFileName.getOriginalFilename();
//
//
//        String path = realPath + "/" + headname;
//        File file = new File(path);
//        if (file.exists()) {
//            resultmap.put("status_code", "1");
//            return resultmap;
//        }
//        try {
//            FileUtil.upFile(uploadFileName.getInputStream(), headname, realPath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//// MultipartFile转file
//
//// CommonsMultipartFile cf= (CommonsMultipartFile)uploadFileName;
//// DiskFileItem fi = (DiskFileItem)cf.getFileItem();
//// File f = fi.getStoreLocation();
//        String string = FileType.getFileType(file);
//        int type = FileType.getType(string);
//
//        String fileName = file.getName();
//        if (type == FileType.IMAGE) {
//            try {
//                FileTransform.createThumbPic(file, realPath);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else if (type == FileType.AUDIO) {
//            if (fileName.endsWith(".amr")) {
//                FileTransform.convertAmrToMp3(fileName, realPath);
//            }
//        }
//        resultmap.put("status_code", "0");
//        return resultmap;
//    }


   /* *//**
     * 直接指定压缩后的宽高：  (先保存原文件，再压缩、上传)
     *
     * @param oldFile   要进行压缩的文件全路径
     * @param width     压缩后的宽度
     * @param height    压缩后的高度
     * @param quality   压缩质量
     * @param smallIcon 文件名的小小后缀(注意，非文件后缀名称),入压缩文件名是yasuo.jpg,则压缩后文件名是yasuo(+smallIcon
     *                  ).jpg
     * @return 返回压缩后的文件的全路径
     *//*

    public static String zipImageFile(String oldFile, int width, int height,

                                      float quality, String smallIcon) {

        if (oldFile == null) {

            return null;

        }

        String newImage = null;

        try {

            *//** 对服务器上的临时文件进行处理 *//*

            Image srcFile = ImageIO.read(new File(oldFile));

            *//** 宽,高设定 *//*

            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);

            String filePrex = oldFile.substring(0, oldFile.indexOf('.'));

            *//** 压缩后的文件名 *//*

            newImage = filePrex + smallIcon + oldFile.substring(filePrex.length());

            *//** 压缩之后临时存放位置 *//*

            FileOutputStream out = new FileOutputStream(newImage);

            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);

            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);

            *//** 压缩质量 *//*

            jep.setQuality(quality, true);

            encoder.encode(tag, jep);

            out.close();

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return newImage;

    }*/


    /*private BufferedImage getNewImage(MultipartFile oldImage, double width, double height) throws IOException {
        *//*srcURl 原图地址；deskURL 缩略图地址；comBase 压缩基数；scale 压缩限制(宽/高)比例*//*

        ByteArrayInputStream bais = new ByteArrayInputStream(oldImage.getBytes());
        MemoryCacheImageInputStream mciis = new MemoryCacheImageInputStream(bais);
        Image src = ImageIO.read(mciis);
        double srcHeight = src.getHeight(null);
        double srcWidth = src.getWidth(null);
        double deskHeight = srcHeight;//缩略图高
        double deskWidth = srcWidth;//缩略图宽
//        if (srcWidth > srcHeight) {
//
//            if (srcWidth > width) {
//                if (width / height > srcWidth / srcHeight) {
//                    deskHeight = height;
//                    deskWidth = srcWidth / (srcHeight / height);
//                } else {
//                    deskHeight = width / (srcWidth / srcHeight);
//                    deskWidth = width;
//                }
//            } else {
//
//                if (srcHeight > height) {
//                    deskHeight = height;
//                    deskWidth = srcWidth / (srcHeight / height);
//                } else {
//                    deskHeight = srcHeight;
//                    deskWidth = srcWidth;
//                }
//
//            }
//
//        } else if (srcHeight > srcWidth) {
//            if (srcHeight > (height)) {
//                if ((height) / width > srcHeight / srcWidth) {
//                    deskHeight = srcHeight / (srcWidth / width);
//                    deskWidth = width;
//                } else {
//                    deskHeight = height;
//                    deskWidth = (height) / (srcHeight / srcWidth);
//                }
//            } else {
//                if (srcWidth > width) {
//                    deskHeight = srcHeight / (srcWidth / width);
//                    deskWidth = width;
//                } else {
//                    deskHeight = srcHeight;
//                    deskWidth = srcWidth;
//                }
//
//            }
//
//        } else if (srcWidth == srcHeight) {
//
//            if (width >= (height) && srcHeight > (height)) {
//                deskWidth = (height);
//                deskHeight = (height);
//            } else if (width <= (height) && srcWidth > width) {
//                deskWidth = width;
//                deskHeight = width;
//            } else if (width == (height) && srcWidth < width) {
//                deskWidth = srcWidth;
//                deskHeight = srcHeight;
//            } else {
//                deskHeight = srcHeight;
//                deskWidth = srcWidth;
//            }
//
//        }
        BufferedImage tag = new BufferedImage((int) deskWidth, (int) deskHeight,
                BufferedImage.TYPE_3BYTE_BGR);
        tag.getGraphics().drawImage(src, 0, 0, (int) deskWidth, (int) deskHeight, null); //绘制缩小后的图
        return tag;
    }*/


//    public static String compressImage(String srcPath, float level) {
//        int index = srcPath.lastIndexOf(".");
//        String destPath = srcPath.substring(0, index) + "_" + level + srcPath.substring(index, srcPath.length());
//        File in = new File(srcPath);
//        File out = new File(destPath);
//        BufferedInputStream bis = null;
//        BufferedOutputStream bos = null;
//        WriteRender wr = null;
//
//        try {
//            bis = new BufferedInputStream(new FileInputStream(in));
//            bos = new BufferedOutputStream(new FileOutputStream(out));
//            WriteParameter e = new WriteParameter();
//            e.setDefaultQuality(level);
//            wr = new WriteRender(new ReadRender(bis, true), bos, ImageFormat.JPEG, e);
//            wr.render();
//        } catch (Exception var18) {
//            var18.printStackTrace();
//        } finally {
//            IOUtils.closeQuietly(bis);
//            IOUtils.closeQuietly(bos);
//            if (wr != null) {
//                try {
//                    wr.dispose();
//                } catch (SimpleImageException var17) {
//                    var17.printStackTrace();
//                }
//            }
//
//        }
//
//        return destPath;
//    }
    public static File compressImage(File file,String compressFolder, float level) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        WriteRender wr = null;
        File out =new File(compressFolder+"-small-" + file.getName());

        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            bos = new BufferedOutputStream(new FileOutputStream(out));
            WriteParameter e = new WriteParameter();
            e.setDefaultQuality(level);
            wr = new WriteRender(new ReadRender(bis, true), bos, ImageFormat.JPEG, e);
            wr.render();

        } catch (Exception var18) {
            var18.printStackTrace();
        } finally {
            IOUtils.closeQuietly(bis);
            IOUtils.closeQuietly(bos);
            if (wr != null) {
                try {
                    wr.dispose();
                } catch (SimpleImageException var17) {
                    var17.printStackTrace();
                }
            }

        }

        return out;
    }
}
