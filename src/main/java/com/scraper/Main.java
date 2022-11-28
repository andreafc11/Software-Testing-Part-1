package com.scraper;

import com.scraper.interfacing.Facade;

public class Main {
    public static void main(String args[]){
        if(task1 == null){
            task1 = new Facade();
        }
        task1.scrapeAndUploadNAlertsWithWordSearch(5, "iPhone");
    }
    static Facade task1;

    public static void setFacade(Facade task1) {
        Main.task1 = task1;
    }
}
