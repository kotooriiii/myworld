package com.github.kotooriiii.myworld.util;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SystemEnvironmentUtils
{
    // Method to find .env files recursively starting from a given directory
    private static List<File> findEnvironmentFiles(File startDir) {
        List<File> envFiles = new ArrayList<>();
        File[] files = startDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    envFiles.addAll(findEnvironmentFiles(file));
                } else if (file.getName().endsWith(".env")) {
                    envFiles.add(file);
                }
            }
        }
        return envFiles;
    }

    // Method to load .env files using dotenv-java
    public static void loadEnvironmentFiles() {
        List<File> envFiles = findEnvironmentFiles(new File("./"));
        for (File envFile : envFiles) {
            if (envFile.exists()) {
                Dotenv dotenv = Dotenv.configure()
                        .directory(envFile.getParent())
                        .filename(envFile.getName())
                        .load();
                dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
            }
        }
    }
}
