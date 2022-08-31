package fengruijie.code;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public enum Audio {
    BGM("bgm.wav"),
    GameOver("lose.wav"),
    Win("win.wav");

    public Clip clip;

    Audio(String name) {
        AudioInputStream stream;
        AudioFormat format;
        DataLine.Info info;

        try {
            stream = AudioSystem.getAudioInputStream(
                    Objects.requireNonNull(
                            Audio.class.getClassLoader().getResource(
                                    Constant.RESOURCES_PATH + name)));
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}