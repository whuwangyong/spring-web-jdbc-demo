package cn.whu.wy.demo.restapi.dto.response;

/**
 * 错误码定义，可参考阿里巴巴开发手册
 *
 * @Author WangYong
 * @Date 2022/05/05
 * @Time 16:39
 */
public enum ResponseCode {

    SUCCESS("0", "成功"),
    FAILURE("1", "服务异常，请稍后再试"),

    USER_NOT_FOUND("100", "用户不存在");


    String code;
    String msg;

    ResponseCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String code() {
        return code;
    }

    public String msg() {
        return msg;
    }
}
