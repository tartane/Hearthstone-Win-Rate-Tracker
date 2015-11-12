package com.hswinratetracker.models;

import java.util.Date;
import hswinratetracker.com.hswinratetracker.R;

public class Deck {
    private long DeckId;
    private String Name;
    private HeroClasses HeroClass;
    private int Wins;
    private int Loses;
    private Date DateCreated;
    private Date DateUpdated;

    public Deck() {
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
                    return R.color.lightyellow;
                case PRIEST:
                    return R.color.white;
                case ROGUE:
                    return R.color.pink;
                case SHAMAN:
                    return R.color.blue;
                case WARLOCK:
                    return R.color.purple;
                case WARRIOR:
                    return R.color.tan;
                default:
                    return 0;
            }
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
