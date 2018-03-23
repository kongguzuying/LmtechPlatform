define('role_edit_ctl', ['jquery', 'jquery_validate', 'vue', 'constant', 'util'], function ($, $valid, Vue, C, util) {
    return {
        init: function () {
            var that = this;
            util.checkToken();

            var id = util.getParam('id');
            if (id) {
                util.httpPost({
                    url: C.service.url.getRole,
                    data: util.buildRequest(id),
                    success: function (data) {
                        that._buildVuePage({
                            id: id,
                            entity: data
                        });
                    }
                });
            } else {
                that._buildVuePage({
                    id: id,
                    entity: {
                        visible: true
                    }
                });
            }
            that._buildValidForm();
        },
        _buildVuePage: function (data) {
            var v = new Vue({
                el: '#page',
                data: data,
                methods: {
                    submit: function () {
                        var reqData = util.buildRequest(v.$data.entity);
                        var url = (v.$data.entity.id ? C.service.url.editRole : C.service.url.addRole);
                        util.httpPost({
                            url: url,
                            data: reqData,
                            success: function (data) {
                                alert('维护角色成功！');
                                window.location.href='index.html';
                            }
                        });
                    }
                },
                mounted: function () {
                    console.log('mounted');
                }
            });
        },
        _buildValidForm: function () {
            $("form").validate({
                rules: {
                    "name":  {
                        required: true,
                        maxlength: 50
                    },
                    "level":  {
                        required: true,
                        digits : true,
                        min: 1,
                        max: 254
                    },
                    "remark": {
                        maxlength: 500
                    }
                },
                messages: {
                    "name": {
                        required: "请输入角色名称",
                        maxlength: "最多输入50个字符"
                    },
                    "level":  {
                        required: "请输入级别",
                        digits : "必须输入正整数",
                        min: "不能小于1",
                        max: "不能大于255"
                    },
                    "remark": {
                        maxlength: "最多输入500个字符"
                    }
                }
            });
            return $("form").valid();
        }
    }
});