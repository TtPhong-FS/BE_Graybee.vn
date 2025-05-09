package vn.graybee.constants;

public class ConstantAuth {

    public static final String token = "token";

    public static final String name = "name";

    public static final String password = "password";

    public static final String username = "username";

    public static final String permissions = "permissions";

    public static final String permission_does_not_exists = "Không thể thực hiện vì quyền không tồn tại trong hệ thống";

    public static final String role_does_not_exists = "Không thể thực hiện vì vai trò không tồn tại trong hệ thống";

    public static final String users_in_use = "Không thể thực hiện vì đang có người dùng trong hệ thống";

    public static final String permission_name_exists = "Tên quyền đã tồn tại trong hệ thống";

    public static final String role_name_exists = "Tên vai trò đã tồn tại trong hệ thống";

    public static final String permission_relation_does_not_exists = "Quyền chưa được liên kết với vai trò. Hãy thử lại!";

    public static final String wrong_password = "Mật khẩu đăng nhập không đúng";

    public static final String wrong_username = "Tên đăng nhập không đúng";

    public static final String missing_authorization = "Khoá xác thực là bắt buộc";
    //    Response


    public static final String success_login = "Đăng nhập thành công";

    public static final String success_signup = "Tạo tài khoản thành công!";

    public static final String success_create_role = "Tạo vai trò thành công";

    public static final String success_create_permission = "Tạo quyền thành công";

    public static final String success_update_permission = "Cập nhật quyền thành công";

    public static final String success_update_role = "Cập nhật vai trò thành công";

    public static final String success_delete_role = "Xoá vai trò thành công";

    public static final String success_delete_permission = "Xoá quyền thành công";

    public static final String success_role_find_by_id = "Tìm vai trò thành công";

    public static final String success_permission_find_by_id = "Tìm quyền thành công";

    public static final String success_delete_permission_relation = "Quyền đã được loại bỏ khỏi vai trò";

    public static final String success_fetch_roles = "Danh sách vai trò đã được lấy thành công";

    public static final String success_fetch_permissions = "Danh sách quyền đã được lấy thành công";

    public static final String account_locked = "Tài khoản hiện đang bị khoá!. Vui lòng liên hệ với quản trị viên/quản lý để biết thêm thông tin";
    
    public static final String no_role_assigned = "Người dùng không có quyền truy cập hệ thống. Vui lòng liên hệ với quản trị viên/quản lý để biết thêm thông tin";

}
