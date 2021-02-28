package com.handen.lab.data.developer;

class BackendDeveloper extends Developer {
    @Override
    public String getPositionTitle() {
        return "Backend Developer";
    }

    @Override
    String doDevelopment() {
        return "This is backend development";
    }
}
