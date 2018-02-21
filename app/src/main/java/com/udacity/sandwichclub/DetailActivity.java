package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mAkaDisplay;
    private TextView mPlaceOfOriginDisplay;
    private TextView mIngridientsDisplay;
    private TextView mDescriptionDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mAkaDisplay = findViewById(R.id.also_known_tv);
        mIngridientsDisplay = findViewById(R.id.ingredients_tv);
        mPlaceOfOriginDisplay = findViewById(R.id.origin_tv);
        mDescriptionDisplay = findViewById(R.id.description_tv);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.ic_do_not_disturb_alt_black_24dp)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        mPlaceOfOriginDisplay.setText(sandwich.getPlaceOfOrigin());

        StringBuilder sb = new StringBuilder();
        List<String> allNames = sandwich.getAlsoKnownAs();
        boolean first = true;
        for (String name : allNames) {
            if(first) {
                sb.append(name);
                first = false;
            } else {
                sb.append(", " + name);
            }
        }
        mAkaDisplay.setText(sb.toString());

        sb.setLength(0);
        List<String> ingridients = sandwich.getIngredients();
        first = true;
        for (String ingridient : ingridients) {
            if(first) {
                sb.append(ingridient);
                first = false;
            } else {
                sb.append(", " + ingridient);
            }
        }
        mIngridientsDisplay.setText(sb.toString());

        mDescriptionDisplay.setText(sandwich.getDescription());
    }
}
