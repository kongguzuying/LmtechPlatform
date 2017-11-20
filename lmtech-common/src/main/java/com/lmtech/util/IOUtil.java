package com.lmtech.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

/**
 * IO工具类
 * @author huang.jb
 *
 */
public class IOUtil {
	/**
	 * 读取输入流字符串
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static String readInputStream(InputStream inputStream) throws IOException {
		InputStreamReader reader = new InputStreamReader(inputStream);
		CharBuffer cb = CharBuffer.allocate(inputStream.available());
		reader.read(cb);
		cb.flip();
		reader.close();
		return cb.toString();
	}
}
