package com.huobi.api.client.domain.event;

import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/7/24 8:43 PM
 */
@Getter
@Setter
public class DepthEventResp {
   private String id;
   private String rep;
   private String status;
   private  Long ts;
   private Object data; //todo
}
