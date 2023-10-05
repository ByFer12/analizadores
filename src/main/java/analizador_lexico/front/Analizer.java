package analizador_lexico.front;

import analizador_lexico.analyzer.Analizador;
import analizador_lexico.analyzer.Sintaxis;
import analizador_lexico.analyzer.Token;
import analizador_lexico.enums.TypeToken;
import analizador_lexico.symbol_table.Simbolo;
import analizador_lexico.utils.Archive;
import java.awt.Color;
import java.awt.Font;
import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import analizador_lexico.symbol_table.SymbolTable;

/**
 *
 * @author tuxrex
 */
public class Analizer extends javax.swing.JFrame {

    Analizador analizador;

    Font font = new Font("Liberation Sans", Font.BOLD, 20);
    private int lineNumber;
    private int columnNumber;
    private Archive archivo;
    private String texto;
    DefaultTableModel modelTable;
    private Analizador analyzer = new Analizador();
    ArrayList<Token> tokens;
    ArrayList<Token> errors;

    ArrayList<Integer> lineNumbers = new ArrayList<>();
    Sintaxis sy;

    public Analizer() {
        initComponents();
        //modifyTextArea();
        lineNumberLabel.setFont(font);
        archivo = new Archive();
        archivo.setPath(path_file);
        analizador = new Analizador();
        modelTable = new DefaultTableModel();
        modelTable.addColumn("Tokens");
        modelTable.addColumn("Patron");
        modelTable.addColumn("Lexema");
        modelTable.addColumn("Linea");
        modelTable.addColumn("Columna");

        
    }

    public void printTable() {

        boolean isError = Analizador.isError;
        tokens = Analizador.tokens;
        //errors=Analizador.errores;
          
        if (isError) {
            this.title.setText("Errores Lexicos");
            Object[] ob = new Object[5];
            for (int i = 0; i < tokens.size(); i++) {
                if (tokens.get(i).getToken() == TypeToken.ERR) {

                    ob[0] = tokens.get(i).getToken();
                    ob[1] = tokens.get(i).getNombre();
                    ob[2] = tokens.get(i).getLexema();
                    ob[3] = tokens.get(i).getFila();
                    ob[4] = tokens.get(i).getColumna();
                    modelTable.addRow(ob);
                }
            }
            table_error_tokens.setModel(modelTable);

        } else {
            title.setText("Tokens Reconocidos");
            Object[] ob = new Object[5];
            for (int i = 0; i < tokens.size(); i++) {
                if ((tokens.get(i).getToken() != TypeToken.ERR)) {
                    ob[0] = tokens.get(i).getToken();
                    ob[1] = tokens.get(i).getNombre();
                    ob[2] = tokens.get(i).getLexema();
                    ob[3] = tokens.get(i).getFila();
                    ob[4] = tokens.get(i).getColumna();
                    modelTable.addRow(ob);
                }

            }

            table_error_tokens.setModel(modelTable);
        }
        Analizador.isError = false;
    }

    public void limpiar() {
        for (int i = 0; i < modelTable.getRowCount(); i++) {
            modelTable.removeRow(i);
            i = i - 1;
        }
    }

    public void pintar() {
        StyledDocument doc = area_code.getStyledDocument();
        SimpleAttributeSet estiloPorDefecto = new SimpleAttributeSet();
        for (Token tk : tokens) {
            SimpleAttributeSet estilo = new SimpleAttributeSet();
            if ((tk.getToken() == TypeToken.OP_ARIT) || (tk.getToken() == TypeToken.OP_COMP)||(tk.getToken() == TypeToken.OP_AS)) {
                StyleConstants.setForeground(estilo, Color.CYAN);

            }else if(tk.getToken()==TypeToken.KY_WD){
                StyleConstants.setForeground(estilo, Color.magenta);
            }else if(tk.getToken()==TypeToken.CONS){
                StyleConstants.setForeground(estilo, Color.ORANGE);
            }else if(tk.getToken()==TypeToken.COM){
                StyleConstants.setForeground(estilo, Color.GRAY);
                
            }else if(tk.getToken()==TypeToken.OT){
                StyleConstants.setForeground(estilo, Color.green);
            }else if(tk.getToken()==TypeToken.ID){
                StyleConstants.setForeground(estilo, Color.BLACK);
            }else if(tk.getToken()==TypeToken.ERR){
                 StyleConstants.setForeground(estilo, Color.red);
            }
            try {
               
                doc.insertString(doc.getLength(), tk.getLexema() + " ", estilo);
                System.out.println("\n");

            } catch (BadLocationException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        tobbe1 = new javax.swing.JTabbedPane();
        error_o_token = new javax.swing.JPanel();
        btn_save_Archivo = new javax.swing.JButton();
        run = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_error_tokens = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        title = new javax.swing.JLabel();
        btn_Archivo1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        path_file = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        lineNumberLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        area_code = new javax.swing.JTextPane();
        position = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        go_analizer = new javax.swing.JMenu();
        go_graphics = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tobbe1.setEnabled(false);

        btn_save_Archivo.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        btn_save_Archivo.setIcon(new javax.swing.ImageIcon("/home/tuxrex/NetBeansProjects/analizador_lexico/src/main/java/analizador_lexico/img/save.png")); // NOI18N
        btn_save_Archivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_save_ArchivoActionPerformed(evt);
            }
        });

        run.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        run.setIcon(new javax.swing.ImageIcon("/home/tuxrex/NetBeansProjects/analizador_lexico/src/main/java/analizador_lexico/img/play.png")); // NOI18N
        run.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runActionPerformed(evt);
            }
        });

        table_error_tokens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Token", "Patron", "Lexema", "Linea", "Columna"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_error_tokens.setColumnSelectionAllowed(true);
        jScrollPane2.setViewportView(table_error_tokens);
        table_error_tokens.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (table_error_tokens.getColumnModel().getColumnCount() > 0) {
            table_error_tokens.getColumnModel().getColumn(0).setHeaderValue("Token");
            table_error_tokens.getColumnModel().getColumn(1).setHeaderValue("Patron");
            table_error_tokens.getColumnModel().getColumn(2).setHeaderValue("Lexema");
            table_error_tokens.getColumnModel().getColumn(3).setResizable(false);
            table_error_tokens.getColumnModel().getColumn(3).setHeaderValue("Linea");
            table_error_tokens.getColumnModel().getColumn(4).setResizable(false);
            table_error_tokens.getColumnModel().getColumn(4).setHeaderValue("Columna");
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(177, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 652, Short.MAX_VALUE)
        );

        title.setFont(new java.awt.Font("Liberation Sans", 1, 23)); // NOI18N

        btn_Archivo1.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        btn_Archivo1.setIcon(new javax.swing.ImageIcon("/home/tuxrex/NetBeansProjects/analizador_lexico/src/main/java/analizador_lexico/img/add_archive.png")); // NOI18N
        btn_Archivo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Archivo1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Liberation Sans", 1, 30)); // NOI18N
        jLabel3.setText("Analizador Lexico");

        path_file.setEditable(false);
        path_file.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon("/home/tuxrex/NetBeansProjects/analizador_lexico/src/main/java/analizador_lexico/img/limpiar.png")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lineNumberLabel.setFont(new java.awt.Font("Liberation Mono", 0, 15)); // NOI18N
        lineNumberLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lineNumberLabel.setAlignmentX(90);
        lineNumberLabel.setAlignmentY(15.0F);
        lineNumberLabel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        area_code.setFont(new java.awt.Font("Liberation Sans", 0, 20)); // NOI18N
        area_code.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                area_codeCaretUpdate(evt);
            }
        });
        jScrollPane3.setViewportView(area_code);

        jButton2.setText("Syntax");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout error_o_tokenLayout = new javax.swing.GroupLayout(error_o_token);
        error_o_token.setLayout(error_o_tokenLayout);
        error_o_tokenLayout.setHorizontalGroup(
            error_o_tokenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(error_o_tokenLayout.createSequentialGroup()
                .addGroup(error_o_tokenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(error_o_tokenLayout.createSequentialGroup()
                        .addComponent(lineNumberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 781, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(error_o_tokenLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(position))
                    .addGroup(error_o_tokenLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(path_file, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Archivo1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_save_Archivo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(error_o_tokenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(error_o_tokenLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(error_o_tokenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(error_o_tokenLayout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(error_o_tokenLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(run, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(188, 188, 188))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, error_o_tokenLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(546, 546, 546))
        );
        error_o_tokenLayout.setVerticalGroup(
            error_o_tokenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(error_o_tokenLayout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(error_o_tokenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(error_o_tokenLayout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(error_o_tokenLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(error_o_tokenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(error_o_tokenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, error_o_tokenLayout.createSequentialGroup()
                                    .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(46, 46, 46))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, error_o_tokenLayout.createSequentialGroup()
                                    .addGroup(error_o_tokenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btn_Archivo1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btn_save_Archivo, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(error_o_tokenLayout.createSequentialGroup()
                                            .addComponent(path_file, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(9, 9, 9)))
                                    .addGap(19, 19, 19)
                                    .addComponent(position)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, error_o_tokenLayout.createSequentialGroup()
                                .addGroup(error_o_tokenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(run, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(error_o_tokenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lineNumberLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(error_o_tokenLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(error_o_tokenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 666, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(8, 8, 8))))
        );

        tobbe1.addTab("", error_o_token);

        jLabel1.setText("Aqui va las graficas");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(456, 456, 456)
                .addComponent(jLabel1)
                .addContainerGap(970, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jLabel1)
                .addContainerGap(736, Short.MAX_VALUE))
        );

        tobbe1.addTab("", jPanel3);

        jLabel4.setText("Aqui va acerca de mi ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(646, 646, 646)
                .addComponent(jLabel4)
                .addContainerGap(766, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(132, 132, 132)
                .addComponent(jLabel4)
                .addContainerGap(658, Short.MAX_VALUE))
        );

        tobbe1.addTab("", jPanel2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tobbe1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tobbe1)
        );

        go_analizer.setIcon(new javax.swing.ImageIcon("/home/tuxrex/NetBeansProjects/analizador_lexico/src/main/java/analizador_lexico/img/icons8-archive-list-of-parts-40.png")); // NOI18N
        go_analizer.setText("Archivo");
        go_analizer.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        go_analizer.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                go_analizerMenuSelected(evt);
            }
        });
        go_analizer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                go_analizerActionPerformed(evt);
            }
        });
        jMenuBar1.add(go_analizer);

        go_graphics.setIcon(new javax.swing.ImageIcon("/home/tuxrex/NetBeansProjects/analizador_lexico/src/main/java/analizador_lexico/img/icons8-vector-64.png")); // NOI18N
        go_graphics.setText("Generar Grafico");
        go_graphics.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        go_graphics.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                go_graphicsMenuSelected(evt);
            }
        });
        jMenuBar1.add(go_graphics);

        jMenu3.setIcon(new javax.swing.ImageIcon("/home/tuxrex/NetBeansProjects/analizador_lexico/src/main/java/analizador_lexico/img/help.png")); // NOI18N
        jMenu3.setText("Ayuda");
        jMenu3.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        jMenuBar1.add(jMenu3);

        jMenu4.setIcon(new javax.swing.ImageIcon("/home/tuxrex/NetBeansProjects/analizador_lexico/src/main/java/analizador_lexico/img/about.png")); // NOI18N
        jMenu4.setText("Acerca de");
        jMenu4.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        jMenu4.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                jMenu4MenuSelected(evt);
            }
        });
        jMenu4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu4ActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void btn_Archivo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Archivo1ActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        int choice = fileChooser.showOpenDialog(this);
        if (choice == JFileChooser.APPROVE_OPTION) {
            try {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                String fileContent = archivo.chooseFile(filePath);
                path_file.setText(filePath);
                area_code.setText(fileContent);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


    }//GEN-LAST:event_btn_Archivo1ActionPerformed

    private void btn_save_ArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_save_ArchivoActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        int choice = fileChooser.showSaveDialog(this);
        if (choice == JFileChooser.APPROVE_OPTION) {
            try {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                String content = area_code.getText();
                archivo.saveFile(filePath, content);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }//GEN-LAST:event_btn_save_ArchivoActionPerformed

    private void go_analizerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_go_analizerActionPerformed
        //  tobbe1.setSelectedIndex(1);
    }//GEN-LAST:event_go_analizerActionPerformed

    private void jMenu4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu4ActionPerformed
        tobbe1.setSelectedIndex(0);
    }//GEN-LAST:event_jMenu4ActionPerformed

    private void jMenu4MenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_jMenu4MenuSelected
        tobbe1.setSelectedIndex(2);
    }//GEN-LAST:event_jMenu4MenuSelected

    private void go_analizerMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_go_analizerMenuSelected
        tobbe1.setSelectedIndex(0);
    }//GEN-LAST:event_go_analizerMenuSelected

    private void go_graphicsMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_go_graphicsMenuSelected
        tobbe1.setSelectedIndex(1);
    }//GEN-LAST:event_go_graphicsMenuSelected

    private void runActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runActionPerformed
        String textCode = area_code.getText();
        Simbolo dimv;
        analizador.analizar(textCode);
        limpiar();
        printTable();
        sy=new Sintaxis(Analizador.tokens);
        sy.declaracionVar();
        SymbolTable tabla=sy.getSybolTable();
        for (Simbolo simbolo : tabla.obtenerTodosSimbolos()) {
               System.out.println("Variable: Nombre= " + simbolo.getNombreVar()+
                       ", Tipo=" + simbolo.getTipoVar() +
                       ", Valor=" + simbolo.getValueVar());
        }
        area_code.setText("");
        pintar();
      tokens.clear();
     
      

    }//GEN-LAST:event_runActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        limpiar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void area_codeCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_area_codeCaretUpdate
        updateCursorPosition();
    }//GEN-LAST:event_area_codeCaretUpdate

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        sy.declaracionVar();
    }//GEN-LAST:event_jButton2ActionPerformed
    private void updateCursorPosition() {
        int caretPosition = area_code.getCaretPosition();
        int newLineNumber = area_code.getDocument().getDefaultRootElement().getElementIndex(caretPosition) + 1;

        if (!lineNumbers.contains(newLineNumber)) {
            lineNumbers.add(newLineNumber);
        }

        String lineNumbersText = buildLineNumbersText();
        lineNumberLabel.setText("<html>" + lineNumbersText.replace("\n", "<br>") + "</html>");

        position.setText("Position: " + newLineNumber + ", "
                + (caretPosition - area_code.getDocument().getDefaultRootElement().getElement(newLineNumber - 1).getStartOffset() + 1));
    }

    private String buildLineNumbersText() {
        StringBuilder sb = new StringBuilder();
        for (int lineNumber : lineNumbers) {
            sb.append(lineNumber).append("<br>");
        }
        return sb.toString();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane area_code;
    private javax.swing.JButton btn_Archivo1;
    private javax.swing.JButton btn_save_Archivo;
    private javax.swing.JPanel error_o_token;
    private javax.swing.JMenu go_analizer;
    private javax.swing.JMenu go_graphics;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lineNumberLabel;
    private javax.swing.JTextField path_file;
    private javax.swing.JLabel position;
    private javax.swing.JButton run;
    private javax.swing.JTable table_error_tokens;
    public javax.swing.JLabel title;
    private javax.swing.JTabbedPane tobbe1;
    // End of variables declaration//GEN-END:variables
}
