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
                    var menus = eval('(' + '[{"dataVersion":2,"id":"897733078791749632","name":"系统管理","parentId":"0","childrens":[{"dataVersion":3,"id":"897733189508792320","name":"菜单管理","parentId":"897733078791749632","childrens":[],"href":"page/platform/menu/index.html","target":"F","visible":true,"menuType":"category","icon":"fa-dot-circle-o","sortNo":0,"appCode":null,"navigator":"系统管理@@菜单管理","root":false,"delete":false},{"createDate":"2017-08-16 16:14:29","createUser":null,"createUserName":null,"updateDate":"2017-10-17 15:34:18","updateUserName":null,"updateUser":null,"dataVersion":2,"id":"897733288293040128","name":"用户管理","parentId":"897733078791749632","childrens":[],"href":"page/platform/user/index.html","target":"F","visible":true,"menuType":"category","icon":"fa-dot-circle-o","sortNo":10,"appCode":null,"navigator":"系统管理@@用户管理","root":false,"delete":false},{"createDate":"2017-08-16 16:14:44","createUser":null,"createUserName":null,"updateDate":"2017-10-17 15:34:25","updateUserName":null,"updateUser":null,"dataVersion":2,"id":"897733352507834368","name":"角色管理","parentId":"897733078791749632","childrens":[],"href":"page/platform/role/index.html","target":"F","visible":true,"menuType":"category","icon":"fa-dot-circle-o","sortNo":20,"appCode":null,"navigator":"系统管理@@角色管理","root":false,"delete":false}],"href":null,"target":"F","visible":true,"menuType":"category","icon":"fa-laptop","sortNo":0,"appCode":null,"navigator":"系统管理","root":true,"delete":false},{"createDate":"2017-09-26 16:46:29","createUser":null,"createUserName":null,"updateDate":"2017-10-18 09:30:38","updateUserName":null,"updateUser":null,"dataVersion":5,"id":"912599244534185984","name":"营销推广","parentId":"0","childrens":[{"createDate":"2017-09-28 14:37:51","createUser":null,"createUserName":null,"updateDate":"2017-10-18 09:55:02","updateUserName":null,"updateUser":null,"dataVersion":3,"id":"913291649029767168","name":"尊享商场广告","parentId":"912599244534185984","childrens":[],"href":null,"target":"F","visible":true,"menuType":"item","icon":"fa-dot-circle-o","sortNo":10,"appCode":null,"navigator":"营销推广@@尊享商场广告","root":false,"delete":false},{"createDate":"2017-10-18 09:54:56","createUser":null,"createUserName":null,"updateDate":"2017-10-18 09:57:42","updateUserName":null,"updateUser":null,"dataVersion":2,"id":"920468207595159552","name":"积分规则管理","parentId":"912599244534185984","childrens":[],"href":"platform/integralRule/list.do","target":"F","visible":true,"menuType":"category","icon":"fa-dot-circle-o","sortNo":20,"appCode":null,"navigator":"营销推广@@积分规则管理","root":false,"delete":false}],"href":null,"target":"F","visible":true,"menuType":"category","icon":"fa-book","sortNo":0,"appCode":null,"navigator":"营销推广","root":true,"delete":false},{"createDate":"2017-09-28 19:18:27","createUser":null,"createUserName":null,"updateDate":"2017-10-17 15:35:22","updateUserName":null,"updateUser":null,"dataVersion":2,"id":"913362261781774336","name":"代码管理","parentId":"0","childrens":[{"createDate":"2017-09-28 19:19:03","createUser":null,"createUserName":null,"updateDate":"2017-10-17 15:35:26","updateUserName":null,"updateUser":null,"dataVersion":2,"id":"913362412697026560","name":"代码类型管理","parentId":"913362261781774336","childrens":[],"href":"platform/code/list.do","target":"F","visible":true,"menuType":"category","icon":"fa-dot-circle-o","sortNo":10,"appCode":null,"navigator":"代码管理@@代码类型管理","root":false,"delete":false}],"href":null,"target":"F","visible":true,"menuType":"category","icon":"fa-code-fork","sortNo":0,"appCode":null,"navigator":"代码管理","root":true,"delete":false},{"createDate":"2017-10-17 13:40:36","createUser":null,"createUserName":null,"updateDate":"2017-10-17 15:35:48","updateUserName":null,"updateUser":null,"dataVersion":2,"id":"920162609133518848","name":"充值记录管理","parentId":"0","childrens":[{"createDate":"2017-10-17 13:41:36","createUser":null,"createUserName":null,"updateDate":"2017-10-17 15:35:54","updateUserName":null,"updateUser":null,"dataVersion":2,"id":"920162859059511296","name":"充值记录管理","parentId":"920162609133518848","childrens":[],"href":"platform/rechargePayRecord/list.do","target":"F","visible":true,"menuType":"category","icon":"fa-dot-circle-o","sortNo":10,"appCode":null,"navigator":"充值记录管理@@充值记录管理","root":false,"delete":false}],"href":null,"target":"F","visible":true,"menuType":"category","icon":"fa-puzzle-piece","sortNo":0,"appCode":null,"navigator":"充值记录管理","root":true,"delete":false},{"createDate":"2017-08-28 18:43:26","createUser":null,"createUserName":null,"updateDate":"2017-10-17 15:36:14","updateUserName":null,"updateUser":null,"dataVersion":2,"id":"902119424507510784","name":"礼品管理","parentId":"0","childrens":[{"createDate":"2017-08-28 18:48:03","createUser":null,"createUserName":null,"updateDate":"2017-10-17 15:36:19","updateUserName":null,"updateUser":null,"dataVersion":3,"id":"902120590159118336","name":"礼品分类管理","parentId":"902119424507510784","childrens":[],"href":"platform/gift/list.do","target":"F","visible":true,"menuType":"category","icon":"fa-dot-circle-o","sortNo":10,"appCode":null,"navigator":"礼品管理@@礼品分类管理","root":false,"delete":false}],"href":null,"target":"F","visible":true,"menuType":"category","icon":"fa-cogs","sortNo":1,"appCode":null,"navigator":"礼品管理","root":true,"delete":false},{"createDate":"2017-09-01 23:26:12","createUser":null,"createUserName":null,"updateDate":"2017-10-17 15:36:43","updateUserName":null,"updateUser":null,"dataVersion":3,"id":"903640140130287616","name":"卡片管理","parentId":"0","childrens":[{"createDate":"2017-09-01 23:27:02","createUser":null,"createUserName":null,"updateDate":"2017-10-17 15:36:50","updateUserName":null,"updateUser":null,"dataVersion":3,"id":"903640347429568512","name":"卡分类管理","parentId":"903640140130287616","childrens":[],"href":"platform/card/list.do","target":"F","visible":true,"menuType":"category","icon":"fa-dot-circle-o","sortNo":10,"appCode":null,"navigator":"卡片管理@@卡分类管理","root":false,"delete":false},{"createDate":"2017-09-27 17:45:38","createUser":null,"createUserName":null,"updateDate":"2017-10-17 15:37:22","updateUserName":null,"updateUser":null,"dataVersion":4,"id":"912976515619094528","name":"礼品会员卡管理","parentId":"903640140130287616","childrens":[],"href":"platform/giftMemberCard/list.do","target":"F","visible":true,"menuType":"category","icon":"fa-dot-circle-o","sortNo":20,"appCode":null,"navigator":"卡片管理@@礼品会员卡管理","root":false,"delete":false},{"createDate":"2017-09-15 21:41:53","createUser":null,"createUserName":null,"updateDate":"2017-10-17 15:37:29","updateUserName":null,"updateUser":null,"dataVersion":3,"id":"908687315973439488","name":"卡激活管理","parentId":"903640140130287616","childrens":[],"href":"platform/cardActiveRecord/list.do","target":"F","visible":true,"menuType":"category","icon":"fa-dot-circle-o","sortNo":30,"appCode":null,"navigator":"卡片管理@@卡激活管理","root":false,"delete":false},{"createDate":"2017-10-16 17:27:49","createUser":null,"createUserName":null,"updateDate":"2017-10-17 15:37:34","updateUserName":null,"updateUser":null,"dataVersion":6,"id":"919857403443281920","name":"卡片购买管理","parentId":"903640140130287616","childrens":[],"href":"platform/cardPayRecord/list.do","target":"F","visible":true,"menuType":"category","icon":"fa-dot-circle-o","sortNo":40,"appCode":null,"navigator":"卡片管理@@卡片购买管理","root":false,"delete":false},{"createDate":"2017-10-17 11:02:09","createUser":null,"createUserName":null,"updateDate":"2017-10-17 15:37:40","updateUserName":null,"updateUser":null,"dataVersion":3,"id":"920122732614516736","name":"卡片赠送管理","parentId":"903640140130287616","childrens":[],"href":"platform/cardPresentRecord/list.do","target":"F","visible":true,"menuType":"category","icon":"fa-dot-circle-o","sortNo":50,"appCode":null,"navigator":"卡片管理@@卡片赠送管理","root":false,"delete":false}],"href":null,"target":"F","visible":true,"menuType":"category","icon":"fa-tasks","sortNo":1,"appCode":null,"navigator":"卡片管理","root":true,"delete":false},{"createDate":"2017-09-14 19:12:24","createUser":null,"createUserName":null,"updateDate":"2017-10-18 09:34:51","updateUserName":null,"updateUser":null,"dataVersion":5,"id":"908287311857319936","name":"积分管理","parentId":"0","childrens":[{"createDate":"2017-09-06 23:41:33","createUser":null,"createUserName":null,"updateDate":"2017-10-18 09:36:46","updateUserName":null,"updateUser":null,"dataVersion":5,"id":"905455939363012608","name":"积分奖品","parentId":"908287311857319936","childrens":[],"href":"platform/lottery/list.do","target":"F","visible":true,"menuType":"category","icon":"fa-dot-circle-o","sortNo":10,"appCode":null,"navigator":"积分管理@@积分奖品","root":false,"delete":false},{"createDate":"2017-09-14 19:12:42","createUser":null,"createUserName":null,"updateDate":"2017-10-18 09:33:49","updateUserName":null,"updateUser":null,"dataVersion":4,"id":"908287384011931648","name":"规则配置","parentId":"908287311857319936","childrens":[],"href":"platform/integralSet/list.do","target":"F","visible":true,"menuType":"category","icon":"fa-dot-circle-o","sortNo":10,"appCode":null,"navigator":"积分管理@@规则配置","root":false,"delete":false}],"href":"","target":"F","visible":true,"menuType":"category","icon":"fa-folder","sortNo":1,"appCode":null,"navigator":"积分管理","root":true,"delete":false},{"createDate":"2017-09-15 21:48:59","createUser":null,"createUserName":null,"updateDate":"2017-10-17 15:38:07","updateUserName":null,"updateUser":null,"dataVersion":2,"id":"908689104512745472","name":"会员注册管理","parentId":"0","childrens":[{"createDate":"2017-09-15 21:49:25","createUser":null,"createUserName":null,"updateDate":"2017-10-17 15:38:13","updateUserName":null,"updateUser":null,"dataVersion":2,"id":"908689210548944896","name":"会员注册管理","parentId":"908689104512745472","childrens":[],"href":"platform/memberRegister/list.do","target":"F","visible":true,"menuType":"category","icon":"fa-dot-circle-o","sortNo":10,"appCode":null,"navigator":"会员注册管理@@会员注册管理","root":false,"delete":false}],"href":null,"target":"F","visible":true,"menuType":"category","icon":"fa-wrench","sortNo":10,"appCode":null,"navigator":"会员注册管理","root":true,"delete":false}]' + ')');

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
            var bodyHeight = $(window).height() - 134;
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