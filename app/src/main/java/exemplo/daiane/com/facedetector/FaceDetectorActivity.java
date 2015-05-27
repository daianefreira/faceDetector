package exemplo.daiane.com.facedetector;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.os.Bundle;
import android.view.View;

public class FaceDetectorActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new faceDetectionView(this));
    }


    private class faceDetectionView extends View {
        private int width, height;
        private int numberMaxFace = 5;
        private FaceDetector faceDetect;
        private FaceDetector.Face[] faces;
        float faceEyesDistance;
        int numberFaceDetected;
        Bitmap bitmap;



        public faceDetectionView(Context context) {
            super(context);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.face, options);
            width = bitmap.getWidth();
            height = bitmap.getHeight();
            faces = new FaceDetector.Face[numberMaxFace];
            faceDetect = new FaceDetector(width, height, numberMaxFace);
            numberFaceDetected = faceDetect.findFaces(bitmap, faces);
        }



        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawBitmap(bitmap, 0, 0, null);
            Paint myPaint = new Paint();
            myPaint.setColor(Color.MAGENTA);
            myPaint.setStyle(Paint.Style.STROKE);
            myPaint.setStrokeWidth(5);

            for (int i = 0; i < numberFaceDetected; i++) {
                FaceDetector.Face face = faces[i];
                PointF pointF = new PointF();
                face.getMidPoint(pointF);
                faceEyesDistance = face.eyesDistance();

                canvas.drawRect(
                        (int) (pointF.x - faceEyesDistance),
                        (int) (pointF.y - faceEyesDistance),
                        (int) (pointF.x + faceEyesDistance),
                        (int) (pointF.y + faceEyesDistance), myPaint);

            }

        }

    }


}