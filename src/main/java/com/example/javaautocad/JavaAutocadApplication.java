package com.example.javaautocad;

import com.example.javaautocad.AutoCad.domain.Polyline;
import com.example.javaautocad.AutoCad.factory.AutoFactory;
import com.example.javaautocad.AutoCad.parser.AutoParser;
import com.example.javaautocad.AutoCad.service.AutoCadServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaAutocadApplication implements CommandLineRunner {

    @Override
    public void run(String... args) {
        AutoParser parser = new AutoParser();
        AutoFactory factory = new AutoFactory();
        AutoCadServiceImpl service = new AutoCadServiceImpl(factory);

        Polyline polyline = service.autoStarts(parser, "/Users/sanghyunyoun/springtest/testfile.dxf");
        System.out.println("총 선분 개수: " + polyline.getLines().size());
    }
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(JavaAutocadApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }
}

