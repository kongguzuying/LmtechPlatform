/**
 *
 *   jQuery Context Menu Plugin
 *
 *  Version 1.01
 *
 *  Cory S.N. LaViska
 *  A Beautiful Site (http://abeautifulsite.net/)
 *
 *  More info: http://abeautifulsite.net/2008/09/jquery-context-menu-plugin/
 *
 *  Terms of Use
 *  This plugin is dual-licensed under the GNU General Public License
 *  and the MIT License and is copyright A Beautiful Site, LLC.
 */

(function ($) {
    //全局变量
    var $shadow;
    var defaults = {
        menuId: null,
        onContextMenuItemSelected: function (menuItemId, $triggerElement, $target) {
        },
        onContextMenuShow: function ($triggerElement, $target) {
            return true;
        },
        showShadow: false,
        fadeInSpeed: 150,
        fadeOutSpeed: 75,
        showRangeIsWindow: false
    }
    $.extend($.fn, {
        contextMenu: function (o) {
            // Defaults
            if (o.menuId == undefined) return false;//如果没有menuId则退出
            if (o.fadeInSpeed == undefined) o.fadeInSpeed = defaults.fadeInSpeed;
            if (o.fadeOutSpeed == undefined) o.fadeOutSpeed = defaults.fadeOutSpeed;
            if (o.showShadow == undefined) o.showShadow = defaults.showShadow;
            // 0 needs to be -1 for expected results (no fade)
            if (o.fadeInSpeed == 0) o.fadeInSpeed = -1;
            if (o.fadeOutSpeed == 0) o.fadeOutSpeed = -1;
            // Loop each context menu
            var $menu = $('#' + o.menuId);
            //把menu移动到body下面，避免计算位置的时候出现问题
            if ($menu.data('isMovedToBody') != true) {//只移动一次
                $menu.appendTo('body').data('isMovedToBody', true);
            }
            if (!$shadow) {
                $shadow = $('<div></div>').css({
                    backgroundColor: '#000',
                    position: 'absolute',
                    opacity: 0.4
                }).appendTo('body').hide();
            }
            $(this).each(function () {
                var $triggerElement = $(this);
                $triggerElement.data('contextMenu', {
                    $menu: $menu,
                    isEnabled: true,
                    disabledMenuItemIdList: [],
                    hideMenu: hideMenu
                })
                // Add contextMenu class
                $menu.addClass('contextMenu');
                $menu.unbind('contextmenu').bind('contextmenu', function (e) {return false;});
                $triggerElement.unbind('contextmenu').bind('contextmenu', function (e) {
                    var $currentTriggerElement = $(this);
                    var contextMenu = $currentTriggerElement.data('contextMenu');
                    //检查菜单是否被屏蔽
                    if ($currentTriggerElement.data('contextMenu').isEnabled === false) return false;
                    //如果有定义onContextMenuShow,在显示前调用
                    if (typeof o.onContextMenuShow == 'function') {
                        if (o.onContextMenuShow($currentTriggerElement, $(e.target)) === false) {
                            hideMenu();
                            return false;
                        }
                    }
                    //显示右键菜单
                    showMenu(e);
                    //绑定菜单项
                    $menu.find('li').removeClass('disabled');
                    var disabledMenuItemIdList = contextMenu.disabledMenuItemIdList;
                    var queryStr = '';
                    if (disabledMenuItemIdList.length > 0) {
                        var strDisabledMenuItemIdList = '';
                        for (var index in disabledMenuItemIdList) {
                            var disabledMenuItemId = disabledMenuItemIdList[index];
                            if (index == 0) {
                                strDisabledMenuItemIdList += '#' + disabledMenuItemId;
                            } else {
                                strDisabledMenuItemIdList += ',#' + disabledMenuItemId;
                            }
                        }

                        queryStr = 'li:not(' + strDisabledMenuItemIdList + ')';
                        $menu.find(strDisabledMenuItemIdList).addClass('disabled');
                    } else {
                        queryStr = 'li';
                    }
                    $menu.find('li').find('a').unbind('click');
                    $menu.find(queryStr).find('a').bind('click', $currentTriggerElement, function (event) {
                        // Callback
                        var callback = o.onContextMenuItemSelected;
                        if (typeof callback == 'function') {
                            callback($(this).parent().attr('id'), event.data, $(event.target));
                        }
                        hideMenu();
                        return false;
                    });
                    $(document).unbind('mousedown').bind('mousedown', function (event) {
                        if ($(event.target).parents('#' + o.menuId).html() == null) {
                            hideMenu();
                        }
                    });
                    //阻止默认右键菜单
                    return false;
                })
                // Disable text selection
                if (/firefox/.test(navigator.userAgent.toLowerCase())) {
                    $menu.each(function () {
                        $(this).css({'MozUserSelect': 'none'});
                    });
                } else if (/msie/.test(navigator.userAgent.toLowerCase())) {
                    $menu.each(function () {
                        $(this).bind('selectstart.disableTextSelect', function () {
                            return false;
                        });
                    });
                } else {
                    $menu.each(function () {
                        $(this).bind('mousedown.disableTextSelect', function () {
                            return false;
                        });
                    });
                }
            });

            function showMenu(event) {
                var left = event.pageX;
                var top = event.pageY;
                if (o.showRangeIsWindow) {
                    var winSize = {height: $(window).height(), width: $(window).width()};
                    var menuSize = {height: $menu.height(), width: $menu.width()};
                    if (left + menuSize.width > winSize.width) {
                        //向右移动
                        var tempLeft = left - menuSize.width;
                        left = tempLeft < 0 ? (winSize.width - menuSize.width) / 2 : tempLeft;
                    }
                    if (top + menuSize.height > winSize.height) {
                        var tempTop = top - menuSize.height;
                        top = tempTop < 0 ? (winSize.height - menuSize.height) / 2 : tempTop;
                    }
                }
                //显示菜单
                $menu.css({
                    position: 'absolute',
                    'left': left,
                    'top': top
                }).fadeIn(o.fadeInSpeed);
                //显示阴影
                if (o.showShadow) {
                    $shadow.css('zIndex', $menu.css('zIndex') - 1);
                    $shadow.css({
                        width: $menu.outerWidth(),
                        height: $menu.outerHeight(),
                        left: left + 2,
                        top: top + 2
                    }).fadeIn(o.fadeInSpeed);
                }

            }

            function hideMenu() {
                $menu.fadeOut(o.fadeOutSpeed);
                if (o.showShadow) {
                    $shadow.fadeOut(o.fadeOutSpeed);
                }
            }

            return $(this);
        },

        hideMenu: function (o) {
            $(this).each(function () {
                var contextMenu = $(this).data('contextMenu');
                var $menu = contextMenu.$menu;


            })
            return ( $(this) );
        },

        /**
         * 参数为id数组，如无参数则disable全部
         */
        disableContextMenuItems: function (o) {
            $(this).each(function () {
                var contextMenu = $(this).data('contextMenu');
                var $menu = contextMenu.$menu;
                if (o == undefined) {
                    var list = [];
                    $menu.find('li').each(function () {
                        var menuItemId = $(this).attr('id');
                        list.push(menuItemId);
                    })
                    contextMenu.disabledMenuItemIdList = list
                } else {
                    contextMenu.disabledMenuItemIdList = o
                }
            })
            return ( $(this) );
        },

        // Enable context menu items on the fly
        enableContextMenuItems: function (o) {
            $(this).each(function () {
                var contextMenu = $(this).data('contextMenu');
                var $menu = contextMenu.$menu;
                if (o == undefined) {
                    contextMenu.disabledMenuItemIdList = []
                } else {
                    contextMenu.disabledMenuItemIdList = $.grep(contextMenu.disabledMenuItemIdList, function (value, index) {
                        if ($.inArray(value, o) != -1) {
                            return false;
                        } else {
                            return true
                        }

                    })
                }
            })
            return ( $(this) );
        },

        // Disable context menu(s)
        disableContextMenu: function () {
            $(this).each(function () {
                var contextMenu = $(this).data('contextMenu');
                contextMenu.isEnabled = false;
            });
            return ( $(this) );
        },

        // Enable context menu(s)
        enableContextMenu: function () {
            $(this).each(function () {
                var contextMenu = $(this).data('contextMenu');
                contextMenu.isEnabled = true;
            });
            return ( $(this) );
        },

        // Destroy context menu(s)
        destroyContextMenu: function () {
            $(this).each(function () {
                $(this).removeData('contextMenu');
            });
            return ( $(this) );
        }

    });
})(jQuery);
