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
    //���¡�� model
    Model m = new Model();
    public void setStuID(String stuID) {
        //�� method ����Ѻ��� model set ��� student id �ҡ gui(view)
        m.setStuID(stuID);
    }
    public void setTemperature(String temperature) {
        //�� method ����Ѻ��� model set ��� temperature �ҡ gui(view)
        m.setTemperature(temperature);
    }
    public String process() {
        //���췹�����¡��÷ӧҹ� model ����ӴѺ�ѧ���
        //���¡�س����Է�� set ���
        //���س��������
        //���ѹ�֡ log ŧ database
        return m.insertDataDB(m.tempStatusCheck(m.getTemp()));
    }

    public DefaultTableModel showData(DefaultTableModel model) {
        //�� method 㹡�����¡�֧������ � database ���ʴ��ҡ model ��� �׹��ҡ�Ѻ�����ʴ�
        m.showData(model);
        return model;
    }
}
