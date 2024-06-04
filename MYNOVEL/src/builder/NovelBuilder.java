package builder;

import entity.Novel;

import java.text.ParseException;
import java.util.Date;

public interface NovelBuilder {
    NovelBuilder getPublic(boolean Public);
    NovelBuilder getID(int ID);
    NovelBuilder getAuthor(String author);
    NovelBuilder getNovelName(String name);
    NovelBuilder getNovelType(String type);
    NovelBuilder getNovelDate(String date) throws ParseException;
    NovelBuilder getPrice(int price);
    NovelBuilder getChapter(int chapter);
    NovelBuilder getNovelDescription(String description);

    Novel build();
}
