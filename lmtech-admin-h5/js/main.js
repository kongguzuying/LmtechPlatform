/**
 * requirejs 配置
 */

var BASE_URL = '/';

require.config({
    baseUrl: ["./", "module"].join(""),
    paths: {
        //公共类库
        jquery: BASE_URL + 'js/plugins/jquery/jquery.min',
        ui_spinner: BASE_URL + 'js/plugins/spinner/ui.spinner',
        jquery_ui: BASE_URL + 'js/plugins/jquery/jquery-ui.min',
        jquery_mousewheel: BASE_URL + 'js/plugins/spinner/jquery.mousewheel',
        jquery_collapsible: BASE_URL + 'js/plugins/ui/jquery.collapsible.min',
        jquery_treeTable: BASE_URL + 'js/plugins/ui/jquery.treeTable',
        jquery_validate: BASE_URL + 'js/plugins/wizard/jquery.validate.min',
        excanvas: BASE_URL + 'js/plugins/charts/excanvas.min',
        constant: BASE_URL + 'js/constant',
        util: BASE_URL + 'js/plugins/util/util',
        array_util: BASE_URL + 'js/plugins/util/array-util',
        vue: BASE_URL + 'js/plugins/vue/vue',
        vue_comps: BASE_URL + 'js/plugins/vue/vue-comps',

        //平台控制器
        login_ctl: BASE_URL + 'js/module/platform/login_ctl',
        index_ctl: BASE_URL + 'js/module/platform/index_ctl',
        menu_index_ctl: BASE_URL + 'js/module/platform/menu/menu_index_ctl',
        menu_edit_ctl: BASE_URL + 'js/module/platform/menu/menu_edit_ctl',
        menu_selparent_ctl: BASE_URL + 'js/module/platform/menu/menu_selparent_ctl',
        role_index_ctl: BASE_URL + 'js/module/platform/role/role_index_ctl',
        role_edit_ctl: BASE_URL + 'js/module/platform/role/role_edit_ctl',
        role_user_ctl: BASE_URL + 'js/module/platform/role/role_user_ctl',
        role_menu_ctl: BASE_URL + 'js/module/platform/role/role_menu_ctl',
        user_index_ctl: BASE_URL + 'js/module/platform/user/user_index_ctl',
        user_edit_ctl: BASE_URL + 'js/module/platform/user/user_edit_ctl',
    },
    shim: {
        jquery: {
            exports: 'jquery'
        },
        ui_spinner: {
            deps: ['jquery'],
            ui_spinner: 'ui_spinner'
        },
        jquery_ui: {
            deps: ['jquery'],
            exports: 'jquery_ui'
        },
        jquery_mousewheel: {
            deps: ['jquery'],
            exports: 'jquery_mousewheel'
        },
        jquery_collapsible: {
            deps: ['jquery', 'jquery_ui'],
            exports: 'jquery_collapsible'
        },
        jquery_treeTable: {
            deps: ['jquery'],
            exports: 'jquery_treeTable'
        },
        jquery_validate: {
            deps: ['jquery'],
            exports: 'jquery_validate'
        },
        excanvas: {
            exports: 'excanvas'
        },
        constant: {
            exports: 'constant'
        },
        util: {
            exports: 'util'
        },
        array_util: {
            exports: 'array_util'
        },
        vue: {
            exports: 'vue'
        },
        vue_comps: {
            exports: 'vue_comps'
        },

        //平台控制器
        login_ctl: {
            exports: 'login_ctl'
        },
        index_ctl: {
            exports: 'index_ctl'
        },
        menu_index_ctl: {
            exports: 'menu_index_ctl'
        },
        menu_edit_ctl: {
            exports: 'menu_edit_ctl'
        },
        menu_selparent_ctl: {
            exports: 'menu_selparent_ctl'
        },
        role_index_ctl: {
            exports: 'role_index_ctl'
        },
        role_edit_ctl: {
            exports: 'role_edit_ctl'
        },
        role_user_ctl: {
            exports: 'role_user_ctl'
        },
        role_menu_ctl: {
            exports: 'role_menu_ctl'
        },
        user_index_ctl: {
            exports: 'user_index_ctl'
        },
        user_edit_ctl: {
            exports: 'user_edit_ctl'
        }

    }
});
require(['jquery', 'ui_spinner', 'jquery_mousewheel', 'jquery_ui', 'jquery_validate', 'excanvas', 'vue', 'vue_comps', 'constant', 'util'], function () {
    console.log('main');
});
