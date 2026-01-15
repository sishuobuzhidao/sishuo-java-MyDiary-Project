package code;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AddPage extends JFrame implements ActionListener{
    JTextField titleField = new JTextField();
    JTextArea contentField = new JTextArea();
    JButton saveButton = new JButton("Save");
    JButton cancelButton = new JButton("Cancel");
    
    public AddPage(){
        initFrame();
        initPageComponents();
    }

    public void initFrame(){
        this.setAlwaysOnTop(true);
        this.setSize(500,530);
        this.setLayout(null);
        this.setTitle("Add new diary");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void initPageComponents(){
        titleField.setBounds(70, 30, 390, 30);
        this.getContentPane().add(titleField);

        contentField.setBounds(20, 95, 450, 320);
        contentField.setAutoscrolls(true);
        contentField.setLineWrap(true);//可换行
        contentField.setWrapStyleWord(true);
        this.getContentPane().add(contentField);

        JLabel titleLabel = new JLabel("Title");
        titleLabel.setBounds(20, 30, 50, 30);
        this.getContentPane().add(titleLabel);

        JLabel contentLabel = new JLabel("Content");
        contentLabel.setBounds(20, 70, 50, 20);
        this.getContentPane().add(contentLabel);

        saveButton.setBounds(90, 430, 100, 40);
        saveButton.addActionListener(this);
        this.getContentPane().add(saveButton);

        cancelButton.setBounds(290, 430, 100, 40);
        cancelButton.addActionListener(this);
        this.getContentPane().add(cancelButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        String title = titleField.getText();
        String content = contentField.getText();
        if (obj == saveButton){
            if (title.equals("") || content.equals("")){
                Utils.throwModal("Empty title or content.");
                return;
            }
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("files\\" + Utils.getCount() + ".txt"));
                bw.write(title);
                bw.newLine();
                bw.write(content);
                bw.close();
                Utils.setCount(Utils.getCount() + 1);
                new MainPage();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            this.setVisible(false);
        }else if (obj == cancelButton){
            this.setVisible(false);
        }
    }
}
