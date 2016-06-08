package com.lykkex.LykkeWallet.gui.fragments.camerascreen;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication;
import com.lykkex.LykkeWallet.gui.customviews.StepsIndicator;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.managers.UserManager;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.widgets.DialogProgress;
import com.lykkex.LykkeWallet.rest.camera.callback.SendDocumentsDataCallback;
import com.lykkex.LykkeWallet.rest.camera.request.models.CameraModel;
import com.lykkex.LykkeWallet.rest.camera.request.models.CameraType;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import retrofit2.Call;

/**
 * Created by Murtic on 31/05/16.
 */
@EFragment
public abstract class CameraBaseFragment extends BaseFragment {

    protected static final int REQUEST_IMAGE_CAPTURE = 101;
    protected static final int REQUEST_CAMERA_PERMISSION = 102;
    protected static final int REQUEST_GALLERY_ACTION = 103;
    protected static final int REQUEST_READ_GALLERY_PERMISSION = 104;

    @Bean
    UserManager userManager;

    @ViewById
    ImageView imageView;

    @ViewById
    Button buttonShoot;

    @ViewById
    Button buttonAction;

    @ViewById
    StepsIndicator stepsIndicator;

    @App
    LykkeApplication lykkeApplication;

    protected ProgressDialog dialog;

    protected DialogProgress progressDialog = new DialogProgress();

    @AfterViews
    void afterViews() {
        stepsIndicator.setCurrentStep(2);

        userManager.setCameraModel(new CameraModel());

        CameraModel model = new CameraModel();
        model.setExt(Constants.JPG);
        model.setType(CameraType.Selfie.toString());

        userManager.setCameraModel(model);

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage(getString(R.string.waiting));
    }

    protected void setCameraType(CameraType cameraType) {
        userManager.getCameraModel().setType(cameraType.toString());

        if(getActivity().getIntent().hasExtra(userManager.getCameraModel().getType())) {
            Uri imageUri = getActivity().getIntent().getParcelableExtra(userManager.getCameraModel().getType());

            if(imageUri != null) {
                applyImage(imageUri);
            }
        }
    }

    @Click(R.id.buttonAction)
    public void onSubmit() {
        sendImage();
    }

    @Click({ R.id.buttonShoot, R.id.buttonShootContainer })
    public void onShoot() {
        dispatchTakePictureIntent();
    }

    @Click({R.id.buttonFile, R.id.buttonFileContainer})
    public void onOpenFile() {
        dispatchLoadPictureIntent();
    }

    @Click({ R.id.imageView })
    public void onClickImageView() {
        if(userManager.getCameraModel().getData() == null) {
            dispatchTakePictureIntent();
        }
    }

    private void dispatchLoadPictureIntent() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_READ_GALLERY_PERMISSION);

                return;
            }
        }

        Intent intent = null;

        // Fix for permission issue on kitkat
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }

        intent.setType("image/*");

        startActivityForResult(intent, REQUEST_GALLERY_ACTION);
    }

    private void dispatchTakePictureIntent() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA_PERMISSION);

                return;
            }
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            File photo = this.createTemporaryFile(userManager.getCameraModel().getType(), ".jpg");

            getActivity().getIntent().putExtra(userManager.getCameraModel().getType(), Uri.fromFile(photo));
            photo.delete();

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        } catch (IOException e) {
            Log.e(CameraBaseFragment.class.getSimpleName(), "Error while creating temporary file: " + userManager.getCameraModel().getType() + ".jpg", e);
        }

        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = getActivity().getIntent().getParcelableExtra(userManager.getCameraModel().getType());

            if(imageUri != null) {
                applyImage(imageUri);
            }
        } else if(requestCode == REQUEST_GALLERY_ACTION && resultCode == Activity.RESULT_OK) {
            if(resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();

                getActivity().getIntent().putExtra(userManager.getCameraModel().getType(), uri);

                if(uri != null) {
                    applyImage(uri);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent();
                }
                break;
            case REQUEST_READ_GALLERY_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchLoadPictureIntent();
                }
                return;
            }
        }
    }

    abstract protected void sendImage();

    public void showProgress(Call call, SendDocumentsDataCallback callback){
        progressDialog.setUpCurrentCall(call, callback);
        progressDialog.show(getActivity().getFragmentManager(),
                    "dlg1" +new Random((int)Constants.DELAY_5000));
    }

    @Background
    void applyImage(Uri imageUri) {
        int orientation = getOrientation(getActivity(), imageUri);

        try {
            final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);

            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

            selectedImage = rotateBitmap(selectedImage, orientation);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            selectedImage.compress(Bitmap.CompressFormat.JPEG, 85, stream);
            byte[] byteArray = stream.toByteArray();

            userManager.getCameraModel().setData(Base64.encodeToString(byteArray, Base64.DEFAULT));

            setImage(selectedImage);
        } catch (FileNotFoundException e) {
            Log.e(CameraBaseFragment.class.getSimpleName(), "Error while loading image file: " + imageUri.toString(), e);
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            Log.e(CameraBaseFragment.class.getSimpleName(), "Error while rotating bitmap.", e);

            return null;
        }
    }

    @UiThread
    void setImage(Bitmap imageBitmap) {
        imageView.setImageBitmap(imageBitmap);
        imageView.setPadding(0, 0, 0, 0);

        buttonAction.setEnabled(true);
    }

    private File createTemporaryFile(String part, String ext) throws IOException
    {
        File tempDir= Environment.getExternalStorageDirectory();
        tempDir=new File(tempDir.getAbsolutePath()+"/.temp/");
        if(!tempDir.exists())
        {
            tempDir.mkdirs();
        }
        return File.createTempFile(part, ext, tempDir);
    }

    public static int getOrientation(Context context, Uri photoUri) {
        File file = new File(photoUri.getPath());

        try {
            ExifInterface exif = new ExifInterface(file.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            return orientation;
        } catch (IOException e) {
            Log.e(CameraBaseFragment.class.getSimpleName(), "Error while loading exif info: " + photoUri, e);
        }

        return -1;
    }
}
