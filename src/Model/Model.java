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
    private String stuID;
    private double temperature;
    
    public void setStuID(String stuID) {
        this.stuID = stuID;
    }
    public double getTemp() {
        return temperature;
    }
    public void setTemperature(String temperature) {
        this.temperature = Double.parseDouble(temperature);
    }
    public String tempStatusCheck(double temperature) {
        String statusTemp = "";
        if(temperature > 37.5)
            statusTemp = "high";
        else if(temperature < 35)
            statusTemp = "low";
        else
            statusTemp = "normal";
        return statusTemp;
    }
    
    public String insertDataDB(String statusTemp) {
        String query = "";
        String status = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        String date = dtf.format(now);
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
