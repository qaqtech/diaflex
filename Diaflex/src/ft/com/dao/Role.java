package ft.com.dao;

public class Role {
    private int roleIdn;
    private String role_nm;
    private String role_dsc;
    private String stt;
    
    public Role() {
        super();
    }

    public void setRoleIdn(int roleIdn) {
        this.roleIdn = roleIdn;
    }

    public int getRoleIdn() {
        return roleIdn;
    }

    public void setRole_nm(String role_nm) {
        this.role_nm = role_nm;
    }

    public String getRole_nm() {
        return role_nm;
    }

    public void setRole_dsc(String role_dsc) {
        this.role_dsc = role_dsc;
    }

    public String getRole_dsc() {
        return role_dsc;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getStt() {
        return stt;
    }
}
