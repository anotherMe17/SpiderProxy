package io.github.anotherme17.spiderproxy.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author lirenhao
 * date: 2017/12/12 上午11:49
 */
@Builder
@Data
@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "of")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class HttpResult<T> {

    private int code;
    private String msg;
    private T date;

    public HttpResult(int code, String msg, T date) {
        this.code = code;
        this.msg = msg;
        this.date = date;
    }
}
