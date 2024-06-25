package util;

import entity.Account;
import entity.Novel;
import entity.User;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WriteFile {

    public static void WriteFileAccount(String path, Account account) {
        try(FileWriter fileWriter = new FileWriter(path, true);
            BufferedWriter writer = new BufferedWriter(fileWriter)) {
            writer.write( account.getEmail() + "; " + account.getName() + "; " + account.getPassword() + "; " + account.getFullName() + "; " + account.getBirthday() + "; " + account.getPhone() + "; " + account.getAddress() + "\n");
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

    public static void WriteFileInvoiceHistory(String path, Account account, List<Novel> novelCart) {
        try (FileWriter fileWriter = new FileWriter(path, true);
        BufferedWriter Writer = new BufferedWriter(fileWriter)) {
            for (Novel novel : novelCart) {
                Writer.write(account.getName() + "; " + novel.getNovelName() + "; " + novel.getPrice() + "; " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
