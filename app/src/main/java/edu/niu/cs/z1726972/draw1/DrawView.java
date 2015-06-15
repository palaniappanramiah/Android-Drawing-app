/******************************************************************
 * Name           : Palaniappan Ramiah, Sridhar Gerendla
 * ZID            : Z1726972, Z1728314
 * Class          : Android
 * Assignment No. : 5
 * Program Name   : DrawView.java
 * Description    : Drawing app
 * Due Date       : 04/24/2015 11:59:59 pm
 *****************************************************************/
package edu.niu.cs.z1726972.draw1;

//Importing packages
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View {

    // Declaring the objects and components
    private Path path;
    private Paint drawPaint, canvasPaint;
    private Canvas canvas;
    private Bitmap canvasBitmap;
    private int paintColor = 0xFFFF0000;
    private float brushSize, lastBrushSize;
    private boolean clear = false;  //used for deciding when/if you are erasing

    // Creating a new draw view area
    public DrawView(Context context) {
        super(context);
    }

    // Creating a new draw view area which will be called after all children have been added.
    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    // Setting up the draw view area
    private void setUp(){

        // Assigning the objects and its attributes
        path =  new Path();
        drawPaint = new Paint();
        brushSize = 20;
        lastBrushSize = 20;
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(brushSize);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG); //bkg props
    }

    // When a layout is changed, the size of this view is also changed
    protected void onSizeChanged(int w,int h, int oldw, int oldh){
        super.onSizeChanged(w,h,oldw,oldh);
        canvasBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        canvas = new Canvas(canvasBitmap);
    }

    // This method is used to draw a specific bitmap using a specific paint
    public void onDraw(Canvas c){
        c.drawBitmap(canvasBitmap,0,0,canvasPaint); // Draw the specified bitmap
        c.drawPath(path,drawPaint); // Draw the specified path using the specified paint.
    }

    // When the draw view is touched, selected color is used to paint
    @Override
    public boolean onTouchEvent(MotionEvent event){

        float touchX, touchY;
        touchX = event.getX();
        touchY = event.getY();

        // Depends on the action of the touched gesture
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(touchX,touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(touchX,touchY);
                break;
            case MotionEvent.ACTION_UP:
                canvas.drawPath(path,drawPaint);
                path.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    // This method is used to set the color to draw
    public void setColor(String newColor)    {
        invalidate(); //invalidate the view first
        paintColor = Color.parseColor(newColor); //setting up the new color
        drawPaint.setColor(paintColor);
    }

    // This method is used to set the size of the brush to draw
    public void setBrushSize(float newSize)    {
        float pixelSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newSize, getResources().getDisplayMetrics());
        brushSize = pixelSize;
        drawPaint.setStrokeWidth(brushSize);
    }

    // This method is used to set the size of the brush that is used last
    public void setLastBrushSize(float lastSize)    {
        lastBrushSize = lastSize;
    }

    // This method is used to get the size of the brush that is used last
    public float getLastBrushSize()    {
        return lastBrushSize;
    }

    // This method is used to erase the content in the draw view area
    public void setErase(boolean isClear)    {
        clear = isClear; //decide if you are erasing or drawing
        if (clear)        {
            drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        }
        else
        {
            drawPaint.setXfermode(null);
        }
    }//end setErase

    // This method is used to create a new draw view area
    public void newDrawing() {
    canvas.drawColor(0, PorterDuff.Mode.CLEAR); //clear the canvas and repaint the screen
    invalidate();
    }

}