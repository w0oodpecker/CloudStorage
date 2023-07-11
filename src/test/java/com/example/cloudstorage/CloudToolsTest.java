package com.example.cloudstorage;

import com.example.cloudstorage.component.CloudTools;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CloudToolsTest {

    @Test
    public void testGetRandomCode(){
        String string = new CloudTools(48,57,6).getRandomCode();
        Assertions.assertEquals(6, string.length());
    }
}
