package testdata;

import pojo.Category;
import pojo.Pet;
import pojo.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BuildPetsData {
    Pet pet;

    public Pet buildPetJSON(int idInput, String name, String categoryName, String tag1) {
        pet = new Pet();
        int id;

        if(idInput == 0) {
            Random rnd = new Random();
            id = rnd.nextInt(1000);
        }
        else
            id = idInput;

        pet.setId(id);
        pet.setName(name);

        Category category = new Category();
        category.setId(0);
        category.setName(categoryName);
        pet.setCategory(category);

        List<String> photoUrls = new ArrayList<>();
        photoUrls.add("url1");
        pet.setPhotoUrls(photoUrls);

        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag();
        tag.setId(0);
        tag.setName(tag1);
        tags.add(tag);
        pet.setTags(tags);

        pet.setStatus("available");

        return pet;
    }
}
