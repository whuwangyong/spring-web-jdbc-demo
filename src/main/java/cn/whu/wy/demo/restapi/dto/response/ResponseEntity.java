package cn.whu.wy.demo.restapi.dto.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author WangYong
 * @Date 2022/05/05
 * @Time 16:33
 */
@Data
public class ResponseEntity<T> implements Serializable {
    private String code;
    private String msg;
    private T data;


    public ResponseEntity() {
    }

    public ResponseEntity(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseEntity(ResponseCode responseCode) {
        this.code = responseCode.code();
        this.msg = responseCode.msg();
    }

    public ResponseEntity(ResponseCode responseCode, T data) {
        this.code = responseCode.code();
        this.msg = responseCode.msg();
        this.data = data;
    }

}
