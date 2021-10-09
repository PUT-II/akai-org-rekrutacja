package pl.akai;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class BookTypeToken extends TypeToken<List<Book>> {
    public static Type type = new BookTypeToken().getType();
}
