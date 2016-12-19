package sing.util.permission.callback;

import java.util.List;

import sing.util.permission.PermissionInfo;

public interface PermissionOriginResultCallBack {

    /**
     * 返回所有结果的列表list,各个list有可能为空
     *
     * 在进行申请方法调用后,此方法一定会被调用返回此次请求后的权限申请的情况
     *
     * @param acceptList   通过的
     * @param rationalList 允许提醒
     * @param deniedList   拒绝的
     */
    void onResult(List<PermissionInfo> acceptList, List<PermissionInfo> rationalList, List<PermissionInfo> deniedList);
}