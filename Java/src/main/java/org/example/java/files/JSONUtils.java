package org.example.java.files;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.example.java.entity.user.User;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class JSONUtils <T> {
    private final String filePath;
    private final Jsonb jsonb;


    public JSONUtils (String filePath) {
        this.filePath = filePath;
        jsonb = JsonbBuilder.create();
    }


    public void save(T data) {
        var json = jsonb.toJson(List.of(data));
        try(var writer = new FileWriter(filePath)) {
            writer.append(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void saveAll(List<T> listOfData) {
        var json = jsonb.toJson(listOfData); // serialize the entire list at once
        try (var writer = new FileWriter(filePath)) {
            writer.write(json); // overwrite file with array
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public List<T> get(Predicate<T> predicate) {
        try {
            var jsonList = Files.readString(Paths.get(filePath));
            List<T> resultList = jsonb.fromJson(
                    jsonList,
                    //ArrayList.class
                    new ArrayList<User>(){}.getClass().getGenericSuperclass()
            );

            //TODO: load result from Json to result list

            return resultList.stream().filter(predicate).toList();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    public void deleteAll() {
        try (var writer = Files.newOutputStream(
                Path.of(filePath),
                StandardOpenOption.TRUNCATE_EXISTING)) {
            //writer.write('[');
            //writer.write('\n');
            //writer.write(']');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
