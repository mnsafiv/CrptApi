package ru.safonoviv;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        CrptApi crptApi = new CrptApi(TimeUnit.SECONDS, 5);
        String wrongJsonStr = "{\"description\": { \"participantInn\": \"string\" }, \"doc_id\": \"string\", \"doc_status\": \"string\", \"doc_type\": \"LP_INTRODUCE_GOODS\", 109 \"importRequest\": true, \"owner_inn\": \"string\", \"participant_inn\": \"string\", \"producer_inn\": \"string\", \"production_date\": \"2020-01-23\", \"production_type\": \"string\", \"products\": [ { \"certificate_document\": \"string\", \"certificate_document_date\": \"2020-01-23\", \"certificate_document_number\": \"string\", \"owner_inn\": \"string\", \"producer_inn\": \"string\", \"production_date\": \"2020-01-23\", \"tnved_code\": \"string\", \"uit_code\": \"string\", \"uitu_code\": \"string\" } ], \"reg_date\": \"2020-01-23\", \"reg_number\": \"string\"}";


        for (int i = 0; i < 10; i++) {
            new Thread(new SendRequest(crptApi, wrongJsonStr)).start();
        }


//        crptApi.shootDown();


    }

    public static class SendRequest implements Runnable{
        private final CrptApi crptApi;
        private final String wrongJsonStr;

        public SendRequest(CrptApi crptApi, String wrongJsonStr) {
            this.crptApi = crptApi;
            this.wrongJsonStr = wrongJsonStr;
        }

        @Override
        public void run() {
            for (int i = 0; i < 500; i++) {
                crptApi.sendRequest(wrongJsonStr);
            }
        }
    }



}