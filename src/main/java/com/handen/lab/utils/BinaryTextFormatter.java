package com.handen.lab.utils;

import javafx.scene.control.TextFormatter;

public class BinaryTextFormatter extends TextFormatter<TextFormatter.Change> {
    public BinaryTextFormatter() {
        super(change -> {
            if(!change.isContentChange()) {
                return change;
            }

            String text = change.getControlNewText();

            if(!isValid(text)) {
                return null;
            }
            return change;
        });
    }

    private static boolean isValid(String s) {
        boolean isValid = true;
        for(char c : s.toCharArray()) {
            isValid = (c == '0' || c == '1');
            if(!isValid) {
                break;
            }
        }

        return isValid;
    }
}
