import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.util.Random;

public class AudioManager {

    private Clip musicClip;
    private String currentPath;

    private String[] playlist;
    private int currentSongIndex;

    private boolean playlistMode;
    private Random random;

    public AudioManager() {
        musicClip = null;
        currentPath = "";

        playlist = null;
        currentSongIndex = -1;

        playlistMode = false;

        random = new Random();
    }

    public void playLoop(String path) {
        if (path == null || path.length() == 0) {
            return;
        }

        playlistMode = false;
        playlist = null;
        currentSongIndex = -1;

        if (musicClip != null
                && musicClip.isRunning()
                && path.equals(currentPath)) {

            return;
        }

        closeCurrentMusic();

        try {
            File audioFile =
                    new File(path);

            if (!audioFile.exists()) {
                System.out.println(
                        "Audio file not found: "
                                + path
                );

                return;
            }

            AudioInputStream audioStream =
                    AudioSystem.getAudioInputStream(
                            audioFile
                    );

            musicClip =
                    AudioSystem.getClip();

            musicClip.open(
                    audioStream
            );

            audioStream.close();

            currentPath = path;

            musicClip.loop(
                    Clip.LOOP_CONTINUOUSLY
            );

            musicClip.start();

        } catch (Exception e) {
            System.out.println(
                    "Could not play music: "
                            + path
            );

            System.out.println(
                    e.getMessage()
            );
        }
    }

    public void startRandomPlaylist(
            String[] songs) {

        if (songs == null
                || songs.length == 0) {

            return;
        }

        stopMusic();

        playlist =
                new String[songs.length];

        for (int i = 0;
             i < songs.length;
             i++) {

            playlist[i] =
                    songs[i];
        }

        playlistMode = true;

        currentSongIndex = -1;

        playRandomSong();
    }

    public void playRandomSong() {
        if (!playlistMode
                || playlist == null
                || playlist.length == 0) {

            return;
        }

        int nextIndex;

        if (playlist.length == 1) {
            nextIndex = 0;

        } else {
            do {
                nextIndex =
                        random.nextInt(
                                playlist.length
                        );

            } while (nextIndex
                    == currentSongIndex);
        }

        playPlaylistSong(
                nextIndex
        );
    }

    public void nextSong() {
        if (!playlistMode) {
            return;
        }

        playRandomSong();
    }

    private void playPlaylistSong(
            int index) {

        if (playlist == null
                || index < 0
                || index >= playlist.length) {

            return;
        }

        closeCurrentMusic();

        String path =
                playlist[index];

        try {
            File audioFile =
                    new File(path);

            if (!audioFile.exists()) {
                System.out.println(
                        "Audio file not found: "
                                + path
                );

                return;
            }

            AudioInputStream audioStream =
                    AudioSystem.getAudioInputStream(
                            audioFile
                    );

            Clip newClip =
                    AudioSystem.getClip();

            newClip.open(
                    audioStream
            );

            audioStream.close();

            musicClip = newClip;
            currentSongIndex = index;
            currentPath = path;

            newClip.addLineListener(
                    event -> {

                        if (event.getType()
                                == LineEvent.Type.STOP) {

                            Clip finishedClip =
                                    (Clip) event.getLine();

                            boolean finishedNaturally =
                                    finishedClip.getFrameLength() > 0
                                            && finishedClip.getFramePosition()
                                            >= finishedClip.getFrameLength() - 1;

                            if (playlistMode
                                    && finishedNaturally
                                    && finishedClip
                                    == musicClip) {

                                SwingUtilities.invokeLater(
                                        () ->
                                                playRandomSong()
                                );
                            }
                        }
                    }
            );

            newClip.start();

        } catch (Exception e) {
            System.out.println(
                    "Could not play playlist song: "
                            + path
            );

            System.out.println(
                    e.getMessage()
            );
        }
    }

    public void pauseMusic() {
        if (musicClip != null
                && musicClip.isRunning()) {

            musicClip.stop();
        }
    }

    public void resumeMusic() {
        if (musicClip == null
                || musicClip.isRunning()) {

            return;
        }

        if (playlistMode) {
            musicClip.start();

        } else {
            musicClip.loop(
                    Clip.LOOP_CONTINUOUSLY
            );

            musicClip.start();
        }
    }

    public void stopMusic() {
        playlistMode = false;
        playlist = null;
        currentSongIndex = -1;

        closeCurrentMusic();
    }

    private void closeCurrentMusic() {
        if (musicClip != null) {
            musicClip.stop();
            musicClip.close();
            musicClip = null;
        }

        currentPath = "";
    }

    public void stop() {
        stopMusic();
    }

    public boolean isPlaying() {
        return musicClip != null
                && musicClip.isRunning();
    }

    public boolean isPlaylistMode() {
        return playlistMode;
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public String getCurrentSongName() {
        if (currentPath == null
                || currentPath.length() == 0) {

            return "";
        }

        return new File(
                currentPath
        ).getName();
    }
}
