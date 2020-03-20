package com.handen.lab.utils;

import javafx.scene.control.TextFormatter;

public class LettersTextFormatter extends TextFormatter<TextFormatter.Change> {

    public LettersTextFormatter() {
        super(change -> {
            if(!change.isContentChange()) {
                return change;
            }

            String text = change.getControlNewText();

            if(isValid(text)) {
                return null;
            }
            return change;
        });
    }

    private static boolean isValid(String s) {
        boolean isValid = true;
        for(char c : s.toCharArray()) {
            isValid = (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
            if(!isValid) {
                break;
            }
        }
        return isValid;
    }
}