define('login_ctl', ['vue', 'constant', 'util'], function (Vue, C, util) {
    return {
        init: function() {
            var vue = new Vue({
                el: '#loginForm',
                data: {
                    loginName: '',
                    password: ''
                },
                computed: {},
                mounted: function() {},
                methods: {
                    submit: function () {
                        var that = this;

                        //请求接口
                        var data = util.buildRequest({
                            loginName: that.loginName,
                            password: that.password
                        });
                        util.httpPost({
                            url: C.service.url.login,
                            data: data,
                            success: function (data) {
                                var token = data.token;
                                var account = data.accountInfo;
                                util.setLocalValue('token', token.tokenCode);
                                util.setLocalValue('userId', account.userId);
                                window.location.href='/index.html';
                            },
                            error: function (data) {
                                alert(data.message);
                            },
                            fault: function (err) {
                                console.error('系统繁忙');
                            }
                        });
                    }
                }
            });
        }
    };
});