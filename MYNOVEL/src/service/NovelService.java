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

    private static final AccountService ACCOUNT_SERVICE = AccountService.getInstance();
    private static final CartService CART_SERVICE = CartService.getInstance();

    public final static List<Novel> NOVEL_LIST = new ArrayList<>();

    static {
        try {
            List<Novel> novelList = ReadFile.readFileNovel("src\\data\\novel.csv");
            NOVEL_LIST.addAll(novelList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private NovelService() {
    }

    public String getAuthorName() {
        return ACCOUNT_SERVICE.getCurrentAccount().getName();
    }

    public void displayNovel(Novel novel) {
        try {
            if (NOVEL_LIST != null) {
                System.out.println("------------------------------------------------------------------------------------->");
                System.out.println(novel.getNovelID() + ". [" + novel.getNovelName() + "] - " + "Author: " + novel.getAuthor() +
                                   " - Chapter: " + novel.getChapter() + " - Price: " + novel.getPrice() + "\n  Genre: " + novel.getType());
                System.out.println("------------------------------------------------------------------------------------->");
            } else throw new RuntimeException("* No novel *");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void displayPublicNovelList() {
        if (NOVEL_LIST != null) {
            System.out.println("\n==> [NOVEL LIST]");
            for (Novel novel : NOVEL_LIST) {
                if (novel.isPublic()) {
                    displayNovel(novel);
                }
            }
        } else throw new RuntimeException("* No novel *");
    }

    public void displayNovelDetail() {
        do {
            try {
                int ID = Input.scanIntegerLine("Enter novel ID: ");
                boolean isExist = NOVEL_LIST.stream().anyMatch(Novel -> Novel.getNovelID() == ID);
                if (isExist) {
                    NOVEL_LIST.stream().filter(Novel -> Novel.getNovelID() == ID).forEach(System.out::println);
                    System.out.print("""
                            Do you want to...
                                [1] Add to your cart
                                [2] Return
                            """);
                    int choice = Input.scanIntegerLine("Enter your choice: ");
                    switch (choice) {
                        case 1 -> {
                            CART_SERVICE.addCart(ID);
                            return;
                        }
                        case 2 -> {
                            return;
                        }
                        default -> throw new InvalidChoiceException("There isn't such a choice!");
                    }
                } else throw new RuntimeException("* Not found ID! *");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void searchNovel() {
        do {
            try {
                if (NOVEL_LIST != null) {
                    System.out.print("""
                            \nSearch novel by...
                                [1] ID
                                [2] Name
                                [3] Type
                                [4] Author
                                [5] Return
                            """);
                    int choice = Input.scanIntegerLine("Enter your choice: ");
                    boolean isExistNovel = false;
                    switch (choice) {
                        case 1 -> displayNovelDetail();
                        case 2 -> {
                            try {
                                String name = Input.scanStringLine("Enter novel name: ");
                                for (Novel novel : NOVEL_LIST) {
                                    if (novel.getNovelName().toLowerCase().contains(name.toLowerCase())) {
                                        displayNovel(novel);
                                        isExistNovel = true;
                                    }
                                }
                                if (!isExistNovel) {
                                    throw new RuntimeException("Name not found!");
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        case 3 -> {
                            try {
                                String type = Input.scanStringLine("Enter novel type: ");
                                for (Novel novel : NOVEL_LIST) {
                                    if (novel.getType().toLowerCase().contains(type.toLowerCase())) {
                                        displayNovel(novel);
                                        isExistNovel = true;
                                    }
                                }
                                if (!isExistNovel) {
                                    throw new RuntimeException("Type not found!");
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        case 4 -> {
                            try {
                                String author = Input.scanStringLine("Enter author name: ");
                                for (Novel novel : NOVEL_LIST) {
                                    if (novel.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                                        displayNovel(novel);
                                        isExistNovel = true;
                                    }
                                }
                                if (!isExistNovel) {
                                    throw new RuntimeException("Author not found!");
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
                    String confirm = Input.scanStringLine("Would you like to see more novel information? (Y -> continue / Any key -> Return): ");
                    if (confirm.equalsIgnoreCase("Y")) {
                        displayNovelDetail();
                    }
                } else throw new RuntimeException("Cannot use this feature because there is no novel!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void sortNovel() {
        do {
            try {
                if (NOVEL_LIST != null) {
                    System.out.print("""
                            \nSort novel by...
                                [1] Price
                                [2] Date
                                [3] ID
                                [4] Chapter
                                [5] Return
                            """);
                    int choice = Input.scanIntegerLine("Enter your choice: ");
                    boolean isExistNovel = false;
                    switch (choice) {
                        case 1 -> {
                            System.out.print("""
                                    How to sort?
                                        [1] Ascending
                                        [2] Descending
                                        [3] Under price
                                        [4] Over price
                                        [5] Price -> price
                                    """);
                            choice = Input.scanIntegerLine("Enter your choice: ");
                            switch (choice) {
                                case 1 ->
                                        NOVEL_LIST.stream().sorted(Comparator.comparingDouble(Novel::getPrice)).forEach(NOVEL_SERVICE::displayNovel);
                                case 2 ->
                                        NOVEL_LIST.stream().sorted(Comparator.comparingDouble(Novel::getPrice).reversed()).forEach(NOVEL_SERVICE::displayNovel);
                                case 3 -> {
                                    int price = Input.scanIntegerLine("Enter price: ");
                                    for (Novel novel : NOVEL_LIST) {
                                        if (novel.getPrice() <= price) {
                                            isExistNovel = true;
                                            break;
                                        }
                                    }
                                    if (!isExistNovel) {
                                        throw new RuntimeException("Price not found!");
                                    }
                                    NOVEL_LIST.stream().filter(Novel -> Novel.getPrice() <= price).forEach(NOVEL_SERVICE::displayNovel);
                                }
                                case 4 -> {
                                    int price = Input.scanIntegerLine("Enter price: ");
                                    for (Novel novel : NOVEL_LIST) {
                                        if (novel.getPrice() >= price) {
                                            isExistNovel = true;
                                            break;
                                        }
                                    }
                                    if (!isExistNovel) {
                                        throw new RuntimeException("Price not found!");
                                    }
                                    NOVEL_LIST.stream().filter(Novel -> Novel.getPrice() >= price).forEach(NOVEL_SERVICE::displayNovel);
                                }
                                case 5 -> {
                                    int price1 = Input.scanIntegerLine("Enter price: ");
                                    int price2 = Input.scanIntegerLine("Enter price: ");
                                    for (Novel novel : NOVEL_LIST) {
                                        if (price1 > price2) {
                                            if (novel.getPrice() <= price1 && novel.getPrice() >= price2) {
                                                displayNovel(novel);
                                                isExistNovel = true;
                                            }
                                        } else if (price2 > price1) {
                                            if (novel.getPrice() >= price1 && novel.getPrice() <= price2) {
                                                displayNovel(novel);
                                                isExistNovel = true;
                                            }
                                        }
                                    }
                                    if (!isExistNovel) {
                                        throw new RuntimeException("Price not found!");
                                    }
                                }
                                default -> throw new InvalidChoiceException("There isn't such a choice!");
                            }
                        }
                        case 2 -> {
                            System.out.print("""
                                    How to sort?
                                        [1] Ascending
                                        [2] Descending
                                    """);
                            choice = Input.scanIntegerLine("Enter your choice: ");
                            switch (choice) {
                                case 1 ->
                                        NOVEL_LIST.stream().sorted(Comparator.comparing(Novel::getDateOfPublication)).forEach(NOVEL_SERVICE::displayNovel);
                                case 2 ->
                                        NOVEL_LIST.stream().sorted(Comparator.comparing(Novel::getDateOfPublication).reversed()).forEach(NOVEL_SERVICE::displayNovel);
                                default -> throw new InvalidChoiceException("There isn't such a choice!");
                            }
                        }
                        case 3 -> {
                            System.out.print("""
                                    How to sort?
                                        [1] Ascending
                                        [2] Descending
                                    """);
                            choice = Input.scanIntegerLine("Enter your choice: ");
                            switch (choice) {
                                case 1 ->
                                        NOVEL_LIST.stream().sorted(Comparator.comparingInt(Novel::getNovelID)).forEach(NOVEL_SERVICE::displayNovel);
                                case 2 ->
                                        NOVEL_LIST.stream().sorted(Comparator.comparingInt(Novel::getNovelID).reversed()).forEach(NOVEL_SERVICE::displayNovel);
                                default -> throw new InvalidChoiceException("There isn't such a choice!");
                            }
                        }
                        case 4 -> {
                            System.out.print("""
                                    How to sort?
                                        [1] Ascending
                                        [2] Descending
                                    """);
                            choice = Input.scanIntegerLine("Enter your choice: ");
                            switch (choice) {
                                case 1 ->
                                        NOVEL_LIST.stream().sorted(Comparator.comparingInt(Novel::getChapter)).forEach(NOVEL_SERVICE::displayNovel);
                                case 2 ->
                                        NOVEL_LIST.stream().sorted(Comparator.comparingInt(Novel::getChapter).reversed()).forEach(NOVEL_SERVICE::displayNovel);
                                default -> throw new InvalidChoiceException("There isn't such a choice!");
                            }
                        }
                        case 5 -> {
                            return;
                        }
                        default -> throw new InvalidChoiceException("There isn't such a choice!");
                    }
                    String confirm = Input.scanStringLine("Would you like to see more novel information? (Y -> continue / Any key -> Return): ");
                    if (confirm.equalsIgnoreCase("Y")) {
                        displayNovelDetail();
                    }
                } else throw new RuntimeException("Cannot use this feature because there is no novel!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public boolean displayYourNovelList() {
        boolean isExistNovel = false;
        String author = getAuthorName();
        System.out.println("\n==> [YOUR NOVEL]");
        for (Novel novel : NOVEL_LIST) {
            if (novel.getAuthor().equals(author)) {
                System.out.println("(Public: " + novel.isPublic() + ")");
                displayNovel(novel);
                isExistNovel = true;
            }
        }
        return isExistNovel;
    }

    public void editYourNovel() {
        int ID = Input.scanIntegerLine("Choose ID: ");
        do {
            try {
                NOVEL_LIST.stream().filter(Novel -> Novel.getNovelID() == ID).forEach(System.out::println);
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
                boolean isNovelChange = false;
                switch (choice) {
                    case 1 -> {
                        for (Novel novel : NOVEL_LIST) {
                            if (novel.getNovelID() == ID) {
                                String name = Input.scanStringLine("Enter new name: ");
                                novel.setNovelName(name);
                                isNovelChange = true;
                                System.out.println("Change successfully!\n");
                                break;
                            }
                        }
                        if (!isNovelChange) {
                            throw new RuntimeException("Change unsuccessfully!\n");
                        }
                    }
                    case 2 -> {
                        for (Novel novel : NOVEL_LIST) {
                            if (novel.getNovelID() == ID) {
                                String genre = Input.scanStringLine("Enter new genre: ");
                                novel.setType(genre);
                                isNovelChange = true;
                                System.out.println("Change successfully!\n");
                                break;
                            }
                        }
                        if (!isNovelChange) {
                            throw new RuntimeException("Change unsuccessfully!\n");
                        }
                    }
                    case 3 -> {
                        for (Novel novel : NOVEL_LIST) {
                            if (novel.getNovelID() == ID) {
                                System.out.println(novel);
                                String date = Input.scanLineRegex("Enter new date: ", "DATE");
                                Date dateOfPublication = new SimpleDateFormat("dd-MM-yyyy").parse(date);
                                novel.setDateOfPublication(dateOfPublication);
                                isNovelChange = true;
                                System.out.println("Change successfully!\n");
                                break;
                            }
                        }
                        if (!isNovelChange) {
                            throw new RuntimeException("Change unsuccessfully!\n");
                        }
                    }
                    case 4 -> {
                        for (Novel novel : NOVEL_LIST) {
                            if (novel.getNovelID() == ID) {
                                int price = Input.scanIntegerLine("Enter new price: ");
                                novel.setPrice(price);
                                isNovelChange = true;
                                System.out.println("Change successfully!\n");
                                break;
                            }
                        }
                        if (!isNovelChange) {
                            throw new RuntimeException("Change unsuccessfully!\n");
                        }
                    }
                    case 5 -> {
                        for (Novel novel : NOVEL_LIST) {
                            if (novel.getNovelID() == ID) {
                                String description = Input.scanStringLine("Enter new description: ");
                                novel.setDescription(description);
                                isNovelChange = true;
                                System.out.println("Change successfully!\n");
                                break;
                            }
                        }
                        if (!isNovelChange) {
                            throw new RuntimeException("Change unsuccessfully!\n");
                        }
                    }
                    case 6 -> {
                        for (Novel novel : NOVEL_LIST) {
                            if (novel.getNovelID() == ID) {
                                int chapter = Input.scanIntegerLine("Enter new chapter number: ");
                                novel.setChapter(chapter);
                                isNovelChange = true;
                                System.out.println("Change successfully!\n");
                                break;
                            }
                        }
                        if (!isNovelChange) {
                            throw new RuntimeException("Change unsuccessfully!\n");
                        }
                    }
                    case 7 -> {
                        for (Novel novel : NOVEL_LIST) {
                            if (novel.getNovelID() == ID) {
                                novel.setPublic(!novel.isPublic());
                                isNovelChange = true;
                                System.out.println("Change successfully!\n");
                                break;
                            }
                        }
                        if (!isNovelChange) {
                            throw new RuntimeException("Change unsuccessfully!\n");
                        }
                    }
                    case 8 -> {
                        return;
                    }
                }
            } catch (ParseException e) {
                System.out.println("Error! " + e.getMessage() + ". Please enter the format 'dd-mm-yyyy'");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void deleteYourNovel() {
        try {
            boolean isExistNovel = false;
            displayYourNovelList();
            int ID = Input.scanIntegerLine("Enter ID of your novel to delete: ");
            for (Novel novel : NOVEL_LIST) {
                if (novel.getNovelID() == ID) {
                    String confirm = Input.scanStringLine("Confirm your delete (Y -> continue / Any key -> Return): ");
                    if (confirm.equalsIgnoreCase("y")) {
                        NOVEL_LIST.remove(novel);
                        isExistNovel = true;
                        System.out.println("Delete successfully!");
                        break;
                    }
                }
            }
            /*
            for (int i = 0; i < NOVEL_LIST.size(); i++) {
                if (NOVEL_LIST.get(i).getNovelID() == ID) {
                    System.out.println(NOVEL_LIST.get(i));
                    String confirm = Input.scanStringLine("Confirm your delete (Y -> continue / Any key -> Return): ");
                    if (confirm.equalsIgnoreCase("y")) {
                        NOVEL_LIST.remove(i);
                        isExistNovel = true;
                        System.out.println("Delete successfully!");
                        break;
                    }
                }
            }

             */
            if (!isExistNovel) {
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
                String date = Input.scanLineRegex("Enter day of publication: ", "DATE");
                int price = Input.scanIntegerLine("Enter price: ");
                int chapter = Input.scanIntegerLine("Enter chapter number: ");
                String description = Input.scanStringLine("Enter description: ");
                boolean Public = Input.scanBooleanLine("Set status public (true/false): ");
                String confirm = Input.scanStringLine("Confirm your create (Y -> continue / Any key -> Return): ");
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
                            WriteFile.WriteFileNovel("src\\data\\novel.csv", novel);
                        }
                    }
                    System.out.println("Create novel successfully!");
                }
            } else {
                throw new RuntimeException("Create novel unsuccessfully!");
            }
        } catch (ParseException e) {
            System.out.println("Error! " + e.getMessage() + ". Please enter the format 'dd-mm-yyyy'");
        }
    }

    public void displayAllNovelList() {
        if (NOVEL_LIST != null) {
            System.out.println("\n==> [NOVEL LIST]");
            for (Novel novel : NOVEL_LIST) {
                displayNovel(novel);
            }
        } else throw new RuntimeException("* No novel *");
    }

    public void displayNovelPage() {
        do {
            try {
                displayAllNovelList();
                System.out.print("""
                        \n==> [NOVEL PAGE]
                            [1] Edit novel
                            [2] Delete novel
                            [3] Back
                        """);
                int choice = Input.scanIntegerLine("Enter your choice: ");
                switch (choice) {
                    case 1 -> editYourNovel();
                    case 2 -> deleteYourNovel();
                    case 3 -> {
                        return;
                    }
                    default -> throw new InvalidChoiceException("There isn't such a choice!");
                }
            } catch (Exception e) {
                System.out.println("Error! " + e.getMessage() + ". Please try again");
            }
        } while (true);
    }
}