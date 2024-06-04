package builder;

import entity.Novel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NovelConcreteBuilder implements NovelBuilder {
    private Boolean Public;
    private int ID;
    private String author;
    private String name;
    private String type;
    private Date date;
    private int price;
    private int chapter;
    private String description;

    @Override
    public NovelBuilder getPublic(boolean Public) {
        this.Public = Public;
        return this;
    }

    @Override
    public NovelBuilder getID(int ID) {
        return this;
    }

    @Override
    public NovelBuilder getAuthor(String author) {
        this.author = author;
        return this;
    }

    @Override
    public NovelBuilder getNovelName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public NovelBuilder getNovelType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public NovelBuilder getNovelDate(String date) throws ParseException {
        this.date = new SimpleDateFormat("dd-MM-yyyy").parse(date);
        return this;
    }


    @Override
    public NovelBuilder getPrice(int price) {
        this.price = price;
        return this;
    }

    @Override
    public NovelBuilder getChapter(int chapter) {
        this.chapter = chapter;
        return this;
    }

    @Override
    public NovelBuilder getNovelDescription(String description) {
        this.description = description;
        return this;
    }


    @Override
    public Novel build() {
        return new Novel(author, name, type, date, price, chapter, description, Public) {};
    }
}
