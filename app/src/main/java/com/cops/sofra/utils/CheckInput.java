package com.cops.sofra.utils;

import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckInput {

    private static Pattern p;
    private static Matcher m;

    public static boolean isEmailValid(EditText email){

         Pattern p = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
         Matcher m = p.matcher(email.getText().toString());

        if(m.matches()){
            return true;

        } else {

            email.setError("Please Enter a Valid Email Address");
            email.requestFocus();
            return false;
        }
    }

    public static boolean isPasswordMatched(EditText password,EditText cPassword){


        if(password.getText().toString().equals(cPassword.getText().toString())){
            return true;


        } else {
            password.setError("Password not matched");
            cPassword.setError("Password not matched");
            password.requestFocus();

            return false;
        }
    }

    public static boolean isPhoneSet(EditText phone){
        p=Pattern.compile("[0-9]{11}");
        m=p.matcher(phone.getText().toString());
        if (m.matches()) {
            return true;
        }else {
            phone.setError("Please Enter a Correct Phone Number");
            phone.requestFocus();
            return false;
        }
    }

    public static boolean isEditTextSet(EditText email,EditText password){
        if(email.getText().toString().equals("")){
            email.setError("Please Enter your Email");
            email.requestFocus();
            return false;

        } else if(password.getText().toString().equals("")){
            password.setError("Please Enter your Password");
            password.requestFocus();
            return false;
        }else {

            return true;
        }
    }

    public static boolean isEditTextSet(EditText editText, EditText editText2,
                                        EditText editText3,EditText editText4,
                                        EditText editText5){
        if(editText.getText().toString().equals("")) {
            editText.setError("This Field is Required");
            editText.requestFocus();
            return false;
        }else if(editText2.getText().toString().equals("")) {
            editText2.setError("This Field is Required");
            editText2.requestFocus();
            return false;
        }else if(editText3.getText().toString().equals("")){
            editText3.setError("This Field is Required");
            editText3.requestFocus();
            return false;
        }else if(editText4.getText().toString().equals("")){
            editText4.setError("This Field is Required");
            editText4.requestFocus();
            return false;
        }else if(editText5.getText().toString().equals("")) {
            editText5.setError("This Field is Required");
            editText5.requestFocus();
            return false;
        }else {
            return true;
        }
    }

    public static boolean isEditTextSet(EditText editText, EditText editText2,
                                        EditText editText3,EditText editText4,
                                        EditText editText5, EditText editText6,EditText editText7){
        if(editText.getText().toString().equals("")) {
            editText.setError("This Field is Required");
            editText.requestFocus();
            return false;
        }else if(editText2.getText().toString().equals("")) {
            editText2.setError("This Field is Required");
            editText2.requestFocus();
            return false;
        }else if(editText3.getText().toString().equals("")){
            editText3.setError("This Field is Required");
            editText3.requestFocus();
            return false;
        }else if(editText4.getText().toString().equals("")){
            editText4.setError("This Field is Required");
            editText4.requestFocus();
            return false;
        }else if(editText5.getText().toString().equals("")) {
            editText5.setError("This Field is Required");
            editText5.requestFocus();
            return false;
        }else if(editText6.getText().toString().equals("")) {
            editText6.setError("This Field is Required");
            editText6.requestFocus();
            return false;
        }else if(editText7.getText().toString().equals("")){
                editText7.setError("This Field is Required");
                editText7.requestFocus();
                return false;

        }else {
            return true;
        }
    }
}
