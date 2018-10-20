package com.example.sheha.myfirstapp;

/**
 * Created by sheha on 12/4/2017.
 */

public class LogTransactionError {
    public String errorId;
    public String errorDesc;
    public String errorTime;
    public String transactionType;

    public LogTransactionError(String errorId, String errorDesc, String errorTime, String transactionType){
        this.errorId=errorId;
        this.errorDesc=errorDesc;
        this.errorTime=errorTime;
        this.transactionType=transactionType;
    }
}
