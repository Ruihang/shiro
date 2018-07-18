package service.impl;

import dao.PermissionDao;
import dao.PermissionDaoImpl;
import entity.Permission;
import service.PermissionService;

/**
 * @author w_huangruixian
 * @date 2018/7/16 15:49
 **/
public class PermissionServiceImpl implements PermissionService {

    private PermissionDao permissionDao = new PermissionDaoImpl();

    public Permission createPermission(Permission permission) {
        return permissionDao.createPermission(permission);
    }

    public void deletePermission(Long permissionId) {
        permissionDao.deletePermission(permissionId);
    }

}
