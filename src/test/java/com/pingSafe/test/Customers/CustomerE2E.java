package com.pingSafe.test.Customers;

import com.github.dzieciou.testing.curl.CurlLoggingRestAssuredConfigFactory;
import com.pingSafe.BaseSetup.TestSetup;
import com.pingSafe.Data.CustomerData;
import com.pingSafe.DataBase.DBConnector;
import com.pingSafe.Messages.CustomerResponse;
import com.pingSafe.POJO.Customer;
import io.restassured.config.RestAssuredConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Objects;

public class CustomerE2E extends TestSetup {

    //RestAssuredConfig config = CurlLoggingRestAssuredConfigFactory.createConfig();

    String id = "";

    @Test(dataProviderClass = CustomerData.class,
            dataProvider = "createCustomerData",
            description = "Create and test customer creation")
    public void createCustomer(Customer customer, CustomerResponse customerResponse) {

        int expectedStatusCode = customerResponse.getResponseCode();
        String expectedMessage = customerResponse.getResponseMessage();
        Response res = customerRequest.createCustomer(customer);

        JsonPath jsonPath = new JsonPath(res.asString());

        if (expectedStatusCode == 200) {

            String aError = jsonPath.getString("message");

            var list = DBConnector.getInstance().getCustomerByID(customer.getId());
//DB validation
            Assert.assertTrue(list.size() == 1, "Multiple entries exist for unique id: " + customer.getId());
            Assert.assertEquals(list.get(0).get("id"), customer.getId());
            Assert.assertEquals(list.get(0).get("name"), customer.getName());
            Assert.assertEquals(list.get(0).get("phone_number"), customer.getPhoneNumber());

           // int smsVal = (int) list.get(0).get("SMS_SENT");
            Assert.assertTrue(Objects.isNull(list.get(0).get("sms_sent")));

            Assert.assertEquals(aError, expectedMessage, "Message not same");

            //for GET call
            id = customer.getId();

        } else if (expectedStatusCode == 500) {
            String aMessage = jsonPath.getString("error");
            Assert.assertFalse(aMessage.isBlank());
            String aError = jsonPath.getString("error");
            Assert.assertEquals(aError, expectedMessage, "Error message not same");
        }

    }

    @Test(dataProviderClass = CustomerData.class,
    dataProvider = "getCustomerData")
    public void getCustomerDetails(String id, Map<String,String> headers,CustomerResponse customerResponse) {

        int eStatusCode = customerResponse.getResponseCode();
        String eMessage = customerResponse.getResponseMessage();

        Response res = customerRequest.getCustomerDetailsById(headers,id);
        JsonPath path = new JsonPath(res.asString());
        System.out.println("------"+res.getStatusCode() );
        int aStatusCode = res.getStatusCode();

        if(eStatusCode == 200){

            Assert.assertEquals(aStatusCode,eStatusCode);
            var dbData = DBConnector.getInstance().getCustomerByID(id);
//DB validation
            Assert.assertEquals(path.getString("id"),dbData.get(0).get("id"));
            Assert.assertEquals(path.getString("name"),dbData.get(0).get("name"));
            Assert.assertEquals(path.getString("phone_number"),dbData.get(0).get("phone_number"));

            String isSMS;
                    if( (int)dbData.get(0).get("sms_sent") == 1)
                        isSMS = "true";
                    else
                        isSMS = "false";

            Assert.assertEquals(path.getString("sms_sent"),isSMS);
        }else if(eStatusCode == 400){
            Assert.assertEquals(path.getString("error"),eMessage);
        }else if (eStatusCode == 403){
            Assert.assertEquals(path.getString("error"),eMessage);
        }

    }


}
