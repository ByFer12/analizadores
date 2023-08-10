/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizador_lexico.utils;

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
    public void chooseFile(JFileChooser file) {
        File fichero=file.getSelectedFile();
        path.setText(fichero.getAbsolutePath());
        
        try(FileReader read = new FileReader(fichero)){
            int valor =read.read();
            while(valor!=-1){
                texto+=(char)valor;
                valor=read.read();
            }
            
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error al abrir el archivo");
            
        }    
        
    }

    @Override
    public void saveFile(JFileChooser file) {
       File fichero=file.getSelectedFile();
       try(FileWriter write = new FileWriter(fichero)){
           write.write(area.getText());
       }catch(IOException e){
           JOptionPane.showMessageDialog(null, "Error al guardar el archivo");
       }
       area.setText("");
    }
    
}
