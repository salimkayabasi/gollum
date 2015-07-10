package com.github.celepharn.gollum.example.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {

    public static final Parcelable.Creator<Student> CREATOR
            = new Parcelable.Creator<Student>() {


        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
    private int id;
    private String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }


    private Student(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
