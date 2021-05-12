package com.abfresh.in;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.NewUtility;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.abfresh.in.Controller.SessionManagement.KEY_MOBILE;
import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

public class UpdateProfile extends AppCompatActivity {
    TextInputEditText update_username,update_email,update_Add,update_mobile;
    Button update_btn,chg_pass_btn_up;
    RadioGroup update_gender_rg;
    RadioButton radioButton;
    ImageView camera_img_update,upro_home_btn;
    CircleImageView update_account_img;
    private int REQUEST_CAMERA = 1, SELECT_FILE = 1;
    private String userChoosenTask;
    SessionManagement sessionManagement;
    String imagedelivered="",imageName;
    ProgressBar update_pb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile);
        sessionManagement = new SessionManagement(getApplicationContext());
        Toolbar toolbar=(Toolbar)findViewById(R.id.update_pro_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);

        update_username = (TextInputEditText)findViewById(R.id.update_username);
        update_email = (TextInputEditText)findViewById(R.id.update_email);
        update_mobile = (TextInputEditText)findViewById(R.id.update_mobile);
        update_mobile.setText(sessionManagement.getUserDetails().get(KEY_MOBILE));
        update_btn = (Button) findViewById(R.id.update_btn);
        chg_pass_btn_up = (Button) findViewById(R.id.chg_pass_btn_up);
        camera_img_update = (ImageView) findViewById(R.id.camera_img_update);
        update_account_img = (CircleImageView) findViewById(R.id.update_account_img);
//        update_gender_rg = (RadioGroup) findViewById(R.id.update_gender_rg);
        update_pb = (ProgressBar)findViewById(R.id.update_pb);
        upro_home_btn = (ImageView) findViewById(R.id.upro_home_btn);
        update_pb.setVisibility(View.GONE);
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(update_username.getText().toString().trim().length()==0){
                    Toast.makeText(UpdateProfile.this, "Please Enter Username", Toast.LENGTH_SHORT).show();


                }else if(update_mobile.getText().toString().trim().length()==0){
                    Toast.makeText(UpdateProfile.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();

                }else if(update_mobile.getText().toString().trim().length()!=10){
                    Toast.makeText(UpdateProfile.this, "Please Enter valid Mobile Number", Toast.LENGTH_SHORT).show();

                }else{
//                    finish();
//                    Toast.makeText(UpdateProfile.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                     UpdateMyProfile();
                }

            }
        });
          getMyProfile();
        camera_img_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();

            }
        });
        chg_pass_btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateProfile.this,ChangePassword.class);
                startActivity(intent);
            }
        });
        upro_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getMyProfile() {
        JSONObject gmpObject = new JSONObject();
        try {
        gmpObject.put("user_id",sessionManagement.getUserDetails().get(KEY_USERID));

        JsonObjectRequest gmpRequest = new JsonObjectRequest(Request.Method.POST, Utility.getProfile, gmpObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getInt("success")==1){
//                            Log.w("UTAG", String.valueOf(response));
                        if(response.getString("name").length()!=0){
                            update_username.setText(response.getString("name"));
                            update_email.setText(response.getString("email"));
                            update_mobile.setText(response.getString("mobile"));

                        }

                        if(response.getString("profile_img").length()!=0){
                            Picasso.with(UpdateProfile.this).load(response.getString("profile_img")).fit().into(update_account_img);

                        }
                        update_pb.setVisibility(View.GONE);

//                            Toast.makeText(UpdateProfile.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                    }else{
                        update_pb.setVisibility(View.GONE);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    update_pb.setVisibility(View.GONE);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                update_pb.setVisibility(View.GONE);

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> header= new HashMap<>();
                header.put(Utility.ServerUsername,Utility.ServerPassword);
                return header;
            }
        }; gmpRequest.setRetryPolicy(new DefaultRetryPolicy( 10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addRequestInQueue(gmpRequest);
    } catch (JSONException e) {
        e.printStackTrace();
        update_pb.setVisibility(View.GONE);

    }
}

    private void UpdateMyProfile() {

        JSONObject gmaObject = new JSONObject();
        try {
            update_pb.setVisibility(View.VISIBLE);
            gmaObject.put("user_id",sessionManagement.getUserDetails().get(KEY_USERID));
            gmaObject.put("name",update_username.getText().toString().trim());
            gmaObject.put("email",update_email.getText().toString().trim());
            gmaObject.put("mobile",update_mobile.getText().toString().trim());
            if(imagedelivered.equals("")){
                gmaObject.put("profile_img","");
            }else{
                gmaObject.put("profile_img",imagedelivered.toString().trim());

            }
            Log.w("UTAG==>", gmaObject+"");

            JsonObjectRequest gmaRequest = new JsonObjectRequest(Request.Method.POST, Utility.updateProfile, gmaObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.w("UTAG==>response", response+"");
                    try {
                        if(response.getInt("success")==1){

                            Toast.makeText(UpdateProfile.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            finish();
                            update_pb.setVisibility(View.GONE);
                            sessionManagement.updateProfile(update_username.getText().toString().trim(),update_email.getText().toString().trim(),update_mobile.getText().toString().trim());
                        }else{
                            update_pb.setVisibility(View.GONE);
                            Toast.makeText(UpdateProfile.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        update_pb.setVisibility(View.GONE);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    update_pb.setVisibility(View.GONE);
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> header= new HashMap<>();
                    header.put(Utility.ServerUsername,Utility.ServerPassword);
                    return header;
                }
            }; gmaRequest.setRetryPolicy(new DefaultRetryPolicy( 10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(gmaRequest);
        } catch (JSONException e) {
            e.printStackTrace();
            update_pb.setVisibility(View.GONE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case NewUtility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {



                        if(userChoosenTask.equals("Choose from Library"))
                            galleryIntent();
                        else
                            Log.w("GTAG","No selection");


                } else {
                    //code for deny
                }
                break;
        }
    }
    private void selectImage() {
        final CharSequence[] items = { "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=NewUtility.checkPermission(UpdateProfile.this);

//                if (items[item].equals("Take Photo")) {
//                    userChoosenTask ="Take Photo";
//                    if(result)
//                        cameraIntent();
//
//                } else
                    if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == SELECT_FILE)
//                onSelectFromGalleryResult(data);
//            else if (requestCode == REQUEST_CAMERA)
//                onCaptureImageResult(data);
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK ) {
            if (requestCode == SELECT_FILE) {
                //getting the image Uri
                Uri imageUri = data.getData();
                try {
                    //getting bitmap object from uri
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());

                    update_account_img.setImageBitmap(bitmap);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
                    byte[] imagenamedelivered = byteArrayOutputStream.toByteArray();

                    imagedelivered = Base64.encodeToString(imagenamedelivered, Base64.NO_WRAP);
                    imageName = "" + System.currentTimeMillis() + ".png";

                    Log.w("IMAGETAG", imagedelivered);
                    Log.w("IMAGETAG", imageName);
                    onSelectFromGalleryResult(data);
//                UpdateMyProfile();
                    //calling the method uploadBitmap to upload image


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    private void onCaptureImageResult(Intent data) {
//        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//
//        File destination = new File(Environment.getExternalStorageDirectory(),
//                System.currentTimeMillis() + ".jpg");
//
//        FileOutputStream fo;
//        try {
//            destination.createNewFile();
//            fo = new FileOutputStream(destination);
//            fo.write(bytes.toByteArray());
//            fo.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        update_account_img.setImageBitmap(thumbnail);
//    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        update_account_img.setImageBitmap(bm);
    }

//    public void checkButton(View v) {
//        int radioId = update_gender_rg.getCheckedRadioButtonId();
//        radioButton = findViewById(radioId);
////        Toast.makeText(this, "Selected Radio Button: " + radioButton.getText(),
////                Toast.LENGTH_SHORT).show();
//    }
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//
//            case android.R.id.home:
//                finish();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}
