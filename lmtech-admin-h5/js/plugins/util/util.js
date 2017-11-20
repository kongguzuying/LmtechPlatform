define('util', ['jquery', 'jquery_ui', 'constant'], function ($, ui, C) {
    return {
        /**
         * httpGet请求
         * @param options
         */
        httpGet: function (options) {
            $.ajax({
                url: options.url,
                type: 'GET',
                async: (options.async ? options.async : true),
                success: function (data) {
                    console.debug(data);
                    if (data.success) {
                        if (options.success) {
                            options.success(data.data);
                        } else {
                            console.debug('未指定success处理方法');
                        }
                    } else {
                        if (options.error) {
                            options.error(data);
                        } else {
                            console.error(data.message);
                        }
                    }
                },
                error: function (err) {
                    if (options.fault) {
                        options.fault(err);
                    }
                    console.error(err);
                }
            });
        },
        /**
         * httpPost请求
         * @param options
         */
        httpPost: function (options) {
            $.ajax({
                url: options.url,
                type: 'POST',
                contentType: (options.contentType ? options.contentType : "application/json"),
                dataType: (options.dataType ? options.dataType : "json"),
                data: (options.data ? JSON.stringify(options.data) : null),
                async: (options.async ? options.async : true),
                success: function (data) {
                    if (data.success) {
                        if (options.success) {
                            options.success(data.data);
                        } else {
                            console.log('未指定success处理方法');
                        }
                    } else {
                        if (options.error) {
                            options.error(data);
                        } else {
                            console.error(data.message);
                        }
                    }
                },
                error: function (err) {
                    if (options.fault) {
                        options.fault(err);
                    } else {
                        console.error('http request error');
                    }
                }
            });
        },
        /**
         * 构建请求
         * @param data
         * @returns {{reqData: *, reqInfo: {}}}
         */
        buildRequest: function (data) {
            return {
                reqData: data,
                reqInfo: {}
            }
        },
        buildPageRequest: function (data) {
            return {
                pageIndex: (data.pageIndex ? data.pageIndex : 1),
                pageSize: (data.pageSize ? data.pageSize : 10),
                reqData: data.data,
                reqInfo: {}
            }
        },
        /**
         * 设置本地值
         * @param key
         * @param value
         */
        setLocalValue: function (key, value) {
            var ls = window.localStorage;
            ls.setItem(key, value);
        },
        /**
         * 获取本地值
         * @param key
         */
        getLocalValue: function (key) {
            var ls = window.localStorage;
            return ls.getItem(key);
        },
        /**
         * 获取请求参数
         * @param name
         * @returns {null}
         */
        getParam: function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) {
                return unescape(r[2]);
            } else {
                return null;
            }
        },
        /**
         * 获取用户token信息
         * @returns {*}
         */
        getToken: function () {
            return this.getLocalValue('token');
        },
        /**
         * 获取用户id
         * @returns {*}
         */
        getUserId: function () {
            return this.getLocalValue('userId');
        },
        /**
         * 校验token
         */
        checkToken: function () {
            var token = this.getToken();
            var req = this.buildRequest();
            req.token = token;
            this.httpPost({
                url: C.service.url.checkToken,
                data: req,
                async: false,
                success: function () {
                    console.debug('token check success');
                },
                error: function (data) {

                    window.location = C.page.url.login;
                }
            });
        }
    }
});