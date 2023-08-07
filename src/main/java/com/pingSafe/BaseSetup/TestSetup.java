package com.pingSafe.BaseSetup;

import com.pingSafe.Helper.BaseURL;
import com.pingSafe.Helper.CustomerRequest;
import lombok.Getter;
import lombok.Setter;
import org.testng.annotations.BeforeSuite;

public class TestSetup {



    public final CustomerRequest customerRequest;

    public TestSetup(){
        customerRequest = new CustomerRequest();
    }

    @BeforeSuite
    public void setUp(){
//clear DB


    }
}
