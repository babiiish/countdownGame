import controller.CountdownController;
import model.CountdownModel;
import view.CountdownView;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        CountdownModel countdownModel = new CountdownModel();
        CountdownView countdownView = new CountdownView();
        CountdownController countdownController = new CountdownController(countdownModel, countdownView);

        countdownController.playGame();
    }
}