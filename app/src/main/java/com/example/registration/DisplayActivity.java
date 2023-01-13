package com.example.registration;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.registration.databinding.ActivityDisplayBinding;
import com.example.registration.databinding.ActivityMainBinding;

public class DisplayActivity extends AppCompatActivity {
    ActivityDisplayBinding binding;

    private TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        binding = ActivityDisplayBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        String time = getIntent().getStringExtra("keyDate");
        getSupportActionBar().setTitle(time);

        Intent intent = getIntent();

        String name = getIntent().getStringExtra("keyName");
        String email = getIntent().getStringExtra("keyEmail");
        String phone = getIntent().getStringExtra("keyPhone");
        String sex = getIntent().getStringExtra("keySex");
        String birthDate = getIntent().getStringExtra("keyBirthdate");
        String img = getIntent().getStringExtra("keyImage");


        binding.txtUser.setText(name);
        binding.txtEmail.setText(email);
        binding.txtPhone.setText("+251"+phone);
        binding.txtSex.setText(sex);
        binding.txtBirthofdate.setText(birthDate);
        Uri uri = Uri.parse(img);
        binding.imageView.setImageURI(uri);
    }
}