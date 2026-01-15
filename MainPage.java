package code;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainPage extends JFrame implements ActionListener{
    //为了防止天天调用getContentPane我直接创建一个变量
    final Container PANE = this.getContentPane();
    JTable notesTable = new JTable();
    DefaultTableModel dtf = null;
    JButton addButton = new JButton("Add");
    JButton editButton = new JButton("Edit");
    JButton deleteButton = new JButton("Delete");
    JMenuItem exportItem;
    JMenuItem importItem;
    int numberOfDiaries;

    public MainPage() throws NumberFormatException, IOException{
        initFrame();
        initMenu();
        initPageComponents();
        loadDiaries();
    }

    public void initFrame(){
        //创建JFrame对象并且修改必要的属性
        this.setAlwaysOnTop(true);
        this.setSize(500,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("My Diary");
        this.setLayout(null);
        this.setVisible(true);
    }

    public void initMenu(){
        //菜单栏
        JMenuBar jMenubar = new JMenuBar();
        JMenu jMenu = new JMenu("Options");
        importItem = new JMenuItem("Import");
        exportItem = new JMenuItem("Export");

        jMenubar.add(jMenu);
        jMenu.add(importItem);
        jMenu.add(exportItem);

        importItem.addActionListener(this);
        exportItem.addActionListener(this);
        this.setJMenuBar(jMenubar);
    }

    public void initPageComponents(){
        //this.removeAll();
        //背景色
        PANE.setBackground(Color.LIGHT_GRAY);

        JLabel titleJLabel = new JLabel("Diary");
        titleJLabel.setBounds(225,30,200,50);
        // Font类：字体类型（如宋体）+加粗/斜体之类的+字体大小
        titleJLabel.setFont(new Font("Times new Roman", Font.BOLD, 20));
        PANE.add(titleJLabel);

        // JFileChooser jfc = new JFileChooser();
        // jfc.setBounds(200,100,200,200);
        
        addButton.setBounds(60, 450, 100, 40);
        addButton.addActionListener(this);
        PANE.add(addButton);
        editButton.setBounds(190, 450, 100, 40);
        editButton.addActionListener(this);
        PANE.add(editButton);
        deleteButton.setBounds(320, 450, 100, 40);
        deleteButton.addActionListener(this);
        PANE.add(deleteButton);
        
        //存储表格之中的行（表头）
        Object[] columns = new Object[]{"ID", "Title", "Content"};
        //表头里面的数据
        Object[][] mains = {};
        dtf = new DefaultTableModel(mains, columns);
        notesTable.setBounds(50, 120, 400, 300);
        notesTable.setSelectionMode(1);
        notesTable.setModel(dtf);

        //滚动界面的对象，可以使得数据过多时可以上下移动
        JScrollPane jsp = new JScrollPane(notesTable);
        jsp.setBounds(50, 120, 400, 300);
        PANE.add(jsp);

        PANE.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == addButton){
            this.setVisible(false);
            new AddPage();
            //这里还有问题，还没法刷新！W            
        }else if (obj == editButton){
            int row = notesTable.getSelectedRow();
            if (row == -1){
                Utils.throwModal("Please select a row first.");
            } else {
                try {
                    this.setVisible(false);
                    new EditPage(row);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }else if (obj == deleteButton){
            int row = notesTable.getSelectedRow();
            if (row == -1){
                Utils.throwModal("Please select a row first.");
            } else {
                finalDecision(row);
            }
        }else if (obj == exportItem){
            int row = notesTable.getSelectedRow();
            if (row == -1){
                Utils.throwModal("Please select a row first.");
            } else {
                
            }
        }else if (obj == importItem){

        }
    }

    public void finalDecision(int row){
        // 删除按钮点击后会弹出这个让你确定要删除
        JDialog jd = new JDialog();
        jd.setSize(400,200);
        jd.setAlwaysOnTop(true);
        jd.setLocationRelativeTo(null);
        jd.setModal(true);
        jd.setLayout(null);

        JLabel jl = new JLabel("Do you really want to delete \"" + dtf.getValueAt(row, 1) + "\" ?");
        jl.setBounds(90, 30, 500, 60);
        jd.getContentPane().add(jl);

        JButton yesButton = new JButton("Yes");
        yesButton.setBounds(100, 120, 80, 30);
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jd.setVisible(false);
                dtf.removeRow(row);
                //调整日记数量
                try {
                    numberOfDiaries = Utils.setCount(numberOfDiaries - 1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                // "files\\" + Utils.getCount() + ".txt"
                for (int i = row + 1 ; i <= numberOfDiaries ; i++){
                    //修改序号所对应的内容
                    try {
                        Utils.writeAll(Utils.readAll("files\\" + i + ".txt"), ("files\\" + (i - 1) + ".txt"));
                    } catch (IOException e1) {
                        //System.out.println("aaaaaaaaaa");
                    }
                }
                MainPage.this.setVisible(false);
                //直接重新创建对象刷新了，我受不了了
                try {
                    new MainPage();
                } catch (NumberFormatException | IOException e1) {
                    e1.printStackTrace();
                }
            }        
        });
        jd.getContentPane().add(yesButton);

        JButton noButton = new JButton("No");
        noButton.setBounds(200, 120, 80, 30);
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jd.setVisible(false);
            }
        });
        jd.getContentPane().add(noButton);
        jd.setVisible(true);
        
    }

    public void loadDiaries() throws NumberFormatException, IOException{
        this.numberOfDiaries = Utils.getCount();
        for (int i = 0 ; i < this.numberOfDiaries ; i++){
            String[] arr = Utils.getInfo("files\\" + i + ".txt");
            dtf.addRow(new Object[]{i, arr[0], arr[1]});
        }
    }
}
