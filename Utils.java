package code;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

public class Utils {
    static int numberOfDiaries = 0;
    private Utils(){}

    public static void throwModal(String text){
        // 弹窗实现报错效果
        JDialog jd = new JDialog();
        jd.setSize(400,300);
        jd.setAlwaysOnTop(true);
        jd.setLocationRelativeTo(null);
        jd.setModal(true);
        jd.setLayout(null);

        JLabel jl = new JLabel(text);
        jl.setFont(new Font("Times New Roman", Font.BOLD, 30));
        jl.setBounds(10, 95, 500, 60);
        jd.getContentPane().add(jl);
        jd.setVisible(true);

    }

    public static int setCount(int count) throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter("files\\count.txt"));
        bw.write(count + "");
        bw.close();
        return count;
    }

    public static int getCount() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("files\\count.txt"));
        int result = Integer.parseInt(br.readLine());
        br.close();
        return result;
    }

    public static String[] getInfo(String src) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(src));
        String title = br.readLine();
        String content = br.readLine();
        br.close();
        return new String[]{title, content};
    }

    public static ArrayList<String> readAll(String src) throws IOException{
        ArrayList<String> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(src));
        String str;
        while((str = br.readLine()) != null){
            list.add(str);
        }
        br.close();
        return list;
    }

    public static void writeAll(ArrayList<String> list, String src) throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter(src));
        for (String str : list) {
            bw.write(str);
            bw.newLine();
        }
        bw.close();
    }
}
