import java.util.Random;
import java.util.Scanner;

/**
 * Created by Sripath Mishra on 9/8/2017.
 *
 */
public class BlackBox {
    public static char box[][]; // The matrix for the game.
    public static int size;       // to store the number of rows and columns.
    public static int numball;
    public static int numlink;
    public static boolean end;
    public static int score;
    public static int high_score=5000;
    public static char ballBox[][];

    /**
     * The default constructor which places default values to the class variables
     */
    public BlackBox()
    {
        this.box=getbox(); //new char[0][0]
        this.size=getSize(); //0
        this.numball=getNumball();
        this.numlink=getNumlink();
        this.end=false; //false
        this.score=getscore();
        this.ballBox=ballBox;
    }
    /**
     * The parameterized constructor which places values to the class variables
     */
    public BlackBox(int size, int numball, int numlink, boolean end, int score)
    {
        this.box=new char[size][size];
        this.size=size;
        this.numball=numball;
        this.numlink=numlink;
        this.end=end;
        this.score=score;
        this.ballBox=new char[size][size];
    }
    /**
     * The main function takes input for the difficulty level and call the functions initialize(int) and
     * playgame()
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        BlackBox game = new BlackBox();

        String input;
        //Todo:start the game print the welcome message and ask for correct difficulty level.

        while(true) {
            System.out.println("Welcome to the Blackbox Game. Please choose the difficulty level: easy/medium/hard or quit to end the game");
            input = in.nextLine();

            input = input.toLowerCase();

            if (input.equals("easy")) {
                game.setSize(7);
                game.initialize();
                printbox();
                playgame();
            } else if (input.equals("medium")) {
                game.setSize(9);
                game.initialize();
                printbox();
                playgame();
            } else if (input.equals("hard")) {
                game.setSize(10);
                game.initialize();
                printbox();
                playgame();
            } else if (input.equals("quit")) {
                game.setEnd(true);
                break;
            }

            //scoring
            if(score == 0){
                System.out.println("Fail");
            }
            else {
                System.out.println("Final score: " + score);
            }

            if(score < high_score && score >= 3){
                high_score = score;
            }
            //high score
            if(high_score == 0 || high_score == -1) {
                System.out.println("High Score: none");
            }
            else{
                System.out.println("High Score: " + high_score);
            }
            score = 0;
        }

        //Todo: end the game if the user says quit.
        //Todo:call the functions initialize and playgame()
        // Todo: take care of high score
    }
    /**
     * The initialize funtion
     */
    public void initialize() {
        //initialise the Box[][]
        this.box = new char[size][size];
        this.ballBox = new char[size][size];

        //place the 'X' and the '#'
        for (int i = 0; i < this.box.length; i++) {
            for (int j = 0; j < this.box[i].length; j++) {
                //replace corners with X
                if (i == 0 && j == 0 || i == 0 && j == size - 1 || i == size - 1 && j == 0 || i == size - 1 && j == size - 1) {
                    box[i][j] = 'X';
                }
                //replace sides with #
                else if (i == 0 || j == 0 || i == size - 1 || j == size - 1) {
                    box[i][j] = '#';
                } else {
                    box[i][j] = ' ';
                }
            }
        }

        //create ballbox
        for (int i = 0; i < this.box.length; i++) {
            for (int j = 0; j < this.box[i].length; j++) {
                ballBox[i][j] = 'E';
            }
        }


        // Todo: place 3 '0''s randomly.
        Random rand = new Random();

        int x;
        int y;

        for(int i = 0; i < 3; i++){
            x  = rand.nextInt(size - 2) + 1;
            y  = rand.nextInt(size - 2) + 1;

            while(ballBox[x][y] == '0'){
                x  = rand.nextInt(size - 2) + 1;
                y  = rand.nextInt(size - 2) + 1;
            }

            ballBox[x][y] = '0';
        }

        //test to see if ballbox works
        /*for (int i = 0; i < this.box.length; i++) {
            for (int j = 0; j < this.box[i].length; j++) {
                System.out.print(ballBox[i][j] + " ");
            }
            System.out.println();
        }*/
    }
    /**
     * The printbox funtion prints out the matrix in a particular format as given in the handout.
     */
    public static void printbox() {
        // for  5*5 example
        /* 1  2  3  4  5  6  7
         ======================
        1|X |# |# |# |# |# |X |
        2|# |  |  |  |  |  |# |
        3|# |  |  |  |  |  |# |
        4|# |  |  |  |  |  |# |
        5|# |  |  |  |  |  |# |
        6|# |  |  |  |  |  |# |
        7|X |# |# |# |# |# |X |
         ======================*/
        //place the guesses as the come and print the balls when the player enter sumbit.

        //print box
        for(int x = 0; x < box[0].length; x++) {
            System.out.print("  " + (x + 1));
        }
        System.out.println();
        System.out.print(" ");
        for(int x = 0; x < box[0].length; x++) {
            System.out.print("===");
        }
        //main box
        for (int i = 0; i < box.length; i++) {
            System.out.println();
            for (int j = 0; j < box[i].length; j++) {
                if(j == 0)
                {
                    System.out.print((i + 1) +  "|");
                }
                System.out.print(box[i][j] + " |");
            }
        }
        System.out.println();
        System.out.print(" ");
        for(int x = 0; x < box[0].length; x++) {
            System.out.print("===");
        }
        System.out.println();
    }
    /**
     * The playgame funtion opens the first cell and is the main controller for the game. It calls various function when needed.
     */
    public static void playgame() {
        BlackBox game = new BlackBox();
        Scanner in = new Scanner(System.in);

        String input; //input from user
        int x; //int with x value from user
        int y; //int with y value from user
        int comma; //index of comma

        while(end == false) {
            //Todo:Take input of a guess or hint from the user.
            System.out.println("Choose the new coordinates (row,column) to play the next step or say submit/quit to end the game");
            input = in.nextLine();

            //Todo:Check for valid input
            while (input.equals(null) || input.equals(" ")) {
                System.out.println("Choose the new coordinates (row,column) to play the next step or say submit/quit to end the game");
                input = in.nextLine();
            }

            //remove spaces on outside sides of string
            input = input.trim();

            //check if x and y coordinate are numbers
            if (input.equals("quit")) {
                game.setEnd(true);
            }
            else if (input.equals("submit")){
                if(numball >= 3){
                    game.setEnd(true);
                    break;
                }
                else{
                    System.out.println("You have not made three guesses yet.");
                    playgame();
                }
            }
            else {
                comma = input.indexOf(',');

                x = Integer.parseInt(input.substring(0, comma));
                y = Integer.parseInt(input.substring(comma + 1));

                check(x, y);
                game.printbox();
                score++;
            }
        }

        for (int i = 0; i < box.length; i++) {
            for (int j = 0; j < box[i].length; j++) {
                if(ballBox[i][j] == '0' && box[i][j] != '*'){
                    score = 0;
                    break;
                }
            }
        }

        end = true;
        numball = 0;
        //Todo:call required functions (check and printbox)
        //Todo:keep tab on score.
    }
    /**
     * The check funtion takes in the row and column in the matrix, checks for Hit (H), Reflection (R) or Divergence(#num)
     *
     */
    public static void check(int i,int j) {
        BlackBox game = new BlackBox();
        Scanner in = new Scanner(System.in);

        String input = ""; //input from user
        int comma;

        //Todo:place a guess when the input of i and j are valid
            while (i < 1 || i > game.getSize() || j < 1 || j > game.getSize() && (input.equals(null) || input.equals(" "))) {
                System.out.println("Choose the new coordinates (row,column) to play the next step or say submit/quit to end the game");
                input = in.nextLine();

                input = input.trim();

                comma = input.indexOf(',');

                i = Integer.parseInt(input.substring(0, comma));
                j = Integer.parseInt(input.substring(comma + 1));
            }

            /*if(i == 1 && j == 1 || i == 1 && j == size || i == size && j == 1 || i == size && j == size){
            System.out.println("You cannot make a guess at a corner");
            playgame();
        }*/
        //Todo:Check for a Hit
        //Todo:Check for a reflection
        //Todo:Check for a bounce

        if(game.hitcheck(i,j) == true){
            game.hitcheck(i,j);
        }
        else if (game.reflectionCheck(i,j) == true){
            game.reflectionCheck(i,j);
        }
        else if(game.deflectionCheck(i,j) == true){
            game.deflectionCheck(i,j);
        }
        else{
            game.straightRay(i,j);
        }

        //Todo:Print a statement telling the user they cannot place a fourth ball.
        if(i > 1 && i < size && j > 1 && j < size){
                if(game.placeball(i,j) == false){
                System.out.println("You have already placed a ball here.");
                playgame();
            }
            else if(i == 1 && j == 1 || i == 1 && j == size || i == size && j == 1 || i == size && j == size){
                    System.out.println("You cannot make a guess at a corner");
                    playgame();
                }
                else if(numball < 3){
                    box[i - 1][j - 1] = '*';
                    numball++;
            }
            else{
                System.out.println("You cannot place a fourth ball");
                playgame();
            }
        }
    }
    boolean placeball(int i,int j){
        if(box[i - 1][j - 1] == '*'){
            return false;
        }
        else {
            return true;
        }
    }
    /**
     * The hitcheck funtion takes in the row and column in the matrix, checks for Hit (H)
     *
     */
    public boolean hitcheck(int i,int j) {
        //todo: check if the ray causes a HIT as defined in the handout
        if(i == 1){
            for(int x = 0; x < box.length; x++){
                if(ballBox[x][j - 1] == '0'){
                    box[i - 1][j - 1] = 'H';
                    return true;
                }
            }
        }
        else if(j == 1){
            for(int x = 0; x < box.length; x++){
                if(ballBox[i - 1][x] == '0'){
                    box[i - 1][j - 1] = 'H';
                    return true;
                }
            }
        }
        else if(i == getSize()){
            for(int x = getSize() - 1; x > 0; x--){
                if(ballBox[x][j - 1] == '0'){
                    box[i - 1][j - 1] = 'H';
                    return true;
                }
            }
        }
        else if(j == getSize()){
            for(int x = getSize() - 1; x > 0; x--){
                if(ballBox[i - 1][x] == '0'){
                    box[i - 1][j - 1] = 'H';
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * The check funtion takes in the row and column in the matrix, checks for Reflection (R)
     *
     */
    public boolean reflectionCheck(int i,int j) {
        //todo: check if the ray causes a Reflection as defined in the handout
        if(i == 1){
            if(j != 1 && j != box.length){
                //45 degree reflection
                if(ballBox[1][j - 2] == '0' || ballBox[1][j] == '0'){
                    box[i - 1][j - 1] = 'R';
                    return true;
                }
                //check if there's an 0 on either side
                for(int x = 0; x < box.length; x++){
                    if(ballBox[x][j - 2] == '0' && ballBox[x][j] == '0'){
                        box[i - 1][j - 1] = 'R';
                        return true;
                    }
                }
            }
        }
        else if(j == 1){
            if(i != 1 && i != box.length){
                //first case
                if(ballBox[i - 2][1] == '0' || ballBox[i][1] == '0'){
                    box[i - 1][j - 1] = 'R';
                    return true;
                }
                //second case
                for(int x = 0; x < box.length; x++){
                    if(ballBox[i - 2][x] == '0' && ballBox[i][x] == '0'){
                        box[i - 1][j - 1] = 'R';
                        return true;
                    }
                }
            }
        }
        else if(i == getSize()){
            if(j != 1 && j != box.length){
                //first case
                if(ballBox[5][j - 2] == '0' || ballBox[5][j] == '0'){
                    box[i - 1][j - 1] = 'R';
                    return true;
                }
                //second case
                for(int x = getSize() - 1; x > 0; x--){
                    if(ballBox[x][j - 2] == '0' && ballBox[x][j] == '0'){
                        box[i - 1][j - 1] = 'R';
                        return true;
                    }
                }
            }
        }
        else if(j == getSize()){
            if(i != 1 && i != box.length){
                //first case
                if(ballBox[i - 2][5] == '0' || ballBox[i][5] == '0'){
                    box[i - 1][j - 1] = 'R';
                    return true;
                }
                //second case
                for(int x = getSize() - 1; x > 0; x--){
                    if(ballBox[i - 2][x] == '0' && ballBox[i][x] == '0'){
                        box[i - 1][j - 1] = 'R';
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     * The check funtion takes in the row and column in the matrix, checks for Divergence(#num)
     **/

    public boolean deflectionCheck(int i,int j) {
        //todo: check if the ray causes a Deflection as defined in the handout
        if(i == 1){
            for(int x = 0; x < box.length; x++){
                //to left
                if(ballBox[x][j - 2] == '0'){
                    numlink++;
                    char num = (char)(numlink + '0');
                    box[i - 1][j - 1] = num;
                    box[x - 1][size - 1] = num;
                    return true;
                }
                //to right
                else if(ballBox[x][j] == '0'){
                    numlink++;
                    char num = (char)(numlink + '0');
                    box[i - 1][j - 1] = num;
                    box[x - 1][0] = num;
                    return true;
                }
            }
        }
        else if(j == 1){
            for(int x = 0; x < box.length; x++){
                //above
                if(ballBox[i - 2][x] == '0'){
                    numlink++;
                    char num = (char)(numlink + '0');
                    box[i - 1][j - 1] = num;
                    box[size - 1][x - 1] = num;
                    return true;
                }
                //below
                else if(ballBox[i][x] == '0'){
                    numlink++;
                    char num = (char)(numlink + '0');
                    box[i - 1][j - 1] = num;
                    box[0][x - 1] = num;
                    return true;
                }
            }
        }
        else if(i == getSize()){
            for(int x = getSize() - 1; x > 0; x--){
                //to left
                if(ballBox[x][j - 2] == '0'){
                    numlink++;
                    char num = (char)(numlink + '0');
                    box[i - 1][j - 1] = num;
                    box[x + 1][size - 1] = num;
                    return true;
                }
                //to right
                else if(ballBox[x][j] == '0'){
                    numlink++;
                    char num = (char)(numlink + '0');
                    box[i - 1][j - 1] = num;
                    box[x + 1][0] = num;
                    return true;
                }
            }
        }
        else if(j == getSize()){
            for(int x = getSize() - 1; x > 0; x--){
                //above
                if(ballBox[i - 2][x] == '0'){
                    numlink++;
                    char num = (char)(numlink + '0');
                    box[i - 1][j - 1] = num;
                    box[size - 1][x + 1] = num;
                    return true;
                }
                //below
                else if(ballBox[i][x] == '0'){
                    numlink++;
                    char num = (char)(numlink + '0');
                    box[i - 1][j - 1] = num;
                    box[0][x + 1] = num;
                    return true;
                }
            }
        }
        return false;
    }
    /*
     The straightRay funtion takes in the row and column in the matrix, checks for Straight ray
     */

    public boolean straightRay(int i,int j){
        //todo: check if the ray is a straight ray as defined in the handout
        if(i == 1){
            for(int x = 0; x < box.length; x++){
                if(ballBox[x][j - 1] == '0'){
                    return false;
                }
            }
            numlink++;
            char num = (char)(numlink + '0');
            box[i - 1][j - 1] = num;
            box[size - 1][j - 1] = num;
            return true;
        }
        else if(j == 1){
            for(int x = 0; x < box.length; x++){
                if(ballBox[i - 1][x] == '0'){
                    return false;
                }
            }
            numlink++;
            char num = (char)(numlink + '0');
            box[i - 1][j - 1] = num;
            box[i - 1][size - 1] = num;
            return true;
        }
        else if(i == getSize()){
            for(int x = getSize() - 1; x > 0; x--){
                if(ballBox[x][j - 1] == '0'){
                    return false;
                }
            }
            numlink++;
            char num = (char)(numlink + '0');
            box[i - 1][j - 1] = num;
            box[0][j - 1] = num;
            return true;
        }
        else if(j == getSize()){
            for(int x = getSize() - 1; x > 0; x--){
                if(ballBox[i - 1][x] == '0'){
                    return false;
                }
            }
            numlink++;
            char num = (char)(numlink + '0');
            box[i - 1][j - 1] = num;
            box[i - 1][0] = num;
            return true;
        }
        return true;
    }
    /**
     * The following definitions are the getters and setter functions which have to be implemented
     *
     */
    public char[][] getbox() {
        return this.box;
    }
    public int getscore() {
        return this.score;
    }
    public int getNumball() {
        return this.numball;
    }
    public int getNumlink() {
        return this.numlink;
    }
    public boolean getend() {
        return this.end;
    }
    public int getSize(){
        return this.size;
    }
    public void setbox(char box[][]) {
        this.box = box;
    }
    public void setSize(int size){
        this.size = size;
    }
    public void setNumball(int Numball) {
        this.numball = Numball;
    }
    public void setNumlink(int Numlink) {
        this.numlink = Numlink;
    }
    public void setEnd(boolean end) {
        this.end = end;
    }
    public void setScore(int score) {
        this.score = score;
    }
}