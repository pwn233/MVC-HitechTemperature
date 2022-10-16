/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class Model {
    //ประกาศตัวแปร stuID stand for student id มารองรับค่า 
    //ประกาศตัวแปร temperatire มารองรับอุณหภูมิ
    private String stuID;
    private double temperature;
    
    public void setStuID(String stuID) {
        //method สำหรับ set ค่า student ID
        this.stuID = stuID;
    }
    public double getTemp() {
        //method สำหรับ คืนค่า temperature เมื่อถูกเรียกใช้
        return temperature;
    }
    public void setTemperature(String temperature) {
        //method สำหรับ set ค่า temperature
        this.temperature = Double.parseDouble(temperature);
    }
    public String tempStatusCheck(double temperature) {
        //method นี้เป็น algorithm based on การวัดอุณหภูมิตามที่โจทย์ require
        //สร้าง status มารองรับในการคืนค่า high low normal
        String statusTemp = "";
        //เงื่อนไขแรกสุดคือการตรวจอุณหภูมิสูงกว่ากฏ โดยจะคืนค่าเป็น high
        if(temperature > 37.5)
            statusTemp = "high";
        //เงื่อนไขต่อมาคือการตรวจอุณหภูมิต่ำกว่ากฏ โดยจะคืนค่าเป็น low
        else if(temperature < 35)
            statusTemp = "low";
        else
        //เงื่อนไขท้ายสุดคือการตรวจอุณหภูมิที่ไม่เข้าข่ายตามกฏ โดยจะคืนค่าเป็น normal  
            statusTemp = "normal";
        //คืนค่า สถานะ high low normal กลับไป
        return statusTemp;
    }
    
    public String insertDataDB(String statusTemp) {
        //เป็น method เพื่อ insert ข้อมูลที่มีลงบันทึกเป็น log ใน database
        //สร้าง query ไว้รองรับคำสั่ง mysql
        String query = "";
        //สร้าง status ไว้เก็บสถานะของการใช้คำสั่ง mysql ว่าสำเร็จหรือไม่
        String status = "";
        //ใช้ java lib date time formatter ในการเรียกใช้วันเวลา และ local date time ในการเรียกช่วงเวลาปัจจุบัน
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        //เก็บค่าวันเวลาลงในรูปแบบของ string(in mysql : varchar)
        String date = dtf.format(now);
        //เปลี่ยนรูปแบบทศนิยมเป็น หลักเดียว
        String pattern = "##.#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        String tempDec = decimalFormat.format(temperature);
        //query valid check in phpmyadmin 
        query = String.format("INSERT INTO TEMPLOG (STUDENT_ID, DATE, Temperature, Status)VALUES ('%1$s', '%2$s', '%3$s', '%4$s')", stuID, date, tempDec, statusTemp);
        try {
            DBConnect conn = new DBConnect();
            boolean result = conn.execute(query);
            if (result) {
                status = "complete";
            } else {
                status = "fail (insertDataDB)";
            }
            conn.close();
        } catch (Exception ex) {
            status = "Error Update: " + ex;
        }
        return status;
    }

    public DefaultTableModel showData(DefaultTableModel model) {
        //method ดึงข้อมูลมาเป็นรูปแบบแถวเพื่อ port ลงตารางใน method ที่เรียก method นี้มา
        String query = "";
        try {
            DBConnect conn = new DBConnect();
            query = String.format("SELECT * FROM templog ORDER BY Temperature DESC LIMIT 10");
            ResultSet rs = conn.getResult(query);
            while (rs.next()) {
                String stuid = rs.getString("STUDENT_ID");
                String date = rs.getString("DATE");
                String temp = rs.getString("Temperature");
                String status = rs.getString("Status");
                String[] row = {stuid, date, temp, status};
                model.addRow(row);
                // critical variable name, beware of it!
            }
            conn.close();
        } catch (Exception ex) {
            //sss
        }
        return model;
    }
}
