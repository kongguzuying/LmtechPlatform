//http://xinglianconnect-dev.380star.com/crm/wxtoken/getAccessToken
define('constant', [], function () {
    return {
        basePath: BASE_URL,
        pager: {
            pageSize: 12
        },
        page: {
            url: {
                login: '/login.html'
            }
        },
        service: {
            url: {
                login: 'http://cd.wewineu.com:8001/auth/account/authenticate',
                checkToken: 'http://cd.wewineu.com:8001/auth/token/validateToken',

                /** 菜单相关接口 */
                getUserMenus: 'http://cd.wewineu.com:8001/infrast/menu/queryUserMenus',
                getAllMenus: 'http://cd.wewineu.com:8001/infrast/menu/getAllSortMenu',
                getMenu: 'http://cd.wewineu.com:8001/infrast/menu/getMenu',
                addMenu: 'http://cd.wewineu.com:8001/infrast/menu/addMenu',
                editMenu: 'http://cd.wewineu.com:8001/infrast/menu/editMenu',
                removeMenu: 'http://cd.wewineu.com:8001/infrast/menu/removeMenu',

                /** 角色相关接口 */
                addRole: 'http://cd.wewineu.com:8001/infrast/role/addRole',    //添加角色
                addRoleMenus: 'http://cd.wewineu.com:8001/infrast/role/addRoleMenus',    //添加角色菜单
                addRoleResources: 'http://cd.wewineu.com:8001/infrast/role/addRoleResources',    //添加角色资源关联
                addRoleUsers: 'http://cd.wewineu.com:8001/infrast/role/addRoleUsers',    //添加角色用户关联
                editRole: 'http://cd.wewineu.com:8001/infrast/role/editRole',    //编辑角色
                existRoleName: 'http://cd.wewineu.com:8001/infrast/role/existRoleName',    //是否已存在角色名称
                getRole: 'http://cd.wewineu.com:8001/infrast/role/getRole',    //获取角色数据
                getRoleMenus: 'http://cd.wewineu.com:8001/infrast/role/getRoleMenus',    //获取角色相关菜单
                getRoleOfPage: 'http://cd.wewineu.com:8001/infrast/role/getRoleOfPage',    //获取角色分页数据
                getRoleResourceOfPage: 'http://cd.wewineu.com:8001/infrast/role/getRoleResourceOfPage',    //获取角色关联资源分页数据
                getRoleResources: 'http://cd.wewineu.com:8001/infrast/role/getRoleResources',    //获取角色关联资源
                getRoleUnauthMenus: 'http://cd.wewineu.com:8001/infrast/role/getRoleUnauthMenus',    //获取角色相关菜单
                getRoleUnauthResourceOfPage: 'http://cd.wewineu.com:8001/infrast/role/getRoleUnauthResourceOfPage',    //获取角色未关联资源
                getRoleUnauthUserOfPage: 'http://cd.wewineu.com:8001/infrast/role/getRoleUnauthUserOfPage',    //获取角色未关联用户
                getRoleUserOfPage: 'http://cd.wewineu.com:8001/infrast/role/getRoleUserOfPage',    //获取角色关联用户分页数据
                getRoleUsers: 'http://cd.wewineu.com:8001/infrast/role/getRoleUsers',    //获取角色关联用户
                queryCurrentUserRole: 'http://cd.wewineu.com:8001/infrast/role/queryCurrentUserRole',    //获取当前用户角色列表
                queryUserRoleByUserId: 'http://cd.wewineu.com:8001/infrast/role/queryUserRoleByUserId',    //通过用户编号获取用户角色关联
                removeRole: 'http://cd.wewineu.com:8001/infrast/role/removeRole',    //删除角色
                removeRoleMenus: 'http://cd.wewineu.com:8001/infrast/role/removeRoleMenus',    //删除角色菜单
                removeRoleResources: 'http://cd.wewineu.com:8001/infrast/role/removeRoleResources',    //删除角色资源关联
                removeRoleUsers: 'http://cd.wewineu.com:8001/infrast/role/removeRoleUsers',    //删除角色用户关联
                setRoleMenus: 'http://cd.wewineu.com:8001/infrast/role/setRoleMenus',    //设置角色菜单
                setRoleResources: 'http://cd.wewineu.com:8001/infrast/role/setRoleResources',    //设置角色资源关联
                setRoleUsers: 'http://cd.wewineu.com:8001/infrast/role/setRoleUsers',    //设置角色用户关联

                /** 用户相关接口 */
                addGroupUser: 'http://cd.wewineu.com:8001/infrast/user/addGroupUser',    //添加用户分组关联
                addUser: 'http://cd.wewineu.com:8001/infrast/user/addUser',    //添加用户
                addUserRoles: 'http://cd.wewineu.com:8001/infrast/user/addUserRoles',    //添加用户关联角色
                changeGroup: 'http://cd.wewineu.com:8001/infrast/user/changeGroup',    //修改分组
                editUser: 'http://cd.wewineu.com:8001/infrast/user/editUser',    //编辑用户
                getUser: 'http://cd.wewineu.com:8001/infrast/user/getUser',    //获取用户
                getUserByGroupId: 'http://cd.wewineu.com:8001/infrast/user/getUserByGroupId',    //获取用户通过分组编号
                getUserOfPage: 'http://cd.wewineu.com:8001/infrast/user/getUserOfPage',    //获取用户分页数据
                getUserRoleOfPage: 'http://cd.wewineu.com:8001/infrast/user/getUserRoleOfPage',    //获取用户关联角色分页数据
                getUserUnauthRoleOfPage: 'http://cd.wewineu.com:8001/infrast/user/getUserUnauthRoleOfPage',    //获取用户未关联角色分页数据
                queryCurrentUserInfo: 'http://cd.wewineu.com:8001/infrast/user/queryCurrentUserInfo',    //查询当前用户信息
                queryUserByGroupId: 'http://cd.wewineu.com:8001/infrast/user/queryUserByGroupId',    //查询用户通过分组编号
                queryUserByKey: 'http://cd.wewineu.com:8001/infrast/user/queryUserByKey',    //查询用户通过关键字（如：用户名、登录名）
                queryUserInfoInGroup: 'http://cd.wewineu.com:8001/infrast/user/queryUserInfoInGroup',    //查询分组下的用户
                queryUserRoles: 'http://cd.wewineu.com:8001/infrast/user/queryUserRoles',    //查询用户关联角色
                removeUser: 'http://cd.wewineu.com:8001/infrast/user/removeUser',    //删除用户
                removeUserRoles: 'http://cd.wewineu.com:8001/infrast/user/removeUserRoles',    //删除用户关联角色
                setUserRoles: 'http://cd.wewineu.com:8001/infrast/user/setUserRoles'        //设置用户关联角色
            }
        }
    }
});