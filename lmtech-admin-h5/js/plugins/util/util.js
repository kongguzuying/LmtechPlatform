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
        /**
         * 构建分页请求
         * @param data
         */
        buildPageRequest: function (data) {
            data.pageIndex = (data.pageIndex ? data.pageIndex : 1);
            data.pageSize = (data.pageSize ? data.pageSize : 10);
            data.pageParam = data.pageParam;
            data.reqData = {};
            data.reqInfo = {};
            return data;
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
        },
        /**
         * 构建分页组件数据
         * @returns {{firstPage: number, lastPage: number, prevPage: number, nextPage: number, currentPageIndex: number, pageIndex: number, pageCount: number, pageItems: [null,null]}}
         */
        buildPageCompData: function (pageData) {
            var currentPageNumber = pageData.currentPageNumber;
            var pageCount = pageData.totalPage;
            var prevPage = (currentPageNumber > 1 ? currentPageNumber - 1 : 1);
            var nextPage = (currentPageNumber < pageCount ? currentPageNumber + 1 : pageCount);

            if (pageCount <= 1) {
                return {
                    visible: false,
                    firstPage: 1,
                    lastPage: pageCount,
                    prevPage: prevPage,
                    nextPage: nextPage,
                    currentPageIndex: currentPageNumber,
                    pageIndex: currentPageNumber,
                    pageCount: pageCount,
                    pageItems: []
                }
            } else {
                var maxItems = 7;
                var pageItems = [];
                if (pageCount <= maxItems) {
                    //显示全部页
                    for (var i = 1; i <= pageCount; i++) {
                        pageItems[i] = {
                            pageIndex: i,
                            isActive: (i == currentPageNumber)
                        }
                    }
                } else {
                    //显示当前页相邻的页数
                    var halfPages = parseInt(maxItems / 2);
                    var i;
                    var leftPage = currentPageNumber - halfPages;
                    var rightPage = currentPageNumber + halfPages;
                    if (leftPage <= 0) {
                        //左边不够显示，补到右边
                        i = 1;
                        if (rightPage < pageCount) {
                            var offset = 1 - leftPage;
                            rightPage = rightPage + offset;
                            if (rightPage > pageCount) {
                                rightPage = pageCount;
                            }
                        }
                    } else {
                        i = leftPage;
                    }
                    if (rightPage > pageCount && i != 1) {
                        //右边不够显示，补到左边
                        var offset = rightPage - pageCount;
                        i = i - offset;
                        if (i < 1) {
                            i = 1;
                        }
                        rightPage = pageCount;
                    }
                    for (var j = 0; i <= rightPage; i++, j++) {
                        pageItems[j] = {
                            pageIndex: i,
                            isActive: (i == currentPageNumber)
                        }
                    }
                }
                return {
                    visible: true,
                    firstPage: 1,
                    lastPage: pageCount,
                    prevPage: prevPage,
                    nextPage: nextPage,
                    currentPageIndex: currentPageNumber,
                    pageIndex: currentPageNumber,
                    pageCount: pageCount,
                    pageItems: pageItems
                };
            }
        }
    }
});