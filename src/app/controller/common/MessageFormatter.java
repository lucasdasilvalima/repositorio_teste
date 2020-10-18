package app.controller.common;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public abstract class MessageFormatter {

    public static TextFormatWrapper format(String from, String content, String date, Color color) {
        Text t1 = new Text("\n" + from);
        Text t2 = new Text(" " + date + ": \n");
        t1.setFill(color);
        t2.setFill(Color.gray(.4));
        t1.setStyle("-fx-font-weight: bold");
        Text t3 = new Text(content + "\n");
        t3.setFill(color);
        return new TextFormatWrapper(t1, t2, t3);
    }

    public static TextFormatWrapper status(String from, String content) {
        Text t1 = new Text("\n" + from + " ");
        t1.setFill(Color.gray(.4));
        t1.setStyle("-fx-font-weight: bold");
        Text t2 = new Text(content + "\n");
        t2.setFill(Color.gray(.4));
        t2.setStyle("-fx-font-weight: bold");
        return new TextFormatWrapper(t1, null, t2);
    }

    public static  TextFormatWrapper format(String from, String date, String content) {
        return format(from, content, date, Color.gray(.8));
    }

}
