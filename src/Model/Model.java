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
    //��С�ȵ���� stuID stand for student id ���ͧ�Ѻ��� 
    //��С�ȵ���� temperatire ���ͧ�Ѻ�س�����
    private String stuID;
    private double temperature;
    
    public void setStuID(String stuID) {
        //method ����Ѻ set ��� student ID
        this.stuID = stuID;
    }
    public double getTemp() {
        //method ����Ѻ �׹��� temperature ����Ͷ١���¡��
        return temperature;
    }
    public void setTemperature(String temperature) {
        //method ����Ѻ set ��� temperature
        this.temperature = Double.parseDouble(temperature);
    }
    public String tempStatusCheck(double temperature) {
        //method ����� algorithm based on ����Ѵ�س����Ե�����⨷�� require
        //���ҧ status ���ͧ�Ѻ㹡�ä׹��� high low normal
        String statusTemp = "";
        //���͹��á�ش��͡�õ�Ǩ�س������٧���ҡ� �¨Ф׹����� high
        if(temperature > 37.5)
            statusTemp = "high";
        //���͹䢵���Ҥ�͡�õ�Ǩ�س����Ե�ӡ��ҡ� �¨Ф׹����� low
        else if(temperature < 35)
            statusTemp = "low";
        else
        //���͹䢷����ش��͡�õ�Ǩ�س����Է�������Ң��µ���� �¨Ф׹����� normal  
            statusTemp = "normal";
        //�׹��� ʶҹ� high low normal ��Ѻ�
        return statusTemp;
    }
    
    public String insertDataDB(String statusTemp) {
        //�� method ���� insert �����ŷ����ŧ�ѹ�֡�� log � database
        //���ҧ query ����ͧ�Ѻ����� mysql
        String query = "";
        //���ҧ status �����ʶҹТͧ��������� mysql ���������������
        String status = "";
        //�� java lib date time formatter 㹡�����¡���ѹ���� ��� local date time 㹡�����¡��ǧ���һѨ�غѹ
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        //�纤���ѹ����ŧ��ٻẺ�ͧ string(in mysql : varchar)
        String date = dtf.format(now);
        //����¹�ٻẺ�ȹ����� ��ѡ����
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
        //method �֧�����������ٻẺ������ port ŧ���ҧ� method ������¡ method �����
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
