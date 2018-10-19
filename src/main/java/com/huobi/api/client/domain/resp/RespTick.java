package com.huobi.api.client.domain.resp;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * created by jacky. 2018/7/21 2:38 PM
 */
@Getter
@Setter
public class RespTick<T> {
   private long id;
   private long ts;
   private List<T> data;
}
