package com.lmtech.service.support;

import com.lmtech.service.Encryption;
import com.lmtech.service.EncryptionAdapter;
import org.springframework.stereotype.Service;

@Service
public class EncryptionAdapterImpl implements EncryptionAdapter {

	private static final long serialVersionUID = 1L;

	@Override
	public Encryption getMd5Encryption() {
		return new MD5EncryptionImpl();
	}

}
