package com.hswinratetracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import hswinratetracker.com.hswinratetracker.R;

public class Deck implements Parcelable {
    private long DeckId;
    private String Name;
    private HeroClasses HeroClass;
    private int Wins;
    private int Loses;
    private Date DateCreated;
    private Date DateUpdated;

    public Deck() {
    }

    public Deck(Parcel in)
    {
        DeckId = in.readLong();
        Name = in.readString();
        HeroClass = (HeroClasses) in.readSerializable();
        Wins= in.readInt();
        Loses = in.readInt();
        DateCreated = (Date)in.readSerializable();
        DateUpdated = (Date)in.readSerializable();
    }

    public Deck(long deckId, String name, HeroClasses heroClass, int wins, int loses, Date dateCreated, Date dateUpdated) {
        DeckId = deckId;
        Name = name;
        HeroClass = heroClass;
        Wins = wins;
        Loses = loses;
        DateCreated = dateCreated;
        DateUpdated = dateUpdated;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(DeckId);
        out.writeString(Name);
        out.writeSerializable(HeroClass);
        out.writeInt(Wins);
        out.writeInt(Loses);
        out.writeSerializable(DateCreated);
        out.writeSerializable(DateUpdated);
    }

    public enum HeroClasses
    {
        MAGE("Mage"),
        PRIEST("Priest"),
        WARLOCK("Warlock"),
        PALADIN("Paladin"),
        WARRIOR("Warrior"),
        DRUID("Druid"),
        HUNTER("Hunter"),
        ROGUE("Rogue"),
        SHAMAN("Shaman");

        private final String text;
        private HeroClasses(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }

        public static HeroClasses fromString(String value) {
            for (HeroClasses heroClass : values()) {
                if (heroClass.text.equals(value)) {
                    return heroClass;
                }
            }
            return null;
        }

        public int getBannerId()
        {
            switch (this)
            {
                case DRUID:
                    return R.drawable.ic_class_druid_banner;
                case HUNTER:
                    return R.drawable.ic_class_hunter_banner;
                case MAGE:
                    return R.drawable.ic_class_mage_banner;
                case PALADIN:
                    return R.drawable.ic_class_paladin_banner;
                case PRIEST:
                    return R.drawable.ic_class_priest_banner;
                case ROGUE:
                    return R.drawable.ic_class_rogue_banner;
                case SHAMAN:
                    return R.drawable.ic_class_shaman_banner;
                case WARLOCK:
                    return R.drawable.ic_class_warlock_banner;
                case WARRIOR:
                    return R.drawable.ic_class_warrior_banner;
                default:
                    return 0;
            }


        }

        public int getIconId()
        {
            switch (this)
            {
                case DRUID:
                    return R.drawable.ic_class_druid;
                case HUNTER:
                    return R.drawable.ic_class_hunter;
                case MAGE:
                    return R.drawable.ic_class_mage;
                case PALADIN:
                    return R.drawable.ic_class_paladin;
                case PRIEST:
                    return R.drawable.ic_class_priest;
                case ROGUE:
                    return R.drawable.ic_class_rogue;
                case SHAMAN:
                    return R.drawable.ic_class_shaman;
                case WARLOCK:
                    return R.drawable.ic_class_warlock;
                case WARRIOR:
                    return R.drawable.ic_class_warrior;
                default:
                    return 0;
            }
        }


        public int getClassColor()
        {
            switch (this)
            {
                case DRUID:
                    return R.color.orange;
                case HUNTER:
                    return R.color.green;
                case MAGE:
                    return R.color.lightblue;
                case PALADIN:
                    return R.color.pink;
                case PRIEST:
                    return R.color.white;
                case ROGUE:
                    return R.color.lightyellow;
                case SHAMAN:
                    return R.color.blue;
                case WARLOCK:
                    return R.color.purple;
                case WARRIOR:
                    return R.color.tan;
            }

            return 0;
        }

    };

    public Date getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        DateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return DateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        DateUpdated = dateUpdated;
    }

    public long getDeckId() {
        return DeckId;
    }

    public void setDeckId(long deckId) {
        DeckId = deckId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public HeroClasses getHeroClass() {
        return HeroClass;
    }

    public void setHeroClass(HeroClasses heroClass) {
        HeroClass = heroClass;
    }

    public int getWins() {
        return Wins;
    }

    public void setWins(int wins) {
        Wins = wins;
    }

    public int getLoses() {
        return Loses;
    }

    public void setLoses(int loses) {
        Loses = loses;
    }


}
