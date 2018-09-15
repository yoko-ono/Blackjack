package Blackjack;

/**
 * Klasse Karte - hier wird festgelegt, wie eine einzelne Spielkarte aufgebaut ist.
 * Karte kann aus 13 Values und 4 Suits bestehen
 *
 * @author Sven Kohl (sINFsvkohl)
 * @version 1.2
 */
class Card {

    private Value value;
    private Suit suit;

    /**
     * Konstruktor, der Format einer Karte vorgibt
     *
     * @param value - kann 13 verschiedenen Values zugewiesen werden
     * @param suit  - kann 4 verschiedenen Suits zugewiesen werden
     */
    public Card(Value value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    /**
     * String-Methode, um eine generierte Karte anzeigen zu können
     *
     * @return - erzeugte Karte (Bsp.: JACK of HEARTS)
     */
    public String toString() {
        return this.value.toString() + " of " + this.suit.toString();
    }

    /**
     * Methode zur Rückgabe von einzelnen Kartenwerten
     *
     * @return - Kartenwert
     */
    Value getValue() {
        return this.value;
    }

}
