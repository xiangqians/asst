package org.asst.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author xiangqian
 * @date 17:07:15 2022/03/26
 */
@Getter
@ApiModel("响应信息")
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("版本")
    private String version;

    @ApiModelProperty("状态码")
    private int statusCode;

    @ApiModelProperty("消息")
    private String message;

    @ApiModelProperty("报文体")
    private T body;

    private Response(String version, int statusCode, String message, T body) {
        this.version = version;
        this.statusCode = statusCode;
        this.message = message;
        this.body = body;
    }

    public static <T> Builder<T> builder() {
        return new Builder();
    }

    public static class Builder<T> {

        private Version version;
        private StatusCode statusCode;
        private String message;
        private T body;

        private Builder() {
        }

        public Builder<T> version(Version version) {
            this.version = version;
            return this;
        }

        public Builder<T> statusCode(StatusCode statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> body(T body) {
            this.body = body;
            return this;
        }

        public Response<T> build() {
            return new Response(Optional.ofNullable(version).map(Version::getValue).orElse(VersionImpl.V2022_8.getValue()),
                    Optional.ofNullable(statusCode).map(StatusCode::getValue).orElse(-1),
                    Optional.ofNullable(message).orElse(Optional.ofNullable(statusCode).map(StatusCode::getReasonPhrase).orElse(null)),
                    body);
        }
    }

}
