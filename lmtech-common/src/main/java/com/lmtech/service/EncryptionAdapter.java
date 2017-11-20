package com.lmtech.service;

import com.lmtech.common.PlatformService;

/**
 * encryption adapter
 * @author huang.jb
 *
 */
public interface EncryptionAdapter extends PlatformService {
	/**
	 * get md5 encryption
	 * @return
	 */
	Encryption getMd5Encryption();
}
