package com.example.dell.wx;

public class paper {
    private final  String author;
    private final  String gender;
    private  final int  age;

    public String getAuthor() {
        return author;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    private paper(Builder builder) {
        author = builder.author;
        gender = builder.gender;
        age = builder.age;

    }

    public static final class Builder {
        private String author;
        private String gender;
        private int age;

        public Builder() {
        }

        public Builder author(String val) {
            author = val;
            return this;
        }

        public Builder gender(String val) {
            gender = val;
            return this;
        }

        public Builder age(int val) {
            age = val;
            return this;
        }

        public paper build() {
            return new paper(this);
        }
    }
}
