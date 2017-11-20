package com.lmtech.admin.common.adaptor;

import com.lmtech.common.PageData;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import com.lmtech.infrastructure.facade.request.ApkPackageQueryRequest;
import com.lmtech.infrastructure.facade.request.ApkPackageRequest;
import com.lmtech.infrastructure.facade.response.ApkPackageListResponse;
import com.lmtech.infrastructure.facade.response.ApkPackageResponse;
import com.lmtech.infrastructure.facade.stub.ApkPackageFacade;
import com.lmtech.infrastructure.model.ApkPackage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 移动端安装包服务Adaptor
 * Created by huang.jb on 2017-3-28.
 */
@Service
public class ApkPackageAdaptor extends ServiceAdaptorBase implements ControllerManager<ApkPackage> {

    //@Autowired
    private ApkPackageFacade apkPackageFacade;

    @Override
    public String add(ApkPackage apkPackage) {
        ApkPackageRequest request = new ApkPackageRequest();
        request.setReqData(apkPackage);
        initRequest(request);

        StringResponse response = apkPackageFacade.addApkPackage(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public String edit(ApkPackage apkPackage) {
        ApkPackageRequest request = new ApkPackageRequest();
        request.setReqData(apkPackage);
        initRequest(request);

        StringResponse response = apkPackageFacade.editApkPackage(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public void remove(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);

        NormalResponse response = apkPackageFacade.removeApkPackage(request);
        validResponse(response);
    }

    @Override
    public ApkPackage get(String id) {
        StringRequest request = new StringRequest(id);
        initRequest(request);

        ApkPackageResponse response = apkPackageFacade.getApkPackage(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public List<ApkPackage> getAll() {
        ApkPackageQueryRequest request = new ApkPackageQueryRequest();
        initRequest(request);

        ApkPackageListResponse response = apkPackageFacade.getAllApkPackage(request);
        validResponse(response);
        return response.getData();
    }

    @Override
    public PageData getPageData(ApkPackage param, int pageIndex, int pageSize) {
        return null;
    }

    /**
     * 查询最新的移动端安装包
     * @return
     */
    public ApkPackage getLatestApkPackage(String type) {
        StringRequest request = new StringRequest();
        request.setReqData(type);
        initRequest(request);

        ApkPackageResponse response = apkPackageFacade.queryLatestApkPackage(request);
        validResponse(response);
        return response.getData();
    }
}
