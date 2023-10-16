package com.example.driveforward;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class SavedQuotesActivity extends AppCompatActivity {

    ListView lvQuotes;
    Gson gson = new Gson();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_quotes);

        lvQuotes = findViewById(R.id.lvSavedQuotes);

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.MY_SAVED_QUOTES, 0);
        String savedQuotesListJSON = sharedPreferences.getString("savedQuotes", "");

        // Converting the task list back into its ArrayList form
        ArrayList<String> savedQuotes = gson.fromJson(savedQuotesListJSON, new TypeToken<ArrayList<String>>(){}.getType());

        if (savedQuotes != null)                                        // Setting adaptor only if there is at least one saved quote
        {
            // Using a custom layout for the adaptor just to make the text color of the listview as white
            adapter = new ArrayAdapter<>(SavedQuotesActivity.this, R.layout.list_item_saved_quote, R.id.tvSavedQuoteList, savedQuotes);
            lvQuotes.setAdapter(adapter);
        }

        // If user wants to delete any saved quote
        lvQuotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

                // Vibrating the device when long pressed on the task item
                Vibrator vibrator = (Vibrator) SavedQuotesActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    vibrator.vibrate(VibrationEffect.createOneShot(60, VibrationEffect.DEFAULT_AMPLITUDE));
                else
                    vibrator.vibrate(60);

                String quoteToDelete = savedQuotes.get(position);                   // Retrieving the quote which has to be deleted

                AlertDialog.Builder builder = new AlertDialog.Builder(SavedQuotesActivity.this);

                builder.setTitle("Delete Quote");
                builder.setMessage("Are you sure you want to delete this quote?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        savedQuotes.remove(quoteToDelete);

                        // Updating the saved quotes in SharedPreferences
                        String quotesListJSON = gson.toJson(savedQuotes);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("savedQuotes", quotesListJSON);
                        editor.apply();

                        adapter.notifyDataSetChanged();

                        Toast.makeText(SavedQuotesActivity.this, "Quote deleted", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                // Showing the dialog box only after setting up functionality for all the buttons
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                return true;
            }
        });
    }
}