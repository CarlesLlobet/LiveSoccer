package xyz.carlesllobet.livesoccer.UI;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import xyz.carlesllobet.livesoccer.DB.UserFunctions;
import xyz.carlesllobet.livesoccer.Domain.EquipSpinnerAdapter;
import xyz.carlesllobet.livesoccer.Domain.Objects.Equip;
import xyz.carlesllobet.livesoccer.Domain.Objects.Jugador;
import xyz.carlesllobet.livesoccer.R;

/**
 * Created by JEDI on 10/08/2015.
 */
public class NewEquipActivity extends AppCompatActivity implements View.OnClickListener {

    private UserFunctions uf;

    private TextView nom;
    private TextView ciutat;

    private ImageView escut;
    private ImageView pic;

    private Spinner spinnerEquip;

    private Button add;

    private Equip entra;
    private Equip surt;

    File bigImage;
    File smallImage;

    private EditText dorsal1, dorsal2, dorsal3, dorsal4, dorsal5, dorsal6, dorsal7, dorsal8, dorsal9, dorsal10, dorsal11, dorsal12;
    private EditText name1, name2, name3, name4, name5, name6, name7, name8, name9, name10, name11, name12;
    private CheckedTextView titular1, titular2, titular3, titular4, titular5, titular6, titular7, titular8, titular9, titular10, titular11, titular12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_equip);

        setTitle("Nou Equip");

        uf = new UserFunctions();

        File bigDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/../LiveSoccer/");
        String bigImageName = new SimpleDateFormat("ddMMyyy_HHmmss").format(new Date());
        bigImage = new File(bigDir, bigImageName + ".jpg");

        if (bigDir.mkdirs())
            Log.d("bigDir", "creat a: " + bigDir.getPath());
        else if (bigDir.exists())
            Log.d("bigDir", "existeix a: " + bigDir.getPath());

        File smallDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/../LiveSoccer/");
        String smallImageName = new SimpleDateFormat("ddMMyyy_HHmmss").format(new Date());
        smallImage = new File(smallDir, smallImageName + "_small.jpg");

        if(smallDir.mkdirs()) Log.d("smallDir", "creat a: " + smallDir.getPath());

        else if (smallDir.exists())
            Log.d("smallDir", "existeix a: " + smallDir.getPath());

        entra = new Equip();

        ArrayList<Equip> equips = uf.getEquips(getApplicationContext());

        spinnerEquip = (Spinner) findViewById(R.id.team);
        final EquipSpinnerAdapter equipSpinnerAdapter = new EquipSpinnerAdapter(NewEquipActivity.this, equips);
        spinnerEquip.setAdapter(equipSpinnerAdapter);

        escut = (ImageView) findViewById(R.id.escut);
        pic = (ImageButton) findViewById(R.id.pic);

        spinnerEquip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                surt = equipSpinnerAdapter.getItem(position);
                escut.setImageURI(surt.getEscut());
                entra.setEscut(surt.getEscut());
                pic.setImageURI(surt.getEscut());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Integer pos = extras.getInt("equip");
            if (pos != null) {
                surt = uf.getEquip(getApplicationContext(), pos);
                spinnerEquip.setSelection(pos);
                escut.setImageURI(surt.getEscut());
                entra.setEscut(surt.getEscut());
                pic.setImageURI(surt.getEscut());
            }
        }


        add = (Button) findViewById(R.id.afegirEquip);

        nom = (EditText) findViewById(R.id.equip);
        ciutat = (EditText) findViewById(R.id.city);

        name1 = (EditText) findViewById(R.id.name1);
        name2 = (EditText) findViewById(R.id.name2);
        name3 = (EditText) findViewById(R.id.name3);
        name4 = (EditText) findViewById(R.id.name4);
        name5 = (EditText) findViewById(R.id.name5);
        name6 = (EditText) findViewById(R.id.name6);
        name7 = (EditText) findViewById(R.id.name7);
        name8 = (EditText) findViewById(R.id.name8);
        name9 = (EditText) findViewById(R.id.name9);
        name10 = (EditText) findViewById(R.id.name10);
        name11 = (EditText) findViewById(R.id.name11);
        name12 = (EditText) findViewById(R.id.name12);

        dorsal1 = (EditText) findViewById(R.id.dorsal1);
        dorsal2 = (EditText) findViewById(R.id.dorsal2);
        dorsal3 = (EditText) findViewById(R.id.dorsal3);
        dorsal4 = (EditText) findViewById(R.id.dorsal4);
        dorsal4 = (EditText) findViewById(R.id.dorsal4);
        dorsal5 = (EditText) findViewById(R.id.dorsal5);
        dorsal6 = (EditText) findViewById(R.id.dorsal6);
        dorsal7 = (EditText) findViewById(R.id.dorsal7);
        dorsal8 = (EditText) findViewById(R.id.dorsal8);
        dorsal9 = (EditText) findViewById(R.id.dorsal9);
        dorsal10 = (EditText) findViewById(R.id.dorsal10);
        dorsal11 = (EditText) findViewById(R.id.dorsal11);
        dorsal12 = (EditText) findViewById(R.id.dorsal12);

        titular1 = (CheckedTextView) findViewById(R.id.titular1);
        titular2 = (CheckedTextView) findViewById(R.id.titular2);
        titular3 = (CheckedTextView) findViewById(R.id.titular3);
        titular4 = (CheckedTextView) findViewById(R.id.titular4);
        titular5 = (CheckedTextView) findViewById(R.id.titular5);
        titular6 = (CheckedTextView) findViewById(R.id.titular6);
        titular7 = (CheckedTextView) findViewById(R.id.titular7);
        titular8 = (CheckedTextView) findViewById(R.id.titular8);
        titular9 = (CheckedTextView) findViewById(R.id.titular9);
        titular10 = (CheckedTextView) findViewById(R.id.titular10);
        titular11 = (CheckedTextView) findViewById(R.id.titular11);
        titular12 = (CheckedTextView) findViewById(R.id.titular12);

        titular1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titular1.isChecked()) titular1.setChecked(false);
                else titular1.setChecked(true);
            }
        });

        titular2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titular2.isChecked()) titular2.setChecked(false);
                else titular2.setChecked(true);
            }
        });

        titular3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titular3.isChecked()) titular3.setChecked(false);
                else titular3.setChecked(true);
            }
        });

        titular4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titular4.isChecked()) titular4.setChecked(false);
                else titular4.setChecked(true);
            }
        });

        titular5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titular5.isChecked()) titular5.setChecked(false);
                else titular5.setChecked(true);
            }
        });

        titular6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titular6.isChecked()) titular6.setChecked(false);
                else titular6.setChecked(true);
            }
        });

        titular7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titular7.isChecked()) titular7.setChecked(false);
                else titular7.setChecked(true);
            }
        });

        titular8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titular8.isChecked()) titular8.setChecked(false);
                else titular8.setChecked(true);
            }
        });

        titular9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titular9.isChecked()) titular9.setChecked(false);
                else titular9.setChecked(true);
            }
        });

        titular10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titular10.isChecked()) titular10.setChecked(false);
                else titular10.setChecked(true);
            }
        });

        titular11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titular11.isChecked()) titular11.setChecked(false);
                else titular11.setChecked(true);
            }
        });

        titular12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titular12.isChecked()) titular12.setChecked(false);
                else titular12.setChecked(true);
            }
        });

        add.setOnClickListener(this);
        pic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pic:
                CharSequence escutPic[] = new CharSequence[]{"Galería", "Cámara"};

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.dialog1);
                builder.setItems(escutPic, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent pickPictureIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                pickPictureIntent.setType("image/*");
                                startActivityForResult(Intent.createChooser(pickPictureIntent, "Selecciona la imatge"), 0);
                                break;
                            case 1:
                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(bigImage));
                                    startActivityForResult(takePictureIntent, 1);
                                }
                                Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(takePicture, 1);
                                break;
                        }
                    }
                });
                builder.show();
                break;
            case R.id.afegirEquip:
                if (checkValues()) {
                    entra.setName(nom.getText().toString());
                    entra.setCiutat(ciutat.getText().toString());
                    uf.deleteEquip(getApplicationContext(), surt.getName());
                    uf.addTeam(getApplicationContext(), entra.getName(), entra.getEscut(), entra.getCiutat());

                    uf.addJugador(getApplicationContext(), name1.getText().toString(), Integer.valueOf(dorsal1.getText().toString()), entra.getName(), titular1.isChecked());
                    uf.addJugador(getApplicationContext(), name2.getText().toString(), Integer.valueOf(dorsal2.getText().toString()), entra.getName(), titular2.isChecked());
                    uf.addJugador(getApplicationContext(), name3.getText().toString(), Integer.valueOf(dorsal3.getText().toString()), entra.getName(), titular3.isChecked());
                    uf.addJugador(getApplicationContext(), name4.getText().toString(), Integer.valueOf(dorsal4.getText().toString()), entra.getName(), titular4.isChecked());
                    uf.addJugador(getApplicationContext(), name5.getText().toString(), Integer.valueOf(dorsal5.getText().toString()), entra.getName(), titular5.isChecked());
                    uf.addJugador(getApplicationContext(), name6.getText().toString(), Integer.valueOf(dorsal6.getText().toString()), entra.getName(), titular6.isChecked());
                    uf.addJugador(getApplicationContext(), name7.getText().toString(), Integer.valueOf(dorsal7.getText().toString()), entra.getName(), titular7.isChecked());
                    uf.addJugador(getApplicationContext(), name8.getText().toString(), Integer.valueOf(dorsal8.getText().toString()), entra.getName(), titular8.isChecked());
                    uf.addJugador(getApplicationContext(), name9.getText().toString(), Integer.valueOf(dorsal9.getText().toString()), entra.getName(), titular9.isChecked());
                    uf.addJugador(getApplicationContext(), name10.getText().toString(), Integer.valueOf(dorsal10.getText().toString()), entra.getName(), titular10.isChecked());
                    uf.addJugador(getApplicationContext(), name11.getText().toString(), Integer.valueOf(dorsal11.getText().toString()), entra.getName(), titular11.isChecked());
                    uf.addJugador(getApplicationContext(), name12.getText().toString(), Integer.valueOf(dorsal12.getText().toString()), entra.getName(), titular12.isChecked());
                    startActivity(new Intent(NewEquipActivity.this,HomeActivity.class));
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    try {

                        Uri photoUri = imageReturnedIntent.getData();
                        if (photoUri != null) Log.d("PICK_IMAGE", "URI:" + photoUri.getPath());
                        else Log.d("URI", "null");
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        // Get the cursor
                        Cursor cursor = getContentResolver().query(photoUri,
                                filePathColumn, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String imgDecodableString = cursor.getString(columnIndex);
                        cursor.close();

                        Bitmap imageBitmap = BitmapFactory.decodeFile(imgDecodableString);

                        getBigImage(imageBitmap, bigImage);
                        entra.setEscut(Uri.parse(bigImage.getAbsolutePath()));

                        Bitmap thumbnail = getSmallImage(imageBitmap, smallImage);
                        entra.setEscut(Uri.parse(smallImage.getAbsolutePath()));
                        pic.setImageBitmap(thumbnail);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    try {

                        String fullSizeImagePath = bigImage.getAbsolutePath();
                        entra.setEscut(Uri.parse(fullSizeImagePath));

                        Bitmap imageBitmap = BitmapFactory.decodeFile(fullSizeImagePath);

                        Bitmap thumbnail = getSmallImage(imageBitmap, smallImage);
                        getBigImage(imageBitmap,bigImage);
                        entra.setEscut(Uri.parse(smallImage.getAbsolutePath()));
                        pic.setImageBitmap(thumbnail);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    public void getBigImage(Bitmap imageBitmap, File fullSizeFile) {

        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;

        try {
            if( imageBitmap.getWidth() > width && imageBitmap.getHeight() > height) imageBitmap  = Bitmap.createScaledBitmap(imageBitmap,width, height,true);
            FileOutputStream image = new FileOutputStream(fullSizeFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 85, image);
            image.flush();
            image.close();
        } catch (IOException x) {
            System.err.format("IOException %s%n", x);
        }

    }

    public Bitmap getSmallImage(Bitmap imageBitmap, File thumbnailFile) {

        int width = Resources.getSystem().getDisplayMetrics().widthPixels;

        int height = Resources.getSystem().getDisplayMetrics().heightPixels;


        try {


            FileOutputStream image = new FileOutputStream(thumbnailFile);
            if (height / 5 < imageBitmap.getHeight() && width / 5 - 10 < imageBitmap.getWidth()) {
                Bitmap scaled = Bitmap.createScaledBitmap(imageBitmap, imageBitmap.getWidth() / 10, imageBitmap.getHeight() / 10 - 10, true);
                scaled.compress(Bitmap.CompressFormat.PNG, 75, image);

                image.flush();
                image.close();
                return scaled;
            } else {
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 75, image);
                image.flush();
                image.close();
                return imageBitmap;
            }
        } catch (IOException x) {
            System.err.format("IOException %s%n", x);
        }
        return null;
    }

    private Boolean checkValues() {
        if (nom.getText().toString().equals("")) {
            Toast.makeText(NewEquipActivity.this, "Especifica el nom del nou equip", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (ciutat.getText().toString().equals("")) {
            Toast.makeText(NewEquipActivity.this, "Especifica la ciutat del nou equip", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (name1.getText().toString().equals("") || name2.getText().equals("") || name3.getText().equals("") || name4.getText().equals("") || name5.getText().equals("") || name6.getText().equals("") || name7.getText().equals("") || name8.getText().equals("") || name9.getText().equals("") || name10.getText().equals("") || name11.getText().equals("") || name12.getText().equals("")) {
            Toast.makeText(NewEquipActivity.this, "Hi ha jugadors sense nom", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dorsal1.getText().toString().equals("") || dorsal2.getText().toString().equals("") || dorsal3.getText().toString().equals("") || dorsal4.getText().toString().equals("") || dorsal5.getText().toString().equals("") || dorsal6.getText().toString().equals("") || dorsal7.getText().toString().equals("") || dorsal8.getText().toString().equals("") || dorsal9.getText().toString().equals("") || dorsal10.getText().toString().equals("") || dorsal11.getText().toString().equals("") || dorsal12.getText().toString().equals("")) {
            Toast.makeText(NewEquipActivity.this, "Hi ha jugadors sense dorsal", Toast.LENGTH_SHORT).show();
            return false;
        }

        Integer titulars = 0;
        if (titular1.isChecked()) ++titulars;
        if (titular2.isChecked()) ++titulars;
        if (titular3.isChecked()) ++titulars;
        if (titular4.isChecked()) ++titulars;
        if (titular5.isChecked()) ++titulars;
        if (titular6.isChecked()) ++titulars;
        if (titular7.isChecked()) ++titulars;
        if (titular8.isChecked()) ++titulars;
        if (titular9.isChecked()) ++titulars;
        if (titular10.isChecked()) ++titulars;
        if (titular11.isChecked()) ++titulars;
        if (titular12.isChecked()) ++titulars;

        if (titulars != 5) {
            Toast.makeText(NewEquipActivity.this, "Hi ha d'haver 5 titulars", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Check copia dorsal o jugador
        ArrayList<EditText> dorsals= new ArrayList<EditText>(12);
        dorsals.add(dorsal1);
        dorsals.add(dorsal2);
        dorsals.add(dorsal3);
        dorsals.add(dorsal4);
        dorsals.add(dorsal5);
        dorsals.add(dorsal6);
        dorsals.add(dorsal7);
        dorsals.add(dorsal8);
        dorsals.add(dorsal9);
        dorsals.add(dorsal10);
        dorsals.add(dorsal11);
        dorsals.add(dorsal12);

        ArrayList<EditText> noms = new ArrayList<EditText>(12);
        noms.add(name1);
        noms.add(name2);
        noms.add(name3);
        noms.add(name4);
        noms.add(name5);
        noms.add(name6);
        noms.add(name7);
        noms.add(name8);
        noms.add(name9);
        noms.add(name10);
        noms.add(name11);
        noms.add(name12);

        for (int i = 0; i < 12; i++){
            for (int j = i+1; j < 12; j++){
                if (dorsals.get(i).getText().toString().equals(dorsals.get(j).getText().toString())){
                    Log.d("Dorsals:", i+":"+j);
                    Toast.makeText(NewEquipActivity.this, "Dos jugadors no poden tenir el mateix dorsal", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (noms.get(i).getText().toString().equals(noms.get(j).getText().toString())){
                    Log.d("Noms:", i+":"+j);
                    Toast.makeText(NewEquipActivity.this, "Dos jugadors no poden tenir el mateix nom", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}