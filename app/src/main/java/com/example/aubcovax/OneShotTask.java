package com.example.aubcovax;

class OneShotTask implements Runnable {
    String str;
    String s3;

    OneShotTask(String s) { str = s; s3="HI"; }

    public void run() {
        try {
            Client c= new Client();
            s3= c.Run(str);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String get() {
        return s3;
    }
}


