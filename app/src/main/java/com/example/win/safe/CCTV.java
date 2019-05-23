package com.example.win.safe;

        import android.Manifest;
        import android.app.Activity;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Environment;
        import android.provider.MediaStore;
        import android.support.v4.app.ActivityCompat;
        import android.util.Log;
        import android.view.KeyEvent;
        import android.view.View;
        import android.webkit.WebSettings;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.Toast;

        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.OutputStream;
        import java.text.SimpleDateFormat;
        import java.util.Date;

public class CCTV extends Activity {

    private WebView mwv;
    private ImageView Capture,Gallery;
    LinearLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cctvpage);
        String FilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/Safe";
        //캡쳐및겔러리이동
        Capture = (ImageView)findViewById(R.id.capture);
        container = (LinearLayout)findViewById(R.id.captureview);
        Gallery = (ImageView)findViewById(R.id.gallery);

        //웹뷰셋팅
        mwv=(WebView)findViewById(R.id.cctvid);
        WebSettings mws = mwv.getSettings();
        mws.setJavaScriptEnabled(true);
        mws.setLoadWithOverviewMode(true);


        mwv.setWebViewClient(new WebViewClient(){

            public boolean shouldOverriderUriLoading(WebView view,String url)
            {
                view.loadUrl(url);
                return true;
            }

        });
        mwv.loadUrl("http://google.com");

        //캡쳐버튼액션
        Capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //View rootView = getWindow().getDecorView();
                File screenShot = ScreenShot(mwv);
                if(screenShot!=null)
                {
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.fromFile(screenShot)));
                    Toast.makeText(CCTV.this,"캡쳐완료",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(CCTV.this,"캡쳐가 잘 안됩니다",Toast.LENGTH_SHORT).show();
                }

            }
        });

        Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Uri targetUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
               // String targetDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/Safe";   // 특정 경로!!
               // targetUri = targetUri.buildUpon().appendQueryParameter("bucketId", String.valueOf(targetDir.toLowerCase().hashCode())).build();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivity(intent);
            }
        });

    }


    //캡쳐 메소드
    public File ScreenShot(View view)
    {
        view.setDrawingCacheEnabled(true);

        Bitmap screenBitmap = view.getDrawingCache();

        String FilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/Safe";
        File file = new File(FilePath);
        if(!file.exists())
        {
            file.mkdirs();
            Toast.makeText(CCTV.this,"폴더생성완료",Toast.LENGTH_SHORT).show();
        }
        SimpleDateFormat day = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();

        FileOutputStream os = null;
        try
        {
            os = new FileOutputStream(file+"/"+day.format(date)+".jpeg");
            screenBitmap.compress(Bitmap.CompressFormat.JPEG,100,os);
            os.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        view.setDrawingCacheEnabled(false);
        return file;
    }





}