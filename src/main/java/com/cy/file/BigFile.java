package com.cy.file;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @Author chenying
 * @Date 2018/12/18
 */
public class BigFile {
    /**
     * 出一道题，开发一个程序：
     * 1000+个小文件（可能是图片、word、文本），存到【一个】大文件中，并可以完成大文件中这些小文件的增删差改。
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        //File file = inputFile();
        File file = new File("C:\\Users\\ying.chen2\\Desktop\\sql");

      /*  String[] list = file.list();
        System.bigOut.println("文件列表：");
        for (String name : list) {
            System.bigOut.println(name);
        }*/

        File bigFile = new File(file.getPath() + File.separator + "大文件.big");
        System.out.println("\n" + bigFile.getPath() + "\n生成大文件...");

        FileOutputStream bigOut = new FileOutputStream(bigFile);

        Map<String, FileData> fileDataMap = new HashMap<>();
        for (File fileItem : file.listFiles()) {
            if (!fileItem.equals(bigFile.getName())) {
                FileData value = new FileData();
                value.fileName = fileItem.getName();
                value.filePath = fileItem.getPath();
                value.size = fileItem.length();
                fileDataMap.put(fileItem.getName(), value);
            }
        }
        bigOut.write("=========\n".getBytes());

        for (File fileItem : file.listFiles()) {
            if (fileItem.getName().equals(bigFile.getName())) {
                continue;
            }
            FileData fileData = fileDataMap.get(fileItem.getName());
            fileData.startIndex = bigFile.length();
            FileInputStream in = new FileInputStream(fileItem);
            byte[] temp = new byte[1024];
            int read = in.read(temp);
            while (read != -1) {
                bigOut.write(temp);
                read = in.read(temp);
            }
            bigOut.write("\n".getBytes());
            bigOut.flush();
            fileData.endIndex = bigFile.length();
        }
        bigOut.close();

        Long length = bigFile.length();
        RandomAccessFile rw = new RandomAccessFile(bigFile,"rw");
        //文件指定位置到 position
        rw.seek(0);
        rw.write(length.byteValue());
        rw.seek(length);
        for (FileData fileData : fileDataMap.values()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(fileData.fileName)
                    .append("\t")
                    .append(fileData.filePath)
                    .append("\t")
                    .append(fileData.size)
                    .append("\t")
                    .append(fileData.startIndex)
                    .append("\t")
                    .append(fileData.endIndex)
                    .append("\n");
            rw.write(stringBuilder.toString().getBytes());
        }

        FileInputStream in = new FileInputStream(bigFile);
        byte[] temp = new byte[1024];
        int read = in.read(temp);
        while (read != -1) {
            System.out.println(new String(temp));
            read = in.read(temp);
        }
        in.close();
    }

    static class FileData {
        String fileName;
        String filePath;
        Long startIndex;
        Long endIndex;
        Long size;
    }

    public static File inputFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入目录名:");
        String filePath = scanner.nextLine();
        File file = new File(filePath);
        while (!file.isDirectory()) {
            if ("q".equals(filePath)) {
                return null;
            }
            System.out.println(filePath + "不是目录！");
            System.out.println("输入目录名(退出请按：q):");
            filePath = scanner.nextLine();
            file = new File(filePath);
        }
        return file;
    }
}
