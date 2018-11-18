package com.example.zeronone.myapplication;

public interface Util {

    String BASEURL = "http://192.168.2.102:5000/api/v1/";

    String ADD_Employee = BASEURL+"add_employee";
    String LIST_EMPLOYEE =BASEURL+"list_employee";
    String Update_Employee = BASEURL + "update_employee/empid=";
    String Delete_Empoyee = BASEURL+"delete_employee/empid=";
}
