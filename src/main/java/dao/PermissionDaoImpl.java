package dao;

import entity.Permission;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import util.JdbcTemplateUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public class PermissionDaoImpl implements PermissionDao {

    private JdbcTemplate jdbcTemplate = JdbcTemplateUtils.jdbcTemplate();

    @Override
    public Permission createPermission(Permission permission) {
        final String sql = "insert into sys_permissions(permission, description, available) values(?,?,?)";
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, permission.getPermission());
            preparedStatement.setString(2, permission.getDescription());
            preparedStatement.setBoolean(3, permission.getAvailable());
            return preparedStatement;
        }, generatedKeyHolder);
        permission.setId(generatedKeyHolder.getKey().longValue());
        return permission;

    }

    public void deletePermission(Long permissionId) {
        //首先把与permission关联的相关表的数据删掉
        String sql = "delete from sys_roles_permissions where permission_id=?";
        jdbcTemplate.update(sql, permissionId);

        sql = "delete from sys_permissions where id=?";
        jdbcTemplate.update(sql, permissionId);
    }

}
