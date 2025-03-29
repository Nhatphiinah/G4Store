/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Home;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL; 
import static java.security.KeyRep.Type.SECRET;
import javax.json.Json; 
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.net.ssl.HttpsURLConnection;


/**
 *
 * @author 
 */
public class VerifyRecaptcha {
	public static final String url = "https://www.google.com/recaptcha/api/siteverify";
	public static final String secret = "6LefS_ApAAAAAKPY2c-RjEMXAYKh67hES3SMqH-6";
	private final static String USER_AGENT = "Mozilla/5.0";
        private static final String PROJECT_ID = "project-1716955414186";
	 public static boolean verify(String gRecaptchaResponse) {
        if (gRecaptchaResponse == null || gRecaptchaResponse.isEmpty()) {
            return false;
        }

        try {
            // Tạo kết nối đến URL của Google reCAPTCHA
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            // Tạo thông tin cho request
            String postParams = "secret=" + secret+ "&response=" + gRecaptchaResponse;

            // Gửi request
            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(postParams);
                wr.flush();
            }

            // Đọc response từ server
            int responseCode = con.getResponseCode();
            if (responseCode != 200) {
                System.out.println("Error: " + responseCode);
                return false;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            // Xác thực response từ server
            return parseResponse(response.toString());

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean parseResponse(String jsonResponse) {
        // Parse JSON response và kiểm tra 'success' value
        return jsonResponse.contains("\"success\": true");
    }
}





