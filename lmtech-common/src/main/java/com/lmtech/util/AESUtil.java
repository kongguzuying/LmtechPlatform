package com.lmtech.util;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * AES工具类
 * @author huang.jb
 *
 */
public class AESUtil {
	// 密钥算法
	public static final String KEY_ALGORITHM = "AES";

	// 加解密算法/工作模式/填充方式,Java6.0支持PKCS5Padding填充方式,BouncyCastle支持PKCS7Padding填充方式
	public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
	
	private static String KEY = "s7vD0s8YSMtoDR/BF4OOow==";

	public static boolean initialized = false;  
	
	/*static {
		try {
			KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM); // 实例化密钥生成器
			kg.init(128); // 初始化密钥生成器:AES要求密钥长度为128,192,256位
			SecretKey secretKey = kg.generateKey(); // 生成密钥
			KEY = Base64.encodeBase64String(secretKey.getEncoded()); // 获取二进制密钥编码形式
		} catch (Exception e) {
			LoggerManager.error(e);
		}
	}*/

	/**
	 * 转换密钥
	 */
	public static Key toKey(byte[] key) throws Exception {
		return new SecretKeySpec(key, KEY_ALGORITHM);
	}

	/**
	 * 加密数据
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return 加密后的数据
	 * */
	public static String encrypt(String data, String key) throws Exception {
		Key k = toKey(Base64.decodeBase64(key)); // 还原密钥
		// 使用PKCS7Padding填充方式,这里就得这么写了(即调用BouncyCastle组件实现)
		// Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM); // 实例化Cipher对象，它用于完成实际的加密操作
		cipher.init(Cipher.ENCRYPT_MODE, k); // 初始化Cipher对象，设置为加密模式
		return Base64.encodeBase64String(cipher.doFinal(data.getBytes())); // 执行加密操作。加密后的结果通常都会用Base64编码进行传输
	}

	/**
	 * 加密数据
	 * 
	 * @param data
	 *            待加密数据
	 * @return 加密后的数据
	 * */
	public static String encrypt(String data) throws Exception {
		return encrypt(data, KEY);
	}
	
	/**
	 * 解密数据
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return 解密后的数据
	 * */
	public static String decrypt(String data, String key) throws Exception {
		Key k = toKey(Base64.decodeBase64(key));
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k); // 初始化Cipher对象，设置为解密模式
		return new String(cipher.doFinal(Base64.decodeBase64(data))); // 执行解密操作
	}
	
	/**
	 * 解密数据，使用默认KEY
	 * 
	 * @param data
	 *            待解密数据
	 **/
	public static String decrypt(String data) throws Exception { 
		return decrypt(data, KEY);
	}

	public static void initialize(){  
        if (initialized) return;
        Security.addProvider(new BouncyCastleProvider());
        initialized = true;  
    }
	//生成iv  
    public static AlgorithmParameters generateIV(byte[] iv) throws Exception{  
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");  
        params.init(new IvParameterSpec(iv));  
        return params;  
    }  
    
	public byte[] decrypt(byte[] content, byte[] keyByte, byte[] ivByte) throws InvalidAlgorithmParameterException {
	     initialize();
	     try {
	         Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
	         Key sKeySpec = new SecretKeySpec(keyByte, "AES");
	         cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIV(ivByte));// 初始化 
	         byte[] result = cipher.doFinal(content);
	         return result;
	     } catch (NoSuchAlgorithmException e) {
	         e.printStackTrace();  
	     } catch (NoSuchPaddingException e) {
	         e.printStackTrace();  
	     } catch (InvalidKeyException e) {
	         e.printStackTrace();
	     } catch (IllegalBlockSizeException e) {
	         e.printStackTrace();
	     } catch (BadPaddingException e) {
	         e.printStackTrace();
	     } catch (NoSuchProviderException e) {
	         e.printStackTrace();
	     } catch (Exception e) {
	         e.printStackTrace();
	     }
	     return null;
	}
}