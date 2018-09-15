package Blackjack;

import org.junit.Test;
import static org.junit.Assert.*;

public class DeckTest {

    private String unshuffledDeck =
                    "TWO of HEARTS\n"+
                    "TWO of SPADES\n"+
                    "TWO of CLUBS\n"+
                    "TWO of DIAMONDS\n"+
                    "THREE of HEARTS\n"+
                    "THREE of SPADES\n"+
                    "THREE of CLUBS\n"+
                    "THREE of DIAMONDS\n"+
                    "FOUR of HEARTS\n"+
                    "FOUR of SPADES\n"+
                    "FOUR of CLUBS\n"+
                    "FOUR of DIAMONDS\n"+
                    "FIVE of HEARTS\n"+
                    "FIVE of SPADES\n"+
                    "FIVE of CLUBS\n"+
                    "FIVE of DIAMONDS\n"+
                    "SIX of HEARTS\n"+
                    "SIX of SPADES\n"+
                    "SIX of CLUBS\n"+
                    "SIX of DIAMONDS\n"+
                    "SEVEN of HEARTS\n"+
                    "SEVEN of SPADES\n"+
                    "SEVEN of CLUBS\n"+
                    "SEVEN of DIAMONDS\n"+
                    "EIGHT of HEARTS\n"+
                    "EIGHT of SPADES\n"+
                    "EIGHT of CLUBS\n"+
                    "EIGHT of DIAMONDS\n"+
                    "NINE of HEARTS\n"+
                    "NINE of SPADES\n"+
                    "NINE of CLUBS\n"+
                    "NINE of DIAMONDS\n"+
                    "TEN of HEARTS\n"+
                    "TEN of SPADES\n"+
                    "TEN of CLUBS\n"+
                    "TEN of DIAMONDS\n"+
                    "JACK of HEARTS\n"+
                    "JACK of SPADES\n"+
                    "JACK of CLUBS\n"+
                    "JACK of DIAMONDS\n"+
                    "QUEEN of HEARTS\n"+
                    "QUEEN of SPADES\n"+
                    "QUEEN of CLUBS\n"+
                    "QUEEN of DIAMONDS\n"+
                    "KING of HEARTS\n"+
                    "KING of SPADES\n"+
                    "KING of CLUBS\n"+
                    "KING of DIAMONDS\n"+
                    "ACE of HEARTS\n"+
                    "ACE of SPADES\n"+
                    "ACE of CLUBS\n"+
                    "ACE of DIAMONDS";

    @Test
    public void createDeck() {
        Deck testDeck = new Deck();
        testDeck.createDeck();
        assertEquals("\n" + unshuffledDeck, testDeck.toString());
    }

    // toString-Methode und deckSize-Methode hier implementiert, kein eigenständiger Test benötigt

    @Test
    public void shuffle() {
        Deck testDeck = new Deck();
        testDeck.createDeck();
        testDeck.shuffle();
        assertNotEquals("\n" + unshuffledDeck, testDeck.toString());
        assertEquals(52, testDeck.deckSize());
    }

    @Test
    public void draw() {
        Deck testHand = new Deck();
        Deck testDeck = new Deck();
        testDeck.createDeck();
        testHand.draw(testDeck);
        testHand.draw(testDeck);
        assertEquals(50, testDeck.deckSize());
    }

    // addCard- und removeCard-Methode hier implementiert, kein eigenständiger Test benötigt
    @Test
    public void moveCardsToDeck() {
        Deck testHand = new Deck();
        Deck testDeck = new Deck();
        testDeck.createDeck();
        testHand.draw(testDeck);
        testHand.draw(testDeck);
        testHand.moveCardsToDeck(testDeck);
        assertEquals(52, testDeck.deckSize());
    }

    @Test
    public void cardsValue() {
        /*
         * Wert der ersten 48 Karten ergibt 336, restlichen 4 Asse zählen als 1 (da totalValue > 10)
         * -> Deck muss Wert 340 annehmen
         */
        Deck testDeck = new Deck();
        testDeck.createDeck();
        assertEquals(340, testDeck.cardsValue());

        /*
         * Fallunterscheidung mit Ass-Wert 1 oder 11:
         * entfernt man alle generierten Karten, bis auf die letzten 5 (1x König, 4x Ass)
         * aus einem sortierten Deck, muss sich ein Wert x = 1*10 + 1*11 + 3*1 = 24 ergeben.
         */
        Deck testDeck2 = new Deck();
        testDeck2.createDeck();
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);
        testDeck2.removeCard(0);

        assertEquals(24, testDeck2.cardsValue());
    }
}