package com.spring.qa.auto.util;

import lombok.SneakyThrows;

public class Pause {

    @SneakyThrows
    public static void pause(Integer seconds) {
        Thread.sleep(seconds * 1000);
    }
}
