define('role_edit_ctl', ['jquery', 'vue', 'constant', 'util', 'index_ctl'], function ($, Vue, C, util, index) {
    return {
        init: function () {
            var that = this;
            util.checkToken();

            var id = util.getParam('id');
            var parentId = util.getParam('parentId');
            var parentName = util.getParam('parentName');
            if (id) {
                util.httpPost({
                    url: C.service.url.getMenu,
                    data: util.buildRequest(id),
                    success: function (data) {
                        that._buildVuePage({
                            id: id,
                            parentId: parentId,
                            parentName: parentName,
                            entity: data
                        });
                    }
                });
            } else {
                that._buildVuePage({
                    id: id,
                    parentId: '0',
                    parentName: '',
                    entity: {
                        parentId: '0',
                        visible: true
                    }
                });
            }

            if (parentId == "0") {
                $("#parentName").val("根菜单");
            }
        },
        _buildVuePage: function (data) {
            var v = new Vue({
                el: '#page',
                data: data,
                methods: {
                    submit: function () {
                        v.$data.entity.parentId = v.$data.parentId;
                        v.$data.entity.parentName = v.$data.parentName;
                        var reqData = util.buildRequest(v.$data.entity);
                        var url = (v.$data.entity.id ? C.service.url.editMenu : C.service.url.addMenu);
                        util.httpPost({
                            url: url,
                            data: reqData,
                            success: function (data) {
                                alert('维护菜单成功！');
                                window.location.href='index.html';
                            }
                        });
                    },
                    setParentMenu: function (entity) {
                        var id = entity.id;
                        var parentId = v.$data.parentId;
                        top.indexCtl.showDialog({
                            width: 800,
                            height: 500,
                            title: '父菜单 - 设置',
                            url: C.basePath + 'page/platform/menu/selparent.html?id=' + (id ? id : "") + '&parentId=' + (parentId ? parentId : '0'),
                            opener: window,
                            refreshFinish: false,
                            okFunc: function () {
                                var content = top.indexCtl.getDialogContent();
                                var parentId = content.find('#parentId').val();
                                var parentName = content.find('#parentName').val();

                                if (parentId) {
                                    v.$data.parentId = parentId;
                                    v.$data.parentName = parentName;
                                } else {
                                    v.$data.parentId = '0';
                                    v.$data.parentName = '根菜单';
                                }
                            }
                        });
                    },
                    setRootMenu: function () {
                        $('#parentId').val('0');
                        $('#parentName').val('根菜单');
                    },
                    changeIcon: function (obj) {
                        $('#icon_img').attr('class', "").addClass('fa icon_fa').addClass($(obj).val());
                    }
                },
                mounted: function () {
                    console.log('mounted');
                }
            });
        }
    }
});