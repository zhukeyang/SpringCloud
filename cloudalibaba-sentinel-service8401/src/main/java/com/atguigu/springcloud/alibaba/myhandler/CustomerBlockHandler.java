package com.atguigu.springcloud.alibaba.myhandler;


import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;

public class CustomerBlockHandler {
    public static CommonResult handlerException(BlockException exception)
    {
        return new CommonResult(444,"按客户自定义限流OK,global 一号",new Payment(2020L,"serial003"));
    }
    public static CommonResult handlerException2(BlockException exception)
    {
        return new CommonResult(444,"按客户自定义限流OK,global  二号",new Payment(2020L,"serial003"));
    }
}
