package com.example.registration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItem;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.registration.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    static String sex = "Male";
    ImageView imageGallery;
    Uri uploadImage;


    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        languageLoad();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if(savedInstanceState != null){
            uploadImage = savedInstanceState.getParcelable("imageUri");
            binding.imageViewPerson.setImageURI(uploadImage);
        }

        binding.imageViewPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);
            }
        });

        binding.userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() >10){
                    binding.userNameLayout.setError("Invalid user name, it should be less than < 10");
                }else if(charSequence.length()<=10){
                    binding.userNameLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(Patterns.EMAIL_ADDRESS.matcher(binding.email.getText().toString()).matches()){
                        binding.emailLayout.setError(null);
                    }else {
                        binding.emailLayout.setError("Invalid Email");
                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        binding.tvDate.setFocusable(false);
        binding.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month  = month + 1;
                String date;
                if(dayOfMonth<10 && month<10){
                    date = "0"+dayOfMonth+"/"+"0"+month+"/"+year;
                }else if(dayOfMonth<10){
                    date = "0"+dayOfMonth+"/"+month+"/"+year;
                }else if(month<10){
                    date = dayOfMonth+"/"+"0"+month+"/"+year;
                }else{
                    date = dayOfMonth+"/"+month+"/"+year;
                }
                binding.tvDate.setText(date);
            }
        };


        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case R.id.radioMale:
                        sex = "Male";
                        break;
                    case R.id.radioFemale:
                        sex = "Female";
                        break;
                    default:
                        sex = "Male";
                }
            }
        });


        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.userName.getText().toString();
                String email = binding.email.getText().toString();
                String phone = binding.phoneNo.getText().toString();
                String birthDate = binding.tvDate.getText().toString();

                if(binding.imageViewPerson.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.person_add_24).getConstantState()){
                    Toast.makeText(getApplicationContext(),"Please select an image", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(binding.userName.getText().toString())){
                    binding.userName.setError("Please insert username");
                }
                else if(TextUtils.isEmpty(binding.email.getText().toString())){
                    binding.email.setError("Please insert an email");
                }
                else if(TextUtils.isEmpty(binding.phoneNo.getText().toString())){
                    binding.phoneNo.setError("Please insert phone number");
                }
                else if(TextUtils.isEmpty(binding.tvDate.getText().toString())){
                    binding.tvDate.setError("Pick your Date of Birth");
                }
                else{
                    // Intent
                    Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                    intent.putExtra("keyName", name);
                    intent.putExtra("keyEmail",email);
                    intent.putExtra("keyPhone", phone);
                    intent.putExtra("keySex", sex);
                    intent.putExtra("keyBirthdate", birthDate);
                    intent.putExtra("keyImage", uploadImage.toString());
                    intent.putExtra("keyDate", currentDate);
                    startActivity(intent);
                }
            }
        });

    }
    // onCreate end

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            uploadImage = data.getData();
            binding.imageViewPerson.setImageURI(uploadImage);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.amharic:
                local(MainActivity.this, "am");
                finish();
                startActivity(getIntent());
                return true;
            case R.id.english:
                local(MainActivity.this, "en");
                finish();
                startActivity(getIntent());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void local (Activity activity, String lan){
        Locale locale = new Locale(lan);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("settings", MODE_PRIVATE).edit();
        editor.putString("myLanguage", lan);
        editor.apply();
    }

    public void languageLoad(){
        SharedPreferences preferences = getSharedPreferences("settings", Activity.MODE_PRIVATE);
        String language = preferences.getString("myLanguage", "");
        local(MainActivity.this, language);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("imageUri", uploadImage);
    }
}