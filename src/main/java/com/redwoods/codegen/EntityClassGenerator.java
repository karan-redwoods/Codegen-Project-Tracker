package com.redwoods.codegen;

import com.redwoods.codegen.config.ApplicationConfig;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

@Component
public class EntityClassGenerator {

    private ApplicationConfig applicationConfig;

    private CodeWriter codeWriter;

    public EntityClassGenerator(ApplicationConfig applicationConfig, CodeWriter codeWriter){
        this.applicationConfig = applicationConfig;
        this.codeWriter = codeWriter;
    }

    public void generateEntityClass(String entityName){
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties prop = new Properties();

        try {
            prop.load(new FileInputStream("src/main/resources/" + entityName.toLowerCase() + "-entity.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Set<String> propertyNames = prop.stringPropertyNames();
        String content = "package " + applicationConfig.getPackageName() + ".models;\n\n" +
                "import jakarta.persistence.*;\n\n"+
                "@Entity\n"+
                "public class " + entityName + "{\n\n";
        for(String propertyName : propertyNames){
            String propertyValue = prop.getProperty(propertyName);
            content = content + "   private " + propertyValue + " "+ propertyName +";\n";
        }
        content = content + "}";

        codeWriter.writeToFile(entityName + ".java", content);
    }
    public void generateEntityClassRole(String entityName){
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties prop = new Properties();

        try {
            prop.load(new FileInputStream("src/main/resources/" + entityName.toLowerCase() + "-entity.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Set<String> propertyNames = prop.stringPropertyNames();
        String content = "package " + applicationConfig.getPackageName() + ".models;\n\n" +
                "public class " + entityName + "{\n\n";
        for(String propertyName : propertyNames){
            String propertyValue = prop.getProperty(propertyName);
            content = content + "   private " + propertyValue + " "+ propertyName +";\n";
        }
        content = content + "}";

        codeWriter.writeToFile(entityName + ".java", content);
    }
}
