package kr.uchart;

public class Example {

    public static void main(String[] args){
        SendInfo task = new SendInfo("MyProgram");
        task.addKeyValuePair("my key","my value");
        task.start();
    }
}
