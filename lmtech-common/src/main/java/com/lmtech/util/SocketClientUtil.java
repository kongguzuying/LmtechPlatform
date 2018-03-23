package com.lmtech.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Socket客户端工具类
 * @author huang.jb
 *
 */
public class SocketClientUtil {
	/**
	 * socket请求
	 * @param address
	 * @param port
	 * @param message
	 * @return
	 */
	public static String socketRequest(String address, int port, String message) {
		try {
			Socket s = new Socket(InetAddress.getByName(address), port);
			OutputStream os = s.getOutputStream();
			InputStream is = s.getInputStream();
			//请求
			BufferedOutputStream bos = new BufferedOutputStream(os);
			bos.write(message.getBytes());
			bos.flush();
			Thread.sleep(100);
			
			//返回结果
			byte[] buf = new byte[512];
			int len = -1;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while (is.available() > 0) {
				len = is.read(buf);
				baos.write(buf, 0, len);
			}
			os.close();
			is.close();
			s.close();
			baos.close();
			return new String(baos.toByteArray());
		} catch (Exception e) {
			LoggerManager.error(e);
			return null;
		}
	}
}
