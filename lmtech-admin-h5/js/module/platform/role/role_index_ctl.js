define('role_index_ctl', ['jquery', 'vue', 'vue_comps', 'constant', 'util'], function ($, Vue, vc, C, util) {
    return {
        init: function () {
            var that = this;
            util.checkToken();
            that._requestData(1, null, true);
        },
        _requestData: function (pageIndex, pageParam, initVue) {
            var that = this;
            util.httpPost({
                url: C.service.url.getRoleOfPage,
                data: util.buildPageRequest({
                    pageIndex: pageIndex,
                    pageSize: C.pager.pageSize,
                    pageParam: pageParam
                }),
                success: function (data) {
                    if (initVue) {
                        that._initPage(data);
                    } else {
                        var vueData = that.v.$data;
                        vueData.pageData = data;
                        vueData.pager = util.buildPageCompData(data);
                    }
                }
            });
        },
        _initPage: function (data) {
            var that = this;
            //初始化组件
            vc.initPagerComp();
            //初始化页面
            var v = new Vue({
                el: '#data',
                data: {
                    pageData: data,
                    pager: util.buildPageCompData(data),
                    queryParam: {
                        roleName: '',
                        roleRemark: ''
                    }
                },
                methods: {
                    addRole: function () {
                        var url = 'edit.html';
                        window.location.href = url;
                    },
                    editRole: function (roleId) {
                        var url = 'edit.html?id=' + roleId;
                        window.location.href = url;
                    },
                    removeRole: function (roleId) {
                        util.httpPost({
                            url: C.service.url.removeRole,
                            data: util.buildRequest(roleId),
                            success: function () {
                                alert("删除角色成功！");
                                window.location.reload();
                            }
                        });
                    },
                    search: function () {
                        that._requestData(1, v.$data.queryParam, false);
                    },
                    authUser: function (roleId) {
                        window.location.href = "ruser.html?roleId=" + roleId + "&type=0";
                    },
                    authMenu: function (roleId) {
                        window.location.href = "rmenu.html?roleId=" + roleId + "&type=0";
                    },
                    pageClick: function () {
                        var pager = v.$data.pager;
                        console.log(pager.pageIndex);

                        that._requestData(pager.pageIndex, null);
                    }
                },
                mounted: function () {
                    console.log('mounted');
                }
            });
            that.v = v;
        }
    }
});