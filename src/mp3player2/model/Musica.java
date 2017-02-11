package mp3player2.model;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Musica {
    private String titulo = "Desconhecido";
    private String artista = "Desconhecido";
    private Image arteAlbum = new Image("mp3player2/imagens/nota.jpg");
    private final Media media;
    private final MediaPlayer player;
    private double duracao = 0;
    
    public Musica(String url){
        this.media = new Media(url);
        this.player = new MediaPlayer(this.media);
    }

    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo){
        if(titulo != null || !"".equals(titulo))
            this.titulo = titulo;
    }

    public String getArtista() {
        return artista;
    }
    
    public void setArtista(String artista){
        if(artista != null || !"".equals(artista))
            this.artista = artista;
    }
    
    public Image getArteAlbum(){
        return arteAlbum;
    }
    
    public void setArteAlbum(Image arteAlbum){
        if(arteAlbum != null)
            this.arteAlbum = arteAlbum;
    }
    
    public MediaPlayer getMediaPlayer(){
        return this.player;
    }
    
    public double getDuracao(){
        return duracao;
    }
    
    public void setDuracao(double duracao){
        this.duracao = duracao;
    }
}
