define('menu_selparent_ctl', ['jquery', 'jquery_treeTable', 'vue', 'constant', 'util'], function ($, tb, Vue, C, util) {
    return {
        init: function () {
            var that = this;
            util.checkToken();

            var parentId = util.getParam('parentId');
            var parentName = util.getParam('parentName');
            util.httpPost({
                url: C.service.url.getAllMenus,
                data: util.buildRequest(null),
                success: function (data) {
                    var v = new Vue({
                        el: '#page',
                        data: {
                            menus: data,
                            parentId: parentId,
                            parentName: parentName
                        },
                        methods: {
                            selParent: function (menu) {
                                v.$data.parentId = menu.id;
                                v.$data.parentName = menu.name;
                                $('input[tag="' + menu.id + '"').attr('checked', 'checked');
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

                            var parentId = util.getParam('parentId');
                            $('input[tag="' + parentId + '"').attr('checked', 'checked');
                        }
                    });
                }
            });
        }
    }
});