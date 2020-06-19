package com.bbu.shenma.util;

public enum ResultCode {
     SUCCESS(20000),ERROR(40000);

     private Integer value;

     private ResultCode(Integer value){
         this.value = value;
     }

     public Integer getValue() {
        return value;
    }

     public void setValue(Integer value) {
        this.value = value;
    }
}
