import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.text.DecimalFormat;

class Card{
    private String rank;
    private String suit;
    public Card(String rank, String suit){
        this.rank=rank;
        this.suit=suit;
    }
    public String getRank(){
        return rank;
    }
    public String getSuit(){
        return suit;
    }
    public String toString(){
        return rank + " of "+suit;
    }
}


public class Casino {
    public static Float checkMoney(){
        Scanner scanner=new Scanner(System.in);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        boolean valid = false;
        float cash = 0.0f;
        while(!valid){
            System.out.print("Enter amount here: $");
            if (scanner.hasNextFloat()){
                cash = scanner.nextFloat();
                String roundedCash = decimalFormat.format(cash);
                cash = Float.parseFloat(roundedCash);
                if (cash<=0){
                    System.out.println("Sorry, you need to have money to actually play. Maybe pawn some items and try again.");
                }
                else{
                    valid=true;
                }
            }
            else {
                scanner.next();
                System.out.println("It looks like there was an error in your input, please try again.");
            }
        }
        return cash;
    }

    public static float deposit(){
        Scanner scanner=new Scanner(System.in);
        System.out.print("It seems you've run out of money. Would you like to deposit some more?\nY/N: "); 
        String deposit = scanner.nextLine();

        while(true){
            if(deposit.equals("Y")||deposit.equals("y")||deposit.equals("Yes")||deposit.equals("yes")){
                float money=checkMoney();
                return money;
            }
            else if(deposit.equals("N")||deposit.equals("n")||deposit.equals("No")||deposit.equals("no")){
                return 0;
            }
            else{
                System.out.println("There was an error with your input, please try again"); 
                scanner.next();
            }
        }
    }

    public static boolean anotherHand(){
        Scanner scanner=new Scanner(System.in);
        System.out.print("Would you like to play another hand?\nY/N: ");
        String hand = scanner.nextLine();
        while(true){
            if(hand.equals("Y")||hand.equals("y")||hand.equals("Yes")||hand.equals("yes")){
                return true;
            }
            else if(hand.equals("N")||hand.equals("n")||hand.equals("No")||hand.equals("no")){
                return false;
            }
            else{
                System.out.println("There was an error with your input, please try again"); 
                scanner.next();
            }
        }
    }

    public static int CardTotal(ArrayList<Card> cards){
        int total=0;
        int aces=0;
        for (int i=0;i<cards.size();i++){
            if (cards.get(i).getRank()=="J"||cards.get(i).getRank()=="Q"||cards.get(i).getRank()=="K"){
                total+=10;
            }
            else if(cards.get(i).getRank()=="A"){
                aces+=1;
            }
            else{
                total+=Integer.parseInt(cards.get(i).getRank());
            }
        }
        if (aces==0){return total;}
        else{
            if (aces==1){
                if (total+11>21){return total+=1;}
                else{return total+=11;}
            }
            else if (aces==2){
                if (total+12>21){return total+=2;}
                else{return total+=12;}
            }
            else if (aces==3){
                if (total+13>21){return total+=3;}
                else{return total+=13;}
            }
            else if (aces==4){
                if (total+14>21){return total+=4;}
                else{ return total+=14;}
            }
            else{return 0;}

        }
    }


    static float Blackjack(float money,  ArrayList<Card> deck){
        Scanner scanner=new Scanner(System.in);
        System.out.println("Welcome to Blackjack! Currently, you are only able to hit or stand. We apologize for the inconvience.");
        int turns = 0;
        boolean hand=true;
        float bet=0;
        while(true){
            if (money==0){return 0;}
            if (turns>0){hand=anotherHand();}
            if (!hand){return money;}
            else{
                System.out.println("How much would you like to bet on this hand?");
                while(true){
                    bet = checkMoney();
                    if (bet>money){
                        System.out.println("You don't have enough money to bet this much. Try a smaller amount.");
                    }
                    else{
                        money=money-bet;
                        break;
                    }
                }
            }
            System.out.println("Great! Let's begin\n");
            ArrayList<Card> dealer = new ArrayList<>();
            ArrayList<Card> player = new ArrayList<>();

            player.add(randomcard(deck));
            deck.remove(player.get(0));
            player.add(randomcard(deck));
            deck.remove(player.get(1));
            dealer.add(randomcard(deck));
            deck.remove(dealer.get(0));

            System.out.println("The dealer was dealt a " + (dealer.get(0)).toString());
            System.out.println("You were dealt a "+(player.get(0)).toString() +" and a "+(player.get(1)).toString());
            
            int playerindex=2;
            int dealerindex=1;
            boolean stand=false;
            boolean bust=false;
            while (true){
                System.out.print("\nWould you like to hit or stand?\nH/S: ");
                String hitstand = scanner.nextLine();
                while(true){
                    if(hitstand.equals("H")||hitstand.equals("h")||hitstand.equals("Hit")||hitstand.equals("hit")){
                        player.add(randomcard(deck));
                        deck.remove(player.get(playerindex));
                        System.out.println("You were dealt a "+(player.get(playerindex)).toString());
                        playerindex++;
                        break;
                    }
                    else if(hitstand.equals("S")||hitstand.equals("s")||hitstand.equals("Stand")||hitstand.equals("stand")){
                        stand=true;
                        break;
                    }
                    else{
                        System.out.println("There was an error with your input, please try again"); 
                        scanner.next();
                    }
                }
                System.out.print("Dealers Cards:\n");
                for (int i=0;i<dealer.size();i++){
                    System.out.println(dealer.get(i).toString());
                }

                System.out.print("\nYour Cards:\n");
                for (int i=0;i<player.size();i++){
                    System.out.println(player.get(i).toString());
                }

                System.out.print("Your card total is: "+ CardTotal(player)+"\n");
                if (CardTotal(player)>21){
                    System.out.println("Sorry, you busted. Better luck next time.");
                    bust=true;
                    break;
                }
                if (stand==true){
                    break;
                }
            }
            if(stand==true && bust==false){
                while(CardTotal(dealer)<17){
                    dealer.add(randomcard(deck));
                    deck.remove(dealer.get(dealerindex));
                    System.out.println("The dealer drew a "+(dealer.get(dealerindex)).toString());
                    dealerindex++;
                }
                System.out.print("Dealers Cards:\n");
                for (int i=0;i<dealer.size();i++){
                    System.out.println(dealer.get(i).toString());
                }
                System.out.print("The dealers card total is: "+ CardTotal(dealer)+"\n");

                if(CardTotal(dealer)>21){
                    System.out.println("The dealer busted. You win!");
                    money+=(bet*2);
                    System.out.println("You won $"+(bet*2)+" and now have a total of $"+money);
                }
                else if(CardTotal(dealer)<21){
                    if(CardTotal(player)>CardTotal(dealer)){
                        System.out.println("You were closer to 21. You win!");
                        money+=(bet*2);
                        System.out.println("You won $"+(bet*2)+" and now have a total of $"+money);    
                    }
                    else if(CardTotal(player)<CardTotal(dealer)){
                        System.out.println("The dealer was closer to 21. Sorry, you lose.");
                        System.out.println("You lost $"+(bet)+" and now have a total of $"+money);  
                    }
                    else{
                        System.out.println("It was a tie. No one wins");
                        System.out.println("Your total remains the same with a total of $"+money); 
                    }
                }
            }
            turns++;
        }
    }

    static ArrayList<Card> MakeDeck(){
        String[] ranks = {"2","3","4","5","6","7","8","9","10","J","Q","K","A",};
        String[] suits = {"Hearts", "Diamonds","Clubs","Spades"};
    
        ArrayList<Card> deck = new ArrayList<>();
        for (int i=0;i<ranks.length;i++){
            for (int j=0;j<suits.length;j++){
                Card card = new Card(ranks[i],suits[j]);
                deck.add(card);
            }
        }
        return deck;
    }
    static Card randomcard(ArrayList<Card> deck){
        Random random = new Random();
        int min=0;
        int max=deck.size()-1;
        int randomInt = random.nextInt(max - min + 1) + min;
        return deck.get(randomInt);
    }

    static void printGames(){
        String[] games = {"Blackjack","Slots","Baccarat"};
        for (int i=0;i<games.length;i++){
            System.out.println((i+1)+". "+games[i]);
        }
    }

    public static void main(String[] args) {
        ArrayList<Card> deck = MakeDeck();
        Scanner scanner=new Scanner(System.in);

        float totalDeposit=0;

        System.out.println("Welcome to the Casino, how much money would you like to initially bet with for the duration of your stay?");

        Float cash=checkMoney();

        System.out.println("\nGreat! You have decided to bring in $" + cash +"\nBelow is our selection of games you can play.\n");
        totalDeposit+=cash;

        printGames();
        System.out.print("\nWhich game would you like to play?\nEnter number here: ");
        int gameChoice = scanner.nextInt();

        if (gameChoice==1){
            System.out.println("\nGood luck and have fun!\n");
            cash=Blackjack(cash,deck);
            if (cash==0){
                cash=deposit();
                totalDeposit+=cash;
            }
        }
        else if (gameChoice==2){
            System.out.println("");
        }
        else if (gameChoice==3){
            System.out.println("");
        }
        else{
            System.out.println("Please try again.");
        }
        
        
    }
}
