package streaming.player;

public class Client {
    public static void main (String[] args) {
        MediaPlayer player = new StandardPlayer(new BluetoothOutput());
        player.play("The Less I Know The Better");
        player.setOutput(new SpeakerOutput());
        player.play("The Less I Know The Better");

        PodcastPlayer podcast = new PodcastPlayer(new BluetoothOutput());
        podcast.pauseAt(1230);
        podcast.play("Успешный успех");
        podcast.setOutput(new SpeakerOutput());
        podcast.play("Успешный успех");
    }
}
