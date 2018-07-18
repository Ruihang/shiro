package permission;

import com.alibaba.druid.util.StringUtils;
import org.apache.shiro.authz.Permission;

/**
 * @author w_huangruixian
 * @date 2018/7/13 10:35
 **/
public class BitPermission implements Permission {

    private String resourceIdentify;
    private int permissionBit;
    private String instanceId;

    public BitPermission(String permissionString) {
        String[] array = permissionString.split("\\+");
        if(array.length > 1) {
            resourceIdentify = array[1];
        }
        if(StringUtils.isEmpty(resourceIdentify)) {
            resourceIdentify = "*";
        }
        if(array.length > 2) {
            permissionBit = Integer.valueOf(array[2]);
        }
        if(array.length > 3) {
            instanceId = array[3];
        }
        if(StringUtils.isEmpty(instanceId)) {
            instanceId = "*";
        }
    }

    @Override
    public boolean implies(Permission p) {
        if(!(p instanceof BitPermission)) {
            return false;
        }
        BitPermission bitPermission = (BitPermission) p;
        if(!("*".equals(this.resourceIdentify) || this.resourceIdentify.equals(bitPermission.resourceIdentify))) {
            return false;
        }
        if (!(this.permissionBit == 0 || (this.permissionBit & bitPermission.permissionBit) != 0)) {
            return false;
        }

        if(!("*".equals(this.instanceId) || this.instanceId.equals(bitPermission.instanceId))) {
            return false;
        }
        return true;
    }
}
