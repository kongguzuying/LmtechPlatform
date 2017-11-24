define('role_index_ctl', ['jquery', 'vue', 'constant', 'util'], function ($, Vue, C, util) {
    return {
        init: function () {
            var that = this;
            util.checkToken();

            util.httpPost({
                url: C.service.url.getRoleOfPage,
                data: util.buildPageRequest({
                    pageIndex: 1,
                    pageSize: 20,
                    pageParam: null
                }),
                success: function (data) {
                    var vd = new Vue({
                        el: '#data',
                        data: {
                            pageData: data
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
                            authUser: function (roleId) {
                                window.location.href = "ruser.html?roleId=" + roleId + "&type=0";
                            },
                            authMenu: function (roleId) {
                                window.location.href = "rmenu.html?roleId=" + roleId + "&type=0";
                            }
                        },
                        mounted: function () {
                            console.log('mounted');
                        }
                    });
                }
            });
        }
    }
});