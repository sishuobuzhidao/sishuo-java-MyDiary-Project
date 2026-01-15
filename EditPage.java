package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

public class EditPage extends JFrame implements ActionListener{
    JTextField titleField = new JTextField();
    JTextArea contentField = new JTextArea();
    JButton saveButton = new JButton("Save");
    JButton cancelButton = new JButton("Cancel");
    int rowToEdit;
    
    public EditPage(int row) throws IOException{
        rowToEdit = row;
        initFrame();
        initPageComponents();
    }

    public void initFrame(){
        this.setAlwaysOnTop(true);
        this.setSize(500,530);
        this.setLayout(null);
        this.setTitle("Edit diary");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void initPageComponents() throws IOException{
        titleField.setBounds(70, 30, 390, 30);
        contentField.setBounds(20, 95, 450, 320);
        contentField.setAutoscrolls(true);
        contentField.setLineWrap(true);//可换行
        contentField.setWrapStyleWord(true);

        //增加要处理的文字内容
        ArrayList<String> sourceList = Utils.readAll("files\\" + rowToEdit + ".txt");
        int index = 0; //计数
        StringBuilder sb = new StringBuilder();
        for (String str : sourceList){
            if (index == 0){
                titleField.setText(str);
            }else {
                sb.append(str + "\r\n");
            }
            index++;
        }
        contentField.setText(sb.toString());

        this.getContentPane().add(titleField);
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
                BufferedWriter bw = new BufferedWriter(new FileWriter("files\\" + rowToEdit + ".txt"));
                bw.write(title);
                bw.newLine();
                bw.write(content);
                bw.close();
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
