package by.bsuir.v27;

import by.bsuir.v27.smo.SMO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
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
            double A = 0;
            double K1 = 0, K2 = 0;
            double Lo = 0, Lc = 0;
            int numberOfSteps = 100000;
            for (int i = 0; i < numberOfSteps; i++) {
                smo.nextStep();
            }
            Map<by.bsuir.v27.smo.State, Integer> countStates = smo.getCountStates();
            Iterator<Map.Entry<by.bsuir.v27.smo.State, Integer>> iterator = countStates.entrySet().iterator();
            String result = "";
            while (iterator.hasNext()) {
                Map.Entry<by.bsuir.v27.smo.State, Integer> next = iterator.next();
                if (next.getValue() != 1) {
                    double P = (double) next.getValue() / numberOfSteps;
                    System.out.println("P" + next.getKey() + " = " + P);
                    result = result.concat("P" + next.getKey().toString() + " = " + P + "\n");
                    if (next.getKey().getChannel1State() == 1) {
                        K1 += P;
                    }
                    if (next.getKey().getChannel2State() == 1) {
                        K2 += P;
                    }
                    if (next.getKey().getQueueState() > 0) {
                        Lo += P * next.getKey().getQueueState();
                    }
                    int count = 0;
                    count += next.getKey().getChannel1State();
                    count += next.getKey().getQueueState();
                    count += next.getKey().getChannel2State();
                    if (count > 0) {
                        Lc += P * count;
                    }
                }
            }
            A = K2 * (1 - pi2);
            System.out.println("K1 " + K1);
            System.out.println("K2 " + K2);
            System.out.println("A " + A);
            System.out.println("Lo " + Lo);
            System.out.println("Lc " + Lc);

            double pBlock = 0;
            double pRej = (double) smo.getRejectedCount() / numberOfSteps;
            //  System.out.println(pRej);
//            double Q = 1 - pRej;
//            double W1 = 1 / (1 - p1);
//            double W2 = (A2 / (A2 + A3)) / (1 - p2);
//            double W3 = (A3 / (A2 + A3)) / (1 - p3);
//            double Wc = W1 + W2 + W3;
//            result += "Pотк=" + pRej + "\n" + "Pблок=" + pBlock + "\n" + "Qотн=" + Q + "\n" + "A=" + A + "\n" + "Wc=" + Wc + "\n" +
//                    "K1=" + K1 + "\n" + "K2=" + K2 + "\n" + "K3=" + K3 + "\n";
            resultField.setText(result);
        });
    }
}