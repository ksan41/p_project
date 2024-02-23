package com.cafe.shop.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class BarcodeCreator {
    
    public static String generateBarcode() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String dateStr = sdf.format(new Date());
        String randomNum = generateRandomNumber();
        return dateStr + randomNum;
    }

    private static String generateRandomNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int num = random.nextInt(9);
            sb.append(String.valueOf(num));
        }

        return sb.toString();
    }

}
