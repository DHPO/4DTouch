package cn.edu.sjtu.se.hcilab;


import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.foxit.sdk.common.Library;
import com.foxit.sdk.common.PDFException;

import com.foxit.sdk.PDFViewCtrl;
import com.foxit.sdk.pdf.PDFDoc;

public class OpenPDFActivity extends FragmentActivity {
    private PDFViewCtrl pdfViewCtrl = null;

    String mPath = new String();

    private static String sn = "aSCMK7z36PcLKCz+9prebJdieH4Aq4pY0iCUFZXaclbsFTgKV3RMxg==";
    private static String key = "ezKXjl0mvB5z9JsXIVWofHWbbugK+r5gY83TdCvhYnTx18PD1p1vJ0xZnmDwRyUs1jyQRlzV1U0iqvx0dP2lP3ejCu/6SkbOlmEl9D5cynA95FmXPVCkKbBAIA+5Kj6yuTsteFskuYnDPS83ldScJjzcc2IL4KtJQraj6txSpec7yS6tOGGr43AQJWPbr8m2aTFNcHzOzQrvVpigridNAzAqrMnQioF9E8Y5YodCtCPbaEmQgborQ1fnFq2DIREb8Vm7wXbp0WpPFM3q0lJBMSIsOqaF7fExlVbvg5IuHufTJhM6+ZgYjxUndHbjODIdhjGfBcV6KZ+u3wJF57E5VAXxCZOr3AFlqeewjes6mEU1hEpOb2iAIbN21uEH7HqyKAGxomf+cwxJH0xraP7z6isBhNyxqRHTTZo8zhmZC9yLOE0UDETPPF8Au/f9EU17QMxdFdwe6HyQEOb+ephWDM5QoiOpA4ayZDh4ViFo/qunrqIslyZ8KUfQQmfElhuMX2dngZ5U5tkAD0ru6IkL/YxEYCltCNLVv6runybBaqmDsVbRv+yby4Cd5zlVg9sf5k4KFwnqpzAZ8TICwdj0wQtT3AZIXWGBZIVqibHM5nD7GnnhwXo0OAS6zIIas+gbvaaAxQwLrBLN7s9451qFWNo54+P8HsBqbsmJRikVGVhWxqzGpFVwBJo3rMKQJVrpV2uhTMS8DBdul3XboGfnTE2dmiI9agd1yZIwkBHL9ilHYccRXODpNj8E0b05PkMB+Rm+hjRh8FbPBswQ0o3Kx+YOTr9uWRW2ns67fLJrG1n0dyOKlHRL/ZX4NtV85v4+TadFXt8JgLq0xQrS5tVt7OcNxfwgLMjkgAPJuVxDnJcFZY0zw09UYMSPfiCPP6xd0goNNqVJk67jWrb5Uw3vw/s9inFm/6S2GoSWyDY9rgOftxIW13zCXsHg2A7U+C6F8N5p5Lfc9wXBihx5Ll/3OBluT57Th4dwBxgMxUrjbHbU4X4BwZjpGt3sn0a2wxGRW6Z5yMUkG78NYNoiwf3iwd4A2bWiCJzcwRC7OFoKszh2LOf0r18802CSM/8PrQcDRdrhrU7kc4FYAkZoPIcZLBZiI6MKskTypP180P08YEccITz3Ga6I+QKeV9YV9mApkeQKnNgL6dhk7vz+P0PZiVbRgBUcfrc9ofuY7SZd/NsD5P6QIeGvdBkpGo/TP77LwFo=";

    static {
        System.loadLibrary("rdk");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.openpdf);

        try {
            Library.init(sn, key);
        } catch (PDFException e) {
            e.printStackTrace();
            return;
        }

        pdfViewCtrl = (PDFViewCtrl) findViewById(R.id.pdfviewer);

        // Note: Here, filePath will be set with the total path of file.

        Intent intent=getIntent();
        mPath=intent.getStringExtra("path");

        try{
            PDFDoc document=new PDFDoc(mPath);
            document.load(null);
            pdfViewCtrl.setDoc(document);
        }catch(Exception e){
            e.printStackTrace();
        }

        //到指定页
        int total=pdfViewCtrl.getPageCount();
        pdfViewCtrl.gotoPage(total-1);

        //前翻
        pdfViewCtrl.gotoLastPage();

        //后翻
        pdfViewCtrl.gotoNextPage();

        //黑夜模式
        pdfViewCtrl.setNightMode(true);
    }

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




