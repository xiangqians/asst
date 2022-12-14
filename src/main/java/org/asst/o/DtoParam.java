package org.asst.o;

/**
 * DTO Parameter
 *
 * @author xiangqian
 * @date 18:50 2022/06/11
 */
public interface DtoParam extends O {

    default <T extends PoParam> T convertToPoParam(Class<T> type) {
        throw new UnsupportedOperationException();
    }


    default <T extends BoParam> T convertToBoParam(Class<T> type) {
        throw new UnsupportedOperationException();
    }

    /**
     * 处理参数
     */
    default void post() {
    }

}
