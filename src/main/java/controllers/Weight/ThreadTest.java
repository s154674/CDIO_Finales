package controllers.Weight;

/**
 * Created by Johan on 09-06-2017.
 */
public class ThreadTest {
    public static void main(String[] args){
        WeightController wc = new WeightController();
        Thread T = new Thread(wc,"lol");
        T.start();
    }
}
