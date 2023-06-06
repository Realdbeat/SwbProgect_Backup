package com.real.myswbmanager;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.AlertDialog;
import android.content.*;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;

public class MainActivity extends AppCompatActivity {
	
	private HashMap<String, Object> temp_map1 = new HashMap<>();
	private double number = 0;
	private String temp_decrypted = "";
	private String search = "";
	private String data = "";
	private String mysc = "";
	private String list = "";
	private String icons = "";
	private String comment = "";
	private String fonts = "";
	private String images = "";
	private SpannableString s;
	private String info_text = "";
	private String sounds = "";
	
	private ArrayList<String> temp_str1 = new ArrayList<>();
	private ArrayList<String> collapses = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> temp_listmap1 = new ArrayList<>();
	
	private LinearLayout linear1;
	private ListView listview1;
	
	private AlertDialog.Builder dialog;
	private AlertDialog.Builder dele;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
		} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		listview1 = findViewById(R.id.listview1);
		dialog = new AlertDialog.Builder(this);
		dele = new AlertDialog.Builder(this);
	}
	
	private void initializeLogic() {
		new onrunner().execute("");
	}
	
	private class onrunner extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {
			
		}
		
		@Override
		protected String doInBackground(String... params) {
			String _param = params[0];
			data = FileUtil.getExternalStorageDir().concat("/.sketchware/data/");
			mysc = FileUtil.getExternalStorageDir().concat("/.sketchware/mysc/");
			list = FileUtil.getExternalStorageDir().concat("/.sketchware/mysc/list/");
			icons = FileUtil.getExternalStorageDir().concat("/.sketchware/resources/icons/");
			comment = FileUtil.getExternalStorageDir().concat("/.sketchware/resources/comment/");
			fonts = FileUtil.getExternalStorageDir().concat("/.sketchware/resources/fonts/");
			images = FileUtil.getExternalStorageDir().concat("/.sketchware/resources/images/");
			sounds = FileUtil.getExternalStorageDir().concat("/.sketchware/resources/sounds/");
			collapses.clear();
			temp_str1.clear();
			temp_listmap1.clear();
			temp_map1.clear();
			FileUtil.listDir(FileUtil.getExternalStorageDir().concat("/.sketchware/mysc/list/"), temp_str1);
			Collections.sort(temp_str1, String.CASE_INSENSITIVE_ORDER);
			number = 0;
			for(int _repeat43 = 0; _repeat43 < (int)(temp_str1.size()); _repeat43++) {
				if (FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/.sketchware/mysc/list/".concat(Uri.parse(temp_str1.get((int)(number))).getLastPathSegment().concat("/project"))))) {
					collapses.add("false");
					try {
						javax.crypto.Cipher instance = javax.crypto.Cipher.getInstance("AES/CBC/PKCS5Padding");
						byte[] bytes = "sketchwaresecure".getBytes();
						instance.init(2, new javax.crypto.spec.SecretKeySpec(bytes, "AES"), new javax.crypto.spec.IvParameterSpec(bytes));
						java.io.RandomAccessFile randomAccessFile = new java.io.RandomAccessFile(FileUtil.getExternalStorageDir().concat("/.sketchware/mysc/list/".concat(Uri.parse(temp_str1.get((int)(number))).getLastPathSegment().concat("/project"))), "r");
						byte[] bArr = new byte[((int) randomAccessFile.length())];
						randomAccessFile.readFully(bArr);
						temp_decrypted = new String(instance.doFinal(bArr));
						temp_map1 = new HashMap<>();
						temp_map1 = new Gson().fromJson(temp_decrypted, new TypeToken<HashMap<String, Object>>(){}.getType());
						temp_listmap1.add(temp_map1);
					} catch(Exception e) {
						showMessage(e.toString());
					}
				}
				number++;
			}
			Collections.reverse(temp_listmap1);
			
			return (new Gson().toJson(temp_listmap1));
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			int _value = values[0];
			
		}
		
		@Override
		protected void onPostExecute(String _result) {
			listview1.setAdapter(new Listview1Adapter(temp_listmap1));
			((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
		}
	}
	
	public void _Create_Spannbale(final String _text) {
		s = new SpannableString(_text);
	}
	
	
	public void _Spannable_Bold(final double _n, final double _n1) {
		s.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), (int)_n, (int)_n1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	}
	
	public class Listview1Adapter extends BaseAdapter {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = getLayoutInflater();
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.viewsp, null);
			}
			
			final androidx.cardview.widget.CardView cardview1 = _view.findViewById(R.id.cardview1);
			final LinearLayout background = _view.findViewById(R.id.background);
			final LinearLayout top = _view.findViewById(R.id.top);
			final LinearLayout bottom = _view.findViewById(R.id.bottom);
			final ImageView icon = _view.findViewById(R.id.icon);
			final LinearLayout linear4 = _view.findViewById(R.id.linear4);
			final ImageView callpose = _view.findViewById(R.id.callpose);
			final TextView title = _view.findViewById(R.id.title);
			final TextView pack = _view.findViewById(R.id.pack);
			final TextView text_id = _view.findViewById(R.id.text_id);
			final LinearLayout backup = _view.findViewById(R.id.backup);
			final LinearLayout restore = _view.findViewById(R.id.restore);
			final LinearLayout clone = _view.findViewById(R.id.clone);
			final LinearLayout info = _view.findViewById(R.id.info);
			final ImageView imageview3 = _view.findViewById(R.id.imageview3);
			final TextView textview3 = _view.findViewById(R.id.textview3);
			final ImageView imageview4 = _view.findViewById(R.id.imageview4);
			final TextView textview4 = _view.findViewById(R.id.textview4);
			final ImageView imageview5 = _view.findViewById(R.id.imageview5);
			final TextView textview5 = _view.findViewById(R.id.textview5);
			final ImageView imageview6 = _view.findViewById(R.id.imageview6);
			final TextView textview6 = _view.findViewById(R.id.textview6);
			
			if (_data.get((int)_position).containsKey("sc_id")) {
				background.setVisibility(View.VISIBLE);
				if (_data.get((int)_position).containsKey("custom_icon")) {
					if (_data.get((int)_position).get("custom_icon").toString().equals("true")) {
						if (FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/.sketchware/resources/icons/".concat(_data.get((int)_position).get("sc_id").toString().concat("/icon.png"))))) {
							if (BitmapFactory.decodeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/resources/icons/".concat(_data.get((int)_position).get("sc_id").toString().concat("/icon.png")))) != null) {
								icon.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(FileUtil.getExternalStorageDir().concat("/.sketchware/resources/icons/".concat(_data.get((int)_position).get("sc_id").toString().concat("/icon.png"))), 1024, 1024));
							}
							else {
								icon.setImageResource(R.drawable.app_ico);
							}
						}
						else {
							icon.setImageResource(R.drawable.app_ico);
						}
					}
					else {
						icon.setImageResource(R.drawable.app_ico);
					}
				}
				else {
					icon.setImageResource(R.drawable.app_ico);
				}
				if (_data.get((int)_position).containsKey("sc_id")) {
					text_id.setText("( ".concat(_data.get((int)_position).get("sc_id").toString().concat(" )")));
				}
				if (_data.get((int)_position).containsKey("my_app_name")) {
					title.setText(_data.get((int)_position).get("my_app_name").toString());
				}
				if (_data.get((int)_position).containsKey("my_sc_pkg_name")) {
					pack.setText(_data.get((int)_position).get("my_sc_pkg_name").toString());
				}
			}
			else {
				background.setVisibility(View.GONE);
			}
			callpose.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					if (bottom.getVisibility()==View.GONE){
						final android.transition.ChangeBounds transition = new android.transition.ChangeBounds(); transition.setDuration(200L);
						android.transition.TransitionManager.beginDelayedTransition(listview1, transition);
						bottom.setVisibility(View.VISIBLE);
						callpose.setRotation((float)(180));
						collapses.set(Integer.parseInt(String.valueOf(_position)),"true");
					}else {
						
						final android.transition.ChangeBounds transition = new android.transition.ChangeBounds(); transition.setDuration(200L);
						android.transition.TransitionManager.beginDelayedTransition(listview1, transition);
						bottom.setVisibility(View.GONE);
						callpose.setRotation((float)(0));
						collapses.set(Integer.parseInt(String.valueOf(_position)),"false");
					}
				}
			});
			if (collapses.get((int)(_position)).contains("false")) {
				bottom.setVisibility(View.GONE);
				callpose.setRotation((float)(0));
			}
			else {
				bottom.setVisibility(View.VISIBLE);
				callpose.setRotation((float)(180));
			}
			((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			backup.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					
				}
			});
			restore.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					
				}
			});
			clone.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					
				}
			});
			info.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					dialog = new AlertDialog.Builder(MainActivity.this,AlertDialog.THEME_HOLO_DARK);
					//dialog = new AlertDialog.Builder(MainActivity.this,AlertDialog.THEME_HOLO_LIGHT);
					info_text = "Project name : %projectname\n\nPackage : %package\n\nProject ID : %projectid\n\nVersion name : %versionname";
					info_text = info_text.replace("%projectname", _data.get((int)_position).get("my_app_name").toString());
					info_text = info_text.replace("%package", _data.get((int)_position).get("my_sc_pkg_name").toString());
					info_text = info_text.replace("%projectid", _data.get((int)_position).get("sc_id").toString());
					info_text = info_text.replace("%versionname", _data.get((int)_position).get("sc_ver_name").toString().concat("(".concat(_data.get((int)_position).get("sc_ver_code").toString().concat(")"))));
					_Create_Spannbale(info_text);
					_Spannable_Bold(info_text.indexOf("Project name :"), info_text.indexOf("Project name :") + 14);
					_Spannable_Bold(info_text.indexOf("Package :"), info_text.indexOf("Package :") + 9);
					_Spannable_Bold(info_text.indexOf("Project ID :"), info_text.indexOf("Project ID :") + 12);
					_Spannable_Bold(info_text.indexOf("Version name :"), info_text.indexOf("Version name :") + 14);
					dialog.setTitle("Show Project Info");
					dialog.setMessage(s);
					dialog.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					dialog.create().show();
				}
			});
			background.setOnLongClickListener(new View.OnLongClickListener() {@Override public boolean onLongClick(View v) { 
					dele.setTitle("Delete Project ?");
					dele.setIcon(R.drawable.delete);
					dele.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					dele.setNegativeButton("No", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					dele.create().show();
					return true; } } );
			
			return _view;
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}