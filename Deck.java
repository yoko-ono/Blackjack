package Blackjack;

import java.util.ArrayList;
import java.util.Random;

/**
 * Klasse Deck - hier werden u.a. die Methoden zur Erstellung und zum Mischen
 * eines Decks implementiert
 *
 * Gespielt wird mit einem gängingem, vollständigen 52-Karten Blackjack-Deck (13 Kartenreihen in 4 verschiedenen Farben)
 *
 * @author Sven Kohl (sINFsvkohl)
 * @version 1.2
 */
class Deck {

    private ArrayList<Card> cards;

    /**
     * Konstruktor für Array,
     * dem Speicherplatz für das Deck, mit dem gespielt wird
     */
    public Deck() {
        this.cards = new ArrayList<Card>();
    }

    /**
     * Generiert sortiertes Deck aus
     * 13 Values * 4 Suits = 52 Cards
     */
    void createDeck() {
        for (Value cardValue : Value.values()) {
            for (Suit cardSuit : Suit.values()) {
                // neue Karte hinzufügen mit Hilfe von Card-Konstruktor
                this.cards.add(new Card(cardValue, cardSuit));
            }
        }
    }

    /**
     * Mischt zuvor erstelltes Deck, indem daraus zufällig
     * einzelne Karten gezogen und an ein neues, temporäres
     * Deck assigned werden - solange bis das alte Deck leer ist
     */
    void shuffle() {
        ArrayList<Card> tmpDeck = new ArrayList<Card>();

        // User Random
        Random random = new Random();
        int randomCardIndex;
        int originalSize = this.cards.size();
        for (int i = 0; i < originalSize; i++) {
            // zufälligen Kartenindex generieren
            randomCardIndex = random.nextInt(this.cards.size() - 1 + 1);
            tmpDeck.add(this.cards.get(randomCardIndex));

            // Karte von ursprünglichen Deck entfernen, um keine Karte doppelt zu adden
            this.cards.remove(randomCardIndex);
        }

        this.cards = tmpDeck;

    }

    /**
     * toString-Methode um unübersichtliche Anzeige der Karten zu vermeiden
     *
     * @return - cardListOutput
     */
    public String toString() {
        String cardListOutput = "";
        for (Card x : this.cards) {
            cardListOutput += "\n" + x.toString();
        }
        return cardListOutput;
    }

    /**
     * Methode zum Entfernen von Karten aus dem Deck
     *
     * @param i - Kartennummer
     */
    public void removeCard(int i) {
        this.cards.remove(i);
    }

    /**
     * getter-Methode zum Anzeigen welche Karte Dealer/Spieler gezogen hat
     *
     * @param i - Kartennummer
     * @return - gezogene Karte
     */
    Card getCard(int i) {
        return this.cards.get(i);
    }


    /**
     * Methode, um Karte einem Deck hinzuzufügen
     *
     * @param addCard - Karte, die hinzugefügt wird
     */
    private void addCard(Card addCard) {
        this.cards.add(addCard);
    }

    /**
     * draw-Methode, um Karte von einem Deck in ein anderes zu verschieben
     *
     * @param comingFrom - ursprüngliches Deck der zu verschiebenen Karte
     */
    void draw(Deck comingFrom) {
        this.cards.add(comingFrom.getCard(0));
        comingFrom.removeCard(0);
    }

    /**
     * Methode zur Übergabe der momentanen Deckgröße
     *
     * @return - Kartenanzahl
     */
    int deckSize() {
        return this.cards.size();
    }

    /**
     * Methode, um Karten nach einer abgeschlossenen Runde wieder in das Spieldeck zu schieben,
     * um die nächste Runde mit einem vollen 52-Karten Deck starten zu können
     *
     * @param moveTo - Zieldeck, wo Karten reinverschoben werden sollen
     */
    void moveCardsToDeck(Deck moveTo) {
        int thisDeckSize = this.cards.size();

        // put cards into moveTo deck
        for (int i = 0; i < thisDeckSize; i++) {
            moveTo.addCard(this.getCard(i));
        }

        for (int i = 0; i < thisDeckSize; i++) {
            this.removeCard(0);
        }

    }

    /**
     * Methode zur Bestimmung der Kartenwerte
     *
     * @return totalValue - Rückgabe der Kartenwerte, einzeln oder addiert als Spieler-/Dealerhand
     */
    int cardsValue() {
        int totalValue = 0;
        int aces = 0;

        for (Card x : this.cards) {
            switch (x.getValue()) {
                case TWO:
                    totalValue += 2;
                    break;
                case THREE:
                    totalValue += 3;
                    break;
                case FOUR:
                    totalValue += 4;
                    break;
                case FIVE:
                    totalValue += 5;
                    break;
                case SIX:
                    totalValue += 6;
                    break;
                case SEVEN:
                    totalValue += 7;
                    break;
                case EIGHT:
                    totalValue += 8;
                    break;
                case NINE:
                    totalValue += 9;
                    break;
                case TEN:
                    totalValue += 10;
                    break;
                case JACK:
                    totalValue += 10;
                    break;
                case QUEEN:
                    totalValue += 10;
                    break;
                case KING:
                    totalValue += 10;
                    break;
                case ACE:
                    aces += 1;
                    break;
            }
        }

        /*
         * Ass kann Wert 1 und Wert 11 annehmen;
         * Wert entspricht solange 11, bis der Wert einer Hand 10 überschreitet,
         * ab dann zählt für ein Ass der Wert 1
         */
        for (int i = 0; i < aces; i++) {
            if (totalValue > 10) {
                totalValue += 1;
            } else {
                totalValue += 11;
            }
        }
        return totalValue;
    }
}

