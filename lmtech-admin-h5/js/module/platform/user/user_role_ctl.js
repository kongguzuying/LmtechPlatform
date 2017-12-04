define('user_role_ctl', ['jquery', 'vue', 'constant', 'util', 'array_util'], function ($, Vue, C, util, arrayUtil) {
    return {
        init: function () {
            var that = this;
            util.checkToken();

            var userId = util.getParam('userId');
            var type = util.getParam('type');
            var url, data;
            if (type == 0) {
                //全部数据
                url = C.service.url.getRoleOfPage;
                data  = { pageIndex: 1, pageSize: C.pager.pageSize, pageParam: {} };
            } else if (type == 1) {
                //已授权数据
                url = C.service.url.getUserRoleOfPage;
                data  = { pageIndex: 1, pageSize: C.pager.pageSize, pageParam: {}, userId: userId };
            } else if (type == 2) {
                //未授权数据
                url = C.service.url.getUserUnauthRoleOfPage;
                data  = { pageIndex: 1, pageSize: C.pager.pageSize, pageParam: {}, userId: userId };
            } else {
                console.log('未知的type:' + type);
            }
            util.httpPost({
                url: url,
                data: data,
                success: function (data) {
                    var v = new Vue({
                        el: '#data',
                        data: {
                            userId: userId,
                            type: type,
                            selectedIds: [],
                            allIds: [],
                            unSelectedIds: [],
                            pageData: data,
                            isCheckAll: false
                        },
                        watch: {
                            isCheckAll: function (val) {
                                if (val) {
                                    var allIds = v.$data.allIds;
                                    v.$data.selectedIds = allIds;
                                } else {
                                    v.$data.selectedIds = [];
                                }
                            }
                        },
                        computed: {
                            allHref: function() {
                                return "urole.html?userId=" + userId + "&type=0";
                            },
                            authHref: function() {
                                return "urole.html?userId=" + userId + "&type=1";
                            },
                            unAuthHref: function() {
                                return "urole.html?userId=" + userId + "&type=2";
                            }
                        },
                        methods: {
                            auth: function () {
                                var allIds = v.$data.allIds;
                                var selIds = v.$data.selectedIds;
                                var unSelIds = arrayUtil.getNotSameDataOfArray(selIds, allIds);
                                v.$data.unSelectedIds = unSelIds;

                                if (type == 0) {
                                    //全部数据
                                    var result1 = that._addAuthItems(userId, selIds);
                                    var result2 = that._addUnAuthItems(userId, unSelIds);
                                    that._showResult(result1 && result2);
                                } else if (type == 1) {
                                    //已授权数据
                                    var result = that._addUnAuthItems(userId, unSelIds);
                                    that._showResult(result);
                                } else if (type == 2) {
                                    //未授权数据
                                    var result = that._addAuthItems(userId, selIds);
                                    that._showResult(result);
                                }
                            }
                        },
                        mounted: function () {
                            console.log('mounted');
                        }
                    });

                    //设置allIds
                    var allIds = that._getDataIds(data.items);
                    v.$data.allIds = allIds;
                    that._initAuthedItems(v, allIds);
                }
            });
        },
        _addAuthItems: function (userId, authIds) {
            var result = false;
            var param = util.buildRequest();
            param.userId = userId;
            param.authIds = authIds;
            util.httpPost({
                url: C.service.url.addUserRoles,
                async: false,
                data: param,
                success: function (data) {
                    result = true;
                }
            });
            return result;
        },
        _addUnAuthItems: function (roleId, unAuthIds) {
            var result = false;
            var param = util.buildRequest();
            param.roleId = roleId;
            param.authIds = unAuthIds;
            util.httpPost({
                url: C.service.url.removeUserRoles,
                async: false,
                data: param,
                success: function (data) {
                    result = true;
                }
            });
            return result;
        },
        _initAuthedItems: function (v, allIds) {
            var that = this;
            //设置已授权用户角色
            util.httpPost({
                url: C.service.url.getUserRoleOfPage,
                data: util.buildRequest(v.$data.roleId),
                success: function (data) {
                    var selIds = arrayUtil.getSameDataOfArray(that._getDataIds(data), allIds);
                    v.$data.selectedIds = selIds;
                }
            });
        },
        _getDataIds: function (data) {
            if (data && data.length > 0) {
                var items = data.items;
                var ids = [];
                for (var i = 0; i < data.length; i++) {
                    ids.push(data[i].id);
                }
                return ids;
            } else {
                return [];
            }
        },
        _showResult: function (result) {
            if (result) {
                alert('授权成功！');
                window.location.reload();
            } else {
                alert('授权过程中出现错误！');
            }
        }
    }
});