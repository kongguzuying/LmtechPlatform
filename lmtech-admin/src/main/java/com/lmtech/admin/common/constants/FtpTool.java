package com.lmtech.admin.common.constants;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * 处理Ftp文件的上传和下载
 *
 * @author 
 *
 */
public class FtpTool {
 
    /**
     * Description: 向FTP服务器上传文件
     *
     * @param url
     *            FTP服务器hostname
     * @param username
     *            FTP登录账号
     * @param password
     *            FTP登录密码
     * @param path
     *            FTP服务器保存目录
     * @param filename
     *            上传到FTP服务器上的文件名
     * @param input
     *            输入流
     * @return 成功返回true，否则返回false
      */
    public boolean uploadFile(String url, String username, String password,
            String path, String filename, InputStream input) {
        boolean success = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            int port = Global.getFtpPort();
            if (port > 0) {
                ftp.setDefaultPort(port);
            }
            ftp.connect(url);
            ftp.login(username, password);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            // 转到指定上传目录
            ftp.changeWorkingDirectory(path);
            ftp.setFileType(FTP.BINARY_FILE_TYPE); // 设置为2进制上传
            // 将上传文件存储到指定目录
            ftp.enterLocalPassiveMode();
            boolean flag = ftp.storeFile(filename, input);
            ftp.logout();
            input.close();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return success;
    }

    /**
     * Description: 从FTP服务器下载文件
     *
     * @param url
     *            FTP服务器hostname
     * @param username
     *            FTP登录账号
     * @param password
     *            FTP登录密码
     * @param remotePath
     *            FTP服务器上的相对路径
     * @param fileName
     *            下载时的默认文件名
     *  localPath
     *            下载后保存到本地的路径
     * @return
      */
    public boolean downFile(String url, String username, String password,
            String remotePath, String fileName, HttpServletResponse response) {
        // 初始表示下载失败
        boolean success = false;
        // 创建FTPClient对象
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            // 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftp.connect(url);
            // 登录ftp
            ftp.login(username, password);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            String realName = remotePath
                    .substring(remotePath.lastIndexOf("/") + 1);
            // 转到指定下载目录
          ftp.changeWorkingDirectory("E:/");
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            // 列出该目录下所有文件
            // 设置文件下载头部
            response.setContentType("application/x-msdownload");// 设置编码
            response.setHeader("Content-Disposition", "attachement;filename="
                    + new String(fileName.getBytes(), "ISO-8859-1"));
            FTPFile[] fs = ftp.listFiles();
            // 遍历所有文件，找到指定的文件
            for (FTPFile ff : fs) {
                if (ff.getName().equals(realName)) {
                    OutputStream out = response.getOutputStream();
                    InputStream bis = ftp.retrieveFileStream(realName);
 
                    // 根据绝对路径初始化文件
                    // 输出流
                    int len = 0;
                    byte[] buf = new byte[1024];
                    while ((len = bis.read(buf)) > 0) {
                        out.write(buf, 0, len);
                        out.flush();
                    }
                    out.close();
                    bis.close();
                }
            }
            ftp.logout();
            // 下载成功
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }
    
    /**
     * Description: 删除FTP服务器上的文件
     *
     * @param url
     *            FTP服务器hostname
     * @param username
     *            FTP登录账号
     * @param password
     *            FTP登录密码
     * @param path
     *            FTP服务器文件目录
     * @param filename
     *            要删除FTP服务器上的文件名
     */
    public boolean delFile(String url, String username, String password,
            String path, String filename) {
        boolean success = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(url);
            ftp.login(username, password);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            // 转到指定ftp目录
            ftp.changeWorkingDirectory(path);
            // 删除指定目录下的ftp文件
            ftp.sendCommand("dele " + filename + "\r\n");
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return success;
    }

}
