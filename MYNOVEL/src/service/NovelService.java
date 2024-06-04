package service;

import builder.NovelConcreteBuilder;
import entity.Novel;
import exception.InvalidChoiceException;
import util.Input;
import util.ReadFile;
import util.WriteFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class NovelService {
    private static final NovelService NOVEL_SERVICE = new NovelService();

    public static NovelService getInstance() {
        return NOVEL_SERVICE;
    }

    AccountService accountService = AccountService.getInstance();

    public String getAuthorName() {
        return accountService.getCurrentAccount().getAccountName();
    }

    public final static List<Novel> NOVEL_LIST = new ArrayList<>();

    static {
        try {
            List<Novel> novelList = ReadFile.readFileNovel("C:\\Users\\ngocc\\PROJECT JAVA\\MYNOVEL\\src\\data\\novel.csv");
            NOVEL_LIST.addAll(novelList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private NovelService() {}

    public List<Novel> getNovelList() {
        return NOVEL_LIST;
    }

    public void displayAllNovelList() {
        try {
            System.out.println("\n==> [NOVEL LIST]");
            List<Novel> novelList = getNovelList();
            for (Novel novel : novelList) {
                System.out.println(novel);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void displayPublicNovelList() {
        try {
            System.out.println("\n==> [NOVEL LIST]");
            for (Novel novel : NOVEL_LIST) {
                if (novel.isPublic()) {
                    System.out.println(novel);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void searchNovel() {
        do {
            try {
                System.out.print("""
                        \nSearch novel by...
                            [1] ID
                            [2] Name
                            [3] Type
                            [4] Price
                            [5] Author
                            [6] Return
                        """);
                int choice = Input.scanIntegerLine("Enter your choice: ");
                switch (choice) {
                    case 1 -> {
                        try {
                            int ID = Input.scanIntegerLine("Enter novel ID: ");
                            boolean isExistIdOfNovel = false;
                            for (Novel novel : NOVEL_LIST) {
                                if (novel.getNovelID() == ID) {
                                    displayPublicNovelList();
                                    isExistIdOfNovel = true;
                                }
                            }
                            if (!isExistIdOfNovel) {
                                throw new RuntimeException("ID not found! (T-T)");
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    case 2 -> {
                        try {
                            String name = Input.scanStringLine("Enter novel name: ");
                            boolean isExistNameOfNovel = false;
                            for (Novel novel : NOVEL_LIST) {
                                if (name.equalsIgnoreCase(novel.getNovelName())) {
                                    displayPublicNovelList();
                                    isExistNameOfNovel = true;
                                }
                            }
                            if (!isExistNameOfNovel) {
                                throw new RuntimeException("Name not found! [T-T]");
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    case 3 -> {
                        try {
                            String type = Input.scanStringLine("Enter novel type: ");
                            boolean isExistTypeOfNovel = false;
                            for (Novel novel : NOVEL_LIST) {
                                if (type.equalsIgnoreCase(novel.getType())) {
                                    displayPublicNovelList();
                                    isExistTypeOfNovel = true;
                                }
                            }
                            if (!isExistTypeOfNovel) {
                                throw new RuntimeException("Type not found! [T-T]");
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    case 4 -> {
                        try {
                            int price = Input.scanIntegerLine("Enter novel price: ");
                            boolean isExistPriceOfNovel = false;
                            for (Novel novel : NOVEL_LIST) {
                                if (price == novel.getPrice()) {
                                    displayPublicNovelList();
                                    isExistPriceOfNovel = true;
                                }
                            }
                            if (!isExistPriceOfNovel) {
                                throw new RuntimeException("Price not found! [T-T]");
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    case 5 -> {
                        try {
                            String author = Input.scanStringLine("Enter author name: ");
                            boolean isExistAuthorOfNovel = false;
                            for (Novel novel : NOVEL_LIST) {
                                if (author.equalsIgnoreCase(novel.getAuthor())) {
                                    displayPublicNovelList();
                                    isExistAuthorOfNovel = true;
                                }
                            }
                            if (!isExistAuthorOfNovel) {
                                throw new RuntimeException("Author not found! [T-T]");
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    case 6 -> {
                        return;
                    }
                    default -> throw new InvalidChoiceException("There isn't such a choice!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void sortNovel() {
        do {
            try {
                System.out.print("""
                        \nSort novel by...
                            [1] Price
                            [2] Date
                            [3] ID
                            [4] Chapter
                            [5] Return
                        """);
                int choice = Input.scanIntegerLine("Enter your choice: ");
                switch (choice) {
                    case 1 -> {
                        try {
                            System.out.print("""
                                    How to sort?
                                        [1] Ascending
                                        [2] Descending
                                    """);
                            choice = Input.scanIntegerLine("Enter your choice: ");
                            switch (choice) {
                                case 1 -> {
                                    NOVEL_LIST.sort(Comparator.comparingDouble(Novel::getPrice));
                                    displayPublicNovelList();
                                }
                                case 2 -> {
                                    NOVEL_LIST.sort(Comparator.comparingDouble(Novel::getPrice).reversed());
                                    displayPublicNovelList();
                                }
                                default -> throw new InvalidChoiceException("There isn't such a choice!");
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    case 2 -> {
                        try {
                            System.out.print("""
                                    How to sort?
                                        [1] Ascending
                                        [2] Descending
                                    """);
                            choice = Input.scanIntegerLine("Enter your choice: ");
                            switch (choice) {
                                case 1 -> {
                                    NOVEL_LIST.sort(Comparator.comparing(Novel::getDateOfPublication));
                                    displayPublicNovelList();
                                }
                                case 2 -> {
                                    NOVEL_LIST.sort(Comparator.comparing(Novel::getDateOfPublication).reversed());
                                    displayPublicNovelList();
                                }
                                default -> throw new InvalidChoiceException("There isn't such a choice!");
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    case 3 -> {
                        try {
                            System.out.print("""
                                    How to sort?
                                        [1] Ascending
                                        [2] Descending
                                    """);
                            choice = Input.scanIntegerLine("Enter your choice: ");
                            switch (choice) {
                                case 1 -> {
                                    NOVEL_LIST.sort(Comparator.comparingInt(Novel::getNovelID));
                                    displayPublicNovelList();
                                }
                                case 2 -> {
                                    NOVEL_LIST.sort(Comparator.comparingInt(Novel::getNovelID).reversed());
                                    displayPublicNovelList();
                                }
                                default -> throw new InvalidChoiceException("There isn't such a choice!");
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    case 4 -> {
                        try {
                            System.out.print("""
                                    How to sort?
                                        [1] Ascending
                                        [2] Descending
                                    """);
                            choice = Input.scanIntegerLine("Enter your choice: ");
                            switch (choice) {
                                case 1 -> {
                                    NOVEL_LIST.sort(Comparator.comparingInt(Novel::getChapter));
                                    displayPublicNovelList();
                                }
                                case 2 -> {
                                    NOVEL_LIST.sort(Comparator.comparingInt(Novel::getChapter).reversed());
                                    displayPublicNovelList();
                                }
                                default -> throw new InvalidChoiceException("There isn't such a choice!");
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    case 5 -> {
                        return;
                    }
                    default -> throw new InvalidChoiceException("There isn't such a choice!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void displayYourNovelList() {
        try {
            String author = getAuthorName();
            System.out.println("\n==> [YOUR NOVEL]");
            for (Novel novel : NOVEL_LIST) {
                if (novel.getAuthor().equals(author)) {
                    System.out.println(novel);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void editYourNovel() {
        do {
            try {
                System.out.print("""
                        \nYou want to edit...
                            [1] Name
                            [2] Genre
                            [3] Date of publication
                            [4] Price
                            [5] Description
                            [6] Chapter
                            [7] Status
                            [8] Return
                        """);
                int choice = Input.scanIntegerLine("Enter your choice: ");
                switch (choice) {
                    case 1 -> {
                        boolean isNovelNameChange = false;
                        displayYourNovelList();
                        int ID = Input.scanIntegerLine("Choose ID: ");
                        for (Novel novel : NOVEL_LIST) {
                            if (novel.getNovelID() == ID) {
                                String name = Input.scanStringLine("Enter new name: ");
                                novel.setNovelName(name);
                                isNovelNameChange = true;
                                System.out.println("Change successfully!");
                            }
                        }
                        if (!isNovelNameChange) {
                            throw new RuntimeException("Change unsuccessfully!");
                        }
                    }
                    case 2 -> {
                        boolean isNovelGenreChange = false;
                        displayYourNovelList();
                        int ID = Input.scanIntegerLine("Choose ID: ");
                        for (Novel novel : NOVEL_LIST) {
                            if (novel.getNovelID() == ID) {
                                String genre = Input.scanStringLine("Enter new genre: ");
                                novel.setType(genre);
                                isNovelGenreChange = true;
                                System.out.println("Change successfully!");
                            }
                        }
                        if (!isNovelGenreChange) {
                            throw new RuntimeException("Change unsuccessfully!");
                        }
                    }
                    case 3 -> {
                        boolean isNovelDateChange = false;
                        displayYourNovelList();
                        int ID = Input.scanIntegerLine("Choose ID: ");
                        for (Novel novel : NOVEL_LIST) {
                            if (novel.getNovelID() == ID) {
                                String date = Input.scanLineRegex("Enter new date: ", "DATE");
                                Date dateOfPublication = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                                novel.setDateOfPublication(dateOfPublication);
                                isNovelDateChange = true;
                                System.out.println("Change successfully!");
                            }
                        }
                        if (!isNovelDateChange) {
                            throw new RuntimeException("Change unsuccessfully!");
                        }
                    }
                    case 4 -> {
                        boolean isNovelPriceChange = false;
                        displayYourNovelList();
                        int ID = Input.scanIntegerLine("Choose ID: ");
                        for (Novel novel : NOVEL_LIST) {
                            if (novel.getNovelID() == ID) {
                                int price = Input.scanIntegerLine("Enter new price: ");
                                novel.setPrice(price);
                                isNovelPriceChange = true;
                                System.out.println("Change successfully!");
                            }
                        }
                        if (!isNovelPriceChange) {
                            throw new RuntimeException("Change unsuccessfully!");
                        }
                    }
                    case 5 -> {
                        boolean isNovelDescriptionChange = false;
                        displayYourNovelList();
                        int ID = Input.scanIntegerLine("Choose ID: ");
                        for (Novel novel : NOVEL_LIST) {
                            if (novel.getNovelID() == ID) {
                                String description = Input.scanStringLine("Enter new description: ");
                                novel.setDescription(description);
                                isNovelDescriptionChange = true;
                                System.out.println("Change successfully!");
                            }
                        }
                        if (!isNovelDescriptionChange) {
                            throw new RuntimeException("Change unsuccessfully!");
                        }
                    }
                    case 6 -> {
                        boolean isNovelChapterChange = false;
                        displayYourNovelList();
                        int ID = Input.scanIntegerLine("Choose ID: ");
                        for (Novel novel : NOVEL_LIST) {
                            if (novel.getNovelID() == ID) {
                                int chapter = Input.scanIntegerLine("Enter new chapter number: ");
                                novel.setChapter(chapter);
                                isNovelChapterChange = true;
                                System.out.println("Change successfully!");
                            }
                        }
                        if (!isNovelChapterChange) {
                            throw new RuntimeException("Change unsuccessfully!");
                        }
                    }
                    case 7 -> {
                        boolean isNovelStatusChange = false;
                        displayYourNovelList();
                        int ID = Input.scanIntegerLine("Choose ID: ");
                        for (Novel novel : NOVEL_LIST) {
                            if (novel.getNovelID() == ID) {
                                if (!novel.isPublic()) {
                                    novel.setPublic(true);
                                    isNovelStatusChange = true;
                                    System.out.println("Change successfully!");
                                } else {
                                    novel.setPublic(false);
                                    isNovelStatusChange = true;
                                    System.out.println("Change successfully!");
                                }
                                break;
                            }
                        }
                        if (!isNovelStatusChange) {
                            throw new RuntimeException("Change unsuccessfully!");
                        }
                    }
                    case 8 -> {
                        return;
                    }
                }
            } catch (ParseException e) {
                System.out.println("Error! " + e.getMessage() + ". Please enter the format 'dd-mm-yyyy' (^,^)");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void deleteYourNovel() {
        try {
            boolean isNovelDelete = false;
            displayYourNovelList();
            int ID = Input.scanIntegerLine("Enter ID of your novel to delete: ");
            for (int i = 0; i < NOVEL_LIST.size(); i++) {
                if (NOVEL_LIST.get(i).getNovelID() == ID) {
                    String confirm = Input.scanStringLine("Confirm your delete (Y/N): ");
                    if (confirm.equalsIgnoreCase("y")) {
                        NOVEL_LIST.remove(i);
                        isNovelDelete = true;
                        System.out.println("Delete successfully!");
                        break;
                    }
                }
            }
            if (!isNovelDelete) {
                throw new RuntimeException("Delete unsuccessfully!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void createYourNovel() {
        try {
            boolean isExistNovel = false;
            String name = Input.scanStringLine("Enter novel name: ");
            for (Novel novel : NOVEL_LIST) {
                if (novel.getNovelName().equalsIgnoreCase(name)) {
                    isExistNovel = true;
                    System.out.println("This novel is already exist!");
                    break;
                }
            }
            if (!isExistNovel) {
                String genre = Input.scanStringLine("Enter genre: ");
                String date = Input.scanStringLine("Enter day of publication: ");
                int price = Input.scanIntegerLine("Enter price: ");
                int chapter = Input.scanIntegerLine("Enter chapter number: ");
                String description = Input.scanStringLine("Enter description: ");
                boolean Public = Input.scanBooleanLine("Set status public (true/false): ");
                String confirm = Input.scanStringLine("Confirm your create (Y/N): ");
                if (confirm.equalsIgnoreCase("y")) {
                    NOVEL_LIST.add(new NovelConcreteBuilder()
                            .getAuthor(getAuthorName())
                            .getNovelName(name)
                            .getNovelType(genre)
                            .getNovelDate(date)
                            .getPrice(price)
                            .getChapter(chapter)
                            .getNovelDescription(description)
                            .getPublic(Public)
                            .build());
                    for (Novel novel : NOVEL_LIST) {
                        if (novel.getNovelName().equals(name)) {
                            WriteFile.WriteFileNovel("C:\\Users\\ngocc\\PROJECT JAVA\\MYNOVEL\\src\\data\\novel.csv", novel);
                        }
                    }
                    System.out.println("Create novel successfully!");
                }
            } else {
                throw new RuntimeException("Create novel unsuccessfully!");
            }
        } catch (ParseException e) {
            System.out.println("Error! " + e.getMessage() + ". Please enter the format 'dd-mm-yyyy' (^,^)");
        }
    }
}