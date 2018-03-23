define('user_edit_ctl', ['jquery', 'jquery_validate', 'vue', 'constant', 'util'], function ($, $valid, Vue, C, util) {
    return {
        init: function () {
            var that = this;
            util.checkToken();

            var id = util.getParam('id');
            if (id) {
                util.httpPost({
                    url: C.service.url.getUser,
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
                        var url = (v.$data.entity.id ? C.service.url.editUser : C.service.url.addUser);
                        util.httpPost({
                            url: url,
                            data: reqData,
                            success: function (data) {
                                alert('维护用户数据成功！');
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
                    "email":  {
                        email: true
                    },
                    "mobile":  {
                        number : true
                    },
                    "qq":  {
                        number : true
                    }
                },
                messages: {
                    "name": {
                        required: "请输入姓名",
                        maxlength: "最多输入50个字符"
                    },
                    "email":  {
                        email: "请输入正确格式"
                    },
                    "mobile":  {
                        number: "请输入正确格式"
                    },
                    "qq":  {
                        number: "请输入正确格式"
                    }
                }
            });
            return $("form").valid();
        }
    }
});