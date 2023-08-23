/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package analizador_lexico.utils;

import java.io.IOException;
import javax.swing.JFileChooser;


public interface IArchive {
    
    
    String chooseFile(String filePath)throws IOException;
    
    void saveFile(String filePath, String content) throws IOException;
    
    
}
