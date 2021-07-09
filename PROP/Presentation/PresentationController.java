package FONTS.Presentation;

import FONTS.Domain.DomainController;

public class PresentationController {
    static DomainController domain;
    static JFrames v1;
    static String lastPage;

    String gameInfo[];

    public int height, width;
    public boolean p1human = true, p2human = true;



    public  PresentationController(){
        v1 = new JFrames(this);
        lastPage = "";
    }

    public static JFrames getV1() {
        return v1;
    }

    public static void setV1(JFrames v1) {
        PresentationController.v1 = v1;
    }

    public void linkDomain(DomainController domain) {
        this.domain = domain;
    }

    public void showLoginPage(){

    }

    public static void showMainPage(){
        lastPage = "MainPage";
        v1.showMainPage();
    }

    public static void showNewGamePage(){
        v1.showNewGamePage();
    }

    public static void showLoadGamePage(){
        v1.showLoadGamePage();
    }

    public static void showGamePage(String from){
        lastPage = from;
        v1.showGamePage();
    }

    public static void showRankingPage(){
        v1.showRanking();
    }

    public static void showSettingsPage(){
        v1.showSettingsPage();
    }

    public static void goBack(){
        if(lastPage.equals("LoadGamePage")) v1.showLoadGamePage();
        else if(lastPage.equals("NewGamePage")) v1.showNewGamePage();
        else v1.showMainPage();

    }

    public void setInfogame(String[] info) {
        domain.setInfo(info);
        this.height = Integer.parseInt(info[0]);
        this.width  = Integer.parseInt(info[0]);

        this.p1human = info[7].equals("Human");
        this.p2human = info[10].equals("Human");
        System.out.println(p1human);
        System.out.println(p2human);
    }

    public void newGameFromPresentation() {
        domain.newGameFromPresentation();
    }

    public Boolean makeMoveFromPresentation(int x, int y) {
        return domain.makeMoveFromPresentation(x,y);
    }

    public static int[][] getBoard() {
        return domain.getBoard();
    }

    public /*Vector<String>*/ void showRankingById(){
        //return this.domainController.getRankingById();
    }
    public /*Vector<String>*/ void showGameLoads(){
        //  return this.persistenceController.getGameLog();
    }

    public void loadGame(String stext) {
        domain.loadGame(stext);
        this.height = DomainController.getHeight();
        this.width = DomainController.getWidth();
        this.p1human = DomainController.getIsHuman(1);
        this.p2human = DomainController.getIsHuman(2);

    }

    public void saveandquit() {
        domain.saveAndQuit();
    }

    public boolean gameOver() {
        return domain.gameOver();
    }

    public int getScore1() {
        return domain.getScore1();
    }

    public int getScore2() {
        return domain.getScore2();
    }
    //public Vector<String> showGamePage(){
    //   this.persistenceController.getPos();
    //  or
    //          this.domainController.
    // 7// }

}
