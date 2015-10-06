package com.hswinratetracker.models;

import android.content.res.Resources;

import hswinratetracker.com.hswinratetracker.R;

public class Deck {

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
        private final int classColor;
        private final int classBanner;
        private HeroClasses(final String text) {
            this.text = text;
            switch (this)
            {
                case DRUID:
                    classBanner =  R.drawable.ic_class_druid_banner;
                    classColor = R.color.orange;
                    break;
                case HUNTER:
                    classBanner =  R.drawable.ic_class_hunter_banner;
                    classColor = R.color.green;
                    break;
                case MAGE:
                    classBanner =  R.drawable.ic_class_mage_banner;
                    classColor = R.color.lightblue;
                    break;
                case PALADIN:
                    classBanner =  R.drawable.ic_class_paladin_banner;
                    classColor = R.color.lightyellow;
                    break;
                case PRIEST:
                    classBanner =  R.drawable.ic_class_priest_banner;
                    classColor = R.color.white;
                    break;
                case ROGUE:
                    classBanner =  R.drawable.ic_class_rogue_banner;
                    classColor = R.color.pink;
                    break;
                case SHAMAN:
                    classBanner =  R.drawable.ic_class_shaman_banner;
                    classColor = R.color.blue;
                    break;
                case WARLOCK:
                    classBanner =  R.drawable.ic_class_warlock_banner;
                    classColor = R.color.purple;
                    break;
                case WARRIOR:
                    classBanner =  R.drawable.ic_class_warrior_banner;
                    classColor = R.color.tan;
                    break;
                default:
                    classBanner = 0;
                    classColor = 0;
                    break;
            }
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
            return classBanner;
        }

        public int getClassColor()
        {
            return classColor;
        }

    };

    private Long DeckId;
    private String Name;
    private HeroClasses HeroClass;
    private int Wins;
    private int Loses;
    private Double WinRate;

    public Deck(Long deckId, String name, HeroClasses heroClass, int wins, int loses) {
        DeckId = deckId;
        Name = name;
        HeroClass = heroClass;
        Wins = wins;
        Loses = loses;
    }

    public Long getDeckId() {
        return DeckId;
    }

    public void setDeckId(Long deckId) {
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

    public Double getWinRate() {
        return WinRate;
    }

    public void setWinRate(Double winRate) {
        WinRate = winRate;
    }
}
