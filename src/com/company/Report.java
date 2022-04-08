package com.company;

public class Report {
    private String type;
    private String content;

    public Report(){
        this.type = "";
        this.content = "";
    }

    public Report(String t, String c){
        this.type = t;
        this.content = c;
    }

    public String getType(){
        return this.type;
    }

    public String getContent(){
        return this.content;
    }
}
