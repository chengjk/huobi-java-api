package com.huobi.api.client.domain.resp;

import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/7/21 2:38 PM
 */
@Getter
@Setter
public class RespTick<T> {
   private long id;
   private long ts;
   private T data;
}
