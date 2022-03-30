package crm.tools;

/**
 * @description:
 * @author: waxsq
 * @date:
 */
//常量
public enum Constant {
//    1是成功
    RETURN_OBJECT_CODE_SUCCESS("1"),
//    0是失败
    RETURN_OBJECT_CODE_FAIL("0"),
//    保存当前用户
    SESSION_USER("sessionUser");
    private final String code;

    Constant(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
