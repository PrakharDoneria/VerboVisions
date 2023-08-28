package com.verbovisons;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.AlertDialog;
import android.content.*;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.Glide;
import java.io.*;
import java.text.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;
import java.io.*;
import java.net.*;

public class SetWallActivity extends AppCompatActivity {
	
	private boolean click = false;
	private String filePath = "";
	private String fileName = "";
	private String share = "";
	private double n = 0;
	private HashMap<String, Object> map = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> favlist = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private ImageView imageview1;
	private LinearLayout linear4;
	private ImageView imageview2;
	private ImageView imageview3;
	private ImageView imageview7;
	private ImageView imageview4;
	private LinearLayout linear7;
	private ImageView imageview6;
	
	private Intent i = new Intent();
	private AlertDialog.Builder d;
	private Calendar gdf = Calendar.getInstance();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.set_wall);
		initialize(_savedInstanceState);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
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
		linear2 = findViewById(R.id.linear2);
		linear3 = findViewById(R.id.linear3);
		linear5 = findViewById(R.id.linear5);
		linear6 = findViewById(R.id.linear6);
		imageview1 = findViewById(R.id.imageview1);
		linear4 = findViewById(R.id.linear4);
		imageview2 = findViewById(R.id.imageview2);
		imageview3 = findViewById(R.id.imageview3);
		imageview7 = findViewById(R.id.imageview7);
		imageview4 = findViewById(R.id.imageview4);
		linear7 = findViewById(R.id.linear7);
		imageview6 = findViewById(R.id.imageview6);
		d = new AlertDialog.Builder(this);
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				d.setTitle("ImaginWall");
				d.setMessage(getIntent().getStringExtra("url"));
				d.setPositiveButton("Copy", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", getIntent().getStringExtra("url")));
						SketchwareUtil.showMessage(getApplicationContext(), "Copied to clipboard! ");
					}
				});
				d.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				d.create().show();
			}
		});
		
		imageview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (click) {
					click = false;
					imageview3.setImageResource(R.drawable.ic_favorite_outline_white);
					
				}
				else {
					click = true;
					imageview3.setImageResource(R.drawable.liked);
					
					SketchwareUtil.showMessage(getApplicationContext(), "Working on it!💝");
				}
			}
		});
		
		imageview4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				share = getIntent().getStringExtra("url");
				Intent i = new Intent(android.content.Intent.ACTION_SEND);
				i.setType("text/plain");
				i.putExtra(android.content.Intent.EXTRA_TEXT,share);
				startActivity(Intent.createChooser(i,"Shareusing"));
			}
		});
		
		imageview6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				gdf = Calendar.getInstance();
				_save(imageview7, "/storage/emulated/0/", new SimpleDateFormat("M-s-dd-MM-yyyy").format(gdf.getTime()).concat(".png"), 100);
				SketchwareUtil.showMessage(getApplicationContext(), "Saved To Gallery ✓");
			}
		});
	}
	
	private void initializeLogic() {
		Window window = this.getWindow();
		window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //Code By Kimo Android Channel
		window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		window.setNavigationBarColor(Color.parseColor("#000000"));
		_Set_img_url(linear1, getIntent().getStringExtra("url"));
		Glide.with(getApplicationContext()).load(Uri.parse(getIntent().getStringExtra("url"))).into(imageview7);
		imageview7.setVisibility(View.INVISIBLE);
	}
	
	public void _Set_img_url(final View _view, final String _url) {
		try { StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy); URL x2 = new URL(_url); HttpURLConnection connection = (HttpURLConnection) x2.openConnection(); connection.setDoInput(true); connection.connect(); InputStream input = connection.getInputStream(); Bitmap myBitmap = BitmapFactory.decodeStream(input); Drawable dr = new BitmapDrawable(myBitmap); _view.setBackgroundDrawable(dr); } catch (IOException e) { e.printStackTrace(); }
	}
	
	
	public void _saveView(final View _view) {
		Bitmap returnedBitmap = Bitmap.createBitmap(_view.getWidth(), _view.getHeight(),Bitmap.Config.ARGB_8888);
		
		Canvas canvas = new Canvas(returnedBitmap);
		android.graphics.drawable.Drawable bgDrawable =_view.getBackground();
		if (bgDrawable!=null) {
			bgDrawable.draw(canvas);
		} else {
			canvas.drawColor(Color.WHITE);
		}
		_view.draw(canvas);
		
		java.io.File pictureFile = new java.io.File(Environment.getExternalStorageDirectory() + "/Download/myimage.png");
		if (pictureFile == null) {
			showMessage("Error creating media file, check storage permissions: ");
			return; }
		try {
			java.io.FileOutputStream fos = new java.io.FileOutputStream(pictureFile); returnedBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.close();
			showMessage("Image Saved in /Download/ folder");
		} catch (java.io.FileNotFoundException e) {
			showMessage("File not found: " + e.getMessage()); } catch (java.io.IOException e) {
			showMessage("Error accessing file: " + e.getMessage());
			
		}
	}
	
	
	public void _DownloadFile(final String _url, final String _path) {
		FileUtil.makeDir(FileUtil.getPackageDataDir(getApplicationContext()));
		android.net.ConnectivityManager connMgr = (android.net.ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		android.net.NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			
			
			final String urlDownload = _url;
			
			DownloadManager.Request request = new DownloadManager.Request(Uri.parse(urlDownload));
			
			final String fileName = URLUtil.guessFileName(urlDownload, null, null);
			
			request.setDescription("Download/ImaginWall - " + urlDownload);
			
			request.setTitle(fileName);
			
			request.allowScanningByMediaScanner();
			
			request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
			
			request.setDestinationInExternalPublicDir(_path, fileName);
			
			final DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
			
			final long downloadId = manager.enqueue(request);
			
			final ProgressDialog prog = new ProgressDialog(this);
			prog.setMax(100);
			prog.setIndeterminate(true);
			prog.setCancelable(false);
			prog.setCanceledOnTouchOutside(false);
			prog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			prog.setTitle("ImaginWall");
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					
					boolean downloading = true;
					
					while (downloading) {
						
						DownloadManager.Query q = new DownloadManager.Query();
						
						q.setFilterById(downloadId);
						
						android.database.Cursor cursor = manager.query(q);
						
						cursor.moveToFirst();
						
						int bytes_downloaded = cursor.getInt(cursor .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
						
						int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
						
						if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
							
							downloading = false;
							
						}
						
						final int dl_progress = (int) ((bytes_downloaded * 100l) / bytes_total);
						
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								
								prog.setTitle("ImaginWall Downloading...");
								prog.setMessage("Downloading " + fileName + ".\n\nProgress - " + dl_progress + "%");
								prog.show();
								if (dl_progress == 100) {
									
									
									filePath = _path.concat(fileName);
									showMessage("Downloaded in" + filePath );
									prog.dismiss();
								}
								
							} });
					} } }).start();
			
			
		} else {
			showMessage("No Internet Connection.");
		}
	}
	
	
	public void _save(final ImageView _imageview, final String _path, final String _name, final double _quality) {
		
		try{
					BitmapDrawable _imageviewBD = (BitmapDrawable) _imageview.getDrawable();
					Bitmap _imageviewB = _imageviewBD.getBitmap();
					java.io.FileOutputStream _imageviewFOS = null;
					java.io.File _imageviewF = Environment.getExternalStorageDirectory();
					java.io.File _imageviewF2 = new java.io.File(_path);
					_imageviewF2.mkdirs();
					String _imageviewFN = _name;
					java.io.File _imageviewF3 = new java.io.File(_imageviewF2, _imageviewFN);
					_imageviewFOS = new java.io.FileOutputStream(_imageviewF3); 
					_imageviewB.compress(Bitmap.CompressFormat.JPEG, (int) _quality, _imageviewFOS);
					_imageviewFOS.flush();
					_imageviewFOS.close(); 
					Intent _imageviewI = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
					_imageviewI.setData(Uri.fromFile(_imageviewF)); sendBroadcast(_imageviewI);
		}catch(Exception e){
		}
		try {
				android.media.MediaScannerConnection.scanFile(SetWallActivity.this,new String[]{new java.io.File(_path + _name).getPath()}, new String[]{"image/jpeg"}, null);
		} catch(Exception e) {}
		try {
				android.media.MediaScannerConnection.scanFile(SetWallActivity.this,new String[]{new java.io.File(_path + _name).getPath()}, new String[]{"image/png"}, null);
		} catch (Exception e) {}
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