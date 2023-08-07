package com.pingSafe.Data;

import com.github.javafaker.Faker;
import com.pingSafe.Messages.CustomerResponse;
import com.pingSafe.POJO.Customer;
import org.testng.annotations.DataProvider;

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

}
