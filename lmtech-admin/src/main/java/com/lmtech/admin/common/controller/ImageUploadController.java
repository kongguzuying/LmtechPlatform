package com.lmtech.admin.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.lmtech.admin.common.adaptor.WxTokenAdaptor;
import com.lmtech.admin.common.constants.CommonResultCode;
import com.lmtech.admin.common.constants.Constant;
import com.lmtech.admin.common.constants.FtpTool;
import com.lmtech.admin.common.constants.Global;
import com.lmtech.common.StateResult;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


@Controller
public class ImageUploadController extends BaseController{

    @Autowired
    private WxTokenAdaptor wxTokenAdaptor;

    @RequestMapping(value = "/image/uploadToFTP.json")
    public void handleFormUpload(HttpServletRequest request,String img_path,
                                 String oldMd5Name,HttpServletResponse response) throws Exception {
        LoggerManager.info("-----------图片上传到ftp开始-----------");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile commonsMultipartFile = multipartRequest.getFile("swfupload");
        if (StringUtil.isNullOrEmpty(commonsMultipartFile.getName())) {
            response.getWriter().printf(getErrorMessage("请选择文件"));
            return;
        }
        resultMap.put("sourceFileName", commonsMultipartFile.getOriginalFilename());
        //检查扩展名
        String name = commonsMultipartFile.getOriginalFilename();
        String fileExt = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
        InputStream in = commonsMultipartFile.getInputStream();
        //生成文件MD5
        String md5Name;
        if(StringUtil.isNullOrEmpty(oldMd5Name)){
            md5Name = DigestUtils.md5Hex(in);
        }else {//FTP分享卡面
            md5Name = oldMd5Name + "_blank";
        }

        resultMap.put("md5Name", md5Name);
        //生成访问url
        String newFileName = md5Name + "." + fileExt;
        String serverpath = null;
        String getBasepath = null;
        if(img_path.equals("img_product")){
            img_path = Constant.IMG_PRODUCT;//积分抽奖奖品
        }else if(img_path.equals("img_card")){
            img_path = Constant.IMG_CARD;//礼品卡
        }else{
            img_path = Constant.IMG_OTHER;//其它图片
        }

        if (!commonsMultipartFile.isEmpty()) {
            // 上传到FTP服务器
            FtpTool ft = new FtpTool();
            String url = Global.getFtpIp();
            String username = Global.getFtpUsername();
            String password = Global.getFtpPassword();
            serverpath = Global.getImgServerpath(img_path);
            getBasepath = Global.getBasepath();
            Boolean uploadflag = ft.uploadFile(url, username, password, getBasepath + serverpath, newFileName, commonsMultipartFile.getInputStream());
            if (!uploadflag) {
                LoggerManager.error("文件上传ftp异常");
                resultMap.put(Constant.RETURN_STATE, CommonResultCode.FAIL.getStatusCode());
                resultMap.put(Constant.RETURN_MSG, "图片上传ftp服务器异常");
                out(response, resultMap);
                return;
            }
        }
        String base_url = Global.getImgUrlHead() + serverpath + newFileName;
        resultMap.put(Constant.RETURN_STATE, 0);
        resultMap.put("url", base_url);
        resultMap.put("firstPart", Global.getImgUrlHead());
        resultMap.put("lastPart", newFileName);
        resultMap.put("basename", base_url);
        resultMap.put("thumb", base_url);

        LoggerManager.info("----------------上传图片到ftp结束------------");

        out(response, resultMap);
    }

    private String getErrorMessage(String message) {
        LoggerManager.warn("swfupload上传异常 ：" + message);
        return "{\"success\":\"FALSE\",\"message\":" + message + "}";
    }

    /**
     * 上传文件到微信服务器
     * @return
     */
    public String uploadFileToWeiXin(MultipartFile commonsMultipartFile) throws Exception{
        LoggerManager.info("----------------获取微信服务器token------------");
        StateResult stateResult = wxTokenAdaptor.getAccessToken();
        String token = null;
        if(stateResult.isSuccess()){
                Map<String, String> takenData = (Map<String, String>)stateResult.getData();
                token = takenData.get("token");
        }else {
            LoggerManager.error("获取微信服务器token失败 " + stateResult.getMsg());
            throw new Exception(stateResult.getMsg());
        }

        return this.FileUpload(Global.getWxUploadimg() + token,commonsMultipartFile);
    }

    @RequestMapping(value = "/image/uploadToWX.json")
    public void handleFormUploadToWX(HttpServletRequest request,HttpServletResponse response) throws Exception {
        LoggerManager.info("-----------图片上传到微信服务器接口被调用-----------");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile commonsMultipartFile = multipartRequest.getFile("swfupload");
        if (StringUtil.isNullOrEmpty(commonsMultipartFile.getName())) {
            response.getWriter().printf(getErrorMessage("请选择文件"));
            return;
        }
        resultMap.put("sourceFileName", commonsMultipartFile.getOriginalFilename());
        resultMap.put(Constant.RETURN_STATE, 0);
        resultMap.put("wx_background", this.uploadFileToWeiXin(commonsMultipartFile));

        out(response, resultMap);
    }

    public String FileUpload(String url, MultipartFile commonsMultipartFile) throws IOException {
        LoggerManager.info("----------------上传图片到微信服务器开始------------");
        String result = null;
        URL urlObj = new URL(url);
        // 连接
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

        //设置关键值
        con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false); // post方式不能使用缓存

        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");


        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary="+ BOUNDARY);

        // 第一部分：
        StringBuilder sb = new StringBuilder();
        sb.append("--"); // 必须多两道线
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"buffer\";filename=\""
                + commonsMultipartFile.getOriginalFilename() + "\"\r\n");
        sb.append("Content-Type: image/jpeg\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");

        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);

        // 文件正文部分
        // 把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(commonsMultipartFile.getInputStream());
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();

        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线

        out.write(foot);
        out.flush();
        out.close();

        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if(result==null){
                result = buffer.toString();
            }
        } catch (IOException e) {
            LoggerManager.info("发送POST请求出现异常！" + e);
            e.printStackTrace();
            throw new IOException("数据读取异常");
        } finally {
            if(reader!=null){
                reader.close();
            }
        }

        JSONObject jsonObj = JSONObject.parseObject(result);
        LoggerManager.info("----------------上传图片到微信服务器结束------------");
        return jsonObj.getString("url");
    }
}
