package com.platform.utils;

import com.platform.utils.upload.Upload;
import com.platform.utils.upload.UploadVo;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * ftp上传
 *
 * @author bjsonghongxu
 * @create 2018-03-16 17:37
 **/
public class FtpUpload {

    private static final Logger logger = Logger.getLogger(FtpUpload.class);

    public static List<UploadVo> upload(List<MultipartFile> fileList, String platformCode,String dirFolderName,Map<String, String> properties){
        List<UploadVo> uploadFileInfos  = new ArrayList<UploadVo>();
        try {

            logger.info("upLoad 参数 -- platformCode : " + platformCode + "--dirFolderName : " + dirFolderName);
            if(org.apache.commons.lang.StringUtils.isBlank(platformCode))platformCode="";
            if(org.apache.commons.lang.StringUtils.isBlank(dirFolderName))dirFolderName="";
            String dir = properties.get("dir");
            String uploadFileTypes = properties.get("uploadFileType");
            String remotePath = properties.get("remotePath");
            String dirPath = properties.get("dirPath");
            for (MultipartFile file : fileList) {
                if (file != null && file.getOriginalFilename() != null &&  !"".equals(file.getOriginalFilename())) {
                    UploadVo uploadVo = new UploadVo();
                    String fileName = file.getOriginalFilename().trim();
                    uploadVo.setOldName(fileName);
                    uploadVo.setFileSize(file.getSize());
                    logger.debug("fileName:" +fileName);
                    //任意文件上传  当文件是符合要求的文件，则上传
                    if (checkFileType(fileName , uploadFileTypes)) {
                        if (org.apache.commons.lang.StringUtils.isNotEmpty(fileName)) {
                            String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
                            uploadVo.setFileType(prefix);
                            String uuid = UUID.randomUUID().toString().replace("-", ""); //获取到唯一的名称
                            fileName = uuid + "." + prefix;  //上传服务器名
                        }
                        String dirFile = remotePath+ platformCode +"/" + dirFolderName  +"/" ;
                        uploadVo.setFileServerPath(dirPath+platformCode +"/" + dirFolderName  +"/"+fileName);
                        try {
                            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(dir+ fileName));// 复制临时文件到指定目录下
                            Upload.ftpFile(dir + fileName, dirFile);//上传到服务器
                        } catch (IOException e) {
                            uploadVo.setReturnStatus(Integer.valueOf(1));
                            e.printStackTrace();
                        }
                    }
                    uploadFileInfos.add(uploadVo);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return  uploadFileInfos;
    }

    //检查文件类型是否符合规范
    public static boolean checkFileType(String fileName , String uploadFileTypes) throws Exception{
        boolean flag = false;
        String[] uploadFileType = uploadFileTypes.split("[|]");
        //检查上传的文件类型是否符合要求的文件
        for (int i = 0; i < uploadFileType.length; i++) {
            if (fileName.toLowerCase().contains(uploadFileType[i])) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}
