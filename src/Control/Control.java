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

    Model m = new Model();
    public void setStuID(String stuID) {

        m.setStuID(stuID);
    }
    public void setTemperature(String temperature) {

        m.setTemperature(temperature);
    }
    public String process() {

        return m.insertDataDB(m.tempStatusCheck(m.getTemp()));
    }

    public DefaultTableModel showData(DefaultTableModel model) {
        m.showData(model);
        return model;
    }
}
