package com.cafe.shop.util.response;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseMsg<T> {

    private MetaData meta;

    private T data;

    public void setData(T data) {
        this.data = data;
    }

}
