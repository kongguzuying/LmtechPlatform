define('user_index_ctl', ['jquery', 'vue', 'vue_comps', 'constant', 'util'], function ($, Vue, vc, C, util) {
    return {
        init: function () {
            var that = this;
            util.checkToken();
            that._requestData(1, null, true);
        },
        _requestData: function (pageIndex, queryParam, initVue) {
            var that = this;
            util.httpPost({
                url: C.service.url.getUserOfPage,
                data: util.buildPageRequest({
                    pageIndex: pageIndex,
                    pageSize: C.pager.pageSize,
                    queryParam: queryParam
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
                        nickName: '',
                        email: '',
                        mobile: '',
                        sex: ''
                    }
                },
                methods: {
                    addUser: function () {
                        var url = 'edit.html';
                        window.location.href = url;
                    },
                    editUser: function (userId) {
                        var url = 'edit.html?id=' + userId;
                        window.location.href = url;
                    },
                    removeUser: function (userId) {
                        util.httpPost({
                            url: C.service.url.removeUser,
                            data: util.buildRequest(userId),
                            success: function () {
                                alert("删除用户成功！");
                                window.location.reload();
                            }
                        });
                    },
                    search: function () {
                        that._requestData(1, v.$data.queryParam, false);
                    },
                    authRole: function (userId) {
                        window.location.href = "urole.html?userId=" + userId + "&type=0";
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