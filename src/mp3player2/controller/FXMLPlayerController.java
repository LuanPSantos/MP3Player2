 package mp3player2.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import mp3player2.model.Musica;

public class FXMLPlayerController implements Initializable {
    
    @FXML MediaView mediaView;
    @FXML Button ButtonPlayPause;
    @FXML Label labelNomeMusica;
    @FXML Label labelNomeArtista;
    @FXML ImageView imageViewArteAlbum;
    @FXML Slider sliderValume;
    @FXML ProgressBar progressBarTempoMusica;
    @FXML Label labelTempo;
    
    //===
    private Musica musicaAtual;
    private int musicaPos = 0;
    private ArquivosController arq;
    private boolean aleatorioOn = false;
    private final List<Integer> historico = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        arq = new ArquivosController();
        
        Random r = new Random();
        musicaPos = r.nextInt(arq.quantidadeMusicas());
        setMusica();
    }    
    
    public void playPause(){  
        if(mediaView.getMediaPlayer().getStatus().equals(Status.PLAYING)){
            mediaView.getMediaPlayer().pause();
            ButtonPlayPause.setText("Play");
        }else {
            mediaView.getMediaPlayer().play();
            ButtonPlayPause.setText("Pause");
        }
    }

    public void musicaSeguinte(){
        historico.add(musicaPos);
        
        if(aleatorioOn){
            Random r = new Random();
            musicaPos = r.nextInt(arq.quantidadeMusicas());
        }else{
            musicaPos++;    
        } 
        
        trocarMusica();
    }
    
    public void musicaAnterior(){
        if(historico.size() > 0){
            musicaPos = historico.get(historico.size() - 1);
            historico.remove(historico.size() - 1); 
            trocarMusica();
        }
    }
    
    private void trocarMusica(){
        mediaView.getMediaPlayer().dispose();
        mediaView.setMediaPlayer(null);
        setMusica();
        mediaView.getMediaPlayer().setAutoPlay(true);
    }
    
    private void setMusica(){
        musicaAtual = new Musica(arq.getURLMusica(musicaPos));        
        
        musicaAtual.getMediaPlayer().setOnReady(() ->{  
            musicaAtual.setTitulo((String) musicaAtual.getMediaPlayer().getMedia().getMetadata().get("title"));
            musicaAtual.setArtista((String) musicaAtual.getMediaPlayer().getMedia().getMetadata().get("artist"));
            musicaAtual.setArteAlbum((Image) musicaAtual.getMediaPlayer().getMedia().getMetadata().get("image"));
            musicaAtual.setDuracao(musicaAtual.getMediaPlayer().getTotalDuration().toSeconds());
            
            labelNomeMusica.setText(musicaAtual.getTitulo());
            labelNomeArtista.setText(musicaAtual.getArtista());
            imageViewArteAlbum.setImage(musicaAtual.getArteAlbum());   
        });
        
        musicaAtual.getMediaPlayer().setOnPlaying(() ->{            
            musicaAtual.getMediaPlayer().currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {            
                    progressBarTempoMusica.setProgress(mediaView.getMediaPlayer().getCurrentTime().toSeconds() / musicaAtual.getDuracao());
                    labelTempo.setText(calcularTempo(mediaView.getMediaPlayer().getCurrentTime().toSeconds()));
                }
            });
        });
        
        musicaAtual.getMediaPlayer().setOnEndOfMedia(() ->{
            musicaAtual.getMediaPlayer().stop();
            musicaAtual.getMediaPlayer().dispose();
            musicaSeguinte();
        });
        
        mediaView.setMediaPlayer(musicaAtual.getMediaPlayer());
        mediaView.getMediaPlayer().setVolume(sliderValume.getValue());
    }
    
    public void switchAleatorio(){
        aleatorioOn = !aleatorioOn;
    }
    
    public void setVolume(){
        mediaView.getMediaPlayer().setVolume(sliderValume.getValue());
    }
    
    private String calcularTempo(double duracao){
        int min = (int) Math.floor(duracao / 60);
        int seg = (int) duracao % 60;
        String m = "00", s = "00";
        
        if(min < 10){
            m = "0" + min;
        }else{
            m = String.valueOf(min);
        }
        
        if(seg < 10){
            s = "0" + seg;
        }else{
            s = String.valueOf(seg);
        }
        
        return  m + ":" + s;
    }
}
