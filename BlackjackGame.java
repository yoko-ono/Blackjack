package Blackjack;

import Login.Spieler;


import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Klasse BlackjackGame - hier wird das Spiel Blackjack implentiert.
 * Gestartet wird das ganze über ein neues Objekt, das von der
 * Startup-Klasse generiert wird.
 *
 * Zuerst wird ein Einsatz getätigt, dann werden die Karten verteilt.
 * Nachdem eine Runde fertig gespielt wurde, wird gefragt, ob eine neue Runde gestartet werden soll.
 * Im Falle eines Nein oder wenn der Spieler keine Chips mehr zur Verfügung hat, erscheint wieder die Spielübersicht.
 *
 * "Spezielle" Regeln:
 * Dealer muss ziehen bis er einen Kartenwert von mind. 17 erreicht.
 * Blackjack wird im Verhältnis 3 : 2 ausgezahlt.
 *
 * @author Sven Kohl (sINFsvkohl)
 * @version 1.2
 */
public class BlackjackGame {
    private Spieler currSpieler;
    private boolean playround = true;

    /**
     * Konstruktor für Klasse Spieler
     *
     * @param cSpieler bekommt derzeitigen Spieler übergeben
     */
    public BlackjackGame(Spieler cSpieler) {
        this.currSpieler = cSpieler;
    }

    /**
     * Methode zum Start des Spiels - hier läuft das gesamte Blackjackgame ab, von der Zuweisung der Decks,
     * Setzen der Chips, Karten austeilen, etc., bis hin zur mögl. Auszahlung und einem Exit ins Spielübersichts-Menü,
     * falls Spieler über keine Chips mehr verfügt oder nicht mehr weiterspielen will
     *
     * außerdem: keine sinnvollen Test-Methoden für diese Methode zu implementieren
     */
    public void start() {
        // Rundenstart und Festlegen der verfügbaren Chips
        int stacksize = (int) this.currSpieler.getKontostand();

        /*
         * playingDeck als Deck, mit dem gespielt wird festlegen und
         * anschließend erstellen & mischen
         */
        Deck playingDeck = new Deck();
        playingDeck.createDeck();
        playingDeck.shuffle();

        /*
         * jeweils ein neues Deck, also die jeweilige Rundenhand,
         * für Spieler und Dealer zuweisen
         */
        Deck playerHand = new Deck();
        Deck dealerHand = new Deck();

        /*
         * Loop für das Spiel, wird ausgeführt solange der Spieler über Chips verfügt
         */
        while (stacksize > 0) {

            int bet;
            boolean endRound = false;

            // Begrüßung und Aufforderung zum Einsatz setzen
            System.out.println("\n\nWillkommen bei Blackjack! ");
            System.out.println("Sie verfügen derzeitig über " + stacksize + " Chips. Bitte tätigen Sie Ihren Einsatz: ");

            /*
             * Scanner, um Einsatz und im Weiteren
             * Spieler-Moves einlesen zu können.
             * Spieler kann setzen, solange er Chips hat
             */
            Scanner input = new Scanner(System.in);
            try {
                bet = input.nextInt();
                if (bet > stacksize) {
                    System.out.println("So viele Chips haben Sie nicht...");
                    break;
                } else {
                    System.out.println(bet + " Chips wurden gesetzt! Nun werden die Karten verteilt...");
                }
            } catch (InputMismatchException e) {
                System.out.println("Kein gültiger Einsatz...");
                return;
            }

            // Runde beginnt, Karten werden ausgeteilt
            // Spieler erhält zwei Karten
            playerHand.draw(playingDeck);
            playerHand.draw(playingDeck);

            // Dealer erhält zwei Karten
            dealerHand.draw(playingDeck);
            dealerHand.draw(playingDeck);

            System.out.println("\nDealer Hand: \n" + dealerHand.getCard(0) + " und [?]");
            System.out.println("\nIhre Hand: " + playerHand);

            while (true) {

                // wenn Spieler bei 21 (Blackjack) steht nach ersten beiden Karten -> keine Entscheidungsfrage
                if (playerHand.cardsValue() == 21) {
                    System.out.println("Blackjack!!");
                    endRound = false;
                    break;
                }

                // Entscheidungsfrage, welchen Move Spieler wählt
                char move;

                do {
                    System.out.println("\n(1)Hit, (2)Stand oder (3)Double");
                    move = input.next().charAt(0);

                } while (move != '1' && move != '2' && move != '3');
                /*
                 * Hit - Spieler zieht eine Karte
                 */
                if (move == '1') {
                    playerHand.draw(playingDeck);
                    System.out.println("\nSpieler zieht " + playerHand.getCard(playerHand.deckSize() - 1));

                    // Spieler busts (wenn Kartenwert 21 überschreitet) -> Dealer gewinnt
                    if (playerHand.cardsValue() > 21) {
                        System.out.println(playerHand.cardsValue() + ", bust. Diese Runde geht an den Dealer.");
                        stacksize -= bet;
                        endRound = true;
                        break;
                    }

                }

                /*
                 * Stand - Spieler will keine Karte mehr
                 */
                if (move == '2') {
                    System.out.println("\nSpieler stands bei " + playerHand.cardsValue());
                    break;
                }

                /*
                 * Double - Spieler zieht genau noch eine Karte
                 * Einsatz wird dementsprechend doppelt ausgezahlt/einkassiert
                 */
                if (move == '3') {
                    System.out.println("\nSpieler verdoppelt seinen Einsatz!");

                    // Spieler zieht Karte
                    playerHand.draw(playingDeck);
                    System.out.println("Spieler zieht " + playerHand.getCard(playerHand.deckSize() - 1));
                    System.out.println("Spieler hat einen Kartenwert von " + playerHand.cardsValue());


                    // Spieler busts -> Dealer gewinnt
                    if (playerHand.cardsValue() > 21) {
                        System.out.println("Spieler busts. Diese Runde geht an den Dealer.");
                        stacksize -= bet * 2;
                        endRound = true;
                        break;
                    }

                    // Dealer Karten anzeigen
                    if ((playerHand.cardsValue() <= 21) && !endRound) {
                        System.out.println("\nDealer Karten: " + dealerHand);
                        endRound = false;
                    }

                    // Dealer stands bei 17 (bei 16 und darunter muss er eine Karte ziehen)
                    while (dealerHand.cardsValue() < 17 && !endRound) {
                        dealerHand.draw(playingDeck);
                        System.out.println("Dealer zieht " + dealerHand.getCard(dealerHand.deckSize() - 1));
                        System.out.println("Dealer hat einen Handwert von " + dealerHand.cardsValue());
                    }


                    // Dealer busts, doppelte Auszahlung
                    if ((dealerHand.cardsValue() > 21) && !endRound) {
                        System.out.println("Dealer busts, Sie gewinnen diese Runde!");
                        stacksize += bet * 2;
                        endRound = true;
                    }

                    // gleicher Handwert, keine Auszahlung, Chipcount bleibt unverändert
                    if ((playerHand.cardsValue() == dealerHand.cardsValue()) && !endRound) {
                        System.out.println("Gleicher Handwert. Push...");
                        endRound = true;
                    }

                    // Spieler gewinnt mit Blackjack, Auszahlung 3 : 1
                    if (playerHand.cardsValue() == 21 && !endRound) {
                        System.out.println("Spieler gewinnt diese Runde mit einem Blackjack!");
                        stacksize += bet * 3;
                        endRound = true;
                    }

                    // beide Hände werden verglichen, Spieler gewinnt und wird doppelt ausgezahlt
                    if (((playerHand.cardsValue() > dealerHand.cardsValue()) && !endRound)) {
                        System.out.println("Sie gewinnen diese Runde!");
                        stacksize += bet * 2;
                        endRound = true;
                    }

                    // beide Hände werden verglichen, Dealer gewinnt und Spieler verliert doppelten Einsatz
                    if ((dealerHand.cardsValue() > playerHand.cardsValue()) && !endRound) {
                        System.out.println(dealerHand.cardsValue() + " schlägt " + playerHand.cardsValue() + ", Dealer gewinnt!");
                        stacksize -= bet * 2;
                        endRound = true;
                    }

                    break;
                }
            }

            // Dealer Karten anzeigen
            if ((playerHand.cardsValue() <= 21) && !endRound) {
                System.out.println("\nDealer Karten: " + dealerHand);
                endRound = false;
            }

            // Dealer stands bei 17 (bei 16 und darunter muss er eine Karte ziehen)
            while ((dealerHand.cardsValue() < 17) && !endRound) {
                dealerHand.draw(playingDeck);
                System.out.println("Dealer zieht " + dealerHand.getCard(dealerHand.deckSize() - 1));
            }

            // finalen Rundenwert der Dealer-Karten anzeigen
            if ((playerHand.cardsValue() <= 21) && !endRound) {
                System.out.println("Dealer hat einen Handwert von " + dealerHand.cardsValue());
                endRound = false;
            }

            // gleicher Handwert, keine Auszahlung, Chipcount bleibt unverändert (Push)
            if ((playerHand.cardsValue() == dealerHand.cardsValue()) && !endRound) {
                System.out.println("Gleicher Handwert. Push...");
                endRound = true;
            }

            // Spieler gewinnt mit Blackjack, Auszahlung 3 : 2
            if ((playerHand.cardsValue() == 21 && !endRound)) {
                System.out.println("Spieler gewinnt diese Runde mit einem Blackjack!");
                stacksize += bet * 1.5;
                endRound = true;
            }

            // Dealer busts -> Spieler gewinnt
            if ((dealerHand.cardsValue() > 21) && !endRound) {
                System.out.println("Dealer busts, Sie gewinnen diese Hand!");
                stacksize += bet;
                endRound = true;
            }

            // beide Hände werden verglichen, Dealer gewinnt und Spieler verliert Einsatz
            if ((dealerHand.cardsValue() > playerHand.cardsValue()) && !endRound) {
                System.out.println(dealerHand.cardsValue() + " schlägt " + playerHand.cardsValue() + ", Dealer gewinnt!");
                stacksize -= bet;
                endRound = true;
            }

            // beide Hände werden verglichen, Spieler gewinnt und wird ausgezahlt
            if ((playerHand.cardsValue() > dealerHand.cardsValue()) && !endRound) {
                System.out.println("Sie gewinnen diese Hand!");
                stacksize += bet;
                endRound = true;
            }

            /*
             * Die Karten von Spieler und Dealer werden nach Rundenende zurück
             * in das anfänglich erstellte und gemischte Deck verschoben, so kann durchgehend
             * mit einem Deck aus 52 Karten weitergespielt werden
             *
             */
            playerHand.moveCardsToDeck(playingDeck);
            dealerHand.moveCardsToDeck(playingDeck);
            System.out.println("\nRunde beendet...");
            currSpieler.setKontostand(stacksize);
            return;
        }

        // Game over
        System.out.println("\nSpieler hat kein Guthaben mehr. Danke für's Mitspielen!");
    }
}
