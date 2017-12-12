define('index_ctl', ['jquery', 'jquery_collapsible', 'vue', 'constant', 'util'], function ($, jqCollapsible, Vue, C, util) {
    return {
        /**
         * 初始化操作
         */
        init: function () {
            var that = this;
            util.checkToken();

            //初始化菜单
            var req = util.buildRequest(util.getUserId());
            util.httpPost({
                url: C.service.url.getUserMenus,
                data: req,
                success: function (data) {
                    var menus = eval('(' + '[{"dataVersion":2,"id":"897733078791749632","name":"系统管理","parentId":"0","childrens":[{"dataVersion":3,"id":"897733189508792320","name":"菜单管理","parentId":"897733078791749632","childrens":[],"href":"page/platform/menu/index.html","target":"F","visible":true,"menuType":"category","icon":"fa-dot-circle-o","sortNo":0,"appCode":null,"navigator":"系统管理@@菜单管理","root":false,"delete":false},{"createDate":"2017-08-16 16:14:29","createUser":null,"createUserName":null,"updateDate":"2017-10-17 15:34:18","updateUserName":null,"updateUser":null,"dataVersion":2,"id":"897733288293040128","name":"用户管理","parentId":"897733078791749632","childrens":[],"href":"page/platform/user/index.html","target":"F","visible":true,"menuType":"category","icon":"fa-dot-circle-o","sortNo":10,"appCode":null,"navigator":"系统管理@@用户管理","root":false,"delete":false},{"createDate":"2017-08-16 16:14:44","createUser":null,"createUserName":null,"updateDate":"2017-10-17 15:34:25","updateUserName":null,"updateUser":null,"dataVersion":2,"id":"897733352507834368","name":"角色管理","parentId":"897733078791749632","childrens":[],"href":"page/platform/role/index.html","target":"F","visible":true,"menuType":"category","icon":"fa-dot-circle-o","sortNo":20,"appCode":null,"navigator":"系统管理@@角色管理","root":false,"delete":false}],"href":null,"target":"F","visible":true,"menuType":"category","icon":"fa-laptop","sortNo":0,"appCode":null,"navigator":"系统管理","root":true,"delete":false},{"createDate":"2017-09-26 16:46:29","createUser":null,"createUserName":null,"updateDate":"2017-10-18 09:30:38","updateUserName":null,"updateUser":null,"dataVersion":5,"id":"912599244534185984","name":"营销推广","parentId":"0","childrens":[{"createDate":"2017-09-28 14:37:51","createUser":null,"createUserName":null,"updateDate":"2017-10-18 09:55:02","updateUserName":null,"updateUser":null,"dataVersion":3,"id":"913291649029767168","name":"尊享商场广告","parentId":"912599244534185984","childrens":[],"href":null,"target":"F","visible":true,"menuType":"item","icon":"fa-dot-circle-o","sortNo":10,"appCode":null,"navigator":"营销推广@@尊享商场广告","root":false,"delete":false},{"createDate":"2017-10-18 09:54:56","createUser":null,"createUserName":null,"updateDate":"2017-10-18 09:57:42","updateUserName":null,"updateUser":null,"dataVersion":2,"id":"920468207595159552","name":"积分规则管理","parentId":"912599244534185984","childrens":[],"href":"platform/integralRule/list.do","target":"F","visible":true,"menuType":"category","icon":"fa-dot-circle-o","sortNo":20,"appCode":null,"navigator":"营销推广@@积分规则管理","root":false,"delete":false}],"href":null,"target":"F","visible":true,"menuType":"category","icon":"fa-book","sortNo":0,"appCode":null,"navigator":"营销推广","root":true,"delete":false},{"createDate":"2017-09-28 19:18:27","createUser":null,"createUserName":null,"updateDate":"2017-10-17 15:35:22","updateUserName":null,"updateUser":null,"dataVersion":2,"id":"913362261781774336","name":"代码管理","parentId":"0","childrens":[{"createDate":"2017-09-28 19:19:03","createUser":null,"createUserName":null,"updateDate":"2017-10-17 15:35:26","updateUserName":null,"updateUser":null,"dataVersion":2,"id":"913362412697026560","name":"代码类型管理","parentId":"913362261781774336","childrens":[],"href":"platform/code/list.do","target":"F","visible":true,"menuType":"category","icon":"fa-dot-circle-o","sortNo":10,"appCode":null,"navigator":"代码管理@@代码类型管理","root":false,"delete":false}],"href":null,"target":"F","visible":true,"menuType":"category","icon":"fa-code-fork","sortNo":0,"appCode":null,"navigator":"代码管理","root":true,"delete":false}]' + ')');

                    new Vue({
                        el: "#indexPage",
                        data: {
                            menus: menus
                        },
                        mounted: function() {
                            console.log("mounted");
                            that._initPage();
                        },
                        methods: {
                            showSetting: function() {
                                that.showDialog({

                                })
                            }
                        }
                    });
                }
            });
        },
        _initPage: function() {
            //页面处理
            $('.exp').collapsible({
                defaultOpen: 'current',
                cookieName: 'navAct',
                cssOpen: 'active',
                cssClose: 'inactive',
                bind: 'click',
                speed: 200
            });
            $('.opened').collapsible({
                defaultOpen: 'opened,toggleOpened',
                cssOpen: 'inactive',
                cssClose: 'normal',
                speed: 200
            });
            $('.closed').collapsible({
                defaultOpen: '',
                cssOpen: 'inactive',
                cssClose: 'normal',
                speed: 200
            });
            $('.goTo').collapsible({
                defaultOpen: 'openedDrop',
                cookieName: 'smallNavAct',
                cssOpen: 'active',
                cssClose: 'inactive',
                speed: 100
            });

            //设置frame页面高度
            var bodyHeight = $(window).height() - 75;
            $("#mainFrame").height(bodyHeight);

            //初始化Dialog组件
            $("#edit-dialog").dialog({
                autoOpen: false,
                modal: true,
                buttons: {
                    Ok: function () {
                        $(this).dialog("close");
                    }
                }
            });
        },
        /**
         * 显示对话框
         * @param options
         */
        showDialog: function (options) {
            var that = this;

            var url = options.url ? options.url : '';
            var title = options.title;
            var opener = options.opener ? options.opener : null;
            var width = options.width ? options.width : 400;
            var height = options.height ? options.height : 300;
            var showBtn = options.showBtn ? options.showBtn : true;
            var refreshFinish = typeof(options.refreshFinish) != "undefined" ? options.refreshFinish : true;
            var closeFinish = typeof(options.closeFinish) != "undefined" ? options.closeFinish : true;
            var okFunc = options.okFunc ? options.okFunc : null;
            var cancelFunc = options.cancelFunc ? options.cancelFunc : null;
            var shown = options.shown ? options.shown : null;
            var showFooter = typeof(options.showFooter) != "undefined" ? options.showFooter : true;
            var onFinished = options.onFinished ? options.onFinished : null;
            var onBeforeHide = options.onBeforeHide ? options.onBeforeHide : null;

            var dialogOptions = {
                draggable: false,
                title: title,
                width: width,
                height: height,
                buttons: [
                    {
                        text: '确定',
                        icon: "ui-icon-ok",
                        click: function() {
                            if (okFunc) {
                                okFunc();
                                if (refreshFinish) {
                                    if (opener) {
                                        opener.location.reload();
                                    }
                                }
                                if (onFinished) {
                                    onFinished();
                                }
                                if (closeFinish) {
                                    that.hideDialog();
                                }
                            }
                        }
                    },
                    {
                        text: '取消',
                        icon: "ui-icon-heart",
                        click: function() {
                            if (cancelFunc) {
                                cancelFunc();
                            } else {
                                $(this).dialog("close");
                            }
                        }
                    }
                ]
            };
            if (!showBtn) {
                options.buttons = [];
            }

            $('#edit-dialog iframe').attr('src', url);
            $('#edit-dialog').dialog(dialogOptions);
            $('#edit-dialog').dialog('open');
        },
        /**
         * 关闭对话框
         */
        hideDialog: function () {
            $('#edit-dialog').dialog('close');
        },
        getDialogContent: function () {
            return $('#edit-dialog iframe').contents();
        }
    }
});