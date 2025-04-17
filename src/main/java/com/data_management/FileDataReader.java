package com.data_management;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileDataReader implements DataReader{
    private final String filePath;

    public FileDataReader(String filePath){
        this.filePath=filePath;

    }

//    int patientId, double measurementValue, String recordType, long timestamp)
    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        FileReader fileReader=new FileReader(filePath);
//        add a buffer reader so it reads line by line
        BufferedReader reader = new BufferedReader(fileReader);

        reader.readLine();
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
//            there are 4 categories, so if the line is invalid skip
            if (parts.length != 4) continue;
            int patientId = Integer.parseInt(parts[0]);
            double measurementValue = Double.parseDouble(parts[1]);
            String recordType = parts[2];
            long timestamp = Long.parseLong(parts[3]);

            dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
        }

        reader.close();
    }


    }

