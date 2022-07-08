package com.tedu.show;

import javax.swing.*;
import java.awt.*;

public class PassDialog extends JDialog {
    public PassDialog(String words){
        this.setTitle("通关");
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setSize(100,70);
        Container contentPane=this.getContentPane();
        JLabel jLabel = new JLabel(words);
        contentPane.add(jLabel);
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }
}
