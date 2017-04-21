package com.example.jatingarg.section1;

import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class SecondActivity extends AppCompatActivity {
    private TextView mTextView;
    public static String NAME_KEY = "name_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mTextView = (TextView) findViewById(R.id.nameTextView);

        String name = "Hello " + getIntent().getStringExtra(NAME_KEY);
        mTextView.setText(name);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        setupTouchListeners();
    }

    private void setupTouchListeners(){
        findViewById(R.id.topLeft).setOnDragListener(new MyDragListener());
        findViewById(R.id.topRight).setOnDragListener(new MyDragListener());
        mTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    ClipData data = ClipData.newPlainText("","");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.startDrag(data,shadowBuilder,v,0);
                    v.setVisibility(View.INVISIBLE);
                    return true;
                }else{
                    return false;
                }
            }
        });
    }

    class MyDragListener implements View.OnDragListener {
        Drawable enterShape = getResources().getDrawable(
                R.drawable.shape_droptarget);
        Drawable normalShape = getResources().getDrawable(R.drawable.shape);

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    LinearLayout container = (LinearLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundDrawable(normalShape);
                    Toast.makeText(SecondActivity.this,"Dragging finished",Toast.LENGTH_SHORT).show();
                default:
                    break;
            }
            return true;
        }
    }
}
