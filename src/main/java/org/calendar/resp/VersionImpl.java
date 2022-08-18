package org.calendar.resp;

import lombok.Getter;

/**
 * @author xiangqian
 * @date 19:53 2022/06/18
 */
@Getter
public enum VersionImpl implements Version {

    V2022_8("v2022.8"),
    ;

    private final String value;

    VersionImpl(String value) {
        this.value = value;
    }

}
