package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;


    @Value("${server.port}")
    private String serverPort;

//    服务发现，获取该服务的信息
    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment)
    {
        int result = paymentService.create(payment);
        log.info("******插入结果:"+result);
        if(result>0)
        {
            return new CommonResult(200,"插入数据库成功,server.port"+serverPort,result);
        }
        else
        {
            return new CommonResult(444,"插入数据库失败",null);
        }
    }
    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id)
    {
        Payment payment = paymentService.getPaymentById(id);
        log.info("******查询结果:"+payment);
        if(payment!=null)
        {
            return new CommonResult(200,"查询成功,server.port:"+serverPort,payment);
        }
        else
        {
            return new CommonResult(444,"查询失败",null);
        }
    }
    @GetMapping(value = "/payment/discovery")
    public  Object discovery()
    {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("*******service"+service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }
        return discoveryClient;
    }
    @GetMapping("/payment/zipkin")
    public String paymentZipkin()
    {
        return "hi,PaymentZipKin server fall back,😀";
    }
}
