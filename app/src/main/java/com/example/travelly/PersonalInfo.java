package com.example.travelly;
import android.os.Parcel;
import android.os.Parcelable;

public class PersonalInfo implements Parcelable {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    public PersonalInfo(String firstName, String lastName, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    protected PersonalInfo(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        phone = in.readString();
        email = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(phone);
        dest.writeString(email);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PersonalInfo> CREATOR = new Creator<PersonalInfo>() {
        @Override
        public PersonalInfo createFromParcel(Parcel in) {
            return new PersonalInfo(in);
        }

        @Override
        public PersonalInfo[] newArray(int size) {
            return new PersonalInfo[size];
        }
    };
}

