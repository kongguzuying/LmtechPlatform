define('vue_comps', ['jquery', 'vue', 'constant', 'util'], function ($, Vue, C, util) {
    return {
        initPagerComp: function () {
            Vue.component('lm-pager', {
                template: '<div class="fg-toolbar ui-toolbar ui-widget-header ui-corner-bl ui-corner-br ui-helper-clearfix" v-if="pager.visible">' +
                '            <div class="dataTables_paginate fg-buttonset ui-buttonset fg-buttonset-multi ui-buttonset-multi paging_full_numbers">' +
                '                <span class="toFirst ui-corner-tl ui-corner-bl fg-button ui-button" v-on:click="page_index_click(pager.firstPage)">首页</span>' +
                '                <span class="previous fg-button ui-button" v-on:click="page_index_click(pager.prevPage)">上一页</span><span>' +
                '                <span class="fg-button ui-button" v-cloak v-for="item in pager.pageItems" v-on:click="page_click(item)" v-bind:class="{ \'ui-state-active\' : item.isActive}">{{item.pageIndex}}</span>' +
                '                </span><span class="next fg-button ui-button" v-on:click="page_index_click(pager.nextPage)">下一页</span>' +
                '                <span class="last ui-corner-tr ui-corner-br fg-button ui-button" v-on:click="page_index_click(pager.lastPage)">尾页</span></div>' +
                '        </div>',
                props: ['pager'],
                methods: {
                    page_click: function (pageItem) {
                        var that = this;
                        var pg = that.pager;
                        that._setCurrentPage(pg, pageItem.pageIndex);
                    },
                    page_index_click: function (pageIndex) {
                        var that = this;
                        var pg = that.pager;
                        that._setCurrentPage(pg, pageIndex);
                    },
                    _setCurrentPage: function (pg, pageIndex) {
                        var that = this;
                        if (pg.currentPageIndex != pageIndex) {
                            pg.currentPageIndex = pageIndex;
                            that._setPageActive(pg, pageIndex);
                            pg.pageIndex = pageIndex;

                            if (pageIndex > 1) {
                                pg.firstPage = 1;
                                pg.prevPage = pageIndex - 1;
                            } else {
                                pg.firstPage = 1;
                                pg.prevPage = 1;
                                pg.currentPageIndex = 1;
                                pg.pageIndex = 1;
                            }
                            if (pageIndex < pg.pageCount) {
                                pg.lastPage = pg.pageCount;
                                pg.nextPage = pageIndex + 1;
                            } else {
                                pg.lastPage = pg.pageCount;
                                pg.nextPage = pg.pageCount;
                                pg.currentPageIndex = pg.pageCount;
                                pg.pageIndex = pg.pageCount;
                            }
                            this.$emit('page_click');
                        }
                    },
                    _setPageActive: function (pg, pageIndex) {
                        for (var i = 0; i < pg.pageItems.length; i++) {
                            var pageItem = pg.pageItems[i];
                            if (pageItem.pageIndex == pageIndex) {
                                pageItem.isActive = true;
                            } else {
                                pageItem.isActive = false;
                            }
                        }
                    }
                }
            });
        }
    }
});