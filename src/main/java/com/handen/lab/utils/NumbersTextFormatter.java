package com.handen.lab.utils;

import javafx.scene.control.TextFormatter;

public class NumbersTextFormatter extends TextFormatter<TextFormatter.Change> {

    public NumbersTextFormatter(int maxNumbers) {
        super(change -> {
            if(!change.isContentChange()) {
                return change;
            }

            String text = change.getControlNewText();

            if(!isValid(text, maxNumbers)) {
                return null;
            }
            return change;
        });
    }

    private static boolean isValid(String s, int maxNumbers) {
        boolean isValid = true;
        for(char c : s.toCharArray()) {
            isValid = (c >= '0' && c <= '9');
            if(!isValid) {
                break;
            }
        }

        return isValid && s.length() <= maxNumbers;
    }
}
