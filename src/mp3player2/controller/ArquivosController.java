package mp3player2.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArquivosController {
    private final String caminho = "C:/Users/santo/Music";
    private final List<String> urlMusicas = new ArrayList<>();
    
    public ArquivosController(){  
        lerDiretorio(caminho);
    }
    
    private void lerDiretorio(String caminho){
        File arquivo = new File(caminho);
        File[] arquivos = arquivo.listFiles();
        
        for(File item : arquivos){
            if(item.isDirectory()){
                lerDiretorio(caminho + "/" + item.getName());
            }else if(item.isFile() && item.getName().indexOf(".mp3") == item.getName().length() - 4){
                try {
                    String caminhoCompleto = "file:///" + URLEncoder.encode(item.getAbsolutePath(), "UTF-8").replace("+", "%20");
                    urlMusicas.add(caminhoCompleto);                    
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(ArquivosController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }            
        }
    }
    
    public int quantidadeMusicas(){
        return urlMusicas.size();
    }
    
    public String getURLMusica(int i){
        return urlMusicas.get(i);
    }
}
