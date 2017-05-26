
package socnet;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class MainFrame extends JFrame {
    
    Frame sn0;
    String query;
   static JTextField sear;
    static JButton searchButton;
    static JLabel mode;
    static DefaultTableModel d1;
   // JProgressMonitor pm;
    static JProgressBar pm2;
    static JDialog m1;
    public MainFrame(){
        sn0 = this;
        
        setMinimumSize(new Dimension(490,390));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Search Engine");
         
        this.setLayout(new BorderLayout());
        
        JPanel MainPanel = new JPanel();
        add(MainPanel);
        FlowLayout f1 = new FlowLayout();
        GridLayout g1 = new GridLayout(1,2);
        f1.setHgap(40);
        MainPanel.setLayout(f1);
      
        
        JPanel m1 = new JPanel();
        JPanel m2 = new JPanel();
        
        m1.setLayout(new BoxLayout(m1,BoxLayout.Y_AXIS));
       
        m2.setLayout(new BoxLayout(m2,BoxLayout.Y_AXIS));
        //-----+++++++++++++++++++++++++++++++
        
        
        //+++++++++++++++++++++++++++++++++++
        
        JPanel txt = new JPanel();
        JPanel wgt = new JPanel();
        JPanel sim = new JPanel();
        JPanel comD = new JPanel();
        JPanel md = new JPanel();
        
        mode = new JLabel(" Mode : None ");
        Border border = BorderFactory.createLineBorder(Color.GRAY, 1, true);

        mode.setBorder(border);
        
        JButton comDis = new JButton("     Set Mode     ");
        
        comD.add(comDis);
        md.add(mode);
        
        
        wgt.setBorder(BorderFactory.createTitledBorder("Weight"));
        wgt.setLayout(new BoxLayout(wgt,BoxLayout.Y_AXIS));
       // wgt.setLayout(new FlowLayout());
        sim.setBorder(BorderFactory.createTitledBorder("Similarity"));
        
        sear = new JTextField(15);
     //  TextPrompt tp7 = new TextPrompt("Insert Search query here", sear);
     //  tp7.changeStyle(Font.BOLD + Font.ITALIC);
     //  tp7.changeAlpha(50);
     //  tp7.changeStyle(Font.PLAIN);
       
        String[] options1 = { "Natural", "Logarithm", "Augmented","boolean","Log Avg"};
        String[] options2 = { "No", "Idf"};
        String[] options3 = { "None", "Cosine"};
        String[] options4 = { "CosDist","Euklid"};
        JComboBox<String> W1ComboBox = new JComboBox<>(options1);
        
        JComboBox<String> W2ComboBox = new JComboBox<>(options2);
        JComboBox<String> W3ComboBox = new JComboBox<>(options3);
        
        JComboBox<String> S1ComboBox = new JComboBox<>(options4);
        //--------------------------------------------------
          JPanel w1C = new JPanel();
          JPanel w2C = new JPanel();
          JPanel w3C = new JPanel();
          w1C.setLayout(new FlowLayout());
          w2C.setLayout(new FlowLayout());
          w3C.setLayout(new FlowLayout());
          w1C.add(W1ComboBox);
          w2C.add(W2ComboBox);
          w3C.add(W3ComboBox);
        //--------------------------------------------------
        txt.add(sear);
        
        comDis.addActionListener(new computeDistanceListener(W1ComboBox,W2ComboBox,W3ComboBox,S1ComboBox));
        
        wgt.add(w1C);
        wgt.add(w2C);
        wgt.add(w3C);
        sim.add(S1ComboBox);
        
        
        m1.add(txt);
        m1.add(md);
        
     //   m1.add(Box.createRigidArea(new Dimension(5,10)));
        m1.add(wgt);
        m1.add(Box.createRigidArea(new Dimension(5,10)));
        m1.add(sim);
         m1.add(Box.createRigidArea(new Dimension(5,10)));
        m1.add(comD);
        
        MainPanel.add(m1);
        MainPanel.add(m2);
        pm2 = new JProgressBar(0, 100);

        //---------------------------Second panel : list+button
        JPanel search = new JPanel();
        searchButton = new JButton("Search");
        searchButton.setEnabled(false);
        
        SearchListener sl = new SearchListener();
        searchButton.addActionListener(sl);
        
        search.add(searchButton);
        
        JPanel lista = new JPanel();
        
//        DefaultListModel<String> myListModel = new DefaultListModel<String>();
//       JList<String> myList = new JList<String>(myListModel);
//        myList.setVisibleRowCount(15);
//        myList.setFixedCellWidth(100);
//        JScrollPane myScrollPane = new JScrollPane(myList);
        d1 = new DefaultTableModel();
        
        JTable t1 = new JTable(d1);
        
    //    t1.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        d1.addColumn("user");
        d1.addColumn("score");
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        t1.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        
         t1.getColumnModel().getColumn(0).setPreferredWidth(10);
         t1.getColumnModel().getColumn(1).setPreferredWidth(10);
         t1.setPreferredScrollableViewportSize(new Dimension(150,250));
        t1.setFillsViewportHeight(true);
         JScrollPane s1 = new JScrollPane(t1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        m2.add(search);
      //  m2.add(myScrollPane);
        m2.add(s1);
     
        
       
    }
    
    public void progressMonitor(){
        
         m1 = new JDialog(MainFrame.this);
    
          m1.setSize(300, 60);
          m1.setTitle("Creating inverted index");
          pm2 = new JProgressBar(0, 18217);
          pm2.setStringPainted(true);
         
          pm2.setValue(0);
          m1.setLayout(new GridLayout());
          m1.add(pm2);
        
          m1.setLocationRelativeTo(MainFrame.this);
      
          m1.setVisible(true);
      
        // MainFrame.this.setEnabled(false);
        
        
    }
    
    static public void endMonitor(){
        
        m1.setVisible(false);

        
    }
    
    public static String getTxt(){
        
        
        return sear.getText();
    }
}
