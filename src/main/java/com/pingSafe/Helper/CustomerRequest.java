package com.pingSafe.Helper;

import com.pingSafe.BaseSetup.Requests;
import com.pingSafe.POJO.Customer;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class CustomerRequest extends Requests {

    public Response createCustomer(Customer customer){
       return post("/api",customer);
    }
    public Response createCustomer(Map<String, String> headers, Customer customer){
        return post(headers,"/api",customer);
    }

    public Response getCustomerDetailsById(String id){
        Map<String,String> map = new HashMap<>();
        map.put("id",id);
        return get("/api",map);
    }

    public Response getCustomerDetailsById(Map<String, String> headers, String id){
        return get("/api",headers,Map.of("id",id));
    }
}
