package com.pichincha.infra.api.router.config;

import com.pichincha.ClientsApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClientsApplication.class)
public class ClientsApplicationTest {

    @Test
    public void contextLoads(){
        ClientsApplication.main(new String[]{
                "--spring.main.web-environment=false",
                "--spring.autoconfigure.exclude=blahblahblah"
        });
    }
}
