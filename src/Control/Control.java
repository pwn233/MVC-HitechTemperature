/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Model;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class Control {
    //เรียกใช้ model
    Model m = new Model();
    public void setStuID(String stuID) {
        //เป็น method สำหรับให้ model set ค่า student id จาก gui(view)
        m.setStuID(stuID);
    }
    public void setTemperature(String temperature) {
        //เป็น method สำหรับให้ model set ค่า temperature จาก gui(view)
        m.setTemperature(temperature);
    }
    public String process() {
        //พาร์ทนี้เรียกการทำงานใน model ตามลำดับดังนี้
        //เรียกอุณหภูมิที่ set ไว้
        //นำอุณหภูมิไปเช็ค
        //จดบันทึก log ลง database
        return m.insertDataDB(m.tempStatusCheck(m.getTemp()));
    }

    public DefaultTableModel showData(DefaultTableModel model) {
        //เป็น method ในการเรียกดึงข้อมูล ใน database มาแสดงจาก model และ คืนค่ากลับไปให้แสดง
        m.showData(model);
        return model;
    }
}
