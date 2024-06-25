package entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Novel {
    private boolean Public = false;
    private String author;
    private int novelID;
    private String novelName;
    private String type;
    private Date dateOfPublication;
    private String description;
    private int price;
    private int chapter;
    private int numberOfNovelPurchase;
    private static int count = 1;

    public Novel() {}

    public Novel(String author, String novelName, String type, Date dateOfPublication, int price, int chapter, String description, Boolean Public) {
        this.novelID = count++;
        this.author = author;
        this.novelName = novelName;
        this.type = type;
        this.dateOfPublication = dateOfPublication;
        this.price = price;
        this.chapter = chapter;
        this.description = description;
        this.Public = Public;
    }

    public String getFormatDateOfPublication() {
        return new SimpleDateFormat("dd-MM-yyyy").format(dateOfPublication);
    }

    @Override
    public String toString() {
        return ("------------------------------------------------------------------------------------->" + "\n" +
                novelID + ". [" + novelName + "]\n" +
                "Genre: " + type + "\n" +
                "Date: " + getFormatDateOfPublication() + "\n" +
                "Author: " + author + "\n" +
                "Chapter: " + chapter + "\n" +
                "Price: " + price + " VND\n" +
                "Description: " + description + "\n" +
                "------------------------------------------------------------------------------------->");
    }

    public boolean isPublic() {
        return Public;
    }

    public void setPublic(boolean Public) {
        this.Public = Public;
    }

    public int getNovelID() {
        return novelID;
    }

    public String getAuthor() {
        return author;
    }

    public String getNovelName() {
        return novelName;
    }

    public void setNovelName(String novelName) {
        this.novelName = novelName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDateOfPublication() {
        return dateOfPublication;
    }

    public void setDateOfPublication(Date dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public int getNumberOfNovelPurchase() { return numberOfNovelPurchase;}

    public void setNumberOfNovelPurchase() {
        this.numberOfNovelPurchase++;
    }
}