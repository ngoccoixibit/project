package util;

import entity.Account;
import entity.Novel;

import java.io.*;

public class WriteFile {

    public static void WriteFileAccount(String path, Account account) {
        try(FileWriter fileWriter = new FileWriter(path, true);
            BufferedWriter writer = new BufferedWriter(fileWriter)) {
            writer.write(account.getAccountName() + "; " + account.getFullName() + "; " + account.getBirthday() + "; " + account.getPhone() + "; " + account.getEmail() + "; " + account.getPassword() + "; " + account.getAddress() + "\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void WriteFileNovel(String path, Novel novel) {
        try (FileWriter fileWriter = new FileWriter(path, true);
             BufferedWriter writer = new BufferedWriter(fileWriter)){
            writer.write(novel.isPublic() + "; " + novel.getAuthor() + "; " + novel.getNovelName() + "; " + novel.getType() + "; " + novel.getDateOfPublication() + "; " + novel.getDescription() + "\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
