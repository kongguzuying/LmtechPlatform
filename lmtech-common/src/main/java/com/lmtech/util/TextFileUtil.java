package com.lmtech.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import com.lmtech.common.PageData;

/**
 * 文本文件工具类
 * @author huang.jb
 *
 */
public class TextFileUtil {
	/**
	 * 获取文本分页数据
	 * @param filePath 文件路径
	 * @param pageIndex 当前页
	 * @param pageSize 每页文本大小，单位为KB
	 * @return
	 */
	public static PageData getTextOfPage(String filePath, int pageIndex, int pageSize) {
		File file = new File(filePath);
		if (!file.exists()) {
			LoggerManager.error(String.format("file %1$s does not exist", filePath));
			return null;
		}
		if (file.isDirectory()) {
			LoggerManager.error(String.format("file %1$s is a directory", filePath));
			return null;
		}
		
		try {
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			FileChannel channel = raf.getChannel();
			
			long total = channel.size();
			int bbSize = pageSize * 1000;
			int position = (pageIndex - 1) * bbSize;
			
			try {
				if (position < total) {
					//if page index is normal
					ByteBuffer bb = null;
					if (position + bbSize <= total) {
						bb = ByteBuffer.allocate(bbSize);
					} else {
						bb = ByteBuffer.allocate((int) (total - position));
					}
					channel.position(position);
					
					while (bb.hasRemaining()) {
						channel.read(bb);
					}
					
					// set result
					String result = new String(bb.array());
					PageData pageData = new PageData();
					pageData.setCurrentPageNumber(pageIndex);
					pageData.setPageSize(pageSize);
					pageData.setTotal((int) (total / 1000));
					pageData.setData(result);
					
					return pageData;
				}
			} finally {
				if (channel != null) {
					channel.close();
				}
				if (raf != null) {
					raf.close();
				}
			}
		} catch (FileNotFoundException e) {
			LoggerManager.error(e);
		} catch (IOException e) {
			LoggerManager.error(e);
		}
		return null;
	}

}
