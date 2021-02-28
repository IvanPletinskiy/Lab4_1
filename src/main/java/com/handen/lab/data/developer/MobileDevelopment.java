package com.handen.lab.data.developer;

class MobileDevelopment extends Developer {
    @Override
    public String getPositionTitle() {
        return "Mobile Developer";
    }

    @Override
    String doDevelopment() {
        return "This is mobile development";
    }
}
