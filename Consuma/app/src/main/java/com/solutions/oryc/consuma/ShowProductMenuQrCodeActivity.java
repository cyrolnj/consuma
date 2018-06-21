package com.solutions.oryc.consuma;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import com.solutions.oryc.consuma.control.ProductMenu;

public class ShowProductMenuQrCodeActivity extends AppCompatActivity {

    public final static int QRcodeWidth = 500 ;
    private ProductMenu productMenu;
    private Bitmap bitmap;
    private ImageView imgProductMenuQrCode;
    private TextView vtxtProductMenuTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product_menu_qr_code);

        productMenu = (ProductMenu) getIntent().getParcelableExtra("productMenu");

        try {
            bitmap = TextToImageEncode(productMenu.getId());
        } catch (WriterException e) {
            e.printStackTrace();
        }

        vtxtProductMenuTitle = findViewById(R.id.vtxtProductMenuTitle);
        imgProductMenuQrCode = findViewById(R.id.imgProductMenuQrCode);

        vtxtProductMenuTitle.setText(productMenu.getName());
        imgProductMenuQrCode.setImageBitmap(bitmap);
    }

    public void onClickSendToEmail(View view) {

    }

    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];
        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}
