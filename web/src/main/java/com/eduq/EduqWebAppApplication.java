package com.eduq;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@SpringBootApplication
public class EduqWebAppApplication {

	public static void main(String[] args) throws IOException {
		FileInputStream refreshToken = new FileInputStream("/Users/gabrielsilva/eduqwebapp/google-services.json");

		SpringApplication.run(EduqWebAppApplication.class, args);
		GoogleCredentials credentials = GoogleCredentials.fromStream(refreshToken);
		FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(credentials).setProjectId("eduq-f49fd")
				.build();
		FirebaseApp.initializeApp(options);
	}
}
