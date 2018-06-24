package com.example.xzt.pdfreader;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.foxit.sdk.PDFViewCtrl;
import com.foxit.sdk.common.Library;
import com.foxit.sdk.common.PDFException;
import com.foxit.sdk.pdf.PDFDoc;
import com.foxit.uiextensions.Module;
import com.foxit.uiextensions.UIExtensionsManager;
import com.foxit.uiextensions.annots.textmarkup.highlight.HighlightModule;
import com.foxit.uiextensions.annots.ink.InkModule;
import com.foxit.uiextensions.annots.ink.EraserModule;

public class OpenPDFActivity extends FragmentActivity {
    private PDFViewCtrl pdfViewCtrl = null;
    private RelativeLayout parent = null;
    private UIExtensionsManager uiExtensionsManager = null;
    private HighlightModule highlightModule = null;
    private InkModule inkModule = null;
    private EraserModule eraserModule = null;
    private ActionMode mActionMode;
    private Context mContext;

    private boolean using=false;

    String mPath = new String();

    private static String sn = "fmt9MTwKG0qe3DRubsfQ3f4m9eUDBZet1fdlW6N9bT+89HgVrsAomQ==";
    private static String key = "ezKXjl3GvG5z9JsXIVWqfHV+ZehluFa2h+1gOBqfoOD1wJCzH3FmzUmwMSC0me2cpkB1xrNgic2o9KuWSUx2XYTjHQ9MnQC0bl4aZAy5Vic5GUKmX/6Ga7OdrnfIHIXjD1P/djsM0yp0Od8ByT05Zr8i4CsBoI55wDvykzZvaDF7HT/Ts/1Re/gr6sPwegeQkHPHWT54T93cuR7axDrrMoKugZcJRs/d8Kon7UUTpw9x+nwA7FA7dwNSDeGIHY0rcOZkxE0KCMleTgPUkcBCj+3K9aS9a9FE2dpsfwNvJer/LWLWdvMU8eu9BZTB2BSRPrB4k0eqPWeKCdLHMC1rIuvQAYB/E1PdZQghxGGYqMF2BJin1751VqkvQ+ZTBOHriP6ZCjQfxNg1xBvvnqXXHzmxYjqIVoPjf6dnhAMGjuILcvtWBdPcqQT0HgUrOLiCooIT4FibQuacEvD4gfyNJ+i/9k5ZODfkWmVgjpWN2u9Rz26BvXnHFi2ZDTGdsO2SFRsvfTG/76l5ZNQhk2BV6uHE/D8r5Y+TSD/n1kEWpdGDx2Ezf+drsrZhCRdFSNd5CmBiH1X/P+Ulge30f+bIL3ixsRtTNCsEOvwoywSwj0j3U8BpKNgMhNBxXziK1sb3aCgTvpTflPIa/JEsWrIh3OoQzkNR2j3HtIFiLjim/ixSmKoRTmB5wZCqTSZpYrZayctFuELSuOSBnlrBuo2nXMNdex4/aoc1OfjRfFwvjGhXk+iTWWDnuc9EweHxOvkwJzd2/F9gnhfx5aF/sWeabdjuUcJKul16vwkIxBY02GeDDtcQePa+qKpoTiAWmrHLmlP/xtRMeqng7D+kW3KA/rIFN6l0Brj33+Y/+WId3ppyUNVOQkCGKx8voV/oKKvWTBdGryKL76IBYr77U+3soj38+w/EuDHniFJUw75/CAIxCL+g39bkgahqErr/loiMUAxEJBjUsWSephfC8zWaH8Pxq+JjzZWg90NNsW1HCr3KVQnsFREoBIhoL0vBHfST8e23Kkm3MYRBLpaaS/F1b+nGBpwDrK+FPeIhSAymqFxhrcuCZmYYKZqnPLrkatT2K3AyRHxfIi2b8PwmBawV2852FbbxkzSsPo/njKXztjsyUoDJg+SPTleCD/r9kqIMZM2djxJ9aPJ4kydLjbXbgOgVU+2mr5QpYqQdsvU8LR+u94ZuHl9/ctqtCICSzlYZBAbo";

    static {
        System.loadLibrary("rdk");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext=this;

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.openpdf);

        try {
            Library.init(sn, key);
        } catch (PDFException e) {
            e.printStackTrace();
            return;
        }

        pdfViewCtrl = (PDFViewCtrl) findViewById(R.id.pdfviewer);
        parent = (RelativeLayout) findViewById(R.id.rd_main_id);


        uiExtensionsManager = new UIExtensionsManager(this.getApplicationContext(), parent, pdfViewCtrl);
        uiExtensionsManager.setAttachedActivity(this);

        pdfViewCtrl.setUIExtensionsManager(uiExtensionsManager);
        // Note: Here, filePath will be set with the total path of file.

        SharedPreferences pref=getSharedPreferences("data",MODE_PRIVATE);
        final int pageturnKey=pref.getInt("pageturnKey",2);
        final int writeKey=pref.getInt("writeKey",1);
        final int settingKey=pref.getInt("settingKey",0);
        final int nightKey=pref.getInt("nightKey",2);
        final int eraserKey=pref.getInt("eraserKey",1);
        final int highlightKey=pref.getInt("highlightKey",0);
        final int resetKey=pref.getInt("resetKey",0);
        Thread thread  = new Thread(new Runnable() {
            public void run() {
                Looper.prepare();
                while (true) {
                    int res = 0;
                    for (int i = 0; i < 5; i++) {
                        if (Data.isFingerPress[i]) {
                            res |= (1 << i);
                        }
                    }
                    if(using){
                        if(res==0){
                            using=false;
                        }
                        continue;
                    }
                    if (res != 0) {
                        long starttime = System.currentTimeMillis();
                        long curtime = System.currentTimeMillis();
                        while (curtime - starttime < 200) {
                            for (int i = 0; i < 5; i++) {
                                if (Data.isFingerPress[i]) {
                                    res |= (1 << i);
                                }
                            }
                            curtime = System.currentTimeMillis();
                        }
                        if (res == pageturnKey) {

                        } else if (res == settingKey) {
                            using=true;
                            Intent intent = new Intent();
                            intent.setClass(OpenPDFActivity.this,UserSettingActivity.class);
                            startActivity(intent);
                        } else if (res == highlightKey) {
                            using=true;
                            if (highlightModule == null)
                                highlightModule = (HighlightModule) uiExtensionsManager.getModuleByName(Module.MODULE_NAME_HIGHLIGHT);
                            uiExtensionsManager.setCurrentToolHandler(highlightModule.getToolHandler());
                        } else if (res == eraserKey) {
                            using=true;
                            if (eraserModule == null)
                                eraserModule = (EraserModule) uiExtensionsManager.getModuleByName(Module.MODULE_NAME_ERASER);
                            uiExtensionsManager.setCurrentToolHandler(eraserModule.getToolHandler());
                        } else if (res == writeKey) {
                            using=true;
                            if (inkModule == null)
                                inkModule = (InkModule) uiExtensionsManager.getModuleByName(Module.MODULE_NAME_INK);
                            uiExtensionsManager.setCurrentToolHandler(inkModule.getToolHandler());
                        } else if (res == nightKey) {
                            using=true;
                            if(pdfViewCtrl.isNightMode()){
                                pdfViewCtrl.setNightMode(false);
                            }
                            else {
                                pdfViewCtrl.setNightMode(true);
                            }
                        }
                        else if (res == resetKey) {
                            using=true;
                            uiExtensionsManager.setCurrentToolHandler(null);
                        }
                    }
                }
            }
        });
        thread.start();

        //文件路径
        Intent intent=getIntent();
        mPath=intent.getStringExtra("path");

        try{
            PDFDoc document=new PDFDoc(mPath);
            document.load(null);
            pdfViewCtrl.setDoc(document);
        }catch(Exception e){
            e.printStackTrace();
        }



 /*       pdfViewCtrl.registerDoubleTapEventListener(new PDFViewCtrl.IDoubleTapEventListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (mActionMode == null) {
                    mActionMode = ((Activity)mContext).startActionMode(mActionModeCallback);
                }
                else {
                    mActionMode.finish();
                    mActionMode = null;
                }
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        });

    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getMenuInflater().inflate(R.menu.menu_pdfview,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.prevpage://前一页
                    pdfViewCtrl.gotoPrevPage();
                    break;
                case R.id.nextpage://下一页
                    pdfViewCtrl.gotoNextPage();
                    break;
                case R.id.nightmode://黑夜模式
                    if(pdfViewCtrl.isNightMode()){
                        pdfViewCtrl.setNightMode(false);
                    }
                    else
                        pdfViewCtrl.setNightMode(true);
                    break;
                case R.id.highlight://高亮
                    if (highlightModule == null)
                        highlightModule = (HighlightModule) uiExtensionsManager.getModuleByName(Module.MODULE_NAME_HIGHLIGHT);
                    uiExtensionsManager.setCurrentToolHandler(highlightModule.getToolHandler());
                    break;
                case R.id.pencil://铅笔
                    if (inkModule == null)
                        inkModule = (InkModule) uiExtensionsManager.getModuleByName(Module.MODULE_NAME_INK);
                    uiExtensionsManager.setCurrentToolHandler(inkModule.getToolHandler());
                    break;
                case R.id.eraser://橡皮
                    if (eraserModule == null) eraserModule = (EraserModule)
                            uiExtensionsManager.getModuleByName(Module.MODULE_NAME_ERASER);
                    uiExtensionsManager.setCurrentToolHandler(eraserModule.getToolHandler());
                    break;
                case R.id.cancel://取消
                    uiExtensionsManager.setCurrentToolHandler(null);
                    break;
                default:
                    break;
            }
            mode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }*/
    };

    @Override
    protected void onDestroy(){
        super.onDestroy();
        try{
            Library.release();
        }
        catch (PDFException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(pdfViewCtrl!=null)
            pdfViewCtrl.requestLayout();

    }



}




