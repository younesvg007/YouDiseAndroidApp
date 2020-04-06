package com.example.youdisenextlevel.Model.Database;

import android.content.ContentResolver;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ImageStorage {

    private static StorageReference mStorageRef;

    public static String addImage(Uri imageUri, String nameImage, OnSuccessListener<UploadTask.TaskSnapshot> onSuccessListenerCallback){
        mStorageRef = FirebaseStorage.getInstance().getReference("Product Images");

        StorageReference ref = mStorageRef.child(nameImage);

        ref
                .putFile(imageUri)
                .addOnSuccessListener(onSuccessListenerCallback);

        return nameImage;
    }

    public static String extention(ContentResolver ct, Uri imageUri){
        ContentResolver cr =  ct;
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(imageUri));
    }


    public static void getImage(String nameImage, ImageView img){
        mStorageRef = FirebaseStorage.getInstance().getReference("Product Images");
        StorageReference ref = mStorageRef.child(nameImage);
        ref.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(img));
    }
}
