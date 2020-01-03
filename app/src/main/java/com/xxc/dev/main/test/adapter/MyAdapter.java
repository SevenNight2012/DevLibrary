package com.xxc.dev.main.test.adapter;

import androidx.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.xxc.dev.image.ImageEngine;
import com.xxc.dev.main.R;
import com.xxc.dev.main.adapter.BaseHolder;
import com.xxc.dev.main.adapter.BaseRecyclerAdapter;
import com.xxc.dev.main.test.adapter.MyAdapter.AnimalHolder;
import com.xxc.dev.main.test.domain.Animal;
import com.xxc.dev.main.test.domain.Bird;
import com.xxc.dev.main.test.domain.Cat;
import com.xxc.dev.main.test.domain.Dog;
import java.util.List;

public class MyAdapter extends BaseRecyclerAdapter<Animal, AnimalHolder<? extends Animal>> {

    public static final String TAG = "MyAdapter>>>";

    public static final int TYPE_DOG = 0;
    public static final int TYPE_CAT = 1;
    public static final int TYPE_BIRD = 2;

    private LayoutInflater mInflater;

    public MyAdapter(List<Animal> animals) {
        mData = animals;
    }

    @Override
    public AnimalHolder<? extends Animal> onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        if (null == mInflater) {
            mInflater = LayoutInflater.from(viewGroup.getContext());
        }
        switch (type) {
            case TYPE_DOG:
                return new DogHolder(mInflater.inflate(R.layout.item_animal, viewGroup, false));
            case TYPE_CAT:
                return new CatHolder(mInflater.inflate(R.layout.item_animal, viewGroup, false));
            case TYPE_BIRD:
                return new BirdHolder(mInflater.inflate(R.layout.item_animal, viewGroup, false));
        }
        return new DogHolder(mInflater.inflate(R.layout.item_animal, viewGroup, false));
    }

    @Override
    public void onBindData(AnimalHolder<? extends Animal> holder, Animal animal) {
        Log.d(TAG, "onBindData: " + animal);
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getAnimalType();
    }

    abstract static class AnimalHolder<anim extends Animal> extends BaseHolder<anim> {

        TextView mAnimalName;
        ImageView mAnimalImage;

        AnimalHolder(@NonNull View itemView) {
            super(itemView);
            mAnimalName = itemView.findViewById(R.id.animal_name);
            mAnimalImage = itemView.findViewById(R.id.animal_image);
        }

    }

    public static class DogHolder extends AnimalHolder<Dog> {

        DogHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void attachItem(Dog dog) {
            mAnimalName.setText(String.valueOf(dog.name));
            ImageEngine.getInstance().load(itemView.getContext(), dog.image, mAnimalImage);
        }
    }

    public static class CatHolder extends AnimalHolder<Cat> {

        CatHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void attachItem(Cat cat) {
            mAnimalName.setText(String.valueOf(cat.name));
            ImageEngine.getInstance().load(itemView.getContext(), cat.image, mAnimalImage);
        }
    }

    public static class BirdHolder extends AnimalHolder<Bird> {

        BirdHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void attachItem(Bird bird) {
            mAnimalName.setText(String.valueOf(bird.name));
            ImageEngine.getInstance().load(itemView.getContext(), bird.image, mAnimalImage);
        }
    }

}
