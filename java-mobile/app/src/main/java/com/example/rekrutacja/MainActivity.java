package com.example.rekrutacja;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rekrutacja.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(v -> onButtonClick());
        binding.button2.setOnClickListener(v -> onYourButtonClick());
    }

    /*1.TODO Pierwszym z zadań jest implementacja metody, w taki sposób, aby
          kliknięcie przycisku powodowało otwarcie activity SecondActivity, gdy
          wpisanym przez użytkownika tekstem jest 'AKAI'.
          Kolejne zadanie znajdziesz w pliku activity_main.xml.

          Podpowiedź poczytaj o Intent.
   */
    private void onButtonClick() {
        String userText = binding.editText.getText().toString();
        binding.textView.setText(userText);

        if (!userText.equals("AKAI")) {
            return;
        }

        Intent intent = new Intent(getBaseContext(), SecondActivity.class);
        startActivity(intent);
    }

    /*2.b TODO Druga część tego zadania polega na implementacji metody, która zmienia
            wszyskie literzy we wpisanym przez użytkownika tekscie na wielkie i
            wstawienie przerobionego tekstu do textView. Nie zapomnij dodać do metody
            OnCreate() OnClickListenera słuchającego na kliknięcia wstawionego przez Ciebie
            przycisku.
            Kolejne zadanie czeka na Ciebie w pliku SecondActiviy
     */
    private void onYourButtonClick() {
        String userText = binding.editText.getText().toString().toUpperCase(Locale.ROOT);
        binding.textView.setText(userText);
    }
}
