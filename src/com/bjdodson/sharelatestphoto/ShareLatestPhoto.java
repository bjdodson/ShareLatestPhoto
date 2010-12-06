package com.bjdodson.sharelatestphoto;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.ImageColumns;
import android.widget.Toast;

public class ShareLatestPhoto extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Uri uri = getLastPhoto();
		if (uri != null) {
			Intent result = new Intent(null, uri);
			setResult(RESULT_OK, result);
		} else {
			Toast.makeText(this, "Could not get image.", Toast.LENGTH_SHORT).show();
			setResult(RESULT_CANCELED);
		}
        finish();
	}
	
	private Uri getLastPhoto() {
		String selection = ImageColumns.BUCKET_DISPLAY_NAME + " = 'Camera'";
		String[] selectionArgs = null;
		String sort = ImageColumns.DATE_TAKEN + " desc";
		Cursor c =
			android.provider.MediaStore.Images.Media.query(getContentResolver(),
					Images.Media.EXTERNAL_CONTENT_URI, 
					new String[] { ImageColumns._ID }, selection, selectionArgs, sort );
		
		int idx = c.getColumnIndex(ImageColumns._ID);
		if (c.moveToFirst()) {
			return Uri.withAppendedPath(Images.Media.EXTERNAL_CONTENT_URI, c.getString(idx));
		}
		return null;
	}
}