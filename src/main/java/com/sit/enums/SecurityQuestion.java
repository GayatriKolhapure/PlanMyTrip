package com.sit.enums;

public enum SecurityQuestion {

    PET_NAME("What is your pet name?"),
    SCHOOL_NAME("What is your school name?"),
    FAVORITE_COLOR("What is your favorite color?"),
    BIRTH_CITY("In which city were you born?"),
    BEST_FRIEND("What is your best friend's name?");

    private final String question;

    SecurityQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }
}
