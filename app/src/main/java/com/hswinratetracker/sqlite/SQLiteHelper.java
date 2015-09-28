package com.hswinratetracker.sqlite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hswinratetracker.models.Deck;

import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DeckDB";

    private static final String TABLE_DECKS = "decks";

    //Columns for decks
    private static final String KEY_DECKID = "DeckId";
    private static final String KEY_NAME = "Name";
    private static final String KEY_HEROCLASS = "HeroClass";
    private static final String KEY_WINS = "Wins";
    private static final String KEY_LOSES = "Loses";

    private static final String[] DECKS_COLUMNS = {KEY_DECKID, KEY_NAME, KEY_HEROCLASS, KEY_WINS, KEY_LOSES};
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_DECKS_TABLE = "CREATE TABLE " + TABLE_DECKS + " ( " +
                KEY_DECKID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                KEY_NAME + " TEXT, "+
                KEY_HEROCLASS + " TEXT, "+
                KEY_WINS + " INTEGER, " +
                KEY_LOSES + " INTEGER)";
        db.execSQL(CREATE_DECKS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public Deck addDeck(String name, Deck.HeroClasses heroClass, int wins, int loses){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_HEROCLASS, heroClass.toString());
        values.put(KEY_WINS, wins);
        values.put(KEY_LOSES, loses);

        Long deckId = db.insert(TABLE_DECKS,
                        null, //nullColumnHack
                        values);

        return new Deck(deckId, name, heroClass, wins, loses);
    }

    public void removeDeck(int deckId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DECKS, KEY_DECKID + " = ?", new String[]{String.valueOf(deckId)});
    }

    public ArrayList<Deck> getDecks()
    {
        ArrayList<Deck> decks = new ArrayList<Deck>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_DECKS,
                        DECKS_COLUMNS,
                        null, //selections
                        null, //selections args
                        null, //group by
                        null, //having
                        null, //order by
                        null);//limit

        if (cursor != null)
        {
            cursor.moveToFirst();
            for(int i = 0; i < cursor.getCount(); i++)
            {
                decks.add(new Deck(
                                cursor.getLong(0),
                                cursor.getString(1),
                                Deck.HeroClasses.valueOf(cursor.getString(2)),
                                cursor.getInt(3),
                                cursor.getInt(4)
                            ));
                cursor.moveToNext();
            }
            cursor.close();
        }

        return decks;
    }
}
