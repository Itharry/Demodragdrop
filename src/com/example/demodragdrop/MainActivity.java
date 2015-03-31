package com.example.demodragdrop;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private ImageView myImage;
	private float imageX, imageY;
	private View a;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myImage = (ImageView)findViewById(R.id.imageView1);
		myImage.setOnLongClickListener(new MyClickListener());
		myImage.setTag("DropNDrag");
		
		findViewById(R.id.imageView1).setOnDragListener(new MyDragListener());
		findViewById(R.id.textView1).setOnDragListener(new MyDragListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private final class MyClickListener implements OnLongClickListener{
		@Override
		public boolean onLongClick(View view){
			ClipData.Item item = new ClipData.Item((CharSequence)view.getTag());
			String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
			ClipData data = new ClipData(view.getTag().toString(),mimeTypes,item);
			DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
			view.startDrag(data, shadowBuilder, view,0);
			view.setVisibility(View.INVISIBLE);
			imageX = view.getX();
			imageY = view.getY();
			a=view;
			return true;
		}
	}
	class MyDragListener implements OnDragListener{
		Drawable normalShare = getResources().getDrawable(R.drawable.normal_shape);
		Drawable targetShare = getResources().getDrawable(R.drawable.target_shape);
		
		@Override
		public boolean onDrag(View v, DragEvent event){
			
			switch (event.getAction()) {
			case DragEvent.ACTION_DRAG_STARTED: break;
			case DragEvent.ACTION_DRAG_ENTERED: {break;}
			case DragEvent.ACTION_DRAG_EXITED: {break;}
			case DragEvent.ACTION_DROP:
			{
				if(v==findViewById(R.id.textView1)){
					TextView t = (TextView)findViewById(R.id.textView1);
					t.setText("OK");
				}
				break;
			}
			case DragEvent.ACTION_DRAG_ENDED: {
				if(!event.getResult()){
					a.setX(imageX);
					a.setY(imageY);
					a.setVisibility(View.VISIBLE);
				}
			}
			default:
				break;
			}
			return true;
		}
	}
	
}
