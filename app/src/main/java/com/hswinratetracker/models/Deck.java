package com.hswinratetracker.models;

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

        private HeroClasses(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
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
