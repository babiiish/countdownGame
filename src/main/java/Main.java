import controller.CountdownController;
import model.CountdownModel;
import view.CountdownView;
import java.util.HashSet;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        CountdownModel countdownModel;
        countdownModel = new CountdownModel(new HashSet<>());
        CountdownView countdownView = new CountdownView();
        CountdownController countdownController = new CountdownController(countdownModel, countdownView);

        countdownController.playGame();
    }
}