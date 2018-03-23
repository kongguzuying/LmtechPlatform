package com.lmtech.admin.common.web;

import com.lmtech.util.LoggerManager;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * Created by huang.jb on 2016-8-10.
 */
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private HttpServletRequest request;
    private byte[] body;
    private String bodyString;
    private boolean readBodyStream;

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (isReadBodyStream()) {
            if (body == null) {
                body = new byte[0];
            }
            final ByteArrayInputStream bais = new ByteArrayInputStream(body);
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                @Override
                public int read() throws IOException {
                    return bais.read();
                }
            };
        } else {
            return super.getInputStream();
        }
    }

    /**
     * 获取body字符串
     * @return
     */
    public String getBodyString() throws UnsupportedEncodingException {
        bodyString = new String(this.getBody(), "UTF-8");
        return bodyString;
    }

    /**
     * 获取body
     * @return
     */
    public synchronized byte[] getBody() {
        if (readBodyStream) {
            return body;
        } else {
            InputStream inputStream = null;
            try {
                inputStream = request.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int byteread;
                byte[] buffer = new byte[1024];
                while ((byteread = inputStream.read(buffer)) != -1) {
                    bos.write(buffer, 0, byteread);
                }
                body = bos.toByteArray();
                bos.close();
                this.readBodyStream = true;
            } catch (IOException e) {
                LoggerManager.error(e);
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        LoggerManager.error(e);
                    }
                }
            }
            return body;
        }
    }

    public boolean isReadBodyStream() {
        return readBodyStream;
    }

    public void setReadBodyStream(boolean readBodyStream) {
        this.readBodyStream = readBodyStream;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
