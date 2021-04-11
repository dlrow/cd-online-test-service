package com.cd.onlinetest.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cd.onlinetest.enums.DifficultyLevel;
import com.cd.onlinetest.enums.Opt;
import com.cd.onlinetest.mongoDomain.Question;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GoogleSheetReader {
    private static final String APPLICATION_NAME = "CD Online test";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GoogleSheetReader.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
    
    /*public static void main(String[] args) throws IOException, GeneralSecurityException {
    	GoogleSheetReader g = new GoogleSheetReader();
    	List<Question> q = g.extractQuestionFromExcel("1K-L8kK2yZ_KXR-_YIiM1ahV7hnPg1MNiHHCE2_7aDbA","2:3");
    	System.out.println(q);
	}*/

    //TODO : read in chunks and pass path
    public  List<Question> extractQuestionFromExcel(String path, String range) throws IOException, GeneralSecurityException {
    	//path = "1K-L8kK2yZ_KXR-_YIiM1ahV7hnPg1MNiHHCE2_7aDbA";
    	List<Question> questions = new LinkedList<Question>();
    	 // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport(); 							  
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        ValueRange responseHeader = service.spreadsheets().values()
                .get(path, "1:1")
                .execute();
        ValueRange response = service.spreadsheets().values()
                .get(path, range)
                .execute();
        List<List<Object>> headerRow = responseHeader.getValues();
        List<List<Object>> values = response.getValues();
        
        if (values == null || values.isEmpty()) {
           log.info("No data found.");
        } else {
            System.out.println("Name, Major");
            for (List<Object> row : values) {
            	Question ques = extractQuestion(headerRow.get(0), row);
    			questions.add(ques);
            }
        }
        return questions;
      
    }
    
    private Question extractQuestion(List<Object> headerRow, List<Object> currRow) {
		Question ques = new Question();
		Map<Opt, String> options = new LinkedHashMap<>();
		for (int j = 0; j < currRow.size(); j++) {
			ques.setId((String) currRow.get(headerRow.indexOf("QuestionId")));
			ques.setQuestion((String) currRow.get(headerRow.indexOf("Question_Text")));
			options.put(Opt.OPT1,(String) currRow.get(headerRow.indexOf("Opt1")));
			options.put(Opt.OPT2,(String) currRow.get(headerRow.indexOf("Opt2")));
			options.put(Opt.OPT3,(String) currRow.get(headerRow.indexOf("Opt3")));
			options.put(Opt.OPT4,(String) currRow.get(headerRow.indexOf("Opt4")));
			ques.setCorrectAns(Opt.identify((String) currRow.get(headerRow.indexOf("Correct"))));
			ques.setLevel(DifficultyLevel.identify ((String) currRow.get(headerRow.indexOf("Type"))));	
		}
		ques.setOptions(options);
		return ques;
	}
	
}