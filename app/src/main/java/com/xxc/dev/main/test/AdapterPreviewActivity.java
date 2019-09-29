package com.xxc.dev.main.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.mrcd.xrouter.annotation.XPath;
import com.xxc.dev.main.R;
import com.xxc.dev.main.test.adapter.MyAdapter;
import com.xxc.dev.main.test.domain.Animal;
import com.xxc.dev.main.test.domain.Bird;
import com.xxc.dev.main.test.domain.Cat;
import com.xxc.dev.main.test.domain.Dog;
import java.util.ArrayList;
import java.util.List;

@XPath
public class AdapterPreviewActivity extends AppCompatActivity {

    public static final String TAG = "AdapterPreviewActivity";

    private RecyclerView mMainList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter);
        mMainList = findViewById(R.id.main_list);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        List<Animal> animals = new ArrayList<>();
        String dogUrl = "http://pic1.win4000.com/wallpaper/b/566a3e45dd4ab.jpg";
        String catUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1569590198502&di=cc66582279f9d24618c016916f3c11b8&imgtype=0&src=http%3A%2F%2Fnewsimg.5054399.com%2Fuploads%2Fuserup%2F1706%2F0QK3033621.jpg";
        String birdUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1569586122304&di=94749bdc9ebf568f411a02aa1b24c7c7&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fitbbs%2F1402%2F11%2Fc20%2F31175617_1392132622123_mthumb.jpg";
        for (int i = 0; i < 10; i++) {
            Animal animal;
            int remainder = i % 3;
            if (remainder == 2) {
                animal = new Bird();
                animal.image = birdUrl;
            } else if (remainder == 1) {
                animal = new Cat();
                animal.image = catUrl;
            } else {
                animal = new Dog();
                animal.image = dogUrl;
            }
            animals.add(animal);
        }
        MyAdapter adapter = new MyAdapter(animals);
        mMainList.setAdapter(adapter);
    }
}
