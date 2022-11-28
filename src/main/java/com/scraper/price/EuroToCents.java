package com.scraper.price;

public class EuroToCents {
    //used to convert the price on maltapark from type String to cents

    public int convert(String originalPrice) {
        // deletes the price symbol and anything which is not numbers or the decimal point
        originalPrice = originalPrice.replaceAll("[^0-9.]","");

        // exit if there is no price
        if(originalPrice.length() == 0){
            return -1;
        }

        // return value in integers
        double euro = Double.parseDouble(originalPrice);
        double cents = euro * 100;
        return (int) cents;
    }
}
