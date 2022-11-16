package by.bsuir.v27;

import by.bsuir.v27.smo.SMO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label incorrectLabel;

    @FXML
    private TextField p1Field;

    @FXML
    private TextField p2Field;


    @FXML
    private Button calculateButton;

    @FXML
    private Label resultField;

    @FXML
    void initialize() {

        calculateButton.setOnAction(event -> {
            double pi1 = 0;
            double pi2 = 0;
            try {
                pi1 = Double.parseDouble(p1Field.getText());
                pi2 = Double.parseDouble(p2Field.getText());
                incorrectLabel.setText("");
            } catch (NumberFormatException e) {
                incorrectLabel.setText("Некорректный ввод");
            }
            SMO smo = new SMO(pi1, pi2, 2);
            double K1 = 0, K2 = 0;
            double Lo = 0, Lc = 0;
            int numberOfSteps = 100000;
            for (int i = 0; i < numberOfSteps; i++) {
                smo.nextStep();
            }
            Map<State, Integer> countStates = smo.getCountStates();
            Iterator<Map.Entry<State, Integer>> iterator = countStates.entrySet().iterator();
            String result = "";
            while (iterator.hasNext()) {
                Map.Entry<State, Integer> next = iterator.next();
                if (next.getValue() != 1) {
                    double P = (double) next.getValue() / numberOfSteps;
                    System.out.println("P" + next.getKey() + " = " + P);
                    result = result.concat("P" + next.getKey().toString() + " = " + P + "\n");
                }
            }
            double A = (double) smo.getFinishCount() / numberOfSteps;
            K1 = (double) smo.getChanel1State() / numberOfSteps;
            K2 = (double) smo.getChanel2State() / numberOfSteps;
            Lo = (double) smo.getQueueLength() / numberOfSteps;
            Lc = (double) smo.getSystemLength() / numberOfSteps;
            System.out.println("K1 " + K1);
            System.out.println("K2 " + K2);
            System.out.println("A " + A);
            System.out.println("Lo " + Lo);
            System.out.println("Lc " + Lc);

            double pBlock = 0;
            double pRej = (double) smo.getRejectedCount() / smo.getSourceGeneratedCount();
            double Q = (double) smo.getFinishCount() / smo.getSourceGeneratedCount();
            System.out.println("pBlock " + pBlock);
            System.out.println("pRej " + pRej);
            System.out.println("Q  " + Q);
            double Wo = Lo / A;
            double Wc = 1 / (1 - pi1) + (Lo + K2) / A;
            System.out.println("Wo  " + Wo);
            System.out.println("Wc  " + Wc);
            result += "Pотк=" + pRej + "\n" + "Pблок=" + pBlock + "\n" + "Qотн=" + Q + "\n" + "A=" + A + "\n" + "Wo=" + Wo + "\n" + "Wc=" + Wc + "\n" +
                    "Lo=" + Lo + "\n" + "Lc=" + Lc + "\nK1=" + K1 + "\n" + "K2=" + K2 + "\n";
            resultField.setText(result);
        });
    }
}