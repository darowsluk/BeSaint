package org.sds.besaint;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
    }

    // TODO: Check parent - if selected from options menu return to running activity
    public void onOKClick(View view) {
        // Save new name to SharedPreferences as value key pair
        EditText editText = (EditText) findViewById(R.id.id_txtNameEdit);
        String userName = editText.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences(DataConstants.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DataConstants.KEY_USERNAME, userName);
        editor.commit();

        Intent intent = new Intent(this, RunningActivity.class);
        startActivity(intent);
    }
}
