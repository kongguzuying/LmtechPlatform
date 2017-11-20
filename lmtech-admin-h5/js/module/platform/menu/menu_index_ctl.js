define('menu_index_ctl', ['jquery', 'jquery_treeTable', 'vue', 'constant', 'util'], function ($, tb, Vue, C, util) {
    return {
        init: function () {
            var that = this;
            util.checkToken();

            util.httpPost({
                url: C.service.url.getAllMenus,
                data: util.buildRequest(null),
                success: function (data) {
                    var v = new Vue({
                        el: '#indexPage',
                        data: {
                            menus: data
                        },
                        methods: {
                            selectTr: function (tr, id, name) {
                                if ($(tr).hasClass('selectedTr')) {
                                    $(tr).removeClass('selectedTr');
                                    $('#hidCurrentId').val('0');
                                } else {
                                    $('.treeTable tr').removeClass('selectedTr');
                                    $(tr).addClass('selectedTr');
                                    $('#hidCurrentId').val(id);
                                }
                            },
                            addMenu: function () {
                                var curId = $('#hidCurrentId').val();
                                var url = 'edit.html?id=&parentId=' + curId;
                                window.location.href = url;
                            },
                            editMenu: function (menu) {
                                var parentName = '';
                                for (var i = 0; i < v.$data.menus.length; i++) {
                                    var pmenu = v.$data.menus[i];
                                    if (pmenu.id == menu.parentId) {
                                        parentName = pmenu.name;
                                    }
                                }
                                var url = 'edit.html?id=' + menu.id + '&parentId=' + menu.parentId + '&parentName=' + escape(parentName);
                                window.location.href = url;
                            },
                            removeMenu: function (id) {
                                var data = util.buildRequest(id);
                                util.httpPost({
                                    url: C.service.url.removeMenu,
                                    data: data,
                                    success: function () {
                                        alert('删除菜单成功！');
                                        window.location.reload();
                                    }
                                });
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
                        }
                    });
                }
            });
        }
    }
});