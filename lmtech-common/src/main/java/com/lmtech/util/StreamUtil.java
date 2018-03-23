package com.lmtech.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by huang.jb on 2016-8-17.
 */
public class StreamUtil {

    public static byte[] readStreamBytes(InputStream inputStream) throws IOException {
        int offSet = 0, size = 1000;
        byte[] buffer = new byte[size];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while ((offSet = inputStream.read(buffer)) > 0) {
            baos.write(buffer, 0, offSet);
        }
        inputStream.close();
        baos.close();
        return baos.toByteArray();
    }
}
