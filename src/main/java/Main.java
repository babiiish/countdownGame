import controller.CountdownController;
import model.LetterModel;
import model.PlayerModel;
import model.WordModel;
import view.CountdownView;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        LetterModel letterModel;
        WordModel wordModel;
        PlayerModel playerModel;
        String wordListPath = "src/main/resources/words_alpha.txt";

        letterModel = new LetterModel();
        wordModel = new WordModel(wordListPath);
        playerModel = new PlayerModel("", 0, 0);

        CountdownView countdownView = new CountdownView();
        CountdownController countdownController = new CountdownController(letterModel, wordModel, playerModel, countdownView);

        countdownController.playGame();
    }
}