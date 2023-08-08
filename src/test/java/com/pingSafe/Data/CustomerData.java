package com.pingSafe.Data;

import com.github.javafaker.Faker;
import com.pingSafe.BaseSetup.Requests;
import com.pingSafe.DataBase.DBConnector;
import com.pingSafe.Messages.CustomerResponse;
import com.pingSafe.POJO.Customer;
import org.testng.annotations.DataProvider;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CustomerData {

    @DataProvider(name = "createCustomerData")
    public Object[][] createCustomerData(){

        Faker faker = new Faker();
       Customer validData = Customer.builder().id(Integer.toString(new Random().nextInt(9999)))
                .name(faker.name().firstName()).phoneNumber(faker.numerify("##########")).build();

        faker = new Faker();
        Customer inValidMobile = Customer.builder().id(Integer.toString(new Random().nextInt(9999)))
                .name(faker.name().firstName()).phoneNumber(faker.numerify("########")).build();
       return new Object[][]{{validData, CustomerResponse.post_success},
               {validData,CustomerResponse.post_sameDataError},
               {inValidMobile,CustomerResponse.post_mobileNumCountError}
       };

    }

    @DataProvider(name = "getCustomerData")
    public Object[][] getCustomerData(){

        var idList = DBConnector.getInstance().getListOfIds();
        int size = idList.size();

        Map<String, String> botHeader = new HashMap<>();
        botHeader.put("x-session-token", "authorized-user");
        botHeader.put("user-agent", "bot-application/postscript");

        Map<String, String> unAuth = new HashMap<>();
        unAuth.put("x-session-token", "Unauthorized-user");
        unAuth.put("user-agent", "bot-application/postscript");

        Map<String, String> blankSession = new HashMap<>();
        blankSession.put("x-session-token", "");
        blankSession.put("user-agent", "application/postscript");


        return new Object[][]{
                {idList.get(new Random().nextInt(size)), Requests.validHeaders(),CustomerResponse.get_success},
                {"", Requests.validHeaders(),CustomerResponse.get_invalid},
                {idList.get(new Random().nextInt(size)), Collections.emptyMap(),CustomerResponse.get_forbidden},
                {idList.get(new Random().nextInt(size)), botHeader,CustomerResponse.get_BOT},
                {idList.get(new Random().nextInt(size)), unAuth,CustomerResponse.get_forbidden}
        };
    }

}
