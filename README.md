U-Chart는 비영리 웹 사이트로서, 1인 개발자를 돕기 위해 제작된 사이트입니다.

C,C++,C#,JAVA로 제작한 프로그램이 배포되었을 때, 프로그램을 추적하여 사용 통계 보고서를 작성해줍니다.

- 사용법 -


https://u-chart.kr 에 접속합니다.


오른쪽 상단의 '계정'을 누르고 회원가입 페이지에 접속해 회원가입 합니다.


회원가입 후 오른쪽 상단 '계정' 옆의 '프로그램 추가'를 클릭합니다. 

첫번째 칸에는 자신이 만든 프로그램 이름을 적습니다. 

아래칸은 어떤 정보를 수집할 것인지 적어야 하는데, 

수집할 데이터 이름;타입(자료형),수집할 데이터 이름;타입(자료형) 형태로 적어야 합니다. 

예를 들어 자바 버전과 임의의 수를 수집하고 싶다면

자바 버전;text,임의의 수;number 

와 같이 적을 수 있습니다. 말 그대로 type은 수집할 데이터가 숫자인지, 문자열인지를 정하는 겁니다.

그리고 예시에서 볼 수 있듯이 수집할 데이터가 여러개라면 데이터이름1;자료형1,데이터이름2;자료형2

와 같이 ','를 추가해야 합니다.

이 과정이 끝났다면 프로그램 언어를 선택하고 제출 버튼을 클릭합니다.

정상적으로 등록되었다는 메시지가 나오면 된 겁니다.


오른쪽 상단의 '프로그램 목록'을 클릭하면 추가한 프로그램이 뜹니다. 거기서 방금 추가한 프로그램 이름을 클릭하면

웹 사이트가 수집한 프로그램 사용 통계를 볼 수 있습니다.

아직 데이터가 수집되지 않은 상태라면 위와 같은 화면을 보실 수 있습니다.


이제 프로그램을 추가했으니 자신의 프로그램에 코드를 삽입할 시간입니다.

홈 화면에서 시작하기 버튼을 클릭하여 위 페이지로 접속하고, 다운로드 버튼을 클릭합니다.

여기서는 자바를 선택하겠습니다.


클릭하면 위의 GitHub 와 연결되는데 두번째 폴더에 들어있는 SendInfo.java 클래스가 데이터를 서버로 보내는 코드이므로 반드시 다운받아 프로그램에 삽입해야 합니다.

Example 폴더에는 예제가 첨부되어 있습니다.

package io.github.singlerr;

 

import java.util.Random;

 

public class Main {

    public static void main(String[] args){

            String javaVersion = System.getProperty("java.version");

            int num  = new Random().nextInt(10); //0~10 난수

            System.out.println(javaVersion);

            SendInfo sendInfo = new SendInfo("singlerr@naver.com/AProgram");

            sendInfo.addKeyValuePair("자바 버전",javaVersion);

            sendInfo.addKeyValuePair("난수",String.valueOf(num));

 

            sendInfo.start();

    }

}

Colored by Color Scripter

cs

예제로 위의 코드를 작성해보았습니다.

아까 자바 버전과 임의의 수를 수집할 데이터로 정하였으니 자바 버전과 0과 10 사이의 난수를 만듭니다.

SendInfo 객체를 생성하는데 파라미터로 가입한 이메일과 프로그램 이름이 필요합니다.

이를 프로그램 id라 하며 이메일/프로그램이름 형식으로 적습니다.

그 다음 addKeyValuePair 함수를 이용해 서버로 보낼 데이터를 추가합니다.

Key는 위에서 정한 데이터 이름이며, Value는 보낼 값입니다.

여기서는 자바 버전에 javaVersion을, 난수에 num 변수를 넣었습니다

단 여기서 주의하실 점은 반드시 Value는 문자열이어야 한다는 것입니다.

그래서 num을 string으로 변환해주었습니다.

sendinfo.start(); 구문으로 데이터 전송 스레드를 시작합니다.

start 이후 30분 간격으로 데이터를 서버로 전송합니다.

SendInfo.java 클래스를 수정하여 데이터 전송 간격을 조정하실 수 있습니다.
