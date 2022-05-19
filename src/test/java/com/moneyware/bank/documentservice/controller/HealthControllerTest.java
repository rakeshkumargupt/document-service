package com.moneyware.bank.documentservice.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class HealthControllerTest {

    @InjectMocks
    private HealthController healthController;

    @Test
    public void testHello() {
        ResponseEntity<Object> responseEntity = healthController.health();
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Assert.assertNotNull(responseEntity);
    }
}
