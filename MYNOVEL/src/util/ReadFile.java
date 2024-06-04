package util;

import builder.NovelConcreteBuilder;
import entity.Admin;
import entity.Novel;
import entity.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {
    public static List<String> readFile(String path) {
        List<String> readFileList = new ArrayList<>();
        File file = new File(path);
        try (FileReader fileReader = new FileReader(file);
             BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                readFileList.add(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage() + " [T-T]");
        }
        return readFileList;
    }

    public static List<User> readFileUser(String path) {
        List<String> propertyOfUserList = readFile(path);
        List<User> userList = new ArrayList<>();
        try {
            final int INDEX_OF_NAME = 0;
            final int INDEX_OF_FULL_NAME = 1;
            final int INDEX_OF_BIRTHDAY = 2;
            final int INDEX_OF_PHONE = 3;
            final int INDEX_OF_EMAIL = 4;
            final int INDEX_OF_PASSWORD = 5;
            final int INDEX_OF_ADDRESS = 6;
            for (String propertyOFUser : propertyOfUserList) {
                String[] properties = propertyOFUser.split("; ");
                userList.add(new User(
                        properties[INDEX_OF_NAME],
                        properties[INDEX_OF_FULL_NAME],
                        new SimpleDateFormat("dd-MM-yyyy").parse(properties[INDEX_OF_BIRTHDAY]),
                        properties[INDEX_OF_PHONE],
                        properties[INDEX_OF_EMAIL],
                        properties[INDEX_OF_PASSWORD],
                        properties[INDEX_OF_ADDRESS]));
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }

    public static List<Admin> readFileAdmin(String path) {
        List<String> propertyOfAdminList = readFile(path);
        List<Admin> adminList = new ArrayList<>();
        try {
            final int INDEX_OF_NAME = 0;
            final int INDEX_OF_FULL_NAME = 1;
            final int INDEX_OF_BIRTHDAY = 2;
            final int INDEX_OF_PHONE = 3;
            final int INDEX_OF_EMAIL = 4;
            final int INDEX_OF_PASSWORD = 5;
            final int INDEX_OF_ADDRESS = 6;
            for (String propertyOFAdmin : propertyOfAdminList) {
                String[] properties = propertyOFAdmin.split("; ");
                adminList.add(new Admin(
                        properties[INDEX_OF_NAME],
                        properties[INDEX_OF_FULL_NAME],
                        new SimpleDateFormat("dd-MM-yyyy").parse(properties[INDEX_OF_BIRTHDAY]),
                        properties[INDEX_OF_PHONE],
                        properties[INDEX_OF_EMAIL],
                        properties[INDEX_OF_PASSWORD],
                        properties[INDEX_OF_ADDRESS]));
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return adminList;
    }

    public static List<Novel> readFileNovel(String path) {
        List<String> propertyOfNovelList = readFile(path);
        List<Novel> novelList = new ArrayList<>();
        try {
            final int INDEX_OF_NOVEL_PUBLIC = 0;
            final int INDEX_OF_NOVEL_AUTHOR = 1;
            final int INDEX_OF_NOVEL_NAME = 2;
            final int INDEX_OF_NOVEL_TYPE = 3;
            final int INDEX_OF_NOVEL_DATE = 4;
            final int INDEX_OF_NOVEL_PRICE = 5;
            final int INDEX_OF_NOVEL_CHAPTER = 6;
            final int INDEX_OF_NOVEL_DETAIL = 7;
            for (String propertyOfNovel : propertyOfNovelList) {
                String[] property = propertyOfNovel.split("; ");
                novelList.add(new NovelConcreteBuilder()
                        .getPublic(Boolean.parseBoolean(property[INDEX_OF_NOVEL_PUBLIC]))
                        .getAuthor(property[INDEX_OF_NOVEL_AUTHOR])
                        .getNovelName(property[INDEX_OF_NOVEL_NAME])
                        .getNovelType(property[INDEX_OF_NOVEL_TYPE])
                        .getNovelDate(property[INDEX_OF_NOVEL_DATE])
                        .getPrice(Integer.parseInt(property[INDEX_OF_NOVEL_PRICE]))
                        .getChapter(Integer.parseInt(property[INDEX_OF_NOVEL_CHAPTER]))
                        .getNovelDescription(property[INDEX_OF_NOVEL_DETAIL])
                        .build());
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return novelList;
    }
}
