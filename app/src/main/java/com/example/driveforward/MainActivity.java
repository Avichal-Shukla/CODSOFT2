package com.example.driveforward;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final String MY_SAVED_QUOTES = "MySavedQuotes";
    String[] quotes = {"There are two ways of spreading light: to be the candle or the mirror that reflects it.",
            "You do not find the happy life. You make it.", "The most wasted of days is one without laughter.",
            "Stay close to anything that makes you glad you are alive.", "Make each day your masterpiece.",
            "Happiness often sneaks in through a door you didn’t know you left open.", "Happiness is not by chance, but by choice.",
            "Life changes very quickly, in a very positive way, if you let it.", "Keep your face to the sunshine and you cannot see a shadow.",
            "Impossible is for the unwilling.", "No pressure, no diamonds.", "Believe you can and you’re halfway there.",
            "Failure is the condiment that gives success its flavor.", "It is never too late to be what you might have been.",
            "When you have a dream, you’ve got to grab it and never let go.", "You must be the change you wish to see in the world.",
            "Stay foolish to stay sane.", "Stay hungry. Stay foolish.", "Whatever you are, be a good one.", "You must do the things you think you cannot do.",
            "Wherever you go, go with all your heart.", "Be faithful to that which exists within yourself.", "Dream big and dare to fail.",
            "My mission in life is not merely to survive, but to thrive.", "You are enough just as you are.",
            "To be the best, you must be able to handle the worst.", "No matter what you’re going through, there’s a light at the end of the tunnel.",
            "Life is like riding a bicycle. To keep your balance, you must keep moving.", "Every moment is a fresh beginning.", "No guts, no story.",
            "Everybody stay positive no matter how negative life gets.", "Your mind must be stronger than your feelings.",
            "Gotta keep my head above water, gotta make it through.", "Never break or fold, that’s what it takes to be major.",
            "I know that now, and now is all that matters.", "Life is all about the evolution", "No matter where life takes me, find me with a smile.",
            "I am the smartest man alive!", "Once you really know yourself, can’t nobody tell you nothing about you.",
            "Sometimes you have to take two steps back to take ten forward.", "Our future is our confidence and self-esteem.",
            "Don’t be scared to live your life without judgment.", "Be kind to one another, even when it’s not requested.", "I am my inspiration.",
            "Look at the sky tonight. All the stars have a reason.", "What a dog I got. His favorite bone is in my arm.",
            "Only thing free in life is options.", "There is no way to happiness – happiness is the way.",
            "I’m not afraid of dying, I’m afraid of not trying.", "To lose your path is the way to find that path.",
            "I will not let anyone scare me out of my full potential.", "Peace is the absence of confusion.", "Our weary eyes still stray to the horizon.",
            "In every walk with nature one receives far more than he seeks.", "I speak my own sins; I cannot judge another. I have no tongue for it.",
            "I work hard. But I play hard, too.", "It’s never too late to get good at something.", "Wear your crown and go chase your dreams.",
            "Life is what you make it, just don’t fake it.", "I cannot teach anybody anything. I can only make them think.",
            "Even a blind man knows when the sun is shining.", "I don’t like people around me sad. I like making people happy.",
            "It’s okay to start over, let someone else love you the right way.", "There is no normal life, there’s just life. Get on with it.",
            "The good life is one inspired by love and guided by knowledge.", "Haters keep on hating cause somebody’s gotta do it.",
            "Keep your friends close, your enemies even closer.", "I’m just doing me, and to me, that’s what got me this far.",
            "I attribute my success to this – I never gave or took any excuse.", "If everything was perfect, you would never learn and you would never grow.",
            "In life you always see the darkest days before the sunshine.", "Muddy water is best cleared by leaving it alone.",
            "Education and work are the levers to uplift a people.", "Don’t wait for them to tell you. Tell them.", "There can only be one king.",
            "I paint flowers so they will not die.", "My fake plants died because I did not pretend to water them.",
            "If I have a platform and a voice, I should use it for my people.", "It takes a lot of courage to show your dreams to someone else.",
            "Well, it’s hard to be yourself, it’s the hardest job there is.", "Decide how badly you want it and proceed accordingly.",
            "Nothing is stopping me from doing what I love to do.", "Faith is a knowledge within the heart, beyond the reach of proof.",
            "Your river is strong. Let it flow.", "Knock me down 9 times, but I get up 10.",
            "Damn right I like the life I live because I went from negative to positive.", "The beginning is the most important part of the work.",
            "I found out that there weren’t too many limitations, if I did it my way.", "Nothing left to do but smile.",
            "The best revenge is massive success.", "Never to suffer would never to have been blessed.", "The harder I work, the luckier I become.",
            "Suffering is part of our training program for becoming wise.", "Sometimes it takes years for a person to become an overnight success.",
            "Luck is not a business model.", "If there is no struggle, there is no progress.", "Behind every successful person lies a pack of haters",
            "Time is a wonderful storyteller.", "Yesterday don’t matter if it’s gone.", "The goal is timeless. Chase the moment, and you lose."};
    String[] authors = {"– Edith Wharton", "– Camilla Eyring Kimball", " – E.E. Cummings", "– Hafez", "– John Wooden", "– John", "– Jim Rohn",
            "– Lindsey Vonn", "– Helen Keller", "– John Keats", "– Thomas Carlyle", "– Theodore Roosevelt", "– Truman Capote", "– George Eliot",
            "– Carol Burnett", "– Mahatma Gandhi", "– Maxime Lagacé", "– Steve Jobs", " – Abraham Lincoln", "– Eleanor Roosevelt", "– Confucius",
            "– André Gide", "– Norman Vaughan", "– Maya Angelou", "– Meghan Markle", " – Wilson Kanadi", "– Demi Lovato", "– Albert Einstein",
            "– T.S. Eliot ", "– Chris Brady", "– Juice WRLD`", "– Andrew Tate", "– NBA YoungBoy", "– Kevin Gates", "— Beth Dutton", "– J. Cole, Change",
            "– Mac Miller", " – Billy Madison", "– Megan Thee Stallion", "– Nipsey Hussle", "– Tupac Shakur", "— Lil Durk", " – XXXTENTACION", "– Lizzo",
            " — Lil Peep", " – Rodney Dangerfield", "– Moneybagg Yo", "– Thich Hat Hanh", "– Jay-Z", "— BTS", "– Nicki Minaj", "– Wu-Tang", "– Pink Floyd",
            "– John Muir", " ― John Proctor", "–Pitbull", "— Guy Fieri", "— Summer Walker", "– G Herbo", "– Socrates", "– Grateful Dead",
            "– Tyler, The Creator", "— Rod Wave", " — Doc Holliday, Tombstone", "— Bertrand Russell", "– Chris Brown", "– Sun Tzu", "– Lil Uzi Vert",
            " – Florence Nightingale", "– Beyoncé Knowles", "― Polo G", "– Alan Watts", "–W. E. B. Du Bois ", "– 50 Cent", "– Pablo Escobar",
            "– Frida Kahlo", " – Mitch Hedberg", " – Bad Bunny", " – Erma Bombeck", "— Bill Murray", "— Robin Arzon", " – The Weeknd", "– Kahlil Gibran",
            "— Glennon Doyle", "– Cardi B", "– Biggie Smalls", "– Plato", "– Johnny Cash", " – Jerry Garcia", "– Frank Sinatra", " – Edgar Allan Poe",
            " – Terry Pratchett", "– Ram Dass", "– Prince", "– Anthony Bourdain", "– Frederick Douglass", "― Eminem", "– Deion Sanders",
            "— The Rolling Stones, Ruby Tuesday", "— Kid Cudi"};
    TextView tvQuote, tvAuthor;
    Button save, share;
    ArrayList<String> savedQuotes;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvQuote = findViewById(R.id.tvQuote);
        tvAuthor = findViewById(R.id.tvAuthor);

        Random random = new Random();                            // Creating an object of the Random class

        int randomNumber = random.nextInt(100);                  // It will generate a random number between 0 to 99 (inclusive)

        String randomQuote = quotes[randomNumber];
        String randomAuthor = authors[randomNumber];

        tvQuote.setText(randomQuote);
        tvAuthor.setText(randomAuthor);

        save = findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Retrieve the existing task list from SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences(MY_SAVED_QUOTES, 0);
                String jsonSavedQuotesList = sharedPreferences.getString("savedQuotes", "");

                // Converting the task list back into its ArrayList form if some tasks are already added else creating a new list
                ArrayList<String> savedQuotes;
                if (!jsonSavedQuotesList.isEmpty())
                    savedQuotes = gson.fromJson(jsonSavedQuotesList, new TypeToken<ArrayList<String>>(){}.getType());
                else
                    savedQuotes = new ArrayList<>();

                String quote = tvQuote.getText().toString();

                if(savedQuotes.contains(quote))
                {
                    Toast.makeText(MainActivity.this, "Saved already", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    savedQuotes.add(quote);

                    String quotesListJSON = gson.toJson(savedQuotes);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("savedQuotes", quotesListJSON);
                    editor.apply();

                    Toast.makeText(MainActivity.this, "Saved successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        share = findViewById(R.id.btnShare);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quote = tvQuote.getText().toString();

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, quote);

                // Start the chooser for sharing
                startActivity(Intent.createChooser(shareIntent, "Share Quote"));
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_for_saved_quotes, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.menuViewSaved)
        {
            Intent intent = new Intent(MainActivity.this, SavedQuotesActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}