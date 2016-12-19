package sing.util.permission;

public class PermissionInfo {
    public String name;
    public String shortName;

    public PermissionInfo(String name) {
        this.name = name;
        this.shortName = name.substring(name.lastIndexOf(".") + 1);
    }
}