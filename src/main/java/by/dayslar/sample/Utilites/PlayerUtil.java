package by.dayslar.sample.Utilites;

import uk.co.caprica.vlcj.binding.internal.libvlc_state_t;
import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.runtime.x.LibXUtil;
import uk.co.caprica.vlcj.version.LibVlcVersion;

public class PlayerUtil {

    private static MediaPlayer mediaPlayer;

    //метод обьеденяющий все инициализации в одном месте
    public static void initialize(){
        initializeMediaLibrary();
        initializeMediaPlayer();
    }

    //инициализация библиотеки
    private static void initializeMediaLibrary() {
        new NativeDiscovery().discover();
        try {
            LibVlcVersion.getVersion();
        } catch (Exception e) {
            System.out.println("Не получилось");
        }

        LibXUtil.initialise();
    }

    //инициализация медиа плеера
    private static void initializeMediaPlayer(){
            mediaPlayer = new AudioMediaPlayerComponent().getMediaPlayer();
    }

    //метод запускае проигрывание
    public static void play(String filePatch){
            if (mediaPlayer != null)
                mediaPlayer.playMedia(filePatch);
    }

    //метод останавливает проигрование
    public static void stop(){
        if (mediaPlayer != null)
            mediaPlayer.stop();
    }

    //метод ставит проигрывание на паузу
    public static void pause(){
        if (mediaPlayer != null)
            mediaPlayer.pause();
    }

    //запуск проигрывания
    public static void start(){
        if (mediaPlayer != null)
            mediaPlayer.start();
    }

    //проверка играет что-нибудь, или нет сейчас
    public static boolean isPlaying(){
        return (mediaPlayer != null && mediaPlayer.getMediaState() == libvlc_state_t.libvlc_Playing);
    }

    //метод для перемотки записи принимает параметр в милисикундах на сколько необходимо промотать запись
    public static void rewindMedia(long size) {
        if (mediaPlayer != null)
            mediaPlayer.skip(size);
    }

    //вещаем свой слушатель для плеера
    public static void addListener(MediaPlayerEventAdapter adapter){
        mediaPlayer.addMediaPlayerEventListener(adapter);
    }

    //освобождение ресурсов занятых плеером
    public static void releasePlayer(){
        if (mediaPlayer != null)
            mediaPlayer.release();
    }

    //возвращает актуальный медиплеер
    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    //возвращает время в формате {00:00:00}
    public static String getCalculateTime(long time){
        int second =  (int) time % 60;
        int minutes = (int) time /60 % 60;
        int hours = (int) time / 3600;

        String formatSecond = (second / 10 < 1)?"0" + second: second + "";
        String formatMinutes = (minutes / 10 < 1)?"0" + minutes: minutes + "";
        String formatHours = (hours / 10 < 1)?"0" + hours: hours + "";

        return (formatHours + ":" + formatMinutes + ":" + formatSecond);
    }
}
