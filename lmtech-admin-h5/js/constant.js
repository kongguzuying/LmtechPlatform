define('constant', [], function () {
    return {
        basePath: BASE_URL,
        page: {
            url: {
                login: '/login.html'
            }
        },
        service: {
            url: {
                login: 'http://172.30.8.224:8001/auth/account/authenticate',
                checkToken: 'http://172.30.8.224:8001/auth/token/validateToken',

                /** 菜单相关接口 */
                getUserMenus: 'http://172.30.8.224:8001/infrast/menu/queryUserMenus',
                getAllMenus: 'http://172.30.8.224:8001/infrast/menu/getAllSortMenu',
                getMenu: 'http://172.30.8.224:8001/infrast/menu/getMenu',
                addMenu: 'http://172.30.8.224:8001/infrast/menu/addMenu',
                editMenu: 'http://172.30.8.224:8001/infrast/menu/editMenu',
                removeMenu: 'http://172.30.8.224:8001/infrast/menu/removeMenu',

                /** 角色相关接口 */
                addRole: 'http://172.30.8.224:8001/infrast/role/addRole',    //添加角色
                addRoleMenus: 'http://172.30.8.224:8001/infrast/role/addRoleMenus',    //添加角色菜单
                addRoleResources: 'http://172.30.8.224:8001/infrast/role/addRoleResources',    //添加角色资源关联
                addRoleUsers: 'http://172.30.8.224:8001/infrast/role/addRoleUsers',    //添加角色用户关联
                editRole: 'http://172.30.8.224:8001/infrast/role/editRole',    //编辑角色
                existRoleName: 'http://172.30.8.224:8001/infrast/role/existRoleName',    //是否已存在角色名称
                getRole: 'http://172.30.8.224:8001/infrast/role/getRole',    //获取角色数据
                getRoleMenus: 'http://172.30.8.224:8001/infrast/role/getRoleMenus',    //获取角色相关菜单
                getRoleOfPage: 'http://172.30.8.224:8001/infrast/role/getRoleOfPage',    //获取角色分页数据
                getRoleResourceOfPage: 'http://172.30.8.224:8001/infrast/role/getRoleResourceOfPage',    //获取角色关联资源分页数据
                getRoleResources: 'http://172.30.8.224:8001/infrast/role/getRoleResources',    //获取角色关联资源
                getRoleUnauthMenus: 'http://172.30.8.224:8001/infrast/role/getRoleUnauthMenus',    //获取角色相关菜单
                getRoleUnauthResourceOfPage: 'http://172.30.8.224:8001/infrast/role/getRoleUnauthResourceOfPage',    //获取角色未关联资源
                getRoleUnauthUserOfPage: 'http://172.30.8.224:8001/infrast/role/getRoleUnauthUserOfPage',    //获取角色未关联用户
                getRoleUserOfPage: 'http://172.30.8.224:8001/infrast/role/getRoleUserOfPage',    //获取角色关联用户分页数据
                getRoleUsers: 'http://172.30.8.224:8001/infrast/role/getRoleUsers',    //获取角色关联用户
                queryCurrentUserRole: 'http://172.30.8.224:8001/infrast/role/queryCurrentUserRole',    //获取当前用户角色列表
                queryUserRoleByUserId: 'http://172.30.8.224:8001/infrast/role/queryUserRoleByUserId',    //通过用户编号获取用户角色关联
                removeRole: 'http://172.30.8.224:8001/infrast/role/removeRole',    //删除角色
                removeRoleMenus: 'http://172.30.8.224:8001/infrast/role/removeRoleMenus',    //删除角色菜单
                removeRoleResources: 'http://172.30.8.224:8001/infrast/role/removeRoleResources',    //删除角色资源关联
                removeRoleUsers: 'http://172.30.8.224:8001/infrast/role/removeRoleUsers',    //删除角色用户关联
                setRoleMenus: 'http://172.30.8.224:8001/infrast/role/setRoleMenus',    //设置角色菜单
                setRoleResources: 'http://172.30.8.224:8001/infrast/role/setRoleResources',    //设置角色资源关联
                setRoleUsers: 'http://172.30.8.224:8001/infrast/role/setRoleUsers'    //设置角色用户关联
            }
        }
    }
});