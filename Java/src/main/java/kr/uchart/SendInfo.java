package kr.uchart;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SendInfo {

    private final String USER_AGENT = "Mozilla/5.0";

    private final String URL = "https://u-chart.kr/addInfo";

    private String programName;
    private HashMap<String,String> keys;
    public SendInfo(String programName){
        this.programName = programName;
        this.keys = new HashMap<>();
    }
    public SendInfo(String programName,HashMap<String,String> keys){
        this.programName = programName;
        this.keys = keys;
    }
    public void addKeyValuePair(String key,String value) {
        keys.put(key, value);
    }
    public void start(){

        if(keys.isEmpty()){
            System.out.println("Hashmap(keys) is empty.");
            System.out.println("Do you really want to submit data?");
            return;
        }
        if(programName == null){
            System.out.println("ProgramName is null.");
            return;
        }

        Runnable submittingTask = new Runnable() {
            @Override
            public void run() {
                submit();
            }
        };

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(submittingTask,0,30, TimeUnit.MINUTES);
    }
    private void submit(){
        if(keys.isEmpty())
            return;
        if(programName == null)
            return;

        StringBuilder postParams = new StringBuilder();
        //<필수 정보 입력
        postParams.append("program_name=");
        postParams.append(programName);
        postParams.append("&os=");
        postParams.append(System.getProperty("os.name"));
        postParams.append("&uuid=");
        postParams.append(getUUID());
        postParams.append("&key_and_value=");

        keys.forEach((key,value) -> postParams.append(key).append(";").append(value).append(","));

        postParams.deleteCharAt(postParams.lastIndexOf(","));

        try {
            submitData(postParams.toString());
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
    private void submitData(String data) throws IOException{
        URL url = new URL(URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent",USER_AGENT);
        con.setDoOutput(true);

        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(data);
        out.flush();
        out.close();

        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        StringBuffer buffer = new StringBuffer();
        String line;
        while((line = in.readLine()) != null){
            buffer.append(line);
        }

        in.close();

        if(responseCode >= 200 && responseCode < 300){
            System.out.println("Successfully submitted data.");
        }else{
            System.out.println("Something wrong was in the process of submitting data.");
            System.out.println("Response: "+buffer.toString());
        }

    }
    public String getUUID(){
        String uuid = "null";
        try{
            StringBuffer buf = new StringBuffer();
            Process puuid = Runtime.getRuntime().exec("wmic csproduct get UUID");
            BufferedReader sNumReader = new BufferedReader(new InputStreamReader(puuid.getInputStream()));
            String line = "";
            while ((line = sNumReader.readLine()) != null) {
                buf.append(line + "\n");
            }
            uuid = buf.toString().substring(buf.indexOf("\n"), buf.length()).trim();;

        }catch (IOException ex){
            ex.printStackTrace();
        }
        return uuid;
    }
}
