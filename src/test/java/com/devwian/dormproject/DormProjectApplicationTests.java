package com.devwian.dormproject;

import com.devwian.dormproject.controller.DormController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Scanner;
import java.util.regex.Pattern;

@SpringBootTest
class DormProjectApplicationTests {

    @Test
    void contextLoads() {
        com.devwian.dormproject.controller.DormController dormController = new DormController();
        System.out.println(dormController.getDorm());
    }



}
