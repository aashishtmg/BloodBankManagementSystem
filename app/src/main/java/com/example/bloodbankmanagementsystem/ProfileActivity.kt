package com.example.bloodbankmanagementsystem

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.*

@Suppress("UNREACHABLE_CODE", "DEPRECATION")
class ProfileActivity : AppCompatActivity() {
    private var REQUEST_GALLERY_CODE = 0
    private val REQUEST_IMAGE_CAPTURE = 100
    private var imageUrl: String? = null
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    private lateinit var db: FirebaseFirestore
    private lateinit var imageUri: Uri
    private lateinit var backhome: ImageView
    private lateinit var camera: ImageView
    private lateinit var btnsave: ImageView
    private lateinit var username: TextView
    private lateinit var eefullname: TextView
    private lateinit var eeemail: TextView
    private lateinit var address: TextView
    private lateinit var etphone: TextView
    private lateinit var profile_signout: TextView
    private lateinit var progressbar: ProgressBar
    private lateinit var progressbar_pic: ProgressBar

    @SuppressLint("SetText18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        backhome = findViewById(R.id.profile_back)
        camera = findViewById(R.id.profile_image)
        username = findViewById(R.id.profile_username)
        eefullname = findViewById(R.id.profile_name)
        address = findViewById(R.id.address)
        eeemail = findViewById(R.id.profile_email)
        etphone = findViewById(R.id.phonenumber)
        btnsave = findViewById(R.id.save)
        profile_signout = findViewById(R.id.profile_signout)
        progressbar = findViewById(R.id.progressbar)
        progressbar_pic = findViewById(R.id.progressbar_pic)


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("USERS")

        loadProfile()

        backhome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        camera.setOnClickListener {

            popupMenu()
        }

        profile_signout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

        btnsave.setOnClickListener {

            val photo = when {
                ::imageUri.isInitialized -> imageUri
                auth.currentUser?.photoUrl == null -> Uri.parse("https://picsum.photos/200")
                else -> auth.currentUser!!.photoUrl
            }

            val name = username.text.toString().trim()

            if (name.isEmpty()) {
                username.error = "name required"
                username.requestFocus()
                return@setOnClickListener
            }

            val updates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(photo)
                .build()

            progressbar.visibility = View.VISIBLE

            auth.currentUser?.updateProfile(updates)
                ?.addOnCompleteListener { task ->
                    progressbar.visibility = View.INVISIBLE
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, task.exception?.message!!, Toast.LENGTH_SHORT).show()
                    }
                }
        }

        auth.currentUser.let { user ->
            Glide.with(this)
                .load(user?.photoUrl)
                .into(camera)
        }
    }

    private fun loadProfile() {
        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        eeemail.text = user?.email

        userreference?.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                username.text = snapshot.child("username").value.toString()
                eefullname.text =
                    snapshot.child("fname").value.toString() + " " + snapshot.child("lname").value.toString()
                etphone.text = snapshot.child("phone").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun popupMenu() {
        val popupMenu = PopupMenu(this@ProfileActivity, camera)
        popupMenu.menuInflater.inflate(R.menu.gallery_camera, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.gallery ->
                    openGallery()
                R.id.camera ->
                    openCamera()
            }
            true
        }
        popupMenu.show()
    }

    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { pictureIntent ->
            pictureIntent.resolveActivity(this?.packageManager!!)?.also {
                startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            uploadImageAndSaveUri(imageBitmap)
        }
    }

    private fun uploadImageAndSaveUri(bitmap: Bitmap) {

        val baos = ByteArrayOutputStream()
        val storageRef = FirebaseStorage.getInstance()
            .reference
            .child("pics/${FirebaseAuth.getInstance().currentUser?.uid}")
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val image = baos.toByteArray()

        val upload = storageRef.putBytes(image)

        progressbar_pic.visibility = View.VISIBLE
        upload.addOnCompleteListener { uploadTask ->
            progressbar_pic.visibility = View.INVISIBLE

            if (uploadTask.isSuccessful) {
                storageRef.downloadUrl.addOnCompleteListener { urlTask ->
                    urlTask.result?.let {
                        imageUri = it
                        Toast.makeText(this, imageUri.toString(), Toast.LENGTH_SHORT).show()
                        camera.setImageBitmap(bitmap)
                    }
                }
            } else {
                uploadTask.exception?.let {
                    Toast.makeText(this, it.message!!, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


