package com.handen.lab.data.developer;

class FrontendDeveloper extends Developer {
    @Override
    public String getPositionTitle() {
        return "Frontend Developer";
    }

    @Override
    String doDevelopment() {
        return "This is frontend development";
    }
}
