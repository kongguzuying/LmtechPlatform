package com.lmtech.infrastructure.facade;

import com.lmtech.facade.request.NormalRequest;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import com.lmtech.infrastructure.facade.request.GroupRequest;
import com.lmtech.infrastructure.facade.response.GroupListResponse;
import com.lmtech.infrastructure.facade.response.GroupResponse;
import com.lmtech.infrastructure.facade.stub.GroupFacade;
import com.lmtech.infrastructure.model.Group;
import com.lmtech.infrastructure.service.GroupManager;
import com.lmtech.infrastructure.service.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by huang.jb on 2017-3-29.
 */
@RestController
@RequestMapping(value = "/group")
@Api(description = "分组API入口")
public class GroupFacadeImpl implements GroupFacade {

    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupManager groupManager;

    @Override
    @RequestMapping(value = "/queryRootGroup", method = RequestMethod.POST)
    @ApiOperation(value = "查询根群组")
    public GroupListResponse queryRootGroup(@RequestBody NormalRequest request) {
        List<Group> groups = groupService.queryRootGroup();

        GroupListResponse response = new GroupListResponse();
        response.setSuccess(true);
        response.setData(groups);
        response.setMessage("查询根群组成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/queryChildrenGroup", method = RequestMethod.POST)
    @ApiOperation(value = "查询子群组")
    public GroupListResponse queryChildrenGroup(@RequestBody StringRequest request) {
        String parentId = request.getReqData();

        List<Group> groups = groupService.queryChildrenGroup(parentId);

        GroupListResponse response = new GroupListResponse();
        response.setSuccess(true);
        response.setData(groups);
        response.setMessage("查询子群组成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getGroup", method = RequestMethod.POST)
    @ApiOperation(value = "获取群组")
    public GroupResponse getGroup(@RequestBody StringRequest request) {
        String id = request.getReqData();

        Group group = groupManager.get(id);

        GroupResponse response = new GroupResponse();
        response.setData(group);
        response.setSuccess(true);
        response.setMessage("获取群组成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/addGroup", method = RequestMethod.POST)
    @ApiOperation(value = "添加群组")
    public StringResponse addGroup(@RequestBody GroupRequest request) {
        Group group = request.getReqData();

        String id = groupManager.add(group);

        StringResponse response = new StringResponse(id);
        response.setSuccess(true);
        response.setMessage("添加群组成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/editGroup", method = RequestMethod.POST)
    @ApiOperation(value = "编辑群组")
    public StringResponse editGroup(@RequestBody GroupRequest request) {
        Group group = request.getReqData();

        groupManager.edit(group);

        StringResponse response = new StringResponse();
        response.setSuccess(true);
        response.setMessage("编辑群组成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/removeGroup", method = RequestMethod.POST)
    @ApiOperation(value = "删除群组")
    public NormalResponse removeGroup(@RequestBody StringRequest request) {
        String id = request.getReqData();

        groupManager.remove(id);

        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("删除群组成功");
        return response;
    }
}
