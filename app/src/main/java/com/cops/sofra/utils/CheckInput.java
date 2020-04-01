package com.cops.sofra.utils;

import android.app.Activity;
import android.app.Application;
import android.content.res.Resources;
import android.widget.EditText;

import com.cops.sofra.R;
import com.cops.sofra.ui.BaseActivity;
import com.google.android.material.textfield.TextInputLayout;
;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CheckInput {

    private static Pattern p;
    private static Matcher m;
    private static Activity activity;

    public CheckInput(Activity activity) {
        this.activity=activity;
    }

    public static boolean isEmailValid(EditText email){

         Pattern p = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
         Matcher m = p.matcher(email.getText().toString());

        if(m.matches()){
            return true;

        } else {

            email.setError(activity.getString(R.string.email_not_valid));
            email.requestFocus();
            return false;
        }
    }

    public static boolean isPasswordMatched(EditText password,EditText cPassword){


        if(password.getText().toString().equals(cPassword.getText().toString())){
            return true;


        } else {
            password.setError(activity.getString(R.string.password_not_matched));
            cPassword.setError(activity.getString(R.string.password_not_matched));
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
            phone.setError(activity.getString(R.string.correct_phone));
            phone.requestFocus();
            return false;
        }
    }

    public static boolean isEditTextSet(TextInputLayout textInputLayout, TextInputLayout textInputLayout1){
        if(textInputLayout.getEditText().getText().toString().equals("")){
            textInputLayout.setError(activity.getString(R.string.required_field));
            textInputLayout.requestFocus();
            return false;

        } else if(textInputLayout1.getEditText().getText().toString().equals("")){
            textInputLayout1.setError(activity.getString(R.string.required_field));
            textInputLayout1.requestFocus();
            return false;
        }else {

            return true;
        }
    }
    public static boolean isEditTextSet(EditText email,EditText password){
        if(email.getText().toString().equals("")){
            email.setError(activity.getString(R.string.email));
            email.requestFocus();
            return false;

        } else if(password.getText().toString().equals("")){
            password.setError(activity.getString(R.string.password));
            password.requestFocus();
            return false;
        }else {

            return true;
        }
    }
    public static boolean isEditTextSet(EditText editText1,EditText editText2,EditText editText3){
        if(editText1.getText().toString().equals("")){
            editText1.setError(activity.getString(R.string.required_field));
            editText1.requestFocus();
            return false;

        } else if(editText2.getText().toString().equals("")){
            editText2.setError(activity.getString(R.string.required_field));
            editText2.requestFocus();
            return false;
        } else if(editText3.getText().toString().equals("")){
            editText3.setError(activity.getString(R.string.required_field));
            editText3.requestFocus();
            return false;
        }else {

            return true;
        }
    }

    public static boolean isEditTextSet(EditText editText, EditText editText2,
                                        EditText editText3,EditText editText4){
        if(editText.getText().toString().equals("")) {
            editText.setError(activity.getString(R.string.required_field));
            editText.requestFocus();
            return false;
        }else if(editText2.getText().toString().equals("")) {
            editText2.setError(activity.getString(R.string.required_field));
            editText2.requestFocus();
            return false;
        }else if(editText3.getText().toString().equals("")){
            editText3.setError(activity.getString(R.string.required_field));
            editText3.requestFocus();
            return false;
        }else if(editText4.getText().toString().equals("")){
            editText4.setError(activity.getString(R.string.required_field));
            editText4.requestFocus();
            return false;
        }else {
            return true;
        }
    }

    public static boolean isEditTextSet(EditText editText, EditText editText2,
                                        EditText editText3,EditText editText4,
                                        EditText editText5){
        if(editText.getText().toString().equals("")) {
            editText.setError(activity.getString(R.string.required_field));
            editText.requestFocus();
            return false;
        }else if(editText2.getText().toString().equals("")) {
            editText2.setError(activity.getString(R.string.required_field));
            editText2.requestFocus();
            return false;
        }else if(editText3.getText().toString().equals("")){
            editText3.setError(activity.getString(R.string.required_field));
            editText3.requestFocus();
            return false;
        }else if(editText4.getText().toString().equals("")){
            editText4.setError(activity.getString(R.string.required_field));
            editText4.requestFocus();
            return false;
        }else if(editText5.getText().toString().equals("")) {
            editText5.setError(activity.getString(R.string.required_field));
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
            editText.setError(activity.getString(R.string.required_field));
            editText.requestFocus();
            return false;
        }else if(editText2.getText().toString().equals("")) {
            editText2.setError(activity.getString(R.string.required_field));
            editText2.requestFocus();
            return false;
        }else if(editText3.getText().toString().equals("")){
            editText3.setError(activity.getString(R.string.required_field));
            editText3.requestFocus();
            return false;
        }else if(editText4.getText().toString().equals("")){
            editText4.setError(activity.getString(R.string.required_field));
            editText4.requestFocus();
            return false;
        }else if(editText5.getText().toString().equals("")) {
            editText5.setError(activity.getString(R.string.required_field));
            editText5.requestFocus();
            return false;
        }else if(editText6.getText().toString().equals("")) {
            editText6.setError(activity.getString(R.string.required_field));
            editText6.requestFocus();
            return false;
        }else if(editText7.getText().toString().equals("")){
                editText7.setError(activity.getString(R.string.required_field));
                editText7.requestFocus();
                return false;

        }else {
            return true;
        }
    }
}
