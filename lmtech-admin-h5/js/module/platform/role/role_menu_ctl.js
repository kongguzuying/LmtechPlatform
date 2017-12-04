define('role_menu_ctl', ['jquery', 'jquery_treeTable', 'vue', 'constant', 'util', 'array_util'], function ($, tb, Vue, C, util, arrayUtil) {
    return {
        init: function () {
            var that = this;
            util.checkToken();

            var roleId = util.getParam('roleId');
            var type = util.getParam('type');
            var url, data;
            if (type == 0) {
                //全部数据
                url = C.service.url.getAllMenus;
                data  = util.buildRequest(util.getUserId());
            } else if (type == 1) {
                //已授权数据
                url = C.service.url.getRoleMenus;
                data  = util.buildRequest(roleId);
            } else if (type == 2) {
                //未授权数据
                url = C.service.url.getRoleUnauthMenus;
                data  = util.buildRequest(roleId);
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
                            roleId: roleId,
                            type: type,
                            selectedIds: [],
                            allIds: [],
                            unSelectedIds: [],
                            menus: data,
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
                                return "rmenu.html?roleId=" + roleId + "&type=0";
                            },
                            authHref: function() {
                                return "rmenu.html?roleId=" + roleId + "&type=1";
                            },
                            unAuthHref: function() {
                                return "rmenu.html?roleId=" + roleId + "&type=2";
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
                                    var result1 = that._addAuthItems(roleId, selIds);
                                    var result2 = that._addUnAuthItems(roleId, unSelIds);
                                    that._showResult(result1 && result2);
                                } else if (type == 1) {
                                    //已授权数据
                                    var result = that._addUnAuthItems(roleId, unSelIds);
                                    that._showResult(result);
                                } else if (type == 2) {
                                    //未授权数据
                                    var result = that._addAuthItems(roleId, selIds);
                                    that._showResult(result);
                                }
                            },
                            getTrClass: function (menu) {
                                if (!menu.root) {
                                    return 'child-of-' + menu.parentId;
                                } else {
                                    return null;
                                }
                            }
                        },
                        mounted: function () {
                            console.log('mounted');
                            $('.treeTable').treeTable({initialState: 'expanded'});
                        }
                    });

                    //设置allIds
                    var allIds = that._getDataIds(data);
                    v.$data.allIds = allIds;
                    that._initAuthedItems(v, allIds);
                    that.v = v;
                }
            });
        },
        _addAuthItems: function (roleId, authIds) {
            var result = false;
            var param = util.buildRequest();
            param.roleId = roleId;
            param.authIds = authIds;
            util.httpPost({
                url: C.service.url.addRoleMenus,
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
                url: C.service.url.removeRoleMenus,
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
                url: C.service.url.getRoleMenus,
                data: util.buildRequest(v.$data.roleId),
                success: function (data) {
                    var selIds = arrayUtil.getSameDataOfArray(that._getDataIds(data), allIds);
                    v.$data.selectedIds = selIds;
                    for (var i = 0; i < selIds.length; i++) {
                        $('input[mid="' + selIds[i] + '"]').attr('checked', 'checked');
                    }
                }
            });
        },
        _getDataIds: function (data) {
            if (data && data.length > 0) {
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