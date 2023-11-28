package com.example.anyang_setup;
import java.util.ArrayList;
import java.util.List;

// 각종 데이터를 담는 전역변수클래스(어느 액티비티에서나 접근 가능)
public class GlobalVariables {

    private static String Student_Name; // 이름
    private static String Student_Id; // 학번
    private static String Grade_Point; // 학점(xx/4.5)
    private static String Major;
    private static List<String> myStringList = new ArrayList<>(); // Your new list

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

    public static String getGlobalVariable_Major() {
        return Major;
    }

    public static void setGlobalVariable_Major(String value) {
        Major = value;
    }

    // Methods for manipulating the list

    // Method to get the last element of the list
    public static String getLastElementOfList() {
        if (myStringList.isEmpty()) {
            return null; // or throw an exception, depending on your requirements
        } else {
            int lastIndex = myStringList.size() - 1;
            return myStringList.get(lastIndex);
        }
    }
    public static List<String> getMyStringList() {
        return myStringList;
    }

    public static void addStringToList(String value) {
        myStringList.add(value);
    }

    public static void removeStringFromList(String value) {
        myStringList.remove(value);
    }

    public static void clearStringList() {
        myStringList.clear();
    }
}
