package com.lmtech.util;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * file util
 * 
 * @author huang.jb
 * 
 */
public class FileUtil {
	/**
	 * get file bytes
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static byte[] getFileBytes(String path) throws Exception {
		File file = new File(path);
		return getFileBytes(file);
	}

	/**
	 * get file bytes
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] getFileBytes(File file) throws IOException {
		if (file != null && file.exists()) {
			FileInputStream inputStream = new FileInputStream(file);
			ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
			int offSet = 0, bufSize = 1000;
			byte[] bt = new byte[bufSize];
			while ((offSet = inputStream.read(bt)) > 0) {
				buffer.put(bt, 0, offSet);
			}
			inputStream.close();
			return buffer.array();
		}
		return null;
	}

	/**
	 * save bytes to file
	 * 
	 * @param path
	 * @param bytes
	 * @throws Exception
	 */
	public static void saveFileBytes(String path, byte[] bytes) throws Exception {
		File file = new File(path);
		file.deleteOnExit();

		createDirWhenNotExist(path);

		if (file.createNewFile()) {
			FileOutputStream outputStream = new FileOutputStream(file);
			outputStream.write(bytes);
			outputStream.flush();
			outputStream.close();
		}
	}

	/**
	 * merger file
	 * 
	 * @param srcFile
	 * @param destFile
	 * @throws Exception
	 */
	public static void mergerFile(String srcFile, String destFile) throws Exception {
		String srcFileContent = readFileContent(srcFile);
		String destFileContent = readFileContent(destFile);

		FileWriter fileWriter = new FileWriter(new File(destFile));
		fileWriter.write(destFileContent + "\n" + srcFileContent);
		fileWriter.flush();
		fileWriter.close();
	}

	/**
	 * merger file
	 * 
	 * @param srcFiles
	 * @param destFile
	 * @throws IOException
	 */
	public static void mergerFile(String[] srcFiles, String destFile) throws IOException {
		String srcFileContents = "";

		for (String srcFile : srcFiles) {
			File file = new File(srcFile);
			if (file.exists() && file.canRead()) {
				srcFileContents += readFileContent(srcFile) + "\n";
			}
		}

		String destFileContent = readFileContent(destFile);
		File destF = new File(destFile);
		FileWriter fileWriter = new FileWriter(destF);
		fileWriter.write(destFileContent + "\n" + srcFileContents);
		fileWriter.flush();
		fileWriter.close();
	}

	/**
	 * 移动文件
	 * 
	 * @param srcFile
	 *            原始文件
	 * @param destPath
	 *            目标路径
	 * @return
	 */
	public static boolean moveFile(File srcFile, String destPath) {
		return moveFile(srcFile, destPath, null);
	}

	/**
	 * 移动文件
	 * 
	 * @param srcFile
	 *            原始文件
	 * @param destPath
	 *            目标路径
	 * @param destFileName
	 *            目标文件名称
	 * @return
	 */
	public static boolean moveFile(File srcFile, String destPath, String destFileName) {
		File dir = new File(destPath);

		boolean success = srcFile.renameTo(new File(dir, (!StringUtil.isNullOrEmpty(destFileName) ? destFileName : srcFile.getName())));

		return success;
	}

	/**
	 * 复制文件
	 * 
	 * @param oldfile
	 * @param newPath
	 */
	public static void copyFile(File oldfile, String newPath) {
		try {
			int byteread = 0;
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldfile);
				File newPathFile = new File(newPath);
				File parentDir = newPathFile.getParentFile();
				if (parentDir != null && !parentDir.exists()) {
					createDirWhenNotExist(parentDir);
				}
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1024];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
				fs.flush();
				inStream.close();
				fs.close();
			}
		} catch (Exception e) {
			LoggerManager.error(e);
		}
	}

	/**
	 * read file content
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static String readFileContent(String filePath) throws IOException {
		StringBuffer fileBuffer = new StringBuffer();
		File file = new File(filePath);
		if (file.exists() && file.canRead()) {
			FileReader fileReader = new FileReader(file);
			int offSet = 0, size = 1000;
			char[] buffer = new char[size];
			while ((offSet = fileReader.read(buffer)) > 0) {
				fileBuffer.append(buffer, 0, offSet);
			}
			fileReader.close();
		}

		return fileBuffer.toString();
	}
	
	/**
	 * read stream content
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static String readFileContent(InputStream inputStream) throws IOException {
		BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = buffer.readLine()) != null) {
			sb.append(line);
		}
		inputStream.close();
		buffer.close();
		
		return sb.toString();
	}
	
	/**
	 * 读取文件行内容
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static List<String> readFileLines(String filePath) throws IOException {
		return readFileLines(new FileInputStream(filePath));
	}
	
	/**
	 * 读取文件行分页内容
	 * @param filePath
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws IOException
	 */
	public static List<String> readFileLines(String filePath, int pageIndex, int pageSize) throws IOException {
		return readFileLines(new FileInputStream(filePath), pageIndex, pageSize);
	}
	
	/**
	 * 读取文件行内容
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static List<String> readFileLines(InputStream inputStream) throws IOException {
		List<String> lines = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (br != null) {
				br.close();
			}
		}
		return lines;
	}
	
	/**
	 * 读取文件行分页内容
	 * @param inputStream
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws IOException
	 */
	public static List<String> readFileLines(InputStream inputStream, int pageIndex, int pageSize) throws IOException {
		List<String> lines = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			int index = 1, size = 0;
			while ((line = br.readLine()) != null) {
				if (size >= pageSize) {
					break;
				}
				if (index >= pageIndex) {
					lines.add(line);
					size++;
				}
				index++;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (br != null) {
				br.close();
			}
		}
		return lines;
	}

	/**
	 * write file content
	 * 
	 * @param filePath
	 * @param fileContent
	 * @throws IOException
	 */
	public static void writeFileContent(String filePath, String fileContent) throws IOException {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}

		createDirWhenNotExist(filePath);

		FileWriter writer = new FileWriter(file);
		writer.write(fileContent);
		writer.flush();
		writer.close();
	}

	/**
	 * get filename without extension
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFilePathNoExtension(String fileName) {
		int lastIndex = fileName.lastIndexOf(".");
		if (lastIndex > 0) {
			return fileName.substring(0, lastIndex);
		}
		return "";
	}

	/**
	 * get file extension
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileExtension(String fileName) {
		int lastIndex = fileName.lastIndexOf(".");
		if (lastIndex > 0) {
			return fileName.substring(lastIndex, fileName.length());
		}
		return "";
	}

	/**
	 * delete file
	 * 
	 * @param file
	 */
	public static void deleteFile(File file) {
		deleteFile(file, false);
	}

	/**
	 * delete file
	 * 
	 * @param file
	 * @param deeply
	 */
	public static void deleteFile(File file, boolean deeply) {
		if (file != null && file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else {
				File[] files = file.listFiles();
				if (files != null) {
					for (File item : files) {
						if (item.isFile()) {
							item.delete();
						} else {
							if (deeply) {
								deleteFile(item, deeply);
							}
						}
					}
				}
				file.delete();
			}
		}
	}

	/**
	 * create directory when it does not exist
	 * 
	 * @param path
	 */
	public static void createDirWhenNotExist(String path) {
		createDirWhenNotExist(new File(path));
	}

	/**
	 * create directory when it does not exist
	 * 
	 * @param file
	 */
	public static void createDirWhenNotExist(File file) {
		boolean isFile = file.isFile();
		boolean isDirectory = file.isDirectory();
		if (!isFile && !StringUtil.isNullOrEmpty(getFileExtension(file.getName()))) {
			isFile = true;
		}
		if (!isDirectory && StringUtil.isNullOrEmpty(getFileExtension(file.getName()))) {
			isDirectory = true;
		}

		if (isFile) {
			File pathFile = file.getParentFile();
			if (pathFile != null && !pathFile.exists()) {
				pathFile.mkdirs();
			}
		} else if (isDirectory) {
			if (!file.exists()) {
				file.mkdirs();
			}
		}
	}

	/**
	 * get root files
	 * 
	 * @return
	 */
	public static String[] getRootFiles() {
		File[] files = File.listRoots();
		String[] result = new String[files.length];
		for (int i = 0; i < files.length; i++) {
			result[i] = files[i].getPath();
		}
		return result;
	}

	/**
	 * get sub file
	 * 
	 * @param path
	 * @return
	 */
	public static List<File> getFiles(String path) {
		return getFiles(path, false);
	}

	/**
	 * get sub file
	 * 
	 * @param path
	 * @param deeply
	 * @return
	 */
	public static List<File> getFiles(String path, boolean deeply) {
		if (path == null || path.equals(""))
			return new ArrayList<File>();

		File filePath = new File(path);
		if (deeply) {
			// get deeply files
			List<File> files = new ArrayList<File>();
			getDeeplyFiles(filePath, files);
			return files;
		} else {
			// get sub files
			File[] subFiles = filePath.listFiles(new FileFilter() {
				@Override
				public boolean accept(File file) {
					return file.isFile();
				}
			});

			List<File> result = new ArrayList<File>();
			if (subFiles != null && subFiles.length > 0) {
				for (int i = 0; i < subFiles.length; i++) {
					result.add(subFiles[i]);
				}
			}
			return result;
		}
	}
	
	/**
	 * 是否存在文件
	 * @param filePath
	 * @return
	 */
	public static boolean existFile(String filePath) {
		if (!StringUtil.isNullOrEmpty(filePath)) {
			File file = new File(filePath);
			return file.exists() && file.isFile();
		}
		return false;
	}

	/**
	 * 将文件流转换成文件，返回文件的路径
	 *
	 * @param ios
	 * @param fileName
	 * @return
	 */
	public static String streamToFile(InputStream ios, String fileName) {
		FileOutputStream fos = null;
		try {
			File directory = new File("");// 参数为空
			String rootPath = directory.getCanonicalPath() + "/file/";
			String fileFullPath = rootPath + fileName;

			File filePath = new File(rootPath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			File file = new File(fileFullPath);
			if (file.exists()) {
				file.delete();
			}

			fos = new FileOutputStream(fileFullPath);

			int size = 0;
			byte[] buffer = new byte[1024];
			while ((size = ios.read(buffer, 0, 1024)) != -1) {
				fos.write(buffer, 0, size);
			}
			return fileFullPath;
		} catch (FileNotFoundException e) {
			LoggerManager.error(e);
		} catch (IOException e) {
			LoggerManager.error(e);
		} finally {
			try {
				if (fos != null)
					fos.close();
				if (ios != null)
					ios.close();
			} catch (IOException e) {
				LoggerManager.error(e);
			}
		}
		return null;
	}

	private static void getDeeplyFiles(File path, List<File> files) {
		List<File> fs = new ArrayList<File>();
		List<File> ds = new ArrayList<File>();
		for (File item : path.listFiles()) {
			if (item.isFile()) {
				fs.add(item);
			} else if (item.isDirectory()) {
				ds.add(item);
			}
		}

		// add files
		if (fs.size() > 0) {
			files.addAll(fs);
		}

		// deeply dirs
		if (ds.size() > 0) {
			for (File dir : ds) {
				getDeeplyFiles(dir, files);
			}
		}
	}
}
