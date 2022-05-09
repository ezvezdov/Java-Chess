package cz.cvut.fel.pjv.model.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FilesIO {

    protected PrintWriter printWriter;
    protected Scanner scanner;

    /**
     * Get file from resources folder
     *
     * @param fileName path of file to get
     * @return File if it's opened successfully
     * @throws NullPointerException
     */
    protected File getFileFromResource(String fileName) throws NullPointerException {
        try {
            String filePath = getClass().getResource(fileName).getFile();
            File file = new File(filePath);
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
    protected File getFile(String fileName) throws NullPointerException{
        try {
            new File(fileName).mkdirs();
            File file = new File(fileName);
            return file;
        } catch (NullPointerException e) {
            System.out.println("Problem with saving file!");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Set output stream (file from resorces)
     *
     * @param filePath path of file to stream
     */
    protected void setPrintStream(String filePath){
        File file = getFileFromResource(filePath);
        try {
            printWriter = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set output stream (file from program folder)
     *
     * @param filePath path of file to stream
     */
    protected void setPrintStreamForUser(String filePath){
        File file = getFile(filePath);

        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Error with opening " + filePath + "file!");
            return;
//            e.printStackTrace();
        }

        try {
            printWriter = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            System.out.println("Error with opening " + filePath + "file!");
            return;
//            e.printStackTrace();
        }
    }

    /**
     * Set input stream
     *
     * @param filePath path of file to stream
     */
    protected void setScanner(String filePath){
        File file = getFileFromResource(filePath);
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
