/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizador_lexico.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author tuxrex
 */
public class Archive implements IArchive{
    private String texto="";
    private JTextField path;
    private JTextArea area;

    public JTextArea getArea() {
        return area;
    }

    public void setArea(JTextArea area) {
        this.area = area;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public JTextField getPath() {
        return path;
    }

    public void setPath(JTextField path) {
        this.path = path;
    }

    @Override
    public String chooseFile(String filePath)throws IOException{
       StringBuilder content = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
    }
    return content.toString();
        
    }

    @Override
    public void saveFile(String filePath, String content) throws IOException{
          try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        writer.write(content);
    }
    }
    
}
