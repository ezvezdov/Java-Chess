package cz.cvut.fel.pjv.model.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FilesIO {

    PrintWriter printWriter;
    Scanner scanner;

    private File file;

    /**
     * Get file from resources folder
     *
     * @param fileName path of file to get
     * @return File if it's opened successfully
     * @throws NullPointerException
     */
    private File getFileFromResource(String fileName) throws NullPointerException {
        try {
            String filePath = getClass().getResource(fileName).getFile();
            file = new File(filePath);
            return file;
        } catch (NullPointerException e) {
            System.out.println("Board annotation isn't available!");
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param fileName path of file to get
     * @return File if it's opened successfully
     * @throws NullPointerException
     */
    private File getFile(String fileName) throws NullPointerException{
        try {
            file = new File(fileName);
            return file;
        } catch (NullPointerException e) {
            System.out.println("Error while saving file!");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Set output stream (file from resorces)
     *
     * @param filePath path of file to stream
     */
    void setPrintStream(String filePath){
        file = getFileFromResource(filePath);
        try {
            printWriter = new PrintWriter(this.file);
        } catch (FileNotFoundException e) {
            System.out.println("Error while opening " + filePath + "file for write!");
            e.printStackTrace();
        }
    }

    /**
     * Set output stream (file from program folder)
     *
     * @param filePath path of file to stream
     */
    void setPrintStreamForUser(String filePath){
        File file = getFile(filePath);
        createFile(filePath);


        try {
            printWriter = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            System.out.println("Error with opening " + filePath + "file for write!");
            e.printStackTrace();
        }
    }

    void closePrintStream(){
        printWriter.close();
        printWriter = null;
    }

    /**
     * Set input stream
     *
     * @param filePath path of file to stream
     */
    void setScanner(String filePath){
        File file = getFileFromResource(filePath);
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Error while setting input stream!");
            e.printStackTrace();
        }
    }

    private void createFile(String filePath){
        File file = getFile(filePath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Error while creating file!");
            e.printStackTrace();
        }
    }

    void createDirectory(String directoryPath){
        new File(directoryPath).mkdirs();

    }

}
