package com.example.helmi.pjbdata.db;


public class Contants {
    //COLUMNS
    static final String ROW_ID="id";
    static final String NAME = "name";
    static final String AVALUE = "a";
    static final String BVALUE = "b";
    static final String CVALUE = "c";

    //DB PROPERTIES
    static final String DB_NAME="b_DB";
    static final String TB_NAME="b_TB";
    static final int DB_VERSION='1';


    //CREATE TABLE STATEMENTS
    static final String CREATE_TB="CREATE TABLE b_TB(id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "name TEXT NOT NULL,a TEXT NOT NULL,b TEXT NOT NULL,c TEXT NOT NULL);";
}

