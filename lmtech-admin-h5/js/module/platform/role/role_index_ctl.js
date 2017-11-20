define('role_index_ctl', ['jquery', 'jquery_treeTable', 'vue', 'constant', 'util'], function ($, tb, Vue, C, util) {
    return {
        init: function () {
            var that = this;
            util.checkToken();

            util.httpPost({
                url: C.service.url.getRoleOfPage,
                data: util.buildRequest(null),
                success: function (data) {
                    var vd = new Vue({
                        el: '#data',
                        data: {
                            pageData: data
                        },
                        methods: {

                        },
                        mounted: function () {
                            console.log('mounted');
                        }
                    });
                }
            });
        }
    }
});