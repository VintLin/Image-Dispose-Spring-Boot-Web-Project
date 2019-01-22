package com.felink.service.common.utility;

import com.felink.service.common.error.VideoFilePathException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileUtil {
    static private FileUtil fileUtil = new FileUtil();

    static public FileUtil getInstanse() {
        return fileUtil;
    }

    private FileUtil() {

    }

    public static JSONArray getFileList(String pathName) throws IOException{
        File dirFile = new File(pathName);
        //判断该文件或目录是否存在，不存在时在控制台输出提醒
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return null;
        }
        JSONArray files = new JSONArray();
        for(String fileStr: Objects.requireNonNull(dirFile.list())) {
            JSONObject file = new JSONObject();
            File f = new File(dirFile.getPath(), fileStr);
            file.put("dir", f.isDirectory());
            file.put("name", fileStr);
            file.put("modified", new Date(f.lastModified()));
            file.put("type", getFileSuffix(fileStr));
            files.add(file);
        }
        return files;
    }

    static public String getFileSuffix(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1);
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private static String getRandomName(String suffix){
        return getUUID() + "." + suffix;
    }

    static public String getUniquePath(String head, String suffix) {
        SimpleDateFormat datetime = new SimpleDateFormat("yyyyMMdd");
        return splicePath(head, datetime.format(new Date()), FileUtil.getRandomName(suffix));
    }

    static public String getUniquePath(String head) {
        SimpleDateFormat datetime = new SimpleDateFormat("yyyyMMdd");
        return splicePath(head, datetime.format(new Date()), getUUID());
    }

    static public String splicePath(String head, String... field) {
        StringBuilder path = new StringBuilder(head);
        for(String f: field) {
            mkdirs(path.toString());
            path.append(File.separator).append(f);
        }
        return path.toString();
    }
    
    static public String getSubFileName(String path) {
        return path.substring(path.lastIndexOf(File.separator) + 1);
    }

    static public String getSubFileNameNoSuffix(String path) {
        return path.substring(path.lastIndexOf(File.separator) + 1, path.lastIndexOf("."));
    }

    static public String getSubFilePath(String path) {
        return path.substring(0, path.lastIndexOf(File.separator) + 1);
    }

    static public void fromStreamSave(InputStream input, String saveFilePath) {
        try {
            fromStreamSave(input, new FileOutputStream(saveFilePath));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static String saveMultiFiles(MultipartHttpServletRequest request, String saveFilePath) throws IOException {
        String suffix = ".jpg";
        MultiValueMap<String, MultipartFile> multiFileMap  = request.getMultiFileMap();
        List<MultipartFile> fileSet = new LinkedList<>();
        for(Map.Entry<String, List<MultipartFile>> temp :multiFileMap.entrySet())  {
            fileSet.addAll(temp.getValue());
        }
        int count = 0;

        for(MultipartFile temp: fileSet) {
            suffix = FileUtil.getFileSuffix(temp.getOriginalFilename());
            String saveFile = FileUtil.splicePath(saveFilePath, count + "." + suffix);
            InputStream inputStream = temp.getInputStream();
            FileUtil.fromStreamSave(inputStream, saveFile);
            count++;
        }
        return suffix;
    }

    private static void fromStreamSave(InputStream input, OutputStream output){
        try{
            int len;
            byte[] buffer = new byte[1024];
            // in.read(buf) 每次读到的数据存放在buf 数组中
            while ((len = input.read(buffer)) != -1) {
                //在buf数组中取出数据写到（输出流）磁盘上
                output.write(buffer, 0, len);
            }
            output.close();
            input.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static public boolean exist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    static public boolean isDir(String filePath) throws VideoFilePathException {
        File file = new File(filePath);
        return file.isDirectory();
    }

    static public boolean isFile(String filePath) throws VideoFilePathException {
        File file = new File(filePath);
        return file.isFile();
    }

    static public void mkdirs(String filePath) {
        File file = new File(filePath);
        if(!file.exists()){
            file.mkdirs();
        }
    }


}
