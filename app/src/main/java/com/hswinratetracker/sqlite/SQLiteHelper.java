package com.hswinratetracker.sqlite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hswinratetracker.models.Deck;

import java.util.ArrayList;
import java.util.Date;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "DeckDB";

    private static final String TABLE_DECKS = "decks";

    //Columns for decks
    private static final String KEY_DECKID = "DeckId";
    private static final String KEY_NAME = "Name";
    private static final String KEY_HEROCLASS = "HeroClass";
    private static final String KEY_WINS = "Wins";
    private static final String KEY_LOSES = "Loses";
    private static final String KEY_DATECREATED = "DateCreated";
    private static final String KEY_DATEUPDATED = "DateUpdated";

    private static final String[] DECKS_COLUMNS = {KEY_DECKID, KEY_NAME, KEY_HEROCLASS, KEY_WINS, KEY_LOSES, KEY_DATECREATED, KEY_DATEUPDATED};
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
                KEY_LOSES + " INTEGER, " +
                KEY_DATECREATED + " INTEGER, " + //Date stored as milliseconds
                KEY_DATEUPDATED + " INTEGER)"; //Date stored as milliseconds
        db.execSQL(CREATE_DECKS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DECKS);
        this.onCreate(db);
    }

    public Deck addDeck(String name, Deck.HeroClasses heroClass, int wins, int loses){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_HEROCLASS, heroClass.toString());
        values.put(KEY_WINS, wins);
        values.put(KEY_LOSES, loses);
        Date dateCreated = new Date();
        values.put(KEY_DATECREATED, dateCreated.getTime()); //today
        values.putNull(KEY_DATEUPDATED);//never updated
        long deckId = db.insert(TABLE_DECKS,
                        null, //nullColumnHack
                        values);

        return new Deck(deckId, name, heroClass, wins, loses, dateCreated, null);
    }

    public void removeDeck(int deckId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DECKS, KEY_DECKID + " = ?", new String[]{String.valueOf(deckId)});
    }

    public int getWins(long deckId)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_DECKS,
                        DECKS_COLUMNS,
                        KEY_DECKID + " = ?", //selections
                        new String[]{String.valueOf(deckId)} , //selections args
                        null, //group by
                        null, //having
                        null, //order by
                        null);//limit

        if (cursor !=null)
            cursor.moveToFirst();


        return cursor.getInt(3);
    }

    public int getLoses(long deckId)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_DECKS,
                        DECKS_COLUMNS,
                        KEY_DECKID + " = ?", //selections
                        new String[]{String.valueOf(deckId)} , //selections args
                        null, //group by
                        null, //having
                        null, //order by
                        null);//limit

        if (cursor !=null)
            cursor.moveToFirst();

        return cursor.getInt(4);

    }

    public void addWin(long deckId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int currentWins = getWins(deckId);
        int updatedWins = currentWins + 1;

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_WINS, updatedWins);

        db.update(TABLE_DECKS, newValues, KEY_DECKID + " = ?", new String[]{String.valueOf(deckId)});
    }

    public void addLose(long deckId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int currentLoses = getLoses(deckId);
        int updatedLoses = currentLoses + 1;

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_LOSES, updatedLoses);

        db.update(TABLE_DECKS, newValues, KEY_DECKID + " = ?", new String[]{String.valueOf(deckId)});
    }
    public Deck getDeck(long deckId)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_DECKS,
                        DECKS_COLUMNS,
                        KEY_DECKID + " = ?", //selections
                        new String[]{String.valueOf(deckId)}, //selections args
                        null, //group by
                        null, //having
                        null, //order by
                        null);//limit

        Deck deck = null;
        if (cursor != null)
        {
            cursor.moveToFirst();
            deck = new Deck();
            deck.setDeckId(cursor.getLong(0));
            deck.setName(cursor.getString(1));
            deck.setHeroClass(Deck.HeroClasses.fromString(cursor.getString(2)));
            deck.setWins(cursor.getInt(3));
            deck.setLoses(cursor.getInt(4));
            deck.setDateCreated(new Date(cursor.getLong(5)));
            if(!cursor.isNull(6))
                deck.setDateUpdated(new Date(cursor.getLong(6)));

            cursor.close();
        }

        return deck;
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
                Deck deck = new Deck();
                deck.setDeckId(cursor.getLong(0));
                deck.setName(cursor.getString(1));
                deck.setHeroClass(Deck.HeroClasses.fromString(cursor.getString(2)));
                deck.setWins(cursor.getInt(3));
                deck.setLoses(cursor.getInt(4));
                deck.setDateCreated(new Date(cursor.getLong(5)));
                if(!cursor.isNull(6))
                    deck.setDateUpdated(new Date(cursor.getLong(6)));
                decks.add(deck);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return decks;
    }
}
