package app.controller.common;

import javafx.scene.text.Text;

public class TextFormatWrapper {
    public final Text from;
    public final Text date;
    public final Text text;
    protected TextFormatWrapper(Text from, Text date, Text text) {
        this.from = from;
        this.date = date;
        this.text = text;
    }
}
