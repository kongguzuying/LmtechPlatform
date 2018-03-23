;
// 微信分享JS
(function () {
    var WeiXin = function (settings) {
        var settings = $.extend({}, WeiXin.defaults, settings);
        // 分享
        this.share = function () {
            var num = 1;
            var iID = setInterval(function () {
                // 500*50 毫秒后不再配置，说明配置请求出现问题
                if (num++ >= 150 || WeiXin.configFlag) {
                    clearInterval(iID);
                    wx.ready(function () {
                        try {
                            // 所有分享页面默认显示的栏目 项见微信文档附录3
                            var menuList = ["menuItem:share:timeline",
                                "menuItem:share:appMessage",
                                "menuItem:originPage"
                            ];
                            if (settings.notShareMessage) {
                                menuList.remove(0);
                                menuList.remove(1);
                            }
                            ;
                            if (settings.openBrowser) {
                                menuList.push("menuItem:openWithQQBrowser");
                                menuList.push("menuItem:openWithSafari");
                                menuList.push("menuItem:copyUrl");
                            }
                            ;
                            wx.showMenuItems({
                                menuList: menuList
                            });

                            // 获取“分享给朋友”按钮点击状态及自定义分享内容接口
                            if (!settings.notShareMessage) {
                                wx.onMenuShareAppMessage({
                                    title: settings.title, // 分享标题
                                    desc: settings.desc, // 分享描述
                                    link: settings.link, // 分享链接
                                    imgUrl: settings.imgUrl, // 分享图标
                                    type: settings.type, // 分享类型,music、video或link，不填默认为link
                                    dataUrl: settings.dataUrl, // 如果type是music或video，则要提供数据链接，默认为空
                                    success: settings.success,
                                    cancel: settings.cancel
                                });

                                // 获取“分享到朋友圈”按钮点击状态及自定义分享内容接口
                                wx.onMenuShareTimeline({
                                    title: settings.title, // 分享标题
                                    link: settings.link, // 分享链接
                                    imgUrl: settings.imgUrl, // 分享图标
                                    success: settings.success,  // 用户确认分享后执行的回调函数
                                    cancel: settings.cancel// 用户取消分享后执行的回调函数
                                });
                            }
                        } catch (e) {
                        }
                    });
                }
            }, 500);
        };

        // 获取地址
        this.getLocation = function (_this, callback) {
            var num = 1;
            var iID = setInterval(function () {
                // 500*50 毫秒后不再配置，说明配置请求出现问题
                if (num++ >= 150 || WeiXin.configFlag) {
                    clearInterval(iID);
                    wx.ready(function () {
                        try {
                            wx.getLocation({
                                type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
                                success: function (res) {
                                    var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                                    var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
                                    if (typeof callback == "function") {
                                        callback.call(_this, longitude, latitude);
                                    }
                                }
                            });
                        } catch (e) {
                        }
                    });
                }
            }, 500);
        };

    }

    var wap_path = WEB_CONFIG.global.WEB_PATH;

    // 微信分享 默认参数
    WeiXin.defaults = {
        title: "分享", // 分享标题
        desc: "分享", // 分享描述
        link: wap_path, // 分享链接
        imgUrl: "", // 分享图标
        type: "link", // 分享类型,music、video或link，不填默认为link
        dataUrl: "", // 如果type是music或video，则要提供数据链接，默认为空
        success: function () {},// 用户确认分享后执行的回调函数
        cancel: function () {}//用户取消分享后执行的回调函数
    }

    // 微信分享配置
    WeiXin.configFlag = false;

    // 微信浏览器检测
    WeiXin.checkBrowser = function () {
        var ua = window.navigator.userAgent.toLowerCase();
        if (ua.match(/MicroMessenger/i) == 'micromessenger') {
            return true;
        } else {
            return false;
        }
    }



    // 微信配置 config
    WeiXin.config = function () {

        // $.ajax({
        //     type: 'POST',
        //     url: [WEB_CONFIG.global.CRM_DOMAIN, "/crm/wxtoken/getJsApiSign"].join(''),
        //     data: {
        //         url: [location.origin, location.pathname, location.search].join('')
        //     },
        //     dataType: 'json',
        // })
        var _params = {
            url: location.href.split('#')[0],
            appname:'_wap_'
        }
        Util.requestPost("/crm/wxtoken/getJsApiSign", _params, 'crm').done(function (data) {

        // Util.requestPost("/queryShareConfig.do", _params, 'wap').done(function (data) {
            data = data.data
            if (data) {
                wx.config({
                    debug: false,
                    appId: data.appId,
                    timestamp: data.timestamp,
                    nonceStr: data.nonceStr,
                    signature: data.signature,
                    jsApiList: ['checkJsApi',
                        'onMenuShareTimeline',
                        'onMenuShareAppMessage',
                        'showOptionMenu',
                        'hideOptionMenu',
                        'openLocation',
                        'getLocation',
                        'hideAllNonBaseMenuItem',
                        'showMenuItems',
                        'addCard',
                        'openCard'
                    ]
                });

                wx.ready(function () {
                    try {
                        console.log('wx ready')
                        WeiXin.configFlag = true;

                        // 隐藏所有的 非基础的菜单项
                        //wx.hideAllNonBaseMenuItem();
                        // wx.showMenuItems({
                        //     menuList: ['menuItem:refresh','menuItem:share:appMessage'] // 要显示的菜单项，所有menu项见附录3
                        // });
                    } catch (e) {
                        console.log('wx no ready')
                    }
                });
            }
        }).fail(function (err) {
            //weui.toast(err, 3000);
        });
    };

    // 执行config
    if (WeiXin.checkBrowser()) {
        WeiXin.config();
    }

    $.WeiXin = function (settings) {
        return new WeiXin(settings);
    };
})();