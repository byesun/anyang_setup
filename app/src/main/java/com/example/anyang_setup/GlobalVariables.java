package com.example.anyang_setup;

//각종 데이터를 담는 전역변수클래스(어느액티비티에서나 접근가능)
public class GlobalVariables {

    private static String Student_Name; //이름
    private static String Student_Id; //학번
    private static String Grade_Point; //학점(xx/4.5)

    public static String getGlobalVariable_id() {
        return Student_Id;
    }

    public static void setGlobalVariable_id(String value) {
        Student_Id = value;
    }

    public static String getGlobalVariable_Name() {
        return Student_Name;
    }

    public static void setGlobalVariable_Name(String value) {
        Student_Name = value;
    }

    public static String getGlobalVariable_Grade_Point() {
        return Grade_Point;
    }

    public static void setGlobalVariable_Grade_Point(String value) {
        Grade_Point = value;
    }
}
