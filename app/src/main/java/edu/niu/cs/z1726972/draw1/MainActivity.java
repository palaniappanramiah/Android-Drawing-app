/******************************************************************
 * Name           : Palaniappan Ramiah, Sridhar Gerendla
 * ZID            : Z1726972, Z1728314
 * Class          : Android
 * Assignment No. : 5
 * Program Name   : MainActivity.java
 * Description    : Drawing app
 * Due Date       : 04/24/2015 11:59:59 pm
 *****************************************************************/

package edu.niu.cs.z1726972.draw1;

//Importing packages
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.UUID;


public class MainActivity extends Activity  implements View.OnClickListener {

    // Declaring the object and components
    private DrawView drawView; //for Drawing area
    ImageButton currentPaintColor; //for current paint color
    float vSmB, smB, medB, lgB; //for paint brush/eraser size
    Button btnBrush, btnClear, btnNew, btnSave; //button to change brush size, to clear/erase, new and save

    @Override
    // When the activity starts onCreate() is called
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assigning the objects to each components of the view
        drawView = (DrawView)findViewById(R.id.drawing);
        LinearLayout paintLayout = (LinearLayout)findViewById(R.id.colors);
        currentPaintColor = (ImageButton)paintLayout.getChildAt(0);
        currentPaintColor.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed)); // Set to draw with the selected color

        //initialize paint brush sizes
        vSmB = 5;
        smB = getResources().getInteger(R.integer.small_size);
        medB = getResources().getInteger(R.integer.medium_size);
        lgB = getResources().getInteger(R.integer.large_size);

        //set up brush button
        btnBrush = (Button)findViewById(R.id.buttonBrush);
        btnBrush.setOnClickListener(this);

        //set up erase/clear button
        btnClear = (Button)findViewById(R.id.buttonClear);
        btnClear.setOnClickListener(this);

        //set up new button
        btnNew = (Button)findViewById(R.id.buttonNew);
        btnNew.setOnClickListener(this);

        //set up save button
        btnSave = (Button)findViewById(R.id.buttonSave);
        btnSave.setOnClickListener(this);
    }

    // The method is used to change the paintbrush color and imageButtons at the bottom of the screen
    public void paintClicked(View v)    { //Because it is an onClick method it must be passed a View

        // If the eraser is set to true before, we are changing it back
        drawView.setErase(false);
        drawView.setBrushSize(drawView.getLastBrushSize());

        if (v != currentPaintColor)        {
         ImageButton imageV = (ImageButton)v; //get the tag of the button clicked from the view that is passed in
         String color = v.getTag().toString(); //get the tag of the color
         drawView.setColor(color); //Set the selected color to the drawview

        /* Now update the UI to reflect the color change, that is so the imagebutton
         * has a different highlight and reset the previous one to the grey outline
         */
        imageV.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed)); //updating the UI for the color change
        currentPaintColor.setImageDrawable(getResources().getDrawable(R.drawable.paint)); //resetting to the grey outline
        currentPaintColor = (ImageButton)v; //Setting the currentPaintColor to the selected paint color
        }
    }//end paintClicked

    // Whenever the UI has been clicked, this method will be called
    public void onClick(View v){

    // When brush button is clicked
    if (v.getId() == R.id.buttonBrush)        {

        // Display a dialog with the brush sizes
        final Dialog brushDialog = new Dialog(this);
        brushDialog.setTitle("Brush Sizes");
        brushDialog.setContentView(R.layout.brush_choice);

        // Set up all the brush buttons and their onClick methods
        Button btnVsm = (Button)brushDialog.findViewById(R.id.buttonVsmBrush); //Assigning an Object to that brush button

        // When that brush button is clicked
        btnVsm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawView.setBrushSize(vSmB); // Setting the brush size as per clicked
                drawView.setLastBrushSize(vSmB); // Setting the last brush size as per clicked
                brushDialog.dismiss(); // Dismissing the dialog that was displayed
            }
        });

        Button btnSm = (Button)brushDialog.findViewById(R.id.buttonSmBrush);
        btnSm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawView.setBrushSize(smB);
                drawView.setLastBrushSize(smB);
                brushDialog.dismiss();
            }
        });

        Button btnMed = (Button)brushDialog.findViewById(R.id.buttonMdBrush);
        btnMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawView.setBrushSize(medB);
                drawView.setLastBrushSize(medB);
                brushDialog.dismiss();
            }
        });

        Button btnLg = (Button)brushDialog.findViewById(R.id.buttonLgBrush);
        btnLg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawView.setBrushSize(lgB);
                drawView.setLastBrushSize(lgB);
                brushDialog.dismiss();                }
        });

        brushDialog.show();
        }//end if button brush clicked

    // When erase/clear button is clicked
    else if (v.getId() == R.id.buttonClear)
    {

        // Display a dialog with the eraser sizes
        final Dialog brushDialog = new Dialog(this);
        brushDialog.setTitle("Eraser Sizes");
        brushDialog.setContentView(R.layout.brush_choice);

        // Set up all the eraser buttons and their onClick methods
        Button btnVsm = (Button)brushDialog.findViewById(R.id.buttonVsmBrush);  //Assigning an Object to that erase button

        // When that erase button is clicked
        btnVsm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawView.setBrushSize(vSmB); // Setting the brush size as per clicked
                drawView.setLastBrushSize(vSmB); // Setting the last brush size as per clicked
                drawView.setErase(true); // Setting to erase in the draw view
                brushDialog.dismiss(); // Dismissing the displayed dialog
            }
        });

        Button btnSm = (Button)brushDialog.findViewById(R.id.buttonSmBrush);
        btnSm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawView.setBrushSize(smB);
                drawView.setLastBrushSize(smB);
                drawView.setErase(true);
                brushDialog.dismiss();
            }
        });

        Button btnMed = (Button)brushDialog.findViewById(R.id.buttonMdBrush);
        btnMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawView.setBrushSize(medB);
                drawView.setLastBrushSize(medB);
                drawView.setErase(true);
                brushDialog.dismiss();
            }
        });

        Button btnLg = (Button)brushDialog.findViewById(R.id.buttonLgBrush);
        btnLg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawView.setBrushSize(lgB);
                drawView.setLastBrushSize(lgB);
                drawView.setErase(true);
                brushDialog.dismiss();
            }
        });

        brushDialog.show();
     }

    // When new button is clicked
    else if (v.getId() == R.id.buttonNew) {

        // Display a dialog alerting the user
        AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
        newDialog.setTitle("New Drawing?");
        newDialog.setMessage("Are you sure you want to start a new drawing? Current drawing will be lost!");

        // Displaying yes in alert Dialog
        newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            // If yes is clicked in the alert, new drawing area is created and dialog box is dismissed
            public void onClick(DialogInterface dialogInterface, int i) {
                drawView.newDrawing();
                dialogInterface.dismiss();
            }
        });

        // Displaying cancel in alert Dialog
        newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            // If no is clicked, Old drawing is continued
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        newDialog.show();

    }

    // When the save button is clicked
    else if (v.getId() == R.id.buttonSave) {

    // Displaying an alert dialog to ask the user to save
    final AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
    saveDialog.setTitle("Save Drawing?");
    saveDialog.setMessage("Save this drawing to the device Gallery?");

    // Displaying yes in alert Dialog
    saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
        //set permissions in manifest to be able to write to device
        drawView.setDrawingCacheEnabled(true);
        String savedImageURI = MediaStore.Images.Media.insertImage(MainActivity.this.getContentResolver(), drawView.getDrawingCache(), UUID.randomUUID().toString() + ".png", "drawing");
        //insertImage() is used to write to the gallery, it returns the url or null
        if(savedImageURI != null) {
            // means the image was saved
            Toast.makeText(getApplicationContext(), "Drawing saved to gallery", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(),"Sorry, drawing could not be saved",Toast.LENGTH_LONG).show();
        }
        drawView.destroyDrawingCache();  //so future drawings won't use the same cache
        //
        }
    });

    // Displaying cancel in alert Dialog
    saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    });

    saveDialog.show();
    }
  }
}