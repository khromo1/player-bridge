package streaming.player;

public abstract class MediaPlayer {
    protected AudioOutput output;
    protected MediaPlayer(AudioOutput output) {
        this.output = output;
    }
    public abstract  void play(String title);
    public void setOutput(AudioOutput output) {
        this.output = output;
    }
}
