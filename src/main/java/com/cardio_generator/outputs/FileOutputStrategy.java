package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;
/**
 * Implements the {@link OutputStrategy} interface to output patient information to a file.
 * <p>
 * This class handles the creation of directories and files, and appends patient data
 * into text files based on their label.
 */

//    here the class name should be the same as the file name
//    also the class name should be un the form UpperCamelCase.
public class FileOutputStrategy implements OutputStrategy {

//    the name should be in lowerCamelCase.
    private String baseDirectory;

//    this also should be in lowerCamelCase.
    private final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>();


    public FileOutputStrategy(String baseDirectory) {

        this.baseDirectory = baseDirectory;
    }
    /**
     * Outputs patient data to a text file.
     * <p>
     * This method creates a directory (if it doesn't already exist), constructs a file path based
     * on the label, stores it in a map if it hasn't been used before, and then appends the
     * patient data to the corresponding file.
     *
     * @param patientId  the ID of the patient
     * @param timestamp  the timestamp of the data
     * @param label      the label/category of the data (used to determine the filename)
     * @param data       the actual patient data to be written
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the FilePath variable
//        this should also be in lowerCamelCase
        String filePath = fileMap.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());


        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
//    a getter method instead of a public access modifier
//    is this okay?
    public ConcurrentHashMap<String, String> getFileMap(){
        return fileMap;
    }

}