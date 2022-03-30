package crm.tools;

/**
 * @description:
 * @author: waxsq
 * @date:
 */
public class ReturnObject {
//    0是失败，1是成功
    private String code;

    private String messge;
//返回其他类型
    private Object reDate;

    public ReturnObject() {
    }

    public ReturnObject(String code, String messge) {
        this.code = code;
        this.messge = messge;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessge() {
        return messge;
    }

    public void setMessge(String messge) {
        this.messge = messge;
    }

    public Object getReDate() {
        return reDate;
    }

    public void setReDate(Object reDate) {
        this.reDate = reDate;
    }
}
